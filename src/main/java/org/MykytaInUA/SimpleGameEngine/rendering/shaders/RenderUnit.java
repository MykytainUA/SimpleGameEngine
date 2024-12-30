package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.IndexedVertexMesh;
import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.MeshComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.SolidColorComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.PositionComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.RotationComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.SizeComponent;
import org.MykytaInUA.SimpleGameEngine.rendering.EBOWrapper;
import org.MykytaInUA.SimpleGameEngine.rendering.VAOWrapper;
import org.MykytaInUA.SimpleGameEngine.rendering.VBOStorage;
import org.MykytaInUA.SimpleGameEngine.rendering.VBOWrapper;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

public class RenderUnit {
	
	private int boundShaderID;
	private final VAOWrapper VAO;
	private final VBOStorage VBO;
	private final EBOWrapper EBO;
	private int objectsCount;
	private int indicesCount = 0;
	
	private ObjectToDataConverter objectToBufferConverter = new ObjectToDataConverter();
	
	public RenderUnit(int boundShaderID, int vbosCount) {
		
		this.boundShaderID = boundShaderID;
		
		VAO = new VAOWrapper();
		VBO = new VBOStorage(vbosCount);
		EBO = new EBOWrapper();
	}
	
	public VAOWrapper getVAO() {
		return VAO;
	}
	
	public VBOStorage getVBOStorage() {
		return VBO;
	}
	
	public EBOWrapper getEBO() {
		return EBO;
	}
	
	public int getObjectsCount() {
		return objectsCount;
	}
	
	public void sendObjectsToGPU(Object3D[] objects, List<Component> components) {
		objectsCount += objects.length;
		
		this.getVAO().bindVertexArray();
		
		this.sendMeshData(objects);
		
		for(Component component : components) {
			// Send data to GPU
			FloatBuffer buf = this.objectToBufferConverter.getComponentsAsFloatBuffer(component.getClass(), objects);
			buf.rewind();
			
			if(component.getClass() == PositionComponent.class) {		
				this.sendVBOFloatVectorInstancedData(1, PositionComponent.ATTRIBUTE_POINTER_NAME, component.getDataPerVertexSize(), buf);	
			} else if(component.getClass() == RotationComponent.class) {
				
				this.sendVBOFloatVectorInstancedData(2, RotationComponent.ATTRIBUTE_POINTER_NAME, component.getDataPerVertexSize(), buf);
			
			} else if(component.getClass() == SizeComponent.class) {
				
				this.sendVBOFloatVectorInstancedData(3, SizeComponent.ATTRIBUTE_POINTER_NAME, component.getDataPerVertexSize(), buf);
				
			} else if(component.getClass() == SolidColorComponent.class) {
				
				this.sendVBOFloatVectorInstancedData(4, SolidColorComponent.ATTRIBUTE_POINTER_NAME, component.getDataPerVertexSize(), buf);
			} 
		}
		
		this.unbindBuffers();
	}
	
	private void bindThisRenderUnit() {
		this.getVAO().bindVertexArray();
	}
	
	// render this RenderUnit
	public void render() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		this.bindThisRenderUnit();
		
		// Print cubes
		gl.glDrawElementsInstanced(GL_TRIANGLES, indicesCount, GL_UNSIGNED_INT, 0, this.getObjectsCount());
		
		this.unbindBuffers();
	}
	
	public void sendVBOFloatVectorInstancedData(int VBOIndex, String attribPointerName, int size, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVectorInstancedData(boundShaderID, attribPointerName, size, dataBuffer);
	}
	
	public void sendVBOFloatVectorData(int VBOIndex, String attribPointerName, int size, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVectorData(boundShaderID, attribPointerName, size, dataBuffer);
	}
	
	public void sendEBOStaticIntegerData(int renderUnitIndex, IntBuffer dataBuffer) {
		this.getEBO().sendStaticIntegerData(dataBuffer);
	}
	
	public void sendMeshData(Object3D[] objects) {
		// Send vertices
		FloatBuffer vertexBuffer = this.objectToBufferConverter.getComponentsAsFloatBuffer(IndexedVertexMesh.class, objects);			
		// Set vertices
		this.sendVBOFloatVectorData(0, IndexedVertexMesh.ATTRIBUTE_POINTER_NAME, 3, vertexBuffer);
		
		IntBuffer indicesBuffer = this.objectToBufferConverter.getMeshIntData(IndexedVertexMesh.class, objects);	
		indicesCount = indicesBuffer.capacity();
		// Set indices 
		this.sendEBOStaticIntegerData(0, indicesBuffer);
	}
	
	public void unbindBuffers() {
		this.unbindVBO();
		this.unbindVAO();
		this.unbindEBO();
	}
	
	public void unbindVBO() {
		VBOWrapper.unbindBuffer();
	}
	
	public void unbindVAO() {		
		VAOWrapper.unbindVertexArray();
	}

	public void unbindEBO() {
		EBOWrapper.unbindBuffer();
	}
}

/**
 * Buffer data is not saved after resizing
 */
class ObjectToDataConverter {
	
	ByteBuffer currentBuffer = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder());

	public ObjectToDataConverter() {
		super();
	}
	
	public <T extends Component> FloatBuffer getComponentsAsFloatBuffer(Class<T> componentType, Object3D[] objects) {
		
		this.checkForNull(componentType, objects);
		
		if(MeshComponent.class.isAssignableFrom(componentType)) {
			T componentExample = (T) objects[0].getComponentByClass(componentType);
			
			this.ensureBufferCapacity(componentExample.getTotalDataSize());
			
			T component = (T) objects[0].getComponentByClass(componentType);
	        component.writeComponentDataToBuffer(currentBuffer);
	        
	        this.currentBuffer.rewind();
			
	        return this.currentBuffer.asFloatBuffer();
		}
		
		T componentExample = (T) objects[0].getComponentByClass(componentType);
		this.ensureBufferCapacity(objects.length * componentExample.getTotalDataSize());
		
		for (Object3D object : objects) {
	        T component = (T) object.getComponentByClass(componentType);
	        component.writeComponentDataToBuffer(currentBuffer);
	    }
		
		this.currentBuffer.rewind();
	    return this.currentBuffer.asFloatBuffer();
	}
	
	public <T extends Component> IntBuffer getMeshIntData(Class<T> componentType, Object3D[] objects) {
		
		if(componentType == IndexedVertexMesh.class) {
			IndexedVertexMesh mesh = (IndexedVertexMesh) objects[0].getComponentByClass(componentType);
			this.ensureBufferCapacity(mesh.getIndices().length * 4);
			
			for (int i = 0; i < mesh.getIndices().length; i++) {
				this.currentBuffer.putInt(mesh.getIndices()[i]);
			}
		}
		
		this.currentBuffer.rewind();
		
		return this.currentBuffer.asIntBuffer();
	}
	
	/**
	 * @param neededCapacity capacity in bytes that buffer must have
	 * If not enough memory is available allocates new memory in buffer
	 */
	private void ensureBufferCapacity(int requiredCapacity) {
		if(!bufferHasEnoughCapacity(requiredCapacity)) {	
			this.currentBuffer = ByteBuffer.allocateDirect(requiredCapacity).order(ByteOrder.nativeOrder());
		}
		
		this.currentBuffer.rewind();
	}
	
	/**
	 * @param neededCapacity capacity in bites that buffer must have
	 * @return boolean true if buffer has enough memory false otherwise
	 */
	private boolean bufferHasEnoughCapacity(int neededCapacity) {
		return currentBuffer.capacity() >= neededCapacity;
	}
	
	private void checkForNull(Class componentType, Object3D[] objects) {
		if (objects == null || objects.length == 0) {
		    throw new IllegalArgumentException("Object array cannot be null or empty.");
		}
		if (componentType == null) {
		    throw new IllegalArgumentException("Component type cannot be null.");
		}
	}
}

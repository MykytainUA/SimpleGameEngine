package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

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
	private IndexedVertexMesh currentMesh = null;
	
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
	
	public void sendObjectsToGPU(Object3D[] objects, Component[] components) {
		objectsCount += objects.length;
		
		this.getVAO().bindVertexArray();

		float[] arrayData3 = new float[objects.length * 3];
		float[] arrayData4 = new float[objects.length * 4];	
		
		currentMesh = null;
		if(objects[0].getComponentBySuperClass(MeshComponent.class) instanceof IndexedVertexMesh) {
			currentMesh = (IndexedVertexMesh)objects[0].getComponentBySuperClass(MeshComponent.class);
		}
		
		// Iterate over components
		for (int i = 0; i < components.length; i++) {
			
			// Iterate over objects
			for (int j = 0; j < objects.length; j++) {
				if(components[i].getClass() == PositionComponent.class) {
					
					PositionComponent positionComponent = (PositionComponent) objects[j].getComponentByClass(PositionComponent.class);
					arrayData3[j * positionComponent.getDataPerVertexSize()] = positionComponent.getPosition().x;
					arrayData3[j * positionComponent.getDataPerVertexSize() + 1] = positionComponent.getPosition().y;
					arrayData3[j * positionComponent.getDataPerVertexSize() + 2] = positionComponent.getPosition().z;
					
				} else if (components[i].getClass() == RotationComponent.class) {
					
					RotationComponent rotationComponent = (RotationComponent) objects[j].getComponentByClass(RotationComponent.class);
					arrayData3[j * rotationComponent.getDataPerVertexSize()] = rotationComponent.getRotation().x;
					arrayData3[j * rotationComponent.getDataPerVertexSize() + 1] =  rotationComponent.getRotation().y;
					arrayData3[j * rotationComponent.getDataPerVertexSize() + 2] =  rotationComponent.getRotation().z;
					
				} else if (components[i].getClass() == SolidColorComponent.class) {
					
					SolidColorComponent solidColorComponent = (SolidColorComponent) objects[j].getComponentByClass(SolidColorComponent.class);
					arrayData4[j * solidColorComponent.getDataPerVertexSize()] = solidColorComponent.getColor().x;
					arrayData4[j * solidColorComponent.getDataPerVertexSize() + 1] = solidColorComponent.getColor().y;
					arrayData4[j * solidColorComponent.getDataPerVertexSize() + 2] = solidColorComponent.getColor().z;
					arrayData4[j * solidColorComponent.getDataPerVertexSize() + 3] = solidColorComponent.getColor().w;
					
				} else if (components[i].getClass() == SizeComponent.class) {
					
					SizeComponent sizeComponent = (SizeComponent) objects[j].getComponentByClass(SizeComponent.class);
					arrayData3[j * sizeComponent.getDataPerVertexSize() + 0] = sizeComponent.getSize().x;
					arrayData3[j * sizeComponent.getDataPerVertexSize() + 1] =  sizeComponent.getSize().y;
					arrayData3[j * sizeComponent.getDataPerVertexSize() + 2] =  sizeComponent.getSize().z;
					
				}
			}
			
			FloatBuffer floatBufferData;
			
			// Set vertices
			this.sendVBOFloatVector3Data(0, "vertices",  currentMesh.getVertices());
			
			// Set indices 
			this.sendEBOStaticIntegerData(0, currentMesh.getIndices());
			
			// Send data to GPU
			if(components[i].getClass() == PositionComponent.class) {
				floatBufferData = FloatBuffer.wrap(arrayData3);
				this.sendVBOFloatVector3InstancedData(1, PositionComponent.ATTRIBUTE_POINTER_NAME, floatBufferData);
			} else if(components[i].getClass() == RotationComponent.class) {
				floatBufferData = FloatBuffer.wrap(arrayData3);
				this.sendVBOFloatVector3InstancedData(2, RotationComponent.ATTRIBUTE_POINTER_NAME, floatBufferData);
			} else if(components[i].getClass() == SizeComponent.class) {
				floatBufferData = FloatBuffer.wrap(arrayData3);
				this.sendVBOFloatVector3InstancedData(4, SizeComponent.ATTRIBUTE_POINTER_NAME, floatBufferData);
			} else if(components[i].getClass() == SolidColorComponent.class) {
				floatBufferData = FloatBuffer.wrap(arrayData4);
				this.sendVBOFloatVector4InstancedData(3, SolidColorComponent.ATTRIBUTE_POINTER_NAME, floatBufferData);
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
		gl.glDrawElementsInstanced(GL_TRIANGLES, currentMesh.getIndices().length, GL_UNSIGNED_INT, 0, this.getObjectsCount());
		
		this.unbindBuffers();
	}
	
	// Functions for transferring data
	public void sendVBOFloatVector4Data(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector4Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector4Data(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector4Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector4InstancedData(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendFloatVector4InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector4InstancedData(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendFloatVector4InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector3Data(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector3Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector3Data(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector3Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector3InstancedData(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendFloatVector3InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector3InstancedData(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendFloatVector3InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector2Data(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector2Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector2Data(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector2Data(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector2InstancedData(int VBOIndex, String attribPointerName, float[] dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector2InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendVBOFloatVector2InstancedData(int VBOIndex, String attribPointerName, FloatBuffer dataBuffer) {
		VBOWrapper vboWrapper = this.getVBOStorage().getVBOWrapper(VBOIndex);
		vboWrapper.sendVBOFloatVector2InstancedData(boundShaderID, attribPointerName, dataBuffer);
	}
	
	public void sendEBOStaticIntegerData(int renderUnitIndex, int[] dataBuffer) {
		this.getEBO().sendStaticIntegerData(dataBuffer);
	}
	
	public void sendEBOStaticIntegerData(int renderUnitIndex, IntBuffer dataBuffer) {
		this.getEBO().sendStaticIntegerData(dataBuffer);
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

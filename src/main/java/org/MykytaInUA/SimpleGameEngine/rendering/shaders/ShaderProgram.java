package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.ObjectsStorage;
import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.IndexedVertexMesh;
import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.MeshComponent;
import org.MykytaInUA.SimpleGameEngine.rendering.EBOWrapper;
import org.MykytaInUA.SimpleGameEngine.rendering.VAOWrapper;
import org.MykytaInUA.SimpleGameEngine.rendering.VBOWrapper;
import org.MykytaInUA.SimpleGameEngine.utilities.Utils;

public class ShaderProgram {
	
	private final Shader SHADER;
	private RenderUnit[] renderUnits;
	private int boundRenderUnitIndex = -1;
	
	private ObjectsStorage<Object3D> objects;
	
	private Camera camera;
	
	private final UniformsStorage uniformsStorage;
	
	public ShaderProgram(String vertexShaderPath, 
						 String fragmentShaderPath,  
						 int renderUnitsCount,
						 int vbosCountPerUnit) {
		
		this.SHADER = ShaderAssembler.getShaderByPath(vertexShaderPath, fragmentShaderPath, null);
		uniformsStorage = new UniformsStorage(this.SHADER.getShaderID());
		
		renderUnits = new RenderUnit[renderUnitsCount];
		for (int i = 0; i < renderUnitsCount; i++) {
			renderUnits[i] = new RenderUnit(this.SHADER.getShaderID(), vbosCountPerUnit);
		}

	}
	
	public void addObjects(ObjectsStorage<Object3D> objects) {
		this.objects = objects;
	}
	
	public void addCamera(Camera camera) {
		this.camera = camera;
	}
	
	public int getProgramID() {
		
		return this.SHADER.getShaderID();
	}
	
	public RenderUnit getBoundRenderUnit() {
		return renderUnits[boundRenderUnitIndex];
	}
	
	public void initRenderUnit(int renderUnitIndex, ObjectsStorage<Object3D> storage) {
		
	}
	
	/*
	 * Low level functions
	 */
	
	
	public void bindRenderUnit(int index) {
		renderUnits[index].getVAO().bindVertexArray();
		this.boundRenderUnitIndex = index;
	}
	
	public void bindEBO(int index) {
		
		renderUnits[index].getEBO().bindBuffer();
	}
	
	public void bufferFloatVBOData(float[] data, int usage) {
		
		VBOWrapper.bufferFloatData(data, usage);
	}
	
	public void bufferFloatVBOData(FloatBuffer data, int usage) {
		
		VBOWrapper.bufferFloatData(data, usage);
	}
	
	public void bufferIntEBOData(int[] data, int usage) {
		
		EBOWrapper.bufferIntData(data, usage); // 0 temporary cause bufferIntData(data, usage) must be class function
	}
	
	public void bufferIntEBOData(IntBuffer data, int usage) {
		
		EBOWrapper.bufferIntData(data, usage);
	}
	
	public void vertexAttribVBOPointer(String name,
									   int size,
									   int type,
						               boolean normalized,
						               int stride,
						               long pointerBufferOffset) {
		
		VBOWrapper.vertexAttribPointer(SHADER.getShaderID(), name, size, type, normalized, stride, pointerBufferOffset);
	}
	
	public void enableVertexAttribArray(String name) {
		
		VBOWrapper.enableVertexAttribArray(SHADER.getShaderID(), name);
	}
	
	public void vertexAttribVBODivisor(String name, int divisor) {
		
		VBOWrapper.vertexAttribDivisor(SHADER.getShaderID(), name, divisor);
	}
	
	public int getUniformLocation(String name) {
		
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		return gl.glGetUniformLocation(SHADER.getShaderID(), name);
	}
	
	// This function must render all RenderUnits
	public void render() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glUseProgram(this.getProgramID());
		
		this.uniformsStorage.updateUniformMatrix4f("v_matrix", camera.getLookAtMatrix());
		this.uniformsStorage.updateUniformMatrix4f("p_matrix", camera.getPerspectiveMatrix());	
		
		this.bindRenderUnit(0);
		
		// Print cubes
		if(objects.getObjects()[0].getComponents().getComponentBySuperClass(MeshComponent.class) instanceof IndexedVertexMesh) {
			IndexedVertexMesh currentMesh = (IndexedVertexMesh)objects.getObjects()[0].getComponents().getComponentBySuperClass(MeshComponent.class);
			gl.glDrawElementsInstanced(GL_TRIANGLES, currentMesh.getIndices().length, GL_UNSIGNED_INT, 0, objects.getObjects().length);
		}
		
		this.getBoundRenderUnit().unbindBuffers();		
	}
	
	public void prepareShaderPrograms() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		IndexedVertexMesh currentMesh = null;
		if(objects.getObjects()[0].getComponents().getComponentBySuperClass(MeshComponent.class) instanceof IndexedVertexMesh) {
			currentMesh = (IndexedVertexMesh)objects.getObjects()[0].getComponents().getComponentBySuperClass(MeshComponent.class);
		}
		
		this.bindRenderUnit(0);
		
		// Set vertices
		this.getBoundRenderUnit().sendVBOFloatVector3Data(0, "vertices",  currentMesh.getVertices());
		
		this.getBoundRenderUnit().sendVBOFloatVector3InstancedData(1, "instancePosition", objects.getObjectsPositions().getBuffer());
		this.getBoundRenderUnit().sendVBOFloatVector3InstancedData(2, "instanceRotation", objects.getObjectsRotations().getBuffer());
		this.getBoundRenderUnit().sendVBOFloatVector3InstancedData(4, "instanceSize", objects.getObjectsSizes().getBuffer());
		this.getBoundRenderUnit().sendVBOFloatVector4InstancedData(3, "instanceColor", objects.getObjectsColors().getBuffer());
		
		// Set indices 
		this.getBoundRenderUnit().sendEBOStaticIntegerData(0, currentMesh.getIndices());
		
		this.getBoundRenderUnit().unbindBuffers();	
		
	}
	
}

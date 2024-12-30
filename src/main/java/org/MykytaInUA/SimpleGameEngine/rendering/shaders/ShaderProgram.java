package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class ShaderProgram {

	private final Shader SHADER;
	private ArrayList<RenderUnit> renderUnits;
	private Camera camera;
	private final UniformsStorage uniformsStorage;
	
	public ShaderProgram(String vertexShaderPath, 
						 String fragmentShaderPath,  
						 List<Component> componentsForShader,
						 int vbosCountPerUnit) {
		
		this.SHADER = ShaderAssembler.getShaderByPath(vertexShaderPath, fragmentShaderPath, componentsForShader);
		
		this.uniformsStorage = new UniformsStorage(this.SHADER.getShaderID());
		
		this.renderUnits = new ArrayList<RenderUnit>();
		
		this.renderUnits.add(new RenderUnit(this.SHADER.getShaderID(), vbosCountPerUnit));
	}
	
	public void addObjects(Object3D[] objects) {	
		this.renderUnits.get(0).sendObjectsToGPU(objects, SHADER.getComponents());	
	}
	
	public void addCamera(Camera camera) {
		this.camera = camera;
	}
	
	public int getProgramID() {
		return this.SHADER.getShaderID();
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
		
		this.renderUnits.get(0).render();	
	}
}

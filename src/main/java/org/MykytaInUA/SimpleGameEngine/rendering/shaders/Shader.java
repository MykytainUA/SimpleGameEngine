package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.util.List;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.utilities.Utils;

public class Shader {
	
	private int shaderID = -1;
	private final List<Component> components;
	
	/**
	 * Create shader from file
	 * @param vertexShaderPath path to vertex shader
	 * @param fragmentShaderPath path to fragment shader
	 */
	public Shader(String vertexShaderPath, String fragmentShaderPath, List<Component> components) {
		
		this.components = components;
		this.createShaderByPath(vertexShaderPath, fragmentShaderPath);
	}
	
	/**
	 * Create shader from source
	 * @param vertexShaderPath source of a vertex shader
	 * @param fragmentShaderPath source of a fragment shader
	 */
	public Shader(String[] vertexShaderPath, String[] fragmentShaderPath, List<Component> components) {
		
		this.components = components;
		this.createShaderBySource(vertexShaderPath, fragmentShaderPath);
	}
	
	public int getShaderID() {
		return this.shaderID;
	}
	
	public List<Component> getComponents() {	
		return this.components;
	}
	
	private void createShaderByPath(String vertexShaderPath, String fragmentShaderPath) {
		int vertexShader, fragmentShader;
		
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		String[] vertexShaderSource = ShaderUtilities.getShaderSourceAsString(vertexShaderPath);
		String[] fragmentShaderSource = ShaderUtilities.getShaderSourceAsString(fragmentShaderPath);
		
		vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
		this.shaderID = gl.glCreateProgram();
		
		gl.glShaderSource(vertexShader, vertexShaderSource.length, vertexShaderSource, null);
		gl.glCompileShader(vertexShader);
		Utils.printShaderLog(vertexShader);
		
		gl.glShaderSource(fragmentShader, fragmentShaderSource.length, fragmentShaderSource, null);
		gl.glCompileShader(fragmentShader);
		Utils.printShaderLog(fragmentShader);
		
		gl.glAttachShader(this.shaderID, vertexShader);
		gl.glAttachShader(this.shaderID, fragmentShader);
		
		gl.glLinkProgram(this.shaderID);
		Utils.printProgramLog(this.shaderID);
		
		gl.glDetachShader(this.shaderID, vertexShader);
		gl.glDetachShader(this.shaderID, fragmentShader);
		
		gl.glDeleteShader(vertexShader);
		gl.glDeleteShader(fragmentShader);
	}
	
	private void createShaderBySource(String[] vertexShaderSource, String[] fragmentShaderSource) {
		int vertexShader, fragmentShader;
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		
		vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
		fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);
		this.shaderID = gl.glCreateProgram();
		
		gl.glShaderSource(vertexShader, vertexShaderSource.length, vertexShaderSource, null);
		gl.glCompileShader(vertexShader);
		Utils.printShaderLog(vertexShader);
		
		gl.glShaderSource(fragmentShader, fragmentShaderSource.length, fragmentShaderSource, null);
		gl.glCompileShader(fragmentShader);
		Utils.printShaderLog(fragmentShader);
		
		gl.glAttachShader(this.shaderID, vertexShader);
		gl.glAttachShader(this.shaderID, fragmentShader);
		
		gl.glLinkProgram(this.shaderID);
		Utils.printProgramLog(this.shaderID);
		
		gl.glDetachShader(this.shaderID, vertexShader);
		gl.glDetachShader(this.shaderID, fragmentShader);
		
		gl.glDeleteShader(vertexShader);
		gl.glDeleteShader(fragmentShader);
	}
}

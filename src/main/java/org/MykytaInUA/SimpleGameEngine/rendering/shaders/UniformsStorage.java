package org.MykytaInUA.SimpleGameEngine.rendering.shaders;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.MykytaInUA.SimpleGameEngine.utilities.Utils;
import org.joml.Matrix4f;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import org.MykytaInUA.SimpleGameEngine.utilities.Utils;

public class UniformsStorage {
	
	private final Map<String, Integer> uniformMap;
	private FloatBuffer vals = Buffers.newDirectFloatBuffer(16);
	private final int boundShaderID;
	
	public UniformsStorage(int shaderID) {
		boundShaderID = shaderID;
		uniformMap = new HashMap<String, Integer>();
	}
	
	public int getProgramUniformLocation(String name) {
		Integer uniformLocation = this.uniformMap.get(name);
		
		// Uniform exists in Map
		if(uniformLocation != null) {
			
			return uniformLocation;
			
		} else { // Uniform location is not found
			
			uniformLocation = this.getUniformLocation(name);
			
		}
		
		// Add new uniform location
		if(uniformLocation > 0) {
			
			this.uniformMap.put(name, this.getUniformLocation(name));
			
		}
		
		// Returns either new uniform or -1 if nothing is found
		return uniformLocation;
	}
	
	public int getUniformLocation(String name) {
		
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		return gl.glGetUniformLocation(boundShaderID, name);
	}
	
	public void updateUniformMatrix4f(String uniformName, Matrix4f matrix) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glUniformMatrix4fv(this.getProgramUniformLocation(uniformName),
				1, 
				false,
				matrix.get(vals));
		
		Utils.checkOpenGLErrors();
	}
}

package org.MykytaInUA.SimpleGameEngine.rendering;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import org.MykytaInUA.SimpleGameEngine.utilities.Utils;

/**
 * Class for storing VAO as array
 */
public class VAOWrapper {
	
	private int vao;
	
	public VAOWrapper() {
		this.genVertexArray();
	}
	
	private void genVertexArray() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int[] vaoID = new int[1];
		
		gl.glGenVertexArrays(1, vaoID, 0);
		
		this.vao = vaoID[0];
		
		Utils.checkOpenGLErrors();
	}
	
	/**
	 * @param index index of vertex array
	 */
	public void bindVertexArray() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glBindVertexArray(vao);
		
		Utils.checkOpenGLErrors();
	}
	
	public static void unbindVertexArray() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glBindVertexArray(0);
		
		Utils.checkOpenGLErrors();
	}
}

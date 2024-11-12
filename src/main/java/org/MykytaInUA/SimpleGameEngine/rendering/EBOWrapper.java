package org.MykytaInUA.SimpleGameEngine.rendering;

import static com.jogamp.opengl.GL.*;

import java.nio.IntBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import org.MykytaInUA.SimpleGameEngine.utilities.Utils;

public class EBOWrapper {
	
	private int ebo;
	
	public EBOWrapper() {
		this.genBuffer();
	}
	
	private void genBuffer() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int[] eboID = new int[1];
		gl.glGenBuffers(1, eboID, 0);
		ebo = eboID[0];
		
		Utils.checkOpenGLErrors();
	}
	
	public void bindBuffer() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		
		Utils.checkOpenGLErrors();
	}
	
	public static void bufferIntData(int[] data, int usage) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		IntBuffer dataBuffer = Buffers.newDirectIntBuffer(data);
		gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, dataBuffer.limit() * Integer.BYTES, dataBuffer, usage);
		
		Utils.checkOpenGLErrors();
	}
	
	public static void bufferIntData(IntBuffer bufferToSend, int usage) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, bufferToSend.limit() * Integer.BYTES, bufferToSend, usage);
		
		Utils.checkOpenGLErrors();
	}
	
	public static void unbindBuffer() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		Utils.checkOpenGLErrors();
	}
	
	// High abstraction functions
	public void sendStaticIntegerData(int[] dataBuffer) {
		this.bindBuffer();
		bufferIntData(dataBuffer, GL_STATIC_DRAW);
	}
	
	public void sendStaticIntegerData(IntBuffer dataBuffer) {
		this.bindBuffer();
		bufferIntData(dataBuffer, GL_STATIC_DRAW);
	}
}

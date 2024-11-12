package org.MykytaInUA.SimpleGameEngine.rendering;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;

import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

import org.MykytaInUA.SimpleGameEngine.utilities.Utils;

public class VBOWrapper {
	
	private int vbo;
	
	public VBOWrapper() {
		this.genBuffer();
	}
	
	public void genBuffer() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int[] vboID = new int[1];
		gl.glGenBuffers(1, vboID, 0);
		this.vbo = vboID[0];
		
		Utils.checkOpenGLErrors();
	}
	
	public void bindBuffer() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, vbo);
		
		Utils.checkOpenGLErrors();
	}
	
	public static void bufferFloatData(FloatBuffer bufferToSend, int usage) {
		GL4 gl = (GL4) GLContext.getCurrentGL();

		gl.glBufferData(GL_ARRAY_BUFFER, bufferToSend.limit() * Float.BYTES, bufferToSend, usage);
		
		Utils.checkOpenGLErrors();
	}
	
	public static void bufferFloatData(float[] data, int usage) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		FloatBuffer dataBuffer = Buffers.newDirectFloatBuffer(new float[1]);
		
		if (dataBuffer.capacity() < data.length) {
			dataBuffer = Buffers.newDirectFloatBuffer(data.length);
        } else {
            clearBufferContents(dataBuffer);
        }
		
		dataBuffer.clear();
		dataBuffer.put(data);
        dataBuffer.flip();

		gl.glBufferData(GL_ARRAY_BUFFER, dataBuffer.limit() * Float.BYTES, dataBuffer, usage);
		Utils.checkOpenGLErrors();
	}
	
    private static void clearBufferContents(FloatBuffer buffer) {
        // Reset the buffer to its initial state
        buffer.clear();
        
        // Fill the buffer with zeros
        while (buffer.hasRemaining()) {
            buffer.put(0.0f);
        }
        
        // Reset the position to the beginning of the buffer
        buffer.rewind();
    }
    
    public static void vertexAttribPointer(int program, String name, int size, int type,
            boolean normalized, int stride, long pointerBufferOffset) {

		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int attributeLoc = gl.glGetAttribLocation(program, name);
		
		gl.glVertexAttribPointer(attributeLoc, size, type, normalized, stride, pointerBufferOffset);		
		
		Utils.checkOpenGLErrors();
	}
    
	public static void enableVertexAttribArray(int program, String name) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int attributeLoc = gl.glGetAttribLocation(program, name);
		
		gl.glEnableVertexAttribArray(attributeLoc);
		
		Utils.checkOpenGLErrors();
	}
	
	public static void vertexAttribDivisor(int program, String attribName, int divisor) {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		int attributeLoc = gl.glGetAttribLocation(program, attribName);	
		gl.glVertexAttribDivisor(attributeLoc, divisor);
		
		Utils.checkOpenGLErrors();
	}
	
	public static void unbindBuffer() {
		GL4 gl = (GL4) GLContext.getCurrentGL();
		
		gl.glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		Utils.checkOpenGLErrors();
	}
	
	// High abstraction functions
	
	public void sendVBOFloatVector4Data(int program, String attribPointerName, float[] dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program, 
				attribPointerName,
				4,
				GL_FLOAT,
				false,
				0,
				0);
		
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendVBOFloatVector4Data(int program, String attribPointerName, FloatBuffer dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program, 
				attribPointerName,
				4,
				GL_FLOAT,
				false,
				0,
				0);
		
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendFloatVector4InstancedData(int program, String attribPointerName, float[] dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program, 
							attribPointerName,
							4,
							GL_FLOAT,
							false,
							0,
							0);
		
		vertexAttribDivisor(program, attribPointerName, 1);
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendFloatVector4InstancedData(int program, String attribPointerName, FloatBuffer dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program, 
							attribPointerName,
							4,
							GL_FLOAT,
							false,
							0,
							0);
		
		vertexAttribDivisor(program, attribPointerName, 1);
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendVBOFloatVector3Data(int program, String attribPointerName, float[] dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program, 
				attribPointerName,
				3,
				GL_FLOAT,
				false,
				0,
				0);
		
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendVBOFloatVector3Data(int program, String attribPointerName, FloatBuffer dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program, 
				attribPointerName,
				3,
				GL_FLOAT,
				false,
				0,
				0);
		
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendFloatVector3InstancedData(int program, String attribPointerName, float[] dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program, 
							attribPointerName,
							3,
							GL_FLOAT,
							false,
							0,
							0);
		
		vertexAttribDivisor(program, attribPointerName, 1);
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendFloatVector3InstancedData(int program, String attribPointerName, FloatBuffer dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program, 
							attribPointerName,
							3,
							GL_FLOAT,
							false,
							0,
							0);
		
		vertexAttribDivisor(program, attribPointerName, 1);
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendVBOFloatVector2Data(int program, String attribPointerName, float[] dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program,
				attribPointerName,
				2,
				GL_FLOAT,
				false,
				0,
				0);
		
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendVBOFloatVector2Data(int program, String attribPointerName, FloatBuffer dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program,
				attribPointerName,
				2,
				GL_FLOAT,
				false,
				0,
				0);
		
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendVBOFloatVector2InstancedData(int program, String attribPointerName, float[] dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program,
							attribPointerName,
							2,
							GL_FLOAT,
							false,
							0,
							0);
		
		vertexAttribDivisor(program, attribPointerName, 1);
		enableVertexAttribArray(program, attribPointerName);
	}
	
	public void sendVBOFloatVector2InstancedData(int program, String attribPointerName, FloatBuffer dataBuffer) {
		this.bindBuffer();
		
		bufferFloatData(dataBuffer, GL_STATIC_DRAW);
		vertexAttribPointer(program,
							attribPointerName,
							2,
							GL_FLOAT,
							false,
							0,
							0);
		
		vertexAttribDivisor(program, attribPointerName, 1);
		enableVertexAttribArray(program, attribPointerName);
	}

}

package org.mykytainua.simplegameengine.rendering.buffers;

import static com.jogamp.opengl.GL.GL_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_FLOAT;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import org.mykytainua.simplegameengine.utilities.Logger;
import org.mykytainua.simplegameengine.utilities.Utils;

/**
 * The {@code VBOWrapper} class provides an abstraction for working with 
 * OpenGL Vertex Buffer Objects (VBOs). It allows users to manage buffer 
 * creation, binding, data upload, and configuration of vertex attribute 
 * pointers with ease.
 * 
 * <p>This class encapsulates common operations such as binding buffers, 
 * sending data, setting vertex attribute pointers, and enabling instanced 
 * rendering. It also includes utility functions to retrieve buffer data 
 * from the GPU.</p>
 */
public class VBOWrapper {

    private final int vbo; // ID of the VBO generated by OpenGL
    private int dataSize;  // Size of the data currently stored in the VBO

    /**
     * Constructs a new {@code VBOWrapper} instance and generates a new 
     * Vertex Buffer Object (VBO).
     */
    public VBOWrapper() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        int[] vboID = new int[1];
        gl.glGenBuffers(1, vboID, 0);
        this.vbo = vboID[0];

        Utils.checkOpenGLErrors();
    }

    /**
     * Binds this VBO to the OpenGL GL_ARRAY_BUFFER target.
     */
    public void bindBuffer() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        gl.glBindBuffer(GL_ARRAY_BUFFER, vbo);

        Utils.checkOpenGLErrors();
    }

    /**
     * Uploads float data to this VBO.
     *
     * @param bufferToSend the buffer containing the data to send
     * @param usage the usage pattern of the data store (e.g., GL_STATIC_DRAW)
     */
    public void bufferFloatData(FloatBuffer bufferToSend, int usage) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        this.dataSize = bufferToSend.limit() * Float.BYTES;

        gl.glBufferData(GL_ARRAY_BUFFER, dataSize, bufferToSend, usage);

        Utils.checkOpenGLErrors();
    }

    /**
     * Configures a vertex attribute pointer for this VBO.
     *
     * @param program the OpenGL shader program
     * @param name the name of the attribute in the shader
     * @param size the number of components per vertex attribute
     * @param type the data type of the components (e.g., GL_FLOAT)
     * @param normalized whether fixed-point data should be normalized
     * @param stride the byte offset between consecutive vertex attributes
     * @param pointerBufferOffset the offset of the first component in the buffer
     */
    public void vertexAttribPointer(int program, 
                                    String name, 
                                    int size, 
                                    int type, 
                                    boolean normalized, 
                                    int stride,
                                    long pointerBufferOffset) {

        GL4 gl = (GL4) GLContext.getCurrentGL();

        int attributeLoc = gl.glGetAttribLocation(program, name);

        gl.glVertexAttribPointer(attributeLoc, size, type, normalized, stride, pointerBufferOffset);

        Utils.checkOpenGLErrors();
    }

    /**
     * Enables the specified vertex attribute array.
     *
     * @param program the OpenGL shader program
     * @param name the name of the attribute in the shader
     */
    public void enableVertexAttribArray(int program, String name) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        int attributeLoc = gl.glGetAttribLocation(program, name);

        gl.glEnableVertexAttribArray(attributeLoc);

        Utils.checkOpenGLErrors();
    }

    /**
     * Sets the divisor for instanced rendering for the specified attribute.
     *
     * @param program the OpenGL shader program
     * @param attribName the name of the attribute in the shader
     * @param divisor the instancing divisor
     */
    public void vertexAttribDivisor(int program, String attribName, int divisor) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        int attributeLoc = gl.glGetAttribLocation(program, attribName);
        gl.glVertexAttribDivisor(attributeLoc, divisor);

        Utils.checkOpenGLErrors();
    }

    /**
     * Unbinds the currently bound buffer from the GL_ARRAY_BUFFER target.
     */
    public static void unbindBuffer() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        gl.glBindBuffer(GL_ARRAY_BUFFER, 0);

        Utils.checkOpenGLErrors();
    }

    /**
     * Retrieves a subset of data from the VBO into a given ByteBuffer.
     *
     * @param destBuffer the destination buffer
     * @return the destination buffer containing the retrieved data
     */
    public ByteBuffer getBufferSubData(ByteBuffer destBuffer) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        this.bindBuffer();

        gl.glGetBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, destBuffer);

        unbindBuffer();

        destBuffer.rewind();

        return destBuffer;
    }

    /**
     * Retrieves the entire data content of this VBO from the GPU.
     *
     * @return a ByteBuffer containing the VBO data
     */
    public ByteBuffer getVBODataFromGPU() {

        GL4 gl = (GL4) GLContext.getCurrentGL();

        ByteBuffer bufferData = ByteBuffer.allocateDirect(dataSize).order(ByteOrder.nativeOrder());

        this.bindBuffer();

        gl.glGetBufferSubData(GL_ARRAY_BUFFER, 0, dataSize, bufferData);

        unbindBuffer();

        bufferData.rewind();

        return bufferData;
    }

    /**
     * Sends float vector data for instanced rendering to the GPU and configures 
     * the attribute pointer and divisor.
     *
     * @param program the OpenGL shader program
     * @param attribPointerName the name of the attribute in the shader
     * @param size the number of components per vertex attribute
     * @param dataBuffer the buffer containing the data to send
     */
    public void sendVBOFloatVectorInstancedData(int program, 
                                                String attribPointerName, 
                                                int size,
                                                FloatBuffer dataBuffer) {
        this.bindBuffer();

        Logger.printBufferData("Instanced \"" 
                               + attribPointerName 
                               + "\" param send to GPU:" 
                               + "\nData:", dataBuffer);

        bufferFloatData(dataBuffer, GL_STATIC_DRAW);
        vertexAttribPointer(program, attribPointerName, size, GL_FLOAT, false, 0, 0);

        vertexAttribDivisor(program, attribPointerName, 1);
        enableVertexAttribArray(program, attribPointerName);
    }

    /**
     * Sends float vector data to the GPU and configures the attribute pointer.
     *
     * @param program the OpenGL shader program
     * @param attribPointerName the name of the attribute in the shader
     * @param size the number of components per vertex attribute
     * @param dataBuffer the buffer containing the data to send
     */
    public void sendVBOFloatVectorData(int program, 
                                       String attribPointerName, 
                                       int size, 
                                       FloatBuffer dataBuffer) {
        this.bindBuffer();

        Logger.printBufferData("Param \"" 
                               + attribPointerName 
                               + "\" send to GPU:" 
                               + "\nData:", 
                               dataBuffer);

        bufferFloatData(dataBuffer, GL_STATIC_DRAW);
        vertexAttribPointer(program, attribPointerName, size, GL_FLOAT, false, 0, 0);

        enableVertexAttribArray(program, attribPointerName);
    }

    public int getDataSize() {
        return this.dataSize;
    }
}

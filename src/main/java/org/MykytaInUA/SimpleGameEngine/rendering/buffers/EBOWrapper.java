package org.mykytainua.simplegameengine.rendering.buffers;

import static com.jogamp.opengl.GL.GL_ELEMENT_ARRAY_BUFFER;
import static com.jogamp.opengl.GL.GL_STATIC_DRAW;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.nio.IntBuffer;
import org.mykytainua.simplegameengine.utilities.Utils;

/**
 * The {@code EBOWrapper} class provides a convenient abstraction for managing
 * Element Buffer Objects (EBOs) in OpenGL.
 * 
 * <p>An EBO is used to define the indices of vertices to be rendered, reducing
 * redundancy and improving performance by reusing vertex data. This class
 * includes methods to generate, bind, unbind, and send index data to the GPU.
 * </p>
 */
public class EBOWrapper {

    private int ebo; // ID of the Element Buffer Object managed by this wrapper
    private int indicesCount; // The number of indices stored in this EBO

    /**
     * Constructs a new {@code EBOWrapper} instance and generates an Element Buffer
     * Object (EBO).
     */
    public EBOWrapper() {
        this.genBuffer();
    }

    /**
     * Generates a new Element Buffer Object (EBO) and assigns its ID to this
     * instance.
     */
    private void genBuffer() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        int[] eboID = new int[1];
        gl.glGenBuffers(1, eboID, 0);
        ebo = eboID[0];

        Utils.checkOpenGLErrors();
    }

    /**
     * Binds the Element Buffer Object (EBO) associated with this instance.
     */
    public void bindBuffer() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);

        Utils.checkOpenGLErrors();
    }

    /**
     * Sends integer index data to the GPU, creating or replacing the buffer's
     * contents.
     *
     * @param data  An array of integers representing the indices.
     * @param usage Specifies the usage pattern of the buffer, e.g.,
     *              {@code GL_STATIC_DRAW}.
     */
    public static void bufferIntData(int[] data, int usage) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        IntBuffer dataBuffer = Buffers.newDirectIntBuffer(data);
        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, 
                        dataBuffer.limit() * Integer.BYTES, 
                        dataBuffer, 
                        usage);

        Utils.checkOpenGLErrors();
    }

    /**
     * Sends integer index data to the GPU, creating or replacing the buffer's
     * contents.
     *
     * @param bufferToSend A direct {@link IntBuffer} containing the indices.
     * @param usage        Specifies the usage pattern of the buffer, e.g.,
     *                     {@code GL_STATIC_DRAW}.
     */
    public static void bufferIntData(IntBuffer bufferToSend, int usage) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        gl.glBufferData(GL_ELEMENT_ARRAY_BUFFER, 
                        bufferToSend.limit() * Integer.BYTES, 
                        bufferToSend, 
                        usage);

        Utils.checkOpenGLErrors();
    }

    /**
     * Unbinds the currently bound Element Buffer Object (EBO).
     * 
     * <p>This method ensures that no EBO remains active, helping prevent unintended
     * modifications to previously bound EBOs.</p>
     */
    public static void unbindBuffer() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        gl.glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        Utils.checkOpenGLErrors();
    }

    /**
     * Sends static integer index data to the GPU for rendering.
     *
     * @param dataBuffer An array of integers containing the indices.
     */
    public void sendStaticIntegerData(int[] dataBuffer) {
        this.bindBuffer();
        bufferIntData(dataBuffer, GL_STATIC_DRAW);
    }

    /**
     * Sends static integer index data to the GPU for rendering.
     *
     * @param dataBuffer A direct {@link IntBuffer} containing the indices.
     */
    public void sendStaticIntegerData(IntBuffer dataBuffer) {
        this.bindBuffer();
        bufferIntData(dataBuffer, GL_STATIC_DRAW);
    }

    /**
     * Gets the number of indices stored in this EBO.
     *
     * @return The number of indices.
     */
    public int getIndicesCount() {
        return indicesCount;
    }

    /**
     * Sets the number of indices stored in this EBO.
     *
     * @param indicesCount The number of indices to set.
     */
    public void setIndicesCount(int indicesCount) {
        this.indicesCount = indicesCount;
    }
}

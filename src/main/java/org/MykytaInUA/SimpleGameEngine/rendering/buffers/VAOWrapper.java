package org.mykytainua.simplegameengine.rendering.buffers;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import org.mykytainua.simplegameengine.utilities.Utils;


/**
 * The {@code VAOWrapper} class provides a convenient abstraction for managing
 * Vertex Array Objects (VAOs) in OpenGL.
 * 
 * <p>A VAO encapsulates the state related to vertex attribute configurations
 * and simplifies the process of binding and unbinding vertex attributes. This
 * class offers methods to generate, bind, and unbind VAOs, ensuring proper
 * error checking after OpenGL operations.</p>
 */
public class VAOWrapper {

    private int vao; // ID of the Vertex Array Object managed by this wrapper

    /**
     * Constructs a new {@code VAOWrapper} instance and generates a Vertex Array
     * Object (VAO).
     */
    public VAOWrapper() {
        this.genVertexArray();
    }

    /**
     * Generates a new Vertex Array Object (VAO) and assigns its ID to this
     * instance.
     */
    private void genVertexArray() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        int[] vaoID = new int[1];
        gl.glGenVertexArrays(1, vaoID, 0);

        this.vao = vaoID[0];

        Utils.checkOpenGLErrors();
    }

    /**
     * Binds the Vertex Array Object (VAO) associated with this instance.
     */
    public void bindVertexArray() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        gl.glBindVertexArray(vao);

        Utils.checkOpenGLErrors();
    }

    /**
     * Unbinds the currently bound Vertex Array Object (VAO).
     * 
     * <p>This method ensures that no VAO remains active, helping prevent unintended
     * modifications to previously bound VAOs.</p>
     */
    public static void unbindVertexArray() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        gl.glBindVertexArray(0);

        Utils.checkOpenGLErrors();
    }
}

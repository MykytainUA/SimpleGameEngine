package org.mykytainua.simplegameengine.rendering.shaders;

import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.joml.Matrix4f;
import org.mykytainua.simplegameengine.utilities.Utils;

/**
 * The {@code UniformsStorage} class provides a mechanism for managing uniform
 * variables in an OpenGL shader program.
 * 
 * <p>This class caches uniform locations to avoid repeated lookups and provides
 * utility methods for updating uniforms such as 4x4 matrices.</p>
 */
public class UniformsStorage {

    private final Map<String, Integer> uniformMap; // Cache for uniform locations
    private FloatBuffer vals = Buffers.newDirectFloatBuffer(16); // Buffer for matrix data
    private final int boundShaderID; // ID of the shader program this storage is bound to

    /**
     * Constructs a new {@code UniformsStorage} instance and binds it to the given
     * shader program ID.
     *
     * @param shaderID The ID of the shader program this storage will manage.
     */
    public UniformsStorage(int shaderID) {
        boundShaderID = shaderID;
        uniformMap = new HashMap<>();
    }

    /**
     * Retrieves the location of a uniform variable in the shader program. If the
     * uniform location is already cached, it is returned; otherwise, it is queried
     * from OpenGL and cached.
     *
     * @param name The name of the uniform variable.
     * @return The location of the uniform variable, or -1 if not found.
     */
    public int getProgramUniformLocation(String name) {
        Integer uniformLocation = this.uniformMap.get(name);

        // Check if the uniform location is already cached
        if (uniformLocation != null) {
            return uniformLocation;
        }

        // Query the uniform location from OpenGL
        uniformLocation = this.getUniformLocation(name);

        // Cache the new uniform location if valid
        if (uniformLocation >= 0) {
            this.uniformMap.put(name, uniformLocation);
        }

        return uniformLocation;
    }

    /**
     * Queries the OpenGL API for the location of a uniform variable in the shader
     * program.
     *
     * @param name The name of the uniform variable.
     * @return The location of the uniform variable, or -1 if not found.
     */
    public int getUniformLocation(String name) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        return gl.glGetUniformLocation(boundShaderID, name);
    }

    /**
     * Updates a 4x4 matrix uniform variable in the shader program.
     * 
     * <p>The matrix data is uploaded to the uniform variable identified by
     * {@code uniformName}.</p>
     *
     * @param uniformName The name of the uniform variable.
     * @param matrix      A {@link Matrix4f} object containing the matrix data.
     */
    public void updateUniformMatrix4f(String uniformName, Matrix4f matrix) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        gl.glUniformMatrix4fv(this.getProgramUniformLocation(uniformName), 
                              1, 
                              false, 
                              matrix.get(vals));

        Utils.checkOpenGLErrors();
    }
}

package org.mykytainua.simplegameengine.rendering.shaders;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.util.List;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.utilities.Utils;


/**
 * The StaticShader class is responsible for creating, compiling, and linking OpenGL
 * shader programs from either file paths or source code. It manages both vertex
 * and fragment shaders and provides utility methods for shader creation and
 * error handling.
 *
 * <p>This class ensures that the shaders are correctly compiled, attached to an
 * OpenGL program, and linked for use in rendering. It also manages a list of
 * components that are associated with the shader, enabling flexibility in how
 * shaders interact with game objects.</p>
 *
 * @see Component
 */

public class StaticShader {

    // The OpenGL program ID for the shader.
    private int shaderID = -1;

    // List of components that are associated with this shader.
    private final List<Component> components;

    /**
     * Constructor to create a shader from shader file paths.
     * 
     * <p>This method loads vertex and fragment shaders from the provided file paths,
     * compiles them, attaches them to a program, and links the program for use in
     * OpenGL.
     *
     * @param vertexShaderPath   Path to the vertex shader file.
     * @param fragmentShaderPath Path to the fragment shader file.
     * @param components         A list of components associated with the shader.
     */
    public StaticShader(String vertexShaderPath, String fragmentShaderPath, List<Component> components) {
        this.components = components;
        this.createShaderByPath(vertexShaderPath, fragmentShaderPath);
    }

    /**
     * Constructor to create a shader from shader source code.
     * 
     * <p>This method loads vertex and fragment shaders from the provided source code
     * arrays, compiles them, attaches them to a program, and links the program for
     * use in OpenGL.
     *
     * @param vertexShaderPath   The source code of the vertex shader.
     * @param fragmentShaderPath The source code of the fragment shader.
     * @param components         A list of components associated with the shader.
     */
    public StaticShader(String[] vertexShaderPath, 
                  String[] fragmentShaderPath, 
                  List<Component> components) {
        this.components = components;
        this.createShaderBySource(vertexShaderPath, fragmentShaderPath);
    }

    /**
     * Gets the OpenGL shader program ID.
     *
     * @return The OpenGL shader program ID.
     */
    public int getShaderID() {
        return this.shaderID;
    }

    /**
     * Gets the list of components associated with this shader.
     *
     * @return The list of components associated with the shader.
     */
    public List<Component> getComponents() {
        return this.components;
    }

    /**
     * Creates a shader program from the vertex and fragment shader files.
     * 
     * <p>This method reads the shader files, compiles them, attaches them to a
     * program, and links the program. It also handles shader compilation errors.
     *
     * @param vertexShaderPath   Path to the vertex shader file.
     * @param fragmentShaderPath Path to the fragment shader file.
     */
    private void createShaderByPath(String vertexShaderPath, String fragmentShaderPath) {
        int vertexShader;
        int fragmentShader;
        
        GL4 gl = (GL4) GLContext.getCurrentGL();


        // Create vertex and fragment shaders
        vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
        fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

        // Create shader program
        this.shaderID = gl.glCreateProgram();
        
        // Read the shader source code from the files
        String[] vertexShaderSource = ShaderUtilities.getShaderSourceAsString(vertexShaderPath);

        // Set the shader source code and compile the shaders
        gl.glShaderSource(vertexShader, vertexShaderSource.length, vertexShaderSource, null);
        gl.glCompileShader(vertexShader);
        Utils.printShaderLog(vertexShader); // Print shader compile log
        
        // Read the shader source code from the files
        String[] fragmentShaderSource = ShaderUtilities.getShaderSourceAsString(fragmentShaderPath);
        gl.glShaderSource(fragmentShader, fragmentShaderSource.length, fragmentShaderSource, null);
        gl.glCompileShader(fragmentShader);
        Utils.printShaderLog(fragmentShader); // Print shader compile log

        // Attach the shaders to the program and link the program
        gl.glAttachShader(this.shaderID, vertexShader);
        gl.glAttachShader(this.shaderID, fragmentShader);

        gl.glLinkProgram(this.shaderID);
        Utils.printProgramLog(this.shaderID); // Print program link log

        // Detach and delete the shaders as they are no longer needed after linking
        gl.glDetachShader(this.shaderID, vertexShader);
        gl.glDetachShader(this.shaderID, fragmentShader);

        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(fragmentShader);
    }

    /**
     * Creates a shader program from the given shader source code.
     * 
     * <p>This method compiles the vertex and fragment shader source code, attaches
     * them to a program, and links the program. It also handles shader compilation
     * errors.
     *
     * @param vertexShaderSource   The source code of the vertex shader.
     * @param fragmentShaderSource The source code of the fragment shader.
     */
    private void createShaderBySource(String[] vertexShaderSource, String[] fragmentShaderSource) {
        int vertexShader;
        int fragmentShader;
        
        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Create vertex and fragment shaders
        vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
        fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

        // Create shader program
        this.shaderID = gl.glCreateProgram();

        // Set the shader source code and compile the shaders
        gl.glShaderSource(vertexShader, vertexShaderSource.length, vertexShaderSource, null);
        gl.glCompileShader(vertexShader);
        Utils.printShaderLog(vertexShader); // Print shader compile log

        gl.glShaderSource(fragmentShader, fragmentShaderSource.length, fragmentShaderSource, null);
        gl.glCompileShader(fragmentShader);
        Utils.printShaderLog(fragmentShader); // Print shader compile log

        // Attach the shaders to the program and link the program
        gl.glAttachShader(this.shaderID, vertexShader);
        gl.glAttachShader(this.shaderID, fragmentShader);

        gl.glLinkProgram(this.shaderID);
        Utils.printProgramLog(this.shaderID); // Print program link log

        // Detach and delete the shaders as they are no longer needed after linking
        gl.glDetachShader(this.shaderID, vertexShader);
        gl.glDetachShader(this.shaderID, fragmentShader);

        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(fragmentShader);
    }
}

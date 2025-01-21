package org.mykytainua.simplegameengine.rendering.shaders;

import static com.jogamp.opengl.GL2ES2.GL_FRAGMENT_SHADER;
import static com.jogamp.opengl.GL2ES2.GL_VERTEX_SHADER;

import java.util.ArrayList;
import java.util.List;

import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.utilities.Utils;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

public abstract class Shader {
    
    // The OpenGL program ID for the shader.
    protected int shaderID = -1;
    
    // A storage for staticShader uniforms that need to be updated for each draw call
    protected UniformsStorage localUniforms;
    
    // List of render units, each containing objects that share the same staticShader
    protected ArrayList<RenderUnit> renderUnits;
    
    // List of components that are associated with this shader.
    protected List<Class<? extends Component>> components;
    
    // The camera used to render the scene (provides view and projection matrices)
    protected Camera camera;
    
    public Shader(List<Class<? extends Component>> components) {
     // Initialize an empty list of render units
        this.components = components;
        this.renderUnits = new ArrayList<RenderUnit>();
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
    public List<Class<? extends Component>> getComponents() {
        return this.components;
    }
    
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void render() {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        
        // Use the staticShader program for rendering
        gl.glUseProgram(this.getShaderID());
        
        // Update the view and projection matrices for the staticShader
        this.localUniforms.updateUniformMatrix4f("v_matrix", camera.getLookAtMatrix());
        this.localUniforms.updateUniformMatrix4f("p_matrix", camera.getPerspectiveMatrix());
        
        // Render each render unit
        for (RenderUnit renderUnit : renderUnits) {
            renderUnit.render();
        }
    }
    
    /**
     * Finds the render unit that can handle the provided set of 3D objects based on
     * the components of the objects. If no such render unit exists, null is
     * returned.
     *
     * @param objects the array of Object3D whose render unit is being searched for
     * @return the render unit containing compatible objects, or null if no such
     *         unit exists
     */
    private RenderUnit getRenderUnitFor3DObjects(Object3D[] objects) {
        for (RenderUnit renderUnit : renderUnits) {
            // Check if the render unit can handle all the components of the given objects
            if (renderUnit.getComponents().containsAll(objects[0].getComponents())) {
                return renderUnit;
            }
        }
        return null;
    }
    
    /**
     * Adds a set of 3D objects to be rendered with the staticShader. If the objects share
     * common characteristics, they are grouped into an existing render unit.
     * Otherwise, a new render unit is created for the objects.
     *
     * @param objects the array of Object3D to be added to the staticShader program
     */
    public void addObjects(Object3D[] objects) {

        // Find an existing render unit that can handle these objects
        RenderUnit renderUnit = this.getRenderUnitFor3DObjects(objects);

        if (renderUnit != null) {
            // Add objects to the found render unit
            renderUnit.addObjects(objects);
        } else {
            // Create a new render unit for these objects and add it to the render units
            // list
            RenderUnit newRenderUnit = new RenderUnit(this.getShaderID(), objects);
            this.renderUnits.add(newRenderUnit);
        }
    }
    
    protected static int createShaderByPath(String vertexShaderPath, String fragmentShaderPath) {
        int shaderID;
        int vertexShader;
        int fragmentShader;
        
        GL4 gl = (GL4) GLContext.getCurrentGL();


        // Create vertex and fragment shaders
        vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
        fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

        // Create shader program
        shaderID = gl.glCreateProgram();
        
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
        gl.glAttachShader(shaderID, vertexShader);
        gl.glAttachShader(shaderID, fragmentShader);

        gl.glLinkProgram(shaderID);
        Utils.printProgramLog(shaderID); // Print program link log

        // Detach and delete the shaders as they are no longer needed after linking
        gl.glDetachShader(shaderID, vertexShader);
        gl.glDetachShader(shaderID, fragmentShader);

        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(fragmentShader);
        
        return shaderID;
    }


    protected static int createShaderBySource(String[] vertexShaderSource, String[] fragmentShaderSource) {
        int shaderID;
        int vertexShader;
        int fragmentShader;
        
        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Create vertex and fragment shaders
        vertexShader = gl.glCreateShader(GL_VERTEX_SHADER);
        fragmentShader = gl.glCreateShader(GL_FRAGMENT_SHADER);

        // Create shader program
        shaderID = gl.glCreateProgram();

        // Set the shader source code and compile the shaders
        gl.glShaderSource(vertexShader, vertexShaderSource.length, vertexShaderSource, null);
        gl.glCompileShader(vertexShader);
        Utils.printShaderLog(vertexShader); // Print shader compile log

        gl.glShaderSource(fragmentShader, fragmentShaderSource.length, fragmentShaderSource, null);
        gl.glCompileShader(fragmentShader);
        Utils.printShaderLog(fragmentShader); // Print shader compile log

        // Attach the shaders to the program and link the program
        gl.glAttachShader(shaderID, vertexShader);
        gl.glAttachShader(shaderID, fragmentShader);

        gl.glLinkProgram(shaderID);
        Utils.printProgramLog(shaderID); // Print program link log

        // Detach and delete the shaders as they are no longer needed after linking
        gl.glDetachShader(shaderID, vertexShader);
        gl.glDetachShader(shaderID, fragmentShader);

        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(fragmentShader);
        
        return shaderID;
    }
}

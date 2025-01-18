package org.mykytainua.simplegameengine.rendering.shaders;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.util.ArrayList;
import java.util.List;
import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * The ShaderProgram class represents a shader program that can be used to
 * render 3D objects. It manages the shader, stores uniforms, and handles the
 * rendering process. This class encapsulates all the components required to
 * render objects with a specific shader program, such as uniform variables,
 * camera settings, and render units.
 * 
 * <p>The class also allows adding objects (3D models) to be rendered with the
 * program, associating them with the appropriate render unit. Render units
 * group objects with similar properties for more efficient rendering.</p>
 */
public class ShaderProgram {

    // The shader associated with this program
    private final Shader shader;

    // List of render units, each containing objects that share the same shader
    private ArrayList<RenderUnit> renderUnits;

    // The camera used to render the scene (provides view and projection matrices)
    private Camera camera;

    // A storage for shader uniforms that need to be updated for each draw call
    private final UniformsStorage uniformsStorage;

    /**
     * Constructor to initialize the ShaderProgram with the given vertex and
     * fragment shaders, and a list of components that the shader will be using. The
     * shaders are assembled, and uniform storage is initialized.
     *
     * @param vertexShaderPath    the path to the vertex shader source file
     * @param fragmentShaderPath  the path to the fragment shader source file
     * @param componentsForShader the list of components that the shader will use
     */
    public ShaderProgram(String vertexShaderPath, 
                         String fragmentShaderPath, 
                         List<Component> componentsForShader) {

        // Assemble the shader using the provided paths and components
        this.shader = ShaderAssembler.getShaderByPath(vertexShaderPath, 
                                                      fragmentShaderPath, 
                                                      componentsForShader);

        // Initialize uniforms storage to manage uniform variables for the shader
        this.uniformsStorage = new UniformsStorage(this.shader.getShaderID());

        // Initialize an empty list of render units
        this.renderUnits = new ArrayList<RenderUnit>();
    }

    /**
     * Adds a set of 3D objects to be rendered with the shader. If the objects share
     * common characteristics, they are grouped into an existing render unit.
     * Otherwise, a new render unit is created for the objects.
     *
     * @param objects the array of Object3D to be added to the shader program
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
            RenderUnit newRenderUnit = new RenderUnit(this.shader.getShaderID(), objects);
            this.renderUnits.add(newRenderUnit);
        }
    }

    /**
     * Sets the camera to be used by the shader program for rendering. The camera is
     * responsible for providing the view and projection matrices.
     *
     * @param camera the camera to be used for rendering
     */
    public void addCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Returns the OpenGL shader program ID associated with this ShaderProgram.
     *
     * @return the shader program ID
     */
    public int getProgramID() {
        return this.shader.getShaderID();
    }

    /**
     * Returns the location of a uniform variable within the shader program.
     *
     * @param name the name of the uniform variable
     * @return the location of the uniform variable
     */
    public int getUniformLocation(String name) {

        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Retrieve the uniform location from the shader program
        return gl.glGetUniformLocation(shader.getShaderID(), name);
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
            if (renderUnit.getComponents().containsAll(objects[0].getComponentClasses())) {
                return renderUnit;
            }
        }
        return null;
    }

    /**
     * This method renders all the render units by setting the shader program and
     * updating the uniform matrices (view and projection matrices). It then calls
     * the render method for each render unit to draw the associated objects.
     */
    public void render() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Use the shader program for rendering
        gl.glUseProgram(this.getProgramID());

        // Update the view and projection matrices for the shader
        this.uniformsStorage.updateUniformMatrix4f("v_matrix", camera.getLookAtMatrix());
        this.uniformsStorage.updateUniformMatrix4f("p_matrix", camera.getPerspectiveMatrix());

        // Render each render unit
        for (RenderUnit renderUnit : renderUnits) {
            renderUnit.render();
        }
    }
}
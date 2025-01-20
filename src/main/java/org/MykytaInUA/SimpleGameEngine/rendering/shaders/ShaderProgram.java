package org.mykytainua.simplegameengine.rendering.shaders;

import static com.jogamp.opengl.GL.GL_BACK;
import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_CW;
import static com.jogamp.opengl.GL.GL_LESS;
import static com.jogamp.opengl.GL4.GL_CULL_FACE;
import static com.jogamp.opengl.GL4.GL_DEPTH_TEST;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;
import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * The ShaderProgram class represents a staticShader program that can be used to
 * render 3D objects. It manages the staticShader, stores uniforms, and handles the
 * rendering process. This class encapsulates all the components required to
 * render objects with a specific staticShader program, such as uniform variables,
 * camera settings, and render units.
 * 
 * <p>The class also allows adding objects (3D models) to be rendered with the
 * program, associating them with the appropriate render unit. Render units
 * group objects with similar properties for more efficient rendering.</p>
 */
public class ShaderProgram {

    // The staticShader associated with this program
    private final StaticShader staticShader;

    // List of render units, each containing objects that share the same staticShader
    private ArrayList<RenderUnit> renderUnits;

    // The camera used to render the scene (provides view and projection matrices)
    private Camera camera;

    // A storage for staticShader uniforms that need to be updated for each draw call
    private final UniformsStorage uniformsStorage;

    /**
     * Constructor to initialize the ShaderProgram with the given vertex and
     * fragment shaders, and a list of components that the staticShader will be using. The
     * shaders are assembled, and uniform storage is initialized.
     *
     * @param vertexShaderPath    the path to the vertex staticShader source file
     * @param fragmentShaderPath  the path to the fragment staticShader source file
     * @param componentsForShader the list of components that the staticShader will use
     */
    public ShaderProgram(String vertexShaderPath, 
                         String fragmentShaderPath, 
                         List<Component> componentsForShader) {
        
        OpenGLSettings.setFaceCulling(true, true, GL_BACK);
        OpenGLSettings.setBlending(false);
        OpenGLSettings.setDepthTesting(true, GL_LESS);
        OpenGLSettings.setVSync(true);
        
        Vector4f backgroundColor = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        OpenGLSettings.setClearColor(backgroundColor);
        
        // Assemble the staticShader using the provided paths and components
        this.staticShader = ShaderAssembler.getShaderByPath(vertexShaderPath, 
                                                      fragmentShaderPath, 
                                                      componentsForShader);

        // Initialize uniforms storage to manage uniform variables for the staticShader
        this.uniformsStorage = new UniformsStorage(this.staticShader.getShaderID());

        // Initialize an empty list of render units
        this.renderUnits = new ArrayList<RenderUnit>();
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
            RenderUnit newRenderUnit = new RenderUnit(this.staticShader.getShaderID(), objects);
            this.renderUnits.add(newRenderUnit);
        }
    }

    /**
     * Sets the camera to be used by the staticShader program for rendering. The camera is
     * responsible for providing the view and projection matrices.
     *
     * @param camera the camera to be used for rendering
     */
    public void addCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Returns the OpenGL staticShader program ID associated with this ShaderProgram.
     *
     * @return the staticShader program ID
     */
    public int getProgramID() {
        return this.staticShader.getShaderID();
    }

    /**
     * Returns the location of a uniform variable within the staticShader program.
     *
     * @param name the name of the uniform variable
     * @return the location of the uniform variable
     */
    public int getUniformLocation(String name) {

        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Retrieve the uniform location from the staticShader program
        return gl.glGetUniformLocation(staticShader.getShaderID(), name);
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
     * This method renders all the render units by setting the staticShader program and
     * updating the uniform matrices (view and projection matrices). It then calls
     * the render method for each render unit to draw the associated objects.
     */
    public void render() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Use the staticShader program for rendering
        gl.glUseProgram(this.getProgramID());

        // Update the view and projection matrices for the staticShader
        this.uniformsStorage.updateUniformMatrix4f("v_matrix", camera.getLookAtMatrix());
        this.uniformsStorage.updateUniformMatrix4f("p_matrix", camera.getPerspectiveMatrix());

        // Render each render unit
        for (RenderUnit renderUnit : renderUnits) {
            renderUnit.render();
        }
    }
    
    private static class OpenGLSettings {
        
        /**
         * Sets face culling for the entire OpenGL context.
         * <p><strong>Warning:</strong> if isActive is false all other parameters are discarded.</p>
         * 
         * @param isActive           enable/disable face culling
         * @param isCounterClockwise whether face culling is set for clockwise or
         *                           counter clockwise sides of an object
         * @param cullFace           defines which side of the object is going to be
         *                           skipped, whether its a front side or back is being
         *                           defined by isCounterClockwise parameter, available
         *                           options are:
         * 
         *                           GL_FRONT - cull face is applied for front polygons
         *                           GL_BACK - cull face is applied for back polygons
         *                           GL_FRONT_AND_BACK - cull face is applied for all polygons
         */
        private static void setFaceCulling(boolean isActive, boolean isCounterClockwise, int cullFace) {
            GL4 gl = (GL4) GLContext.getCurrentGL();
            
            // Disable culling if needed
            if(!isActive) {
                gl.glDisable(GL_CULL_FACE);
                return;
            }
            
            // Enable culling according to parameters
            gl.glEnable(GL_CULL_FACE);
            
            if(isCounterClockwise) {  
                gl.glFrontFace(GL_CCW);
            } else {
                gl.glFrontFace(GL_CW);
            }
            
            gl.glCullFace(cullFace);
        }
        
        /**
         * Sets blending (applying alpha channel) for the entire OpenGL context.
         * 
         * @param isActive enable/disable blending
         */
        private static void setBlending(boolean isActive) {
            GL4 gl = (GL4) GLContext.getCurrentGL();
            
            // Disable blending if needed
            if(!isActive) {
                gl.glDisable(GL_BLEND);
                return;
            }
            
            gl.glEnable(GL_BLEND);
        }
        
        /**
         * Sets depth testing for the entire OpenGL context.
         * 
         * @param isActive      enable/disable depth testing
         * @param depthFunction specifies the function used to compare each incoming
         *                      pixel depth value with the depth value present in the
         *                      depth buffer, available
         *                      options are:
         *                      
         *                      GL_NEVER - Never passes.
         *                      GL_LESS - Passes if the incoming depth value is less 
         *                      than the stored depth value.
         *                      GL_EQUAL - Passes if the incoming depth value is 
         *                      equal to the stored depth value.
         *                      GL_LEQUAL - Passes if the incoming depth value is 
         *                      less than or equal to the stored depth value.
         *                      GL_GREATER - Passes if the incoming depth value is
         *                      greater than the stored depth value.
         *                      GL_NOTEQUAL - Passes if the incoming depth value is 
         *                      not equal to the stored depth value.
         *                      GL_GEQUAL - Passes if the incoming depth value is 
         *                      greater than or equal to the stored depth value.
         *                      GL_ALWAYS - Always passes.
         *                      
         *                      
         */
        private static void setDepthTesting(boolean isActive, int depthFunction) {
            GL4 gl = (GL4) GLContext.getCurrentGL();
            
            // Disable blending if needed
            if(!isActive) {
                gl.glDisable(GL_DEPTH_TEST);
                return;
            }
            
            gl.glEnable(GL_DEPTH_TEST);
            
            gl.glDepthFunc(depthFunction);
        }
        
        /**
         * Sets VSync (frame rate is synchronized with monitor) parameter for the entire OpenGL context.
         * @param isActive if true VSync is ON, otherwise false.
         */
        private static void setVSync(boolean isActive) {
            GL4 gl = (GL4) GLContext.getCurrentGL();
            
            if(isActive) {
                gl.setSwapInterval(1);
            } else {
                gl.setSwapInterval(0);
            }
        }
        
        /**
         * Sets clear values for the color buffers
         * @param backgroundColor the red, green, blue, and alpha values used by glClear to clear the color buffers.
         */
        private static void setClearColor(Vector4f backgroundColor) {
            GL4 gl = (GL4) GLContext.getCurrentGL();
            
            gl.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w);
        }
    }
}
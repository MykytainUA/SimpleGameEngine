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

public class ShaderProgram {

    // The staticShader associated with this program
    private final ArrayList<Shader> shaders = new ArrayList<Shader>();
    
    private Camera camera;

    /**
     * Constructor to initialize the ShaderProgram with the given vertex and
     * fragment shaders, and a list of components that the staticShader will be using. The
     * shaders are assembled, and uniform storage is initialized.
     *
     * @param vertexShaderPath    the path to the vertex staticShader source file
     * @param fragmentShaderPath  the path to the fragment staticShader source file
     * @param componentsForShader the list of components classes that the staticShader will use
     */
    public ShaderProgram(Camera camera) {
        
        OpenGLSettings.setFaceCulling(true, true, GL_BACK);
        OpenGLSettings.setBlending(false);
        OpenGLSettings.setDepthTesting(true, GL_LESS);
        OpenGLSettings.setVSync(true);
        
        Vector4f backgroundColor = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        OpenGLSettings.setClearColor(backgroundColor);
        
        this.camera = camera;
    }
    
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void addObjects(Object3D[] objects) {    
        Shader shader = this.getAppropriateShader(objects[0].getComponentsClasses());
                
        shader.addObjects(objects);
    }

    public void render() {
        for (Shader shader : shaders) {
            shader.render();
        }
    }
    
    private Shader getAppropriateShader(List<Class<? extends Component>> componentsForShader) {
        
        for(Shader shader : shaders) {
            if(shader.getComponents().containsAll(componentsForShader)) {
                return shader;
            }
        }
        
        Shader shader = ShaderAssembler.getShaderByPath(".\\src\\main\\java\\shaders\\vertexShader.glsl", 
                                                        ".\\src\\main\\java\\shaders\\fragmentShader.glsl", 
                                                         componentsForShader);
        
        shader.setCamera(camera);
        shaders.add(shader);
        
        return shader;
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
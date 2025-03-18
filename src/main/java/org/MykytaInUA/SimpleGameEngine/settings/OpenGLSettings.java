package org.mykytainua.simplegameengine.settings;

import static com.jogamp.opengl.GL.GL_BLEND;
import static com.jogamp.opengl.GL.GL_CULL_FACE;
import static com.jogamp.opengl.GL.GL_DEPTH_TEST;

import org.joml.Vector4f;
import org.mykytainua.simplegameengine.rendering.shaders.RenderConfig;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;

/**
 * Used to get to setting functions of OpenGL, stores them in a config.
 * Config represents current state of OpenGL without accessing OpenGL itself,
 * (Dublicates OpenGL flags on CPU side)
 */
public class OpenGLSettings {
    
    private final static RenderConfig ACTUAL_RENDER_CONFIG = new RenderConfig();
    
    /**
     * Sets clear values for the color buffers
     * @param backgroundColor the red, green, blue, and alpha values used by glClear to clear the color buffers.
     */
    public static void setClearColor(Vector4f backgroundColor) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        
        ACTUAL_RENDER_CONFIG.setBackgroundColor(backgroundColor);
        
        gl.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, backgroundColor.w);
    }
    
    /**
     * Sets VSync (frame rate is synchronized with monitor) parameter for the entire OpenGL context.
     * @param isActive if true VSync is ON, otherwise false.
     */
    public static void setVSync(boolean isActive) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        
        ACTUAL_RENDER_CONFIG.setvSync(isActive);
        
        if(isActive) {
            gl.setSwapInterval(1);
        } else {
            gl.setSwapInterval(0);
        }
    }
    
    /**
     * Sets blending (applying alpha channel) for the entire OpenGL context.
     * 
     * @param isActive enable/disable blending
     */
    public static void setBlending(boolean isActive) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        
        ACTUAL_RENDER_CONFIG.setBlending(isActive);
        
        // Disable blending if needed
        if(!isActive) {
            gl.glDisable(GL_BLEND);
        } else {
            gl.glEnable(GL_BLEND);
        }      
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
    public static void setDepthTesting(boolean isActive, int depthFunction) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        
        ACTUAL_RENDER_CONFIG.setDepthTesting(isActive);
        ACTUAL_RENDER_CONFIG.setDepthTestingMode(depthFunction);
        
        if(!isActive) {
            gl.glDisable(GL_DEPTH_TEST);
        } else {
            gl.glEnable(GL_DEPTH_TEST);
        }
        
        gl.glDepthFunc(depthFunction);
    }
    
    /**
     * Sets face culling for the entire OpenGL context.
     * <p><strong>Warning:</strong> if isActive is false all other parameters are discarded.</p>
     * 
     * @param isActive           enable/disable face culling
     * 
     * @param isCounterClockwise defines which polygons are going to be skipped
     *                           GL_CCW - affects polygons defined counter clockwise 
     *                           GL_CCW - affects polygons defined clockwise
     *                           
     * @param cullFace           defines which side of the object is going to be
     *                           skipped, whether its a front side or back is being
     *                           defined by isCounterClockwise parameter, available
     *                           options are:
     *                           GL_FRONT - cull face is applied for front polygons
     *                           GL_BACK - cull face is applied for back polygons
     *                           GL_FRONT_AND_BACK - cull face is applied for all polygons
     */
    public static void setFaceCulling(boolean isActive,  int faceCullingDirection, int faceCullingMode) {
        GL4 gl = (GL4) GLContext.getCurrentGL();
        
        ACTUAL_RENDER_CONFIG.setFaceCulling(isActive);
        ACTUAL_RENDER_CONFIG.setFaceCullingDirection(faceCullingDirection);
        ACTUAL_RENDER_CONFIG.setFaceCullingMode(faceCullingMode); 
        
        if(isActive) {
            gl.glEnable(GL_CULL_FACE);
        } else {
            gl.glDisable(GL_CULL_FACE);
        }
        
        gl.glFrontFace(faceCullingDirection);
        
        gl.glCullFace(faceCullingMode);
    }
    
    /**
     * Applies passed config with least count of calls to OpenGL functions.
     * Updates global OpenGL CPU side config
     * 
     * @param renderConfig - a rendering config that is going to be applied to OpenGL
     */
    public static void applyRenderConfig(RenderConfig renderConfig) {
        
        // Each function applies setting
        // if other other setting is currently defined
        // then new setting is written to current config
        checkBackgroundColor(renderConfig);
        checkVSync(renderConfig);
        checkBlending(renderConfig);
        checkDepthTesting(renderConfig);
        checkFaceCulling(renderConfig);
    }
    
    private static void checkBackgroundColor(RenderConfig renderConfig) {
        if (!ACTUAL_RENDER_CONFIG.getBackgroundColor().equals(renderConfig.getBackgroundColor())) {
            setClearColor(renderConfig.getBackgroundColor());
        }
    }
    
    private static void checkVSync(RenderConfig renderConfig) {
        if (hasChanged(ACTUAL_RENDER_CONFIG.isvSync(), renderConfig.isvSync())) {
            setVSync(renderConfig.isvSync());
        }
    }
    
    private static void checkBlending(RenderConfig renderConfig) {
        if (hasChanged(ACTUAL_RENDER_CONFIG.isBlending(), renderConfig.isBlending())) {
            setBlending(renderConfig.isBlending());
        }
    }
    
    private static void checkDepthTesting(RenderConfig renderConfig) {
        if (hasDepthTestingChanged(ACTUAL_RENDER_CONFIG, renderConfig)) {
            setDepthTesting(renderConfig.isDepthTesting(), renderConfig.getDepthTestingMode());
        }
    }
    
    private static void checkFaceCulling(RenderConfig renderConfig) {
        if (hasFaceCullingChanged(ACTUAL_RENDER_CONFIG, renderConfig)) {
            setFaceCulling(renderConfig.isFaceCulling(), 
                           renderConfig.getFaceCullingDirection(), 
                           renderConfig.getFaceCullingMode());
        }
    }

    // Helper method for boolean comparisons
    private static boolean hasChanged(boolean current, boolean desired) {
        return current != desired;
    }

    // Helper method for depth testing checks
    private static boolean hasDepthTestingChanged(RenderConfig current, RenderConfig desired) {
        return hasChanged(current.isDepthTesting(), desired.isDepthTesting()) ||
               current.getDepthTestingMode() != desired.getDepthTestingMode();
    }

    // Helper method for face culling checks
    private static boolean hasFaceCullingChanged(RenderConfig current, RenderConfig desired) {
        return hasChanged(current.isFaceCulling(), desired.isFaceCulling()) ||
               current.getFaceCullingDirection() != desired.getFaceCullingDirection() ||
               current.getFaceCullingMode() != desired.getFaceCullingMode();
    }
}

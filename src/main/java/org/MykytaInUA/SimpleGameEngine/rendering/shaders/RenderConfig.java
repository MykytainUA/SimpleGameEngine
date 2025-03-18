package org.mykytainua.simplegameengine.rendering.shaders;

import static com.jogamp.opengl.GL.GL_BACK;
import static com.jogamp.opengl.GL.GL_CCW;

import org.joml.Vector4f;

public class RenderConfig {
    private Vector4f backgroundColor = new Vector4f(0, 0, 0, 1);
    
    private boolean vSync = true;
    private boolean blending = false;
    
    private boolean depthTesting = false;
    private int depthTestingMode = -1;
    
    private boolean faceCulling = false;
    private int faceCullingDirection = GL_CCW;
    private int faceCullingMode = GL_BACK;
    
    public Vector4f getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Vector4f backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public boolean isvSync() {
        return vSync;
    }
    
    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }
    
    public boolean isBlending() {
        return blending;
    }
    
    public void setBlending(boolean blending) {
        this.blending = blending;
    }
    
    public boolean isDepthTesting() {
        return depthTesting;
    }
    
    public void setDepthTesting(boolean depthTesting) {
        this.depthTesting = depthTesting;
    }
    
    public int getDepthTestingMode() {
        return depthTestingMode;
    }
    
    public void setDepthTestingMode(int depthTestingMode) {
        this.depthTestingMode = depthTestingMode;
    }
    
    public boolean isFaceCulling() {
        return faceCulling;
    }
    
    public void setFaceCulling(boolean faceCulling) {
        this.faceCulling = faceCulling;
    }
    
    public int getFaceCullingDirection() {
        return faceCullingDirection;
    }
    
    public void setFaceCullingDirection(int faceCullingDirection) {
        this.faceCullingDirection = faceCullingDirection;
    }
    
    public int getFaceCullingMode() {
        return faceCullingMode;
    }
    
    public void setFaceCullingMode(int faceCullingMode) {
        this.faceCullingMode = faceCullingMode;
    }
}

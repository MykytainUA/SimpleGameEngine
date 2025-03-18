package org.mykytainua.simplegameengine.rendering.shaders;

import org.mykytainua.simplegameengine.settings.OpenGLSettings;

public class RenderSetting {
    
    private final RenderConfig RENDER_CONFIG = new RenderConfig();
    
    public RenderSetting () {}  
    
    public boolean isVSync() {
        return RENDER_CONFIG.isvSync();
    }
    
    public RenderSetting setVSync(boolean active) {
        RENDER_CONFIG.setvSync(active);
        return this;
    }
    
    public boolean isBlending() {
        return RENDER_CONFIG.isBlending();
    }
    
    public RenderSetting setBlending(boolean active) {
        RENDER_CONFIG.setBlending(active);
        return this;
    }
    
    public boolean isDepthTesting() {
        return RENDER_CONFIG.isDepthTesting();
    }
    
    public int getDepthTestingMode() {       
        return RENDER_CONFIG.getDepthTestingMode();
    }
    
    public RenderSetting setDepthTesting(boolean active, int depthTestingMode) {
        RENDER_CONFIG.setDepthTesting(active);
        RENDER_CONFIG.setDepthTestingMode(depthTestingMode);
        return this;
    }
    
    public boolean isFaceCulling() {
        return  RENDER_CONFIG.isFaceCulling();
    }
    
    public RenderSetting setFaceCulling(boolean active, int faceCullingDirection, int faceCullingMode) {
        RENDER_CONFIG.setFaceCulling(active);
        RENDER_CONFIG.setFaceCullingDirection(faceCullingDirection);
        RENDER_CONFIG.setFaceCullingMode(faceCullingMode);
        return this;
    }
    
    public void applyRenderSetting() {
        OpenGLSettings.applyRenderConfig(this.RENDER_CONFIG);
    }
}

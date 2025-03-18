package org.mykytainua.simplegameengine.rendering.shaders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.ShaderComponent;

public class ShaderProgram {
    
    private static final String SHADER_DIR = "./src/main/java/shaders/";
    private static final String VERTEX_SHADER = "vertexShader.glsl";
    private static final String FRAGMENT_SHADER = "fragmentShader.glsl";
    
    // OpenGL settigs of current shader program
    private final RenderSetting renderSetting;
    
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
    public ShaderProgram(RenderSetting renderSetting, Camera camera) {
        Objects.requireNonNull(camera, "ERROR:Camera must not be null in a shader!!!");
        this.renderSetting = renderSetting;
        this.renderSetting.applyRenderSetting();
        this.camera = camera;
    }
    
    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
    
    public void addObject(Object3D object) {    
        
        List<Class<? extends ShaderComponent>> shaderComponentClasses = object.getShaderComponentsClasses();
        Shader shader = this.findCompatibleShader(shaderComponentClasses);
        
        // When no appropriate shader were found
        // create a new one
        if(shader == null) {
            shader = this.buildShader(object.getShaderComponents());
        }
                
        shader.addObject(object);
    }

    public void render() {
        
        this.renderSetting.applyRenderSetting();
        
        for (Shader shader : shaders) {
            shader.render();
        }
    }
    
    private Shader findCompatibleShader(List<Class<? extends ShaderComponent>> shaderComponentClasses) {
        
        for(Shader shader : shaders) {
            if(shader.isComponentCompatible(shaderComponentClasses)) {
                return shader;
            }
        }
        return null;
    }
    
    private Shader buildShader(List<ShaderComponent> shaderComponents) {
        
        ShaderBuilder shaderBuilder = new ShaderBuilder()
                .setVertexShaderByPath(SHADER_DIR + VERTEX_SHADER)
                .setFragmentShaderByPath(SHADER_DIR + FRAGMENT_SHADER)
                .setComponentsForShader(shaderComponents);
        
        Shader shader = shaderBuilder.getShader();
        shader.setCamera(camera);
        
        this.shaders.add(shader);
        
        return shader;
    }
    
    public String toString() {
        String shaderProgramRepresentation = "ShaderProgram " 
                + Integer.toHexString(System.identityHashCode(this)) + ":"  
                + "\nShaders count:" + shaders.size() 
                + "\nSHADER_DIR: " + SHADER_DIR
                + "\nVERTEX_SHADER: " + VERTEX_SHADER
                + "\nFRAGMENT_SHADER: " + FRAGMENT_SHADER;
        
        for (Shader shader : shaders) {
            shaderProgramRepresentation += "\n" + shader.toString();
        }
        
        return shaderProgramRepresentation;
    }
}
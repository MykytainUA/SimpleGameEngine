package org.mykytainua.simplegameengine.rendering.shaders;

import java.util.ArrayList;
import java.util.List;

import org.mykytainua.simplegameengine.objects.components.ShaderComponent;

public class ShaderBuilder {
    // StaticShader source code variables
    private String[] vertexShaderSource;
    private String[] fragmentShaderSource;

    // List of components classes those are used in the shader
    private List<ShaderComponent> componentsForShader = new ArrayList<ShaderComponent>();
    
    public Shader getShader() {
        this.vertexShaderSource = addDefines(vertexShaderSource);
        this.fragmentShaderSource = addDefines(fragmentShaderSource);
        
        List<Class<? extends ShaderComponent>> componentsForShader = new ArrayList<Class<? extends ShaderComponent>>();
        
        for (ShaderComponent component : this.componentsForShader) {
            componentsForShader.add(component.getClass());
        }
        
        return new StaticShader(vertexShaderSource, fragmentShaderSource, componentsForShader, this.componentsForShader);
    }

    /**
     * This method processes the shader source code and adds necessary preprocessor
     * defines for the components being used in the shader.
     *
     * @param source the original shader source code
     * @return the shader source code with added preprocessor defines
     */
    private String[] addDefines(String[] source) {

        List<String> sourceCodeAsList = new ArrayList<String>();

        // Convert the source array to a list for easier manipulation
        for (int i = 0; i < source.length; i++) {
            sourceCodeAsList.add(source[i]);
        }

        // Locate the macro placeholder in the source code
        int macroPlaceholderIndex = -1;
        for (String lineOfCode : sourceCodeAsList) {
            if (lineOfCode.contains("%ADD_DEFINES%")) {
                macroPlaceholderIndex = sourceCodeAsList.indexOf(lineOfCode);
            }
        }
        
        for (ShaderComponent shaderComponent : this.componentsForShader) {
            String preprocDirective = "#define " + shaderComponent.getPreprocessorDefine() + "\n";
            
            sourceCodeAsList.add(macroPlaceholderIndex + 1, preprocDirective);
            shaderComponent.getPreprocessorDefine();
        }

        // Return the modified source code as an array
        return sourceCodeAsList.toArray(new String[0]);
    }
    
    public ShaderBuilder setVertexShaderSource(String[] source) {
        this.vertexShaderSource = source;
        return this;
    }
    
    public ShaderBuilder setVertexShaderByPath(String pathToShader) {
        this.vertexShaderSource = ShaderUtilities.getShaderSourceAsString(pathToShader);
        return this;
    }
    
    public ShaderBuilder setFragmentShaderSource(String[] source) {
        this.fragmentShaderSource = source;
        return this;
    }
    
    public ShaderBuilder setFragmentShaderByPath(String pathToShader) {
        this.fragmentShaderSource = ShaderUtilities.getShaderSourceAsString(pathToShader);
        return this;
    }
    
    public ShaderBuilder setComponentsForShader(List<ShaderComponent> componentsForShader) {
        this.componentsForShader = componentsForShader;
        
        return this;
    }
}

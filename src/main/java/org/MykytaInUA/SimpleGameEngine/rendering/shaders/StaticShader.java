package org.mykytainua.simplegameengine.rendering.shaders;

import java.util.List;
import org.mykytainua.simplegameengine.objects.components.ShaderComponent;

public class StaticShader extends Shader{

    public StaticShader(String vertexShaderPath, 
                        String fragmentShaderPath, 
                        List<Class<? extends ShaderComponent>> componentsClasses,
                        List<ShaderComponent> components) {
        super(componentsClasses, components);
        this.shaderID = createShaderByPath(vertexShaderPath, fragmentShaderPath);
        this.localUniforms = new UniformsStorage(this.shaderID);
    }

    public StaticShader(String[] vertexShaderPath, 
                  String[] fragmentShaderPath, 
                  List<Class<? extends ShaderComponent>> componentsClasses,
                  List<ShaderComponent> components) {
        super(componentsClasses, components);
        this.shaderID = createShaderBySource(vertexShaderPath, fragmentShaderPath);
        this.localUniforms = new UniformsStorage(this.shaderID);
    }
}

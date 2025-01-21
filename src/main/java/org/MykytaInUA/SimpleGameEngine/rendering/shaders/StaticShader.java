package org.mykytainua.simplegameengine.rendering.shaders;

import java.util.List;
import org.mykytainua.simplegameengine.objects.components.Component;

public class StaticShader extends Shader{

    public StaticShader(String vertexShaderPath, 
                        String fragmentShaderPath, 
                        List<Class<? extends Component>> components) {
        super(components);
        this.shaderID = createShaderByPath(vertexShaderPath, fragmentShaderPath);
        this.localUniforms = new UniformsStorage(this.shaderID);
    }

    public StaticShader(String[] vertexShaderPath, 
                  String[] fragmentShaderPath, 
                  List<Class<? extends Component>> components) {
        super(components);
        this.shaderID = createShaderBySource(vertexShaderPath, fragmentShaderPath);
        this.localUniforms = new UniformsStorage(this.shaderID);
    }
}

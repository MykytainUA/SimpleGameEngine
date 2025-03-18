package org.mykytainua.simplegameengine.objects.components;

import java.nio.ByteBuffer;

public interface ShaderComponent extends Component{  
    /**
     * Retrieves the preprocessor define for this shader component.
     * Example:#define POSITION_COMPONENT
     * @return a string representing a define in shader.
     */
    public String getPreprocessorDefine();
    
    public ByteBuffer getComponentData(String name);
}

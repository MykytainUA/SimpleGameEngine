package org.mykytainua.simplegameengine.objects.components;

import java.util.Map;

import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.global.AttributeDefinition;

public class ComponentMetadata {
    private final ComponentLayout layout;
    private final int sizeInBytes;
    
    public ComponentMetadata(ComponentLayout layout) {
        this.layout = layout;
        this.sizeInBytes = this.layout.getFixedDataSize();
    }
    
    public AttributeDefinition getDataType(String parameterName) {
        return this.layout.getParameter(parameterName);
    }
    
    public int getPredefinedParameterBytesCount() {
        return this.sizeInBytes;
    }
    
    public int getBytePositionOfPredefinedParameter(String parameterName) throws IllegalArgumentException {  
        return this.layout.getPositionOfPredefinedParameter(parameterName);
    }
    
    public Map <String, AttributeDefinition> getParameters(){
        return this.layout.getParameters();
    }
}

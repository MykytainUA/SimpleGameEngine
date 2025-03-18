package org.mykytainua.simplegameengine.objects.components;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.global.AttributeDefinition;

public class ComponentLayout {
    
    private Map <String, AttributeDefinition> parameters;
    
    public ComponentLayout(AttributeDefinition... parameters) { 
        this.parameters = new LinkedHashMap<String, AttributeDefinition>(parameters.length);
        
        for(AttributeDefinition parameter : parameters) {
            this.parameters.put(parameter.getName(), parameter);
        }
    } 
    
    public AttributeDefinition getParameter(String parameterName) {
        return parameters.get(parameterName);
    }
    
    public int getFixedDataSize() {
        int byteCount = 0;
        
        for (Entry<String, AttributeDefinition> entry : parameters.entrySet()) {
            if(!entry.getValue().dataType().isBufferType()) {
                byteCount += entry.getValue().dataType().getByteSize();
            }   
        }
        
        return byteCount;
    }
    
    public int getPositionOfPredefinedParameter(String parameterName) throws IllegalArgumentException {
        int position = 0;
        boolean isComponentFound = false;
        
        for (Entry<String, AttributeDefinition> entry : parameters.entrySet()) {
            
            if(entry.getValue().dataType().isBufferType()) {
                continue;
            }
            
            if(entry.getKey().equals(parameterName)) {
                isComponentFound = true;
                break;
            }
            
            position += entry.getValue().dataType().getByteSize();
        }
        
        if(!isComponentFound) {
            throw new IllegalArgumentException(
                    "Trying to get parameter that is not available from component:" + parameterName
                );
        }
        return position;
    }   
    
    public Map <String, AttributeDefinition> getParameters(){
        return Collections.unmodifiableMap(this.parameters);
    }
}

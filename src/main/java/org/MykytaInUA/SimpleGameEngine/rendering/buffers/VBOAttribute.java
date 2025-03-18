package org.mykytainua.simplegameengine.rendering.buffers;

import org.mykytainua.simplegameengine.global.DataType;

public class VBOAttribute extends VBOWrapper{
    
  private final DataType dataType;
  private final String dataName;
    
    public VBOAttribute(DataType dataType, String dataName) {
        super();
        this.dataType = dataType;
        this.dataName = dataName;
    }
    
    public DataType getDataType() {
        return this.dataType;
    }
    
    public String getDataName() {
        return this.dataName;
    }
}

package org.mykytainua.simplegameengine.objects;

import java.nio.ByteBuffer;

import org.joml.Vector3f;
import org.mykytainua.simplegameengine.global.DataType;

public interface DataProvider {
    
    /**
     * Returns read only buffer that contains 
     * all data of an object
     * @param dataSizeInBytes
     */
    public ByteBuffer getAllData();
    
    public void setRawData(ByteBuffer data, String parameterName);
    public ByteBuffer getRawData(String parameterName);

    public ByteBuffer getRawData(DataType dataType, String parameterName);
}

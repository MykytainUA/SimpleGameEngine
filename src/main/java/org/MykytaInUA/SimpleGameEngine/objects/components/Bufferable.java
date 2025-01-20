package org.mykytainua.simplegameengine.objects.components;

import java.nio.ByteBuffer;

public interface Bufferable {
    
    /**
     * Retrieves the total size of the data associated with this component.
     *
     * @return the total number of bytes of data for the component.
     */
    public int getTotalDataSize();
    
    /**
     * Retrieves the size of the data associated with this component per vertex.
     *
     * @return the number of bytes of data per vertex.
     */
    public int getDataPerVertexSize();
    
    /**
     * Writes the component's data to the specified buffer.
     *
     * @param destinationBuffer the buffer where the component's data will be
     *                          written.
     */
    public void writeComponentDataToBuffer(ByteBuffer destinationBuffer);
}

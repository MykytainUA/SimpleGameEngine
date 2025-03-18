package org.mykytainua.simplegameengine.utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ResizableByteBuffer {
    private ByteBuffer resizableBuffer;
    
    public ResizableByteBuffer(int initialCapacity, ByteOrder byteOrder) {
        this.resizableBuffer = ByteBuffer.allocate(initialCapacity).order(byteOrder);
    }
    
    public int getLimit() {
        return resizableBuffer.limit();
    }
    
    public int getCapacity() {
        return resizableBuffer.capacity();
    }
    
    public void put(int index, ByteBuffer src) {
        this.ensureBufferCapacity(index + src.limit());
        this.resizableBuffer.put(index, src, 0, src.limit());
    }
    
    public void append(ByteBuffer src) {
        int firstFreeBytePosition = resizableBuffer.limit();
        
        ByteBuffer savedData = this.resizableBuffer;
        
        this.ensureBufferCapacity(resizableBuffer.capacity() + src.limit());
        
        this.resizableBuffer.put(0, savedData, 0, savedData.limit());
        
        this.resizableBuffer.put(firstFreeBytePosition, src, 0, src.limit());
    }
    
    public ByteBuffer get(int index, int size) {
        return resizableBuffer.slice(index, size);
    }
    
    /**
     * Ensures that the current buffer has enough capacity to hold the required
     * number of bytes.
     *
     * @param requiredCapacity the number of bytes needed
     */
    private void ensureBufferCapacity(int requiredCapacity) {
        // If the current buffer doesn't have enough capacity, allocate a new buffer
        if (!bufferHasEnoughCapacity(requiredCapacity)) {
            this.resizableBuffer = ByteBuffer.allocate(requiredCapacity)
                                             .order(resizableBuffer.order());
        }
        this.resizableBuffer.rewind();
    }

    /**
     * Checks if the current buffer has enough capacity for the required number of
     * bytes.
     *
     * @param neededCapacity the required capacity in bytes
     * @return true if the buffer has enough capacity, false otherwise
     */
    private boolean bufferHasEnoughCapacity(int neededCapacity) {
        return resizableBuffer.capacity() >= neededCapacity;
    }
}

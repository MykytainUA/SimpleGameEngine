package org.mykytainua.simplegameengine.utilities;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class BufferUtils {
    public static ByteBuffer toDirectByteBuffer(ByteBuffer buff) {
        buff.order(ByteOrder.nativeOrder());
        
        
        ByteBuffer directBuff = ByteBuffer.allocateDirect(buff.limit()).order(ByteOrder.nativeOrder());
        
        while(buff.hasRemaining()) {
            directBuff.put(buff.get());
        }
        
        directBuff.flip();
        
        return directBuff;
    }
}

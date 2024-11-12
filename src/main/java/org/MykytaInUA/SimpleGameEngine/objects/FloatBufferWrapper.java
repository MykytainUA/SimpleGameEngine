package org.MykytaInUA.SimpleGameEngine.objects;

import java.nio.FloatBuffer;

import com.jogamp.common.nio.Buffers;

// Stores cubes as buffers, indices must be kept inside cubes

public class FloatBufferWrapper {
	
	private FloatBuffer dataBuffer;
	
	public FloatBufferWrapper() {
		
		dataBuffer = Buffers.newDirectFloatBuffer(new float[0]);
		
	}
	
	public FloatBuffer getBuffer() {
		
		return this.dataBuffer;
		
	}
	
	public void updateAllContents(float[] dataToBuffer) {
		
		try {
			dataBuffer.clear();
			
			if (dataBuffer.capacity() < dataToBuffer.length) {
				dataBuffer = Buffers.newDirectFloatBuffer(dataToBuffer.length);
			} 
			
			dataBuffer.put(dataToBuffer);
			dataBuffer.flip();
			
		} catch (Exception e) {
			System.out.println(e);	
		}
		
	}
}

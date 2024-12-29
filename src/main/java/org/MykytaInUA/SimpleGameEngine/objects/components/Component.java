package org.MykytaInUA.SimpleGameEngine.objects.components;

import java.nio.ByteBuffer;

public interface Component {
	public Component deepCopy();
	
	/**
	 * For example: 
	 * SolidColorComponent(0,0,0,0) will return 4
	 * Position component (0,0,0) will return 3
	 * @return Count of parameters per vertex 
	 */
	public int getDataPerVertexSize();
	
	/**
	 * For example: 
	 * SolidColorComponent(0,0,0,0) will return 4*4 = 16
	 * Position component (0,0,0) will return 3*4 = 12
	 * @return Bytes that occupies this component data
	 */
	public int getTotalDataSize();
	
	public void writeComponentDataToBuffer(ByteBuffer destinationBuffer);
}

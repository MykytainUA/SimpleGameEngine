package org.MykytaInUA.SimpleGameEngine.objects.components;

public interface Component {
	public Component deepCopy();
	
	/**
	 * For example: 
	 * SolidColorComponent(0,0,0,0) will return 4
	 * Position component (0,0,0) will return 3
	 * @return Count of parameters per vertex 
	 */
	public int getDataPerVertexSize();
}

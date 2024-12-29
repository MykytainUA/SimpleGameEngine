package org.MykytaInUA.SimpleGameEngine.objects.components.transform;

import org.joml.Vector3f;

import java.nio.ByteBuffer;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class PositionComponent implements TransformComponent{
	
	private Vector3f position;
	
	private final static int DATA_PER_VERTEX_SIZE = 3;
	private final static int TOTAL_DATA_SIZE = 12;
	
	public static final String ATTRIBUTE_POINTER_NAME = "instancePosition";
	
	public PositionComponent(Vector3f position) {
		this.position = position;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	@Override
	public Component deepCopy() {
		return new PositionComponent(new Vector3f(this.position));
	}

	@Override
	public int getDataPerVertexSize() {
		return DATA_PER_VERTEX_SIZE;
	}

	@Override
	public int getTotalDataSize() {
		return TOTAL_DATA_SIZE;
	}

	@Override
	public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
		destinationBuffer.putFloat(this.getPosition().x);
		destinationBuffer.putFloat(this.getPosition().y);
        destinationBuffer.putFloat(this.getPosition().z);
		
	}
}

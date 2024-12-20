package org.MykytaInUA.SimpleGameEngine.objects.components.transform;

import org.joml.Vector3f;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class PositionComponent implements TransformComponent{
	private Vector3f position;
	
	private final static int DATA_PER_VERTEX_SIZE = 3;
	
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
		// TODO Auto-generated method stub
		return DATA_PER_VERTEX_SIZE;
	}
}

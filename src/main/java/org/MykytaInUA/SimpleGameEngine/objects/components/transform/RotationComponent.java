package org.MykytaInUA.SimpleGameEngine.objects.components.transform;

import org.joml.Vector3f;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class RotationComponent implements TransformComponent{
	private Vector3f rotation;
	
	private final static int DATA_PER_VERTEX_SIZE = 3;
	
	public static final String ATTRIBUTE_POINTER_NAME = "instanceRotation";
	
	public RotationComponent(Vector3f rotation) {
		this.rotation = rotation;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	@Override
	public Component deepCopy() {
		return new RotationComponent(new Vector3f(this.rotation));
	}

	@Override
	public int getDataPerVertexSize() {
		return DATA_PER_VERTEX_SIZE;
	}
}

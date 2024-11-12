package org.MykytaInUA.SimpleGameEngine.objects.components.transform;

import org.joml.Vector3f;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class RotationComponent implements TransformComponent{
	private Vector3f rotation;
	
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
}

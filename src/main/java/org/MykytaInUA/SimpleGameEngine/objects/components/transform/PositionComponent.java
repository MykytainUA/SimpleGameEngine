package org.MykytaInUA.SimpleGameEngine.objects.components.transform;

import org.joml.Vector3f;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class PositionComponent implements TransformComponent{
	private Vector3f position;
	
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
}

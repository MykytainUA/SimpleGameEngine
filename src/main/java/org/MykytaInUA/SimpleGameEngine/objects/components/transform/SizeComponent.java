package org.MykytaInUA.SimpleGameEngine.objects.components.transform;

import org.joml.Vector3f;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class SizeComponent implements TransformComponent{
	private Vector3f size;
	
	public SizeComponent(Vector3f size) {
		this.size = size;
	}

	public Vector3f getSize() {
		return size;
	}

	public void setSize(Vector3f size) {
		this.size = size;
	}
	
	@Override
	public Component deepCopy() {
		return new SizeComponent(new Vector3f(this.size));
	}
}

package org.MykytaInUA.SimpleGameEngine.objects.components.transform;

import org.joml.Vector3f;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class SizeComponent implements TransformComponent{
	private Vector3f size;
	
	private final static int DATA_PER_VERTEX_SIZE = 3;
	
	public static final String ATTRIBUTE_POINTER_NAME = "instanceSize";
	
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

	@Override
	public int getDataPerVertexSize() {
		// TODO Auto-generated method stub
		return DATA_PER_VERTEX_SIZE;
	}
}

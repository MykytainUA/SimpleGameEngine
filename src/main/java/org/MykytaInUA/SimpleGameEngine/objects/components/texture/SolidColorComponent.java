package org.MykytaInUA.SimpleGameEngine.objects.components.texture;

import org.joml.Vector4f;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class SolidColorComponent implements RenderMaterialComponent{
	private Vector4f color;
	
	public final static int DATA_PER_VERTEX_SIZE = 4;
	
	public static final String ATTRIBUTE_POINTER_NAME = "instanceColor";
	
	public SolidColorComponent(Vector4f color) {
		this.color = color;
	}

	public Vector4f getColor() {
		return color;
	}

	public void setColor(Vector4f color) {
		this.color = color;
	}

	@Override
	public Component deepCopy() {
		return new SolidColorComponent(new Vector4f(this.color));
	}

	@Override
	public int getDataPerVertexSize() {
		return DATA_PER_VERTEX_SIZE;
	}
}

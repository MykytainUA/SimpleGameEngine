package org.MykytaInUA.SimpleGameEngine.objects.components.texture;

import org.joml.Vector4f;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class SolidColorComponent implements RenderMaterialComponent{
	private Vector4f color;
	
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
}

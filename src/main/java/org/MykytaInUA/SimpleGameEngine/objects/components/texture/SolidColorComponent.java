package org.MykytaInUA.SimpleGameEngine.objects.components.texture;

import org.joml.Vector4f;

import java.nio.ByteBuffer;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class SolidColorComponent implements RenderMaterialComponent{
	
	private Vector4f color;
	
	public final static int DATA_PER_VERTEX_SIZE = 4;
	private final static int TOTAL_DATA_SIZE = 16;
	
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

	@Override
	public int getTotalDataSize() {
		// TODO Auto-generated method stub
		return TOTAL_DATA_SIZE;
	}

	@Override
	public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
		destinationBuffer.putFloat(this.getColor().x);
		destinationBuffer.putFloat(this.getColor().y);
		destinationBuffer.putFloat(this.getColor().z);
		destinationBuffer.putFloat(this.getColor().w);
	}
}

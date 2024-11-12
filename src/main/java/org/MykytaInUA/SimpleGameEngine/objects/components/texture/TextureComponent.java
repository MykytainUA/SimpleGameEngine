package org.MykytaInUA.SimpleGameEngine.objects.components.texture;

import java.util.Arrays;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class TextureComponent implements RenderMaterialComponent{
	
	private float[] textureCoordinates;
	private int textureID;
	
	public TextureComponent(int textureID, float[] textureCoordinates) {
		this.textureCoordinates = textureCoordinates;
		this.textureID = textureID;
	}
	
	public float[] getTextureCoordinates() {
		return textureCoordinates;
	}
	
	public void setTextureCoordinates(float[] textureCoordinates) {
		this.textureCoordinates = textureCoordinates;
	}

	public int getTextureID() {
		return textureID;
	}
	
	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	@Override
	public Component deepCopy() {
		float[] copiedTexturecoordinates = Arrays.copyOf(this.textureCoordinates, this.textureCoordinates.length);
		return new TextureComponent(this.textureID, copiedTexturecoordinates);
	}
}

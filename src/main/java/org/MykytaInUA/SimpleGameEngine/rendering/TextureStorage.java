package org.MykytaInUA.SimpleGameEngine.rendering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureStorage {
	
	private Map<String, Integer> texturesMap = new HashMap<String, Integer>();	
	
	public void loadTexture(String pathToTexture, String textureName) {
		Texture texture = null;
		
		try {
			File imageFile = new File(pathToTexture);
			texture = TextureIO.newTexture(imageFile, false);
			texture.getTextureObject();
			this.putTextureIntoMap(textureName, texture.getTextureObject());
		} catch (Exception e) {
			System.out.println("Error loading texture:" + e.getMessage());
		}
		
	}
	
	public void putTextureIntoMap(String textureName, int textureLocation) {
		if(textureLocation != 0) {
			this.texturesMap.put(textureName, textureLocation);		
		}
	}
	
	public int getTextureLocation(String textureName) {
		return this.texturesMap.get(textureName);
	}
}

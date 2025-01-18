package org.mykytainua.simplegameengine.rendering;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * The TextureStorage class manages textures by loading them from files and
 * storing them in a map. It allows textures to be retrieved by their names and
 * loaded from the file system.
 */
public class TextureStorage {

    // A map to store textures by their names, with the texture name as the key and
    // its OpenGL texture ID as the value
    private Map<String, Integer> texturesMap = new HashMap<String, Integer>();

    /**
     * Loads a texture from a file and stores it in the textures map with a
     * specified name.
     *
     * @param pathToTexture The file path to the texture image
     * @param textureName   The name to associate with the loaded texture
     */
    public void loadTexture(String pathToTexture, String textureName) {
        Texture texture = null;

        try {
            // Create a File object from the provided texture file path
            File imageFile = new File(pathToTexture);

            // Load the texture from the image file
            texture = TextureIO.newTexture(imageFile, false);

            // Retrieve the texture's OpenGL texture ID
            texture.getTextureObject();

            // Store the texture in the map using the provided name
            this.putTextureIntoMap(textureName, texture.getTextureObject());
        } catch (Exception e) {
            // Print error message if loading the texture fails
            System.out.println("Error loading texture:" + e.getMessage());
        }
    }

    /**
     * Puts a texture into the map with its associated name and OpenGL texture
     * location.
     *
     * @param textureName     The name of the texture
     * @param textureLocation The OpenGL texture ID
     */
    public void putTextureIntoMap(String textureName, int textureLocation) {
        // Only add the texture to the map if the texture location is non-zero (valid)
        if (textureLocation != 0) {
            this.texturesMap.put(textureName, textureLocation);
        }
    }

    /**
     * Retrieves the OpenGL texture location associated with a given texture name.
     *
     * @param textureName The name of the texture
     * @return The OpenGL texture ID associated with the texture name
     */
    public int getTextureLocation(String textureName) {
        // Return the texture location from the map, or 0 if not found
        return this.texturesMap.get(textureName);
    }
}

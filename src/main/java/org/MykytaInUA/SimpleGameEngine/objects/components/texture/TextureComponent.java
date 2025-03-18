package org.mykytainua.simplegameengine.objects.components.texture;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.global.AttributeDefinition;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.ComponentLayout;
import org.mykytainua.simplegameengine.objects.components.ComponentMetadata;
import org.mykytainua.simplegameengine.objects.components.ShaderComponent;
import org.mykytainua.simplegameengine.rendering.OpenGLBufferType;

/**
 * The {@code TextureComponent} class represents a material component that
 * defines a texture for rendering 3D objects.
 *
 * <p>This component stores texture coordinates and a texture ID, which are used
 * to apply textures to objects in the rendering pipeline. Texture coordinates
 * specify how the texture maps to the surface of the object.</p>
 *
 * <p>Implements the {@link RenderMaterialComponent} interface to provide
 * rendering material functionality for 3D rendering engines.</p>
 */
public class TextureComponent implements RenderMaterialComponent,
                                         ShaderComponent {
    
    private static final ComponentMetadata METADATA;
    
    static {
        METADATA = new ComponentMetadata(new ComponentLayout(
                new AttributeDefinition(DataType.INT, 
                                        "textureID", 
                                        OpenGLBufferType.VBO,
                                        false),
                
                new AttributeDefinition(DataType.FLOAT_BUFFER, 
                                        "textureCoordinates", 
                                        OpenGLBufferType.VBO,
                                        false)));
    }

    /**
     * An array of texture coordinates for the object.
     */
    private float[] textureCoordinates;

    /**
     * The ID of the texture used for rendering.
     */
    private int textureId;

    /**
     * Constructs a {@code TextureComponent} with the specified texture ID and
     * texture coordinates.
     *
     * @param textureId          the unique identifier of the texture
     * @param textureCoordinates an array of texture coordinates
     */
    public TextureComponent(int textureId, float[] textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
        this.textureId = textureId;
    }

    /**
     * Returns the texture coordinates for this component.
     *
     * @return an array of texture coordinates
     */
    public float[] getTextureCoordinates() {
        return textureCoordinates;
    }

    /**
     * Sets the texture coordinates for this component.
     *
     * @param textureCoordinates an array of new texture coordinates
     */
    public void setTextureCoordinates(float[] textureCoordinates) {
        this.textureCoordinates = textureCoordinates;
    }

    /**
     * Returns the texture ID associated with this component.
     *
     * @return the texture ID
     */
    public int getTextureId() {
        return textureId;
    }

    /**
     * Sets the texture ID for this component.
     *
     * @param textureId the new texture ID
     */
    public void setTextureId(int textureId) {
        this.textureId = textureId;
    }

    /**
     * Creates a deep copy of this component, ensuring its state is fully cloned.
     *
     * @return a new {@code TextureComponent} with the same texture ID and copied
     *         texture coordinates
     */
    @Override
    public Component deepCopy() {
        float[] copiedTextureCoordinates = Arrays.copyOf(this.textureCoordinates, 
                                                         this.textureCoordinates.length);
        return new TextureComponent(this.textureId, copiedTextureCoordinates);
    }

    @Override
    public String getPreprocessorDefine() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ComponentMetadata getComponentMetadata() {
        // TODO Auto-generated method stub
        return METADATA;
    }
    
    @Override
    public ByteBuffer getComponentData(String name) {
        return null;
    }
}

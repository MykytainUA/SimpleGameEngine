package org.mykytainua.simplegameengine.objects.components.texture;

import java.nio.ByteBuffer;
import org.joml.Vector4f;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * The {@code SolidColorComponent} class represents a material component that
 * defines a solid color for rendering 3D objects.
 *
 * <p>This component is used to assign a specific color to an object in a
 * rendering pipeline. The color is defined using a {@link Vector4f}, which
 * includes red, green, blue, and alpha (transparency) channels.</p>
 *
 * <p>Implements the {@link RenderMaterialComponent} interface to provide
 * functionality for rendering materials in the context of a 3D engine.</p>
 */
public class SolidColorComponent implements RenderMaterialComponent {

    /**
     * The color represented as a {@link Vector4f} with RGBA channels.
     */
    private Vector4f color;

    /**
     * The size of data per vertex, in number of floats (4 floats for RGBA).
     */
    public static final int DATA_PER_VERTEX_SIZE = 4;

    /**
     * The total size of the color data, in bytes (4 floats, each 4 bytes).
     */
    private static final int TOTAL_DATA_SIZE = 16;

    /**
     * The name of the attribute pointer associated with the color data in the
     * shader program.
     */
    public static final String ATTRIBUTE_POINTER_NAME = "instanceColor";

    /**
     * Constructs a {@code SolidColorComponent} with the specified color.
     *
     * @param color the RGBA color as a {@link Vector4f}
     */
    public SolidColorComponent(Vector4f color) {
        this.color = color;
    }

    /**
     * Returns the RGBA color of this component.
     *
     * @return the color as a {@link Vector4f}
     */
    public Vector4f getColor() {
        return color;
    }

    /**
     * Sets the RGBA color for this component.
     *
     * @param color the new color as a {@link Vector4f}
     */
    public void setColor(Vector4f color) {
        this.color = color;
    }

    /**
     * Creates a deep copy of this component, ensuring its state is fully cloned.
     *
     * @return a new {@code SolidColorComponent} with the same color
     */
    @Override
    public Component deepCopy() {
        return new SolidColorComponent(new Vector4f(this.color));
    }

    /**
     * Returns the size of the data per vertex in terms of floats (4 floats for
     * RGBA).
     *
     * @return the number of floats per vertex
     */
    @Override
    public int getDataPerVertexSize() {
        return DATA_PER_VERTEX_SIZE;
    }

    /**
     * Returns the total size of the color data in bytes.
     *
     * @return the size of the data in bytes
     */
    @Override
    public int getTotalDataSize() {
        return TOTAL_DATA_SIZE;
    }

    /**
     * Writes the RGBA color data to the provided {@link ByteBuffer}.
     *
     * @param destinationBuffer the {@link ByteBuffer} to write data to
     */
    @Override
    public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
        destinationBuffer.putFloat(this.getColor().x);
        destinationBuffer.putFloat(this.getColor().y);
        destinationBuffer.putFloat(this.getColor().z);
        destinationBuffer.putFloat(this.getColor().w);
    }

    /**
     * Returns the name of the attribute pointer associated with the color data.
     *
     * @return the attribute pointer name
     */
    @Override
    public String getAttrubutePointerName() {
        return ATTRIBUTE_POINTER_NAME;
    }
}

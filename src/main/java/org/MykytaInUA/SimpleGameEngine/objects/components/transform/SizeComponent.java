package org.mykytainua.simplegameengine.objects.components.transform;

import java.nio.ByteBuffer;
import org.joml.Vector3f;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * The {@code SizeComponent} class represents the size of a 3D object in terms
 * of its width, height, and depth.
 * 
 * <p>This component is used to store size information and provides
 * functionality to interact with rendering buffers and shaders. It implements
 * the {@link Transform} interface, making it suitable for
 * transformation-related operations within the game engine.</p>
 * 
 */
public class SizeComponent implements Transform {

    private Vector3f size;

    private static final int DATA_PER_VERTEX_SIZE = 3;
    private static final int TOTAL_DATA_SIZE = 12;

    public static final String ATTRIBUTE_POINTER_NAME = "instanceSize";

    /**
     * Constructs a {@code SizeComponent} with the specified size vector.
     *
     * @param size a {@link Vector3f} representing the width, height, and depth of
     *             the object.
     */
    public SizeComponent(Vector3f size) {
        this.size = size;
    }

    /**
     * Retrieves the size vector.
     *
     * @return a {@link Vector3f} representing the width, height, and depth of the
     *         object.
     */
    public Vector3f getSize() {
        return size;
    }

    /**
     * Updates the size vector.
     *
     * @param size a {@link Vector3f} representing the new width, height, and depth
     *             of the object.
     */
    public void setSize(Vector3f size) {
        this.size = size;
    }

    /**
     * Creates a deep copy of this {@code SizeComponent}.
     *
     * @return a new {@code SizeComponent} instance with the same size vector as
     *         this instance.
     */
    @Override
    public Component deepCopy() {
        return new SizeComponent(new Vector3f(this.size));
    }

    /**
     * Retrieves the size of the data per vertex in this component.
     *
     * @return the size of the data per vertex, in bytes.
     */
    @Override
    public int getDataPerVertexSize() {
        return DATA_PER_VERTEX_SIZE;
    }

    /**
     * Retrieves the total size of the data in this component.
     *
     * @return the total data size, in bytes.
     */
    @Override
    public int getTotalDataSize() {
        return TOTAL_DATA_SIZE;
    }

    /**
     * Writes the size data to the specified {@link ByteBuffer}.
     *
     * @param destinationBuffer the {@link ByteBuffer} where the size data will be
     *                          written.
     */
    @Override
    public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
        destinationBuffer.putFloat(this.getSize().x);
        destinationBuffer.putFloat(this.getSize().y);
        destinationBuffer.putFloat(this.getSize().z);
    }

    /**
     * Retrieves the name of the attribute pointer used in shaders for this
     * component.
     *
     * @return a {@link String} representing the attribute pointer name.
     */
    @Override
    public String getAttrubutePointerName() {
        return ATTRIBUTE_POINTER_NAME;
    }
}

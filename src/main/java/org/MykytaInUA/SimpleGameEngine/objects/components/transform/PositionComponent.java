package org.mykytainua.simplegameengine.objects.components.transform;

import java.nio.ByteBuffer;
import org.joml.Vector3f;
import org.mykytainua.simplegameengine.objects.components.Bufferable;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * Represents the position of a 3D object in the game engine. The position is
 * stored as a {@link Vector3f}, and this component provides methods for
 * managing and retrieving position-related data.
 */
public class PositionComponent implements Transform,
                                          Bufferable {

    /** The position vector of the component. */
    private Vector3f position;

    /** The size of the data per vertex in this component, measured in floats. */
    private static final int DATA_PER_VERTEX_SIZE = 3;

    /** The total size of the component data, measured in bytes. */
    private static final int TOTAL_DATA_SIZE = 12;

    /** The name of the attribute pointer for this component in the shader. */
    public static final String ATTRIBUTE_POINTER_NAME = "instancePosition";

    /**
     * Constructs a {@code PositionComponent} with the given position.
     *
     * @param position the position vector to initialize this component with.
     */
    public PositionComponent(Vector3f position) {
        this.position = position;
    }

    /**
     * Retrieves the position vector of this component.
     *
     * @return the current position vector.
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     * Updates the position vector of this component.
     *
     * @param position the new position vector.
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     * Creates a deep copy of this position component.
     *
     * @return a new {@code PositionComponent} instance with identical position
     *         data.
     */
    @Override
    public Component deepCopy() {
        return new PositionComponent(new Vector3f(this.position));
    }

    /**
     * Retrieves the size of the data per vertex for this component.
     *
     * @return the number of floats per vertex (3 for a {@code Vector3f}).
     */
    @Override
    public int getDataPerVertexSize() {
        return DATA_PER_VERTEX_SIZE;
    }

    /**
     * Retrieves the total size of the data for this component in bytes.
     *
     * @return the total number of bytes (12 bytes for a {@code Vector3f}).
     */
    @Override
    public int getTotalDataSize() {
        return TOTAL_DATA_SIZE;
    }

    /**
     * Writes the position data of this component into a byte buffer.
     *
     * @param destinationBuffer the buffer where the position data will be written.
     */
    @Override
    public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
        destinationBuffer.putFloat(this.getPosition().x);
        destinationBuffer.putFloat(this.getPosition().y);
        destinationBuffer.putFloat(this.getPosition().z);
    }

    /**
     * Retrieves the name of the attribute pointer for this component.
     *
     * @return the attribute pointer name ("instancePosition").
     */
    @Override
    public String getAttrubutePointerName() {
        return ATTRIBUTE_POINTER_NAME;
    }
}


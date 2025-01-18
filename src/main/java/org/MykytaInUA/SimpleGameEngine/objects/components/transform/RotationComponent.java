package org.mykytainua.simplegameengine.objects.components.transform;

import java.nio.ByteBuffer;
import org.joml.Vector3f;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * The {@code RotationComponent} class represents the rotation of a 3D object in
 * terms of its pitch, yaw, and roll angles.
 * 
 * <p>This component provides functionality to store, manipulate, and pass
 * rotation data to rendering buffers and shaders. It implements the
 * {@link Transform} interface, making it suitable for transformation-related
 * operations in the game engine.</p>
 * 
 * <p>Instances of this class are used to manage the rotational state of 3D
 * objects in a consistent and efficient manner.</p>
 */
public class RotationComponent implements Transform {

    private Vector3f rotation;

    private static final int DATA_PER_VERTEX_SIZE = 3;
    private static final int TOTAL_DATA_SIZE = 12;

    public static final String ATTRIBUTE_POINTER_NAME = "instanceRotation";

    /**
     * Constructs a {@code RotationComponent} with the specified rotation vector.
     *
     * @param rotation a {@link Vector3f} representing the pitch, yaw, and roll
     *                 angles of the object's rotation.
     */
    public RotationComponent(Vector3f rotation) {
        this.rotation = rotation;
    }

    /**
     * Retrieves the rotation vector.
     *
     * @return a {@link Vector3f} representing the pitch, yaw, and roll angles of
     *         the object's rotation.
     */
    public Vector3f getRotation() {
        return rotation;
    }

    /**
     * Updates the rotation vector.
     *
     * @param rotation a {@link Vector3f} representing the new pitch, yaw, and roll
     *                 angles of the object's rotation.
     */
    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    /**
     * Creates a deep copy of this {@code RotationComponent}.
     *
     * @return a new {@code RotationComponent} instance with the same rotation
     *         vector as this instance.
     */
    @Override
    public Component deepCopy() {
        return new RotationComponent(new Vector3f(this.rotation));
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
     * Writes the rotation data to the specified {@link ByteBuffer}.
     *
     * @param destinationBuffer the {@link ByteBuffer} where the rotation data will
     *                          be written.
     */
    @Override
    public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
        destinationBuffer.putFloat(this.getRotation().x);
        destinationBuffer.putFloat(this.getRotation().y);
        destinationBuffer.putFloat(this.getRotation().z);
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


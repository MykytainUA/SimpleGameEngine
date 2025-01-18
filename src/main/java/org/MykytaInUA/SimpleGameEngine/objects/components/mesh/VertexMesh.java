package org.mykytainua.simplegameengine.objects.components.mesh;

import java.nio.ByteBuffer;
import java.util.Arrays;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * The {@code VertexMesh} class represents a simple mesh consisting of a
 * collection of vertices.
 *
 * <p>This implementation of the {@link Mesh} interface stores vertex data as a
 * float array. Each vertex typically includes position data and may include
 * additional attributes depending on the specific use case.</p>
 *
 * <p>This class provides functionality for deep copying vertex data, retrieving
 * vertex information, and interfacing with rendering buffers.</p>
 */
public class VertexMesh implements Mesh {

    /**
     * The array of vertex data for this mesh. Each element represents a coordinate
     * or attribute for a vertex.
     */
    protected float[] vertices;

    /**
     * Constructs a {@code VertexMesh} with the specified vertex data.
     *
     * @param vertices a float array containing the vertex data
     */
    public VertexMesh(float[] vertices) {
        this.vertices = vertices;
    }

    /**
     * Retrieves the array of vertices in this mesh.
     *
     * @return a float array representing the vertex data
     */
    @Override
    public float[] getVertices() {
        return this.vertices;
    }

    /**
     * Creates a deep copy of this {@code VertexMesh}, duplicating the vertex data.
     *
     * @return a new {@code VertexMesh} instance with the same vertex data
     */
    @Override
    public Component deepCopy() {
        float[] copiedVertices = Arrays.copyOf(vertices, vertices.length);
        return new VertexMesh(copiedVertices);
    }

    /**
     * Retrieves the size of the data per vertex.
     *
     * @return the number of data elements per vertex (e.g., 3 for x, y, z
     *         coordinates)
     */
    @Override
    public int getDataPerVertexSize() {
        // TODO: Implement and return the correct value for data per vertex size
        return 0;
    }

    /**
     * Retrieves the total size of the vertex data.
     *
     * @return the total number of elements in the vertex data array
     */
    @Override
    public int getTotalDataSize() {
        // TODO: Implement and return the correct value for total data size
        return 0;
    }

    /**
     * Writes the vertex data to the specified buffer.
     *
     * @param destinationBuffer the buffer to which the vertex data should be
     *                          written
     */
    @Override
    public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
        // TODO: Implement buffer writing logic for vertex data
    }

    /**
     * Retrieves the attribute pointer name used in the shader for this mesh.
     *
     * @return the attribute pointer name
     */
    @Override
    public String getAttrubutePointerName() {
        // TODO: Implement and return the attribute pointer name
        return null;
    }
}


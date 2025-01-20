package org.mykytainua.simplegameengine.objects.components.mesh;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.mykytainua.simplegameengine.objects.components.Bufferable;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * {@code IndexedVertexMesh} represents a 3D mesh that uses indexed vertices. It
 * stores vertices and indices to define a shape and provides methods for
 * handling and copying the mesh data.
 */
public class IndexedVertexMesh implements Mesh, 
                                          Bufferable {

    private final int[] indices;
    protected float[] vertices;

    public static final String ATTRIBUTE_POINTER_NAME = "vertices";

    /**
     * Constructs an {@code IndexedVertexMesh} with the given vertex and index data.
     *
     * @param vertices the vertex data (position coordinates)
     * @param indices  the index data that defines the triangles
     */
    public IndexedVertexMesh(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    @Override
    public float[] getVertices() {
        return this.vertices;
    }

    public int[] getIndices() {
        return this.indices;
    }

    /**
     * A predefined cube mesh with vertices and indices.
     */
    public static class CubeMesh {
        private static final float[] VERTICES = new float[] {
            // Cube vertices
            1.0f, 1.0f, 1.0f, // Front top right
            1.0f, -1.0f, 1.0f, // Front bottom right
            -1.0f, 1.0f, 1.0f, // Front top left
            -1.0f, -1.0f, 1.0f, // Front bottom left
            1.0f, 1.0f, -1.0f, // Back top right
            1.0f, -1.0f, -1.0f, // Back bottom right
            -1.0f, 1.0f, -1.0f, // Back top left
            -1.0f, -1.0f, -1.0f // Back bottom left
        };

        private static final int[] INDICES = new int[] {
            // Cube indices (defining the triangles)
            2, 3, 1, 2, 1, 0, // Front
            4, 5, 7, 4, 7, 6, // Back
            0, 1, 5, 0, 5, 4, // Right
            6, 7, 3, 6, 3, 2, // Left
            2, 0, 4, 2, 4, 6, // Top
            7, 5, 1, 7, 1, 3 // Bottom
        };

        private static final float[] TEXTURECOORDINATES = {
            // Texture coordinates for cube faces
            1.0f, 1.0f, // Front top right
            1.0f, 0.0f, // Front bottom right
            0.0f, 1.0f, // Front top left
            0.0f, 0.0f, // Front bottom left
            0.0f, 1.0f, // Back top right
            0.0f, 0.0f, // Back bottom right
            1.0f, 0.0f, // Back top left
            1.0f, 1.0f // Back bottom left
        };

        public static IndexedVertexMesh getMesh() {
            return new IndexedVertexMesh(VERTICES, INDICES);
        }

        public static float[] getVertices() {
            return VERTICES;
        }

        public static int[] getIndices() {
            return INDICES;
        }

        public static float[] getTextureCoordinates() {
            return TEXTURECOORDINATES;
        }
    }

    /**
     * A predefined pyramid mesh with vertices and indices.
     */
    public static class PyramidMesh {
        private static float[] vertices = new float[] { 1.0f, -1.0f, 1.0f, // Front right
            -1.0f, -1.0f, 1.0f, // Front left
            1.0f, -1.0f, -1.0f, // Back right
            -1.0f, -1.0f, -1.0f, // Back left
            0.0f, 1.0f, 0.0f // Top
        };

        private static int[] indices = new int[] {
            // Pyramid indices (defining the faces)
            2, 0, 1, 2, 1, 3, // Base
            4, 1, 0, // Front side
            4, 0, 2, // Right side
            4, 3, 1, // Left side
            4, 2, 3 // Back side
        };

        public static IndexedVertexMesh getMesh() {
            return new IndexedVertexMesh(vertices, indices);
        }

        public static float[] getVertices() {
            return vertices;
        }

        public static int[] getIndices() {
            return indices;
        }
    }

    @Override
    public Component deepCopy() {
        float[] copiedVertices = Arrays.copyOf(vertices, vertices.length);
        int[] copiedIndices = Arrays.copyOf(indices, indices.length);
        return new IndexedVertexMesh(copiedVertices, copiedIndices);
    }

    @Override
    public int getDataPerVertexSize() {
        return 0;
    }

    @Override
    public int getTotalDataSize() {
        // Assuming each float is 4 bytes, and there are 3 values per vertex (x, y, z)
        return this.getVertices().length * 4;
    }

    @Override
    public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
        for (int i = 0; i < this.getVertices().length; i++) {
            destinationBuffer.putFloat(this.getVertices()[i]);
        }
    }

    @Override
    public String getAttrubutePointerName() {
        return ATTRIBUTE_POINTER_NAME;
    }
}

package org.mykytainua.simplegameengine.rendering;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.Bufferable;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.mesh.IndexedVertexMesh;
import org.mykytainua.simplegameengine.objects.components.mesh.Mesh;


/**
 * The ObjectToDataBufferConverter class is responsible for converting 3D object
 * data (e.g., mesh and component data) into buffers (FloatBuffer, IntBuffer)
 * that can be used for sending data to the GPU.
 */
public class ObjectToDataBufferConverter {

    // The current buffer used for conversion, initialized with 0 capacity
    ByteBuffer currentBuffer = ByteBuffer.allocateDirect(0).order(ByteOrder.nativeOrder());

    /**
     * Constructor for ObjectToDataBufferConverter.
     */
    public ObjectToDataBufferConverter() {
        super();
    }

    /**
     * Converts a component of the given type from a list of objects into a
     * FloatBuffer.
     *
     * @param <T>           the type of the component (extends Component)
     * @param componentType the class type of the component to convert
     * @param objects       the array of objects containing the component
     * @return a FloatBuffer containing the component's data
     */
    public <T extends Component> FloatBuffer getComponentAsFloatBuffer(Class<T> componentType, 
                                                                       Object3D[] objects) {

        // Ensure neither the component type nor objects array is null or empty
        this.checkForNull(componentType, objects);

        // Get an example component from the first object to determine the buffer size
        Bufferable componentExample = (Bufferable) objects[0].getComponentByClass(componentType);

        // Ensure the buffer has enough capacity for the given number of objects
        this.ensureBufferCapacity(objects.length * componentExample.getTotalDataSize());

        // Iterate over the objects and write the component data to the buffer
        for (Object3D object : objects) {
            Bufferable component = (Bufferable) object.getComponentByClass(componentType);
            component.writeComponentDataToBuffer(currentBuffer);
        }

        // Rewind the buffer to the beginning and return it as a FloatBuffer
        this.currentBuffer.rewind();
        return this.currentBuffer.asFloatBuffer();
    }

    /**
     * Converts mesh vertex data into a FloatBuffer.
     *
     * @param mesh the mesh containing the vertex data
     * @return a FloatBuffer containing the mesh's vertex data
     */
    public <T extends Component> FloatBuffer getMeshVertexData(Mesh mesh) {

        // Ensure the buffer has enough capacity for all vertex data
        this.ensureBufferCapacity(mesh.getVertices().length * Float.BYTES);

        // Put the vertex data into the buffer
        for (int i = 0; i < mesh.getVertices().length; i++) {
            this.currentBuffer.putFloat(mesh.getVertices()[i]);
        }

        // Rewind the buffer to the beginning and return it as a FloatBuffer
        this.currentBuffer.rewind();
        return this.currentBuffer.asFloatBuffer();
    }

    /**
     * Converts mesh indices data into an IntBuffer.
     *
     * @param mesh the indexed vertex mesh containing the index data
     * @return an IntBuffer containing the mesh's index data
     */
    public <T extends Component> IntBuffer getMeshIndicesData(IndexedVertexMesh mesh) {

        // Ensure the buffer has enough capacity for all index data
        this.ensureBufferCapacity(mesh.getIndices().length * Integer.BYTES);

        // Put the index data into the buffer
        for (int i = 0; i < mesh.getIndices().length; i++) {
            this.currentBuffer.putInt(mesh.getIndices()[i]);
        }

        // Rewind the buffer to the beginning and return it as an IntBuffer
        this.currentBuffer.rewind();
        return this.currentBuffer.asIntBuffer();
    }

    /**
     * Ensures that the current buffer has enough capacity to hold the required
     * number of bytes.
     *
     * @param requiredCapacity the number of bytes needed
     */
    private void ensureBufferCapacity(int requiredCapacity) {
        // If the current buffer doesn't have enough capacity, allocate a new buffer
        if (!bufferHasEnoughCapacity(requiredCapacity)) {
            this.currentBuffer = ByteBuffer.allocateDirect(requiredCapacity)
                                           .order(ByteOrder.nativeOrder());
        }
        // Set the buffer's limit to the required capacity
        this.currentBuffer.limit(requiredCapacity);
        this.currentBuffer.rewind();
    }

    /**
     * Checks if the current buffer has enough capacity for the required number of
     * bytes.
     *
     * @param neededCapacity the required capacity in bytes
     * @return true if the buffer has enough capacity, false otherwise
     */
    private boolean bufferHasEnoughCapacity(int neededCapacity) {
        return currentBuffer.capacity() >= neededCapacity;
    }

    /**
     * Checks if the componentType and objects array are valid (not null or empty).
     *
     * @param <T> generic type
     * @param componentType the component class type
     * @param objects       the array of objects
     * @throws IllegalArgumentException if either the componentType or objects array
     *                                  is invalid
     */
    private <T> void checkForNull(Class<T> componentType, Object3D[] objects) {
        if (objects == null || objects.length == 0) {
            throw new IllegalArgumentException("Object array cannot be null or empty.");
        }
        if (componentType == null) {
            throw new IllegalArgumentException("Component type cannot be null.");
        }
    }
}

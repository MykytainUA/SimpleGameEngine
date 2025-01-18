package org.mykytainua.simplegameengine.rendering;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.mesh.IndexedVertexMesh;
import org.mykytainua.simplegameengine.objects.components.mesh.Mesh;
import org.mykytainua.simplegameengine.rendering.buffers.EBOWrapper;
import org.mykytainua.simplegameengine.rendering.buffers.VAOWrapper;
import org.mykytainua.simplegameengine.rendering.buffers.VBOStorage;
import org.mykytainua.simplegameengine.rendering.buffers.VBOWrapper;

/**
 * The GPUDataAccessor class is responsible for managing and transferring data
 * to and from the GPU. It handles the initialization and management of vbo
 * (Vertex Buffer Object), ebo (Element Buffer Object), and VAO (Vertex Array
 * Object) for objects in the scene, optimizing the way 3D object data is stored
 * and accessed.
 */
public class GPUDataAccessor {

    private final int boundShaderID; // The shader ID currently bound to the context
    private final VBOStorage vbo; // Vertex Buffer Object storage for holding vbo data
    private final EBOWrapper ebo; // Element Buffer Object wrapper for managing index data

    private ObjectToDataBufferConverter objectToBufferConverter = 
            new ObjectToDataBufferConverter(); 

    /**
     * Constructor initializes the GPU data storage (vbo, ebo) and loads object data
     * into GPU memory.
     *
     * @param vao            the Vertex Array Object that binds and configures
     *                       vertex attributes
     * @param vboStorageSize the size of the vbo storage
     * @param boundShaderID  the ID of the shader that will be bound during data
     *                       transfers
     * @param objects        the objects to be loaded into GPU memory
     */
    public GPUDataAccessor(VAOWrapper vao, 
                           int vboStorageSize, 
                           int boundShaderID, 
                           Object3D[] objects) {
        super();
        this.vbo = new VBOStorage(vboStorageSize); // Initialize vbo storage
        this.ebo = new EBOWrapper(); // Initialize ebo wrapper
        this.boundShaderID = boundShaderID; // Store the bound shader ID

        // Initialize object data in GPU memory
        this.initObjectsDataInGPU(objects, vao);
    }

    /**
     * Gets the vbo storage.
     *
     * @return the vbo storage instance
     */
    public VBOStorage getVbo() {
        return vbo;
    }

    /**
     * Gets the ebo wrapper.
     *
     * @return the ebo wrapper instance
     */
    public EBOWrapper getEbo() {
        return ebo;
    }

    /**
     * Initializes mesh data (vertices and indices) in GPU memory for the given
     * objects.
     *
     * @param objects the 3D objects to initialize in GPU memory
     */
    public void initMeshDataInGPU(Object3D[] objects) {
        // Convert mesh vertices to a FloatBuffer
        Mesh mesh = objects[0].getMesh();
        FloatBuffer vertexBuffer = this.objectToBufferConverter.getMeshVertexData(mesh);

        // Send vertex data to vbo
        VBOWrapper vbo = this.getVbo().getVBOWrapper(0);
        vbo.sendVBOFloatVectorData(boundShaderID, 
                                   IndexedVertexMesh.ATTRIBUTE_POINTER_NAME, 
                                   3, 
                                   vertexBuffer);

        // Convert mesh indices to an IntBuffer
        IntBuffer indicesBuffer = this.objectToBufferConverter
                .getMeshIndicesData((IndexedVertexMesh) objects[0].getMesh());

        this.getEbo().setIndicesCount(indicesBuffer.capacity()); // Set the count of indices

        // Send index data to ebo
        this.getEbo().sendStaticIntegerData(indicesBuffer);
    }

    /**
     * Initializes all object data (mesh data + components) in GPU memory.
     *
     * @param objects the 3D objects to initialize in GPU memory
     * @param vao     the Vertex Array Object that binds and configures vertex
     *                attributes
     */
    public void initObjectsDataInGPU(Object3D[] objects, VAOWrapper vao) {
        int vboIndex = 1; // Start with the second vbo (since the first one is for mesh data)

        vao.bindVertexArray(); // Bind the VAO to configure vertex attributes

        // Initialize mesh data in GPU memory
        this.initMeshDataInGPU(objects);
        
        FloatBuffer buf;
        // Initialize additional component data in GPU memory
        for (Component component : objects[0].getComponentClasses()) {
            // Convert component data to a FloatBuffer
            buf = this.objectToBufferConverter.getComponentAsFloatBuffer(component.getClass(), 
                                                                         objects);
            buf.rewind(); // Rewind the buffer to the beginning

            if (component.getAttrubutePointerName() != null) {
                // Send component data to vbo as instanced data
                VBOWrapper vbo = this.getVbo().getVBOWrapper(vboIndex);
                vbo.sendVBOFloatVectorInstancedData(this.boundShaderID,
                                                    component.getAttrubutePointerName(), 
                                                    component.getDataPerVertexSize(), 
                                                    buf);

                vboIndex++; // Increment the vbo index for the next component
            }
        }

        // Retrieve data from GPU (e.g., check successful upload)
        this.getVbo().getVBOWrapper(1).getVBODataFromGPU();

        // Unbind all buffers after data is transferred to GPU
        this.unbindAllBuffers();
    }

    /**
     * Unbinds all buffers (vbo, VAO, and ebo) to ensure no buffers are left bound.
     */
    public void unbindAllBuffers() {
        this.unbindVBO(); // Unbind the vbo
        this.unbindVAO(); // Unbind the VAO
        this.unbindEBO(); // Unbind the ebo
    }

    /**
     * Unbinds the vbo to ensure no vbo is left bound.
     */
    public void unbindVBO() {
        VBOWrapper.unbindBuffer(); // Unbind the vbo
    }

    /**
     * Unbinds the VAO to ensure no VAO is left bound.
     */
    public void unbindVAO() {
        VAOWrapper.unbindVertexArray(); // Unbind the VAO
    }

    /**
     * Unbinds the ebo to ensure no ebo is left bound.
     */
    public void unbindEBO() {
        EBOWrapper.unbindBuffer(); // Unbind the ebo
    }
}

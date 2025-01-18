package org.mykytainua.simplegameengine.rendering.buffers;

import org.mykytainua.simplegameengine.utilities.Utils;

/**
 * The {@code VBOStorage} class manages an array of {@link VBOWrapper} instances,
 * providing functionality to generate, access, and bind multiple Vertex Buffer 
 * Objects (VBOs) in an organized way.
 * 
 * <p>This class simplifies the management of multiple VBOs by allowing 
 * users to create and interact with a collection of buffers, useful in 
 * scenarios involving multiple datasets or objects in rendering pipelines.</p>
 */
public class VBOStorage {

    private VBOWrapper[] vbos; // Array of VBOWrapper instances managed by this class

    /**
     * Constructs a new {@code VBOStorage} instance and generates the specified 
     * number of {@link VBOWrapper} instances.
     *
     * @param size the number of VBOs to create
     */
    public VBOStorage(int size) {
        this.genBuffers(size);

        Utils.checkOpenGLErrors();
    }

    /**
     * Generates an array of {@link VBOWrapper} instances.
     *
     * @param size the number of VBOs to create
     */
    public void genBuffers(int size) {
        vbos = new VBOWrapper[size];
        for (int i = 0; i < size; i++) {
            vbos[i] = new VBOWrapper();
        }
    }

    /**
     * Binds the VBO at the specified index in the storage.
     *
     * @param index the index of the VBO to bind
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public void bindBuffer(int index) {
        vbos[index].bindBuffer();
        Utils.checkOpenGLErrors();
    }

    /**
     * Retrieves the {@link VBOWrapper} instance at the specified index.
     *
     * @param index the index of the VBO to retrieve
     * @return the {@link VBOWrapper} instance at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public VBOWrapper getVBOWrapper(int index) {
        return this.vbos[index];
    }
}

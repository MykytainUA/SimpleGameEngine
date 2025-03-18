package org.mykytainua.simplegameengine.rendering.buffers;

import java.util.ArrayList;
import java.util.List;

import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.utilities.Utils;

public class VBOAttributeStorage {
    private List<VBOAttribute> VBOAttributes;

    /**
     * Constructs a new {@code VBOStorage} instance and generates the specified 
     * number of {@link VBOWrapper} instances.
     *
     * @param size the number of VBOs to create
     */
    public VBOAttributeStorage() {
        this.VBOAttributes = new ArrayList<VBOAttribute>();
        Utils.checkOpenGLErrors();
    }
    
    /**
     * Generates an array of {@link VBOWrapper} instances.
     *
     * @param size the number of VBOs to create
     */
    public void genAttributeBuffer(DataType dataType, String dataName) {   
        VBOAttribute attribute = new VBOAttribute(dataType, dataName);
        
        this.VBOAttributes.add(attribute);
    }

    /**
     * Binds the VBO at the specified index in the storage.
     *
     * @param index the index of the VBO to bind
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public void bindBuffer(int index) {
        this.VBOAttributes.get(index).bindBuffer();
        Utils.checkOpenGLErrors();
    }

    /**
     * Retrieves the {@link VBOWrapper} instance at the specified index.
     *
     * @param index the index of the VBO to retrieve
     * @return the {@link VBOWrapper} instance at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public VBOWrapper getVBOAttributeAt(int index) {
        return this.VBOAttributes.get(index);
    }
    
    public VBOAttribute getAppropriateVBO(DataType dataType, String name) {
        for (VBOAttribute vboAttribute : VBOAttributes) {
            if(vboAttribute.getDataType() == dataType && 
               vboAttribute.getDataName().equals(name) ) {
                return vboAttribute;
            }
        }
        
        throw new IllegalArgumentException("Could not find appropriate VBO attribute for " 
                                            + name + " of type " + dataType);
    }
}

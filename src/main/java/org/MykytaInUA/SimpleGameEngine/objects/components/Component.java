package org.mykytainua.simplegameengine.objects.components;

/**
 * Represents a component that can be attached to 3D objects in the game engine.
 * Components are responsible for defining various attributes and behaviors,
 * such as mesh, texture, or transformation data, and provide functionality for
 * deep copying, data management, and attribute handling.
 */
public interface Component {

    /**
     * Creates a deep copy of the component.
     *
     * @return a new instance of the component with identical data.
     */
    public Component deepCopy();

//    /**
//     * Retrieves the size of the data associated with this component per vertex.
//     *
//     * @return the number of bytes of data per vertex.
//     */
//    public int getDataPerVertexSize();

    /**
     * Retrieves the name of the attribute pointer associated with this component.
     *
     * @return a string representing the attribute pointer name.
     */
    public String getAttrubutePointerName();
}

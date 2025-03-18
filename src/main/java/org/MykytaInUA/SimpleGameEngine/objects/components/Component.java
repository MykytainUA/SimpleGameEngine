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
    
    /**
     *  Returns metadata for a component {@link ComponentMetadata}
     *  
     * @return implemented by creator of a component metadata
     */
    public ComponentMetadata getComponentMetadata();  
}

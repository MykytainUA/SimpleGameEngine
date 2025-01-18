package org.mykytainua.simplegameengine.objects.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * Abstract builder class for creating and assembling 3D objects.
 * 
 * <p>This class provides a framework for building an Object3D by adding
 * components either by reference or by deep copy. Specific implementation of
 * `getObject()` should be provided by subclasses to return the constructed
 * Object3D.
 */
public abstract class Object3dBuilder {

    // Storage for components added by reference
    protected ReferenceStorage componentsByReference;

    // Storage for components added by deep copy
    protected ReferenceStorage componentsByCopies;

    /**
     * Constructor initializes both empty storage references.
     */
    public Object3dBuilder() {
        this.componentsByReference = new ReferenceStorage();
        this.componentsByCopies = new ReferenceStorage();
    }

    // Returns the built Object3D (currently returns null, to be implemented)
    public Object3D getObject() {
        return null;
    }

    /**
     * Adds a component by reference to the builder. If the component already
     * exists, it will be replaced.
     *
     * @param component to be added by reference
     */
    public void addComponentByReference(Component component) {
        this.componentsByReference.add(component);
    }

    /**
     * Adds a deep copy of a component to the builder. If the component already
     * exists, it will be replaced.
     *
     * @param component to be added by deep copy
     */
    public void addComponentByCopy(Component component) {
        this.componentsByCopies.add(component.deepCopy());
    }

    /**
     * Replaces all current components with new components passed by reference.
     *
     * @param componentsByReference list of components to replace the current ones
     */
    public void setComponentsByReference(ArrayList<? extends Component> componentsByReference) {
        // Clears current components and adds the new ones by reference
        this.componentsByReference.clear();

        for (Component component : componentsByReference) {
            this.componentsByReference.add(component);
        }
    }

    /**
     * Replaces all current components with new components passed by deep copy.
     *
     * @param componentsByCopies list of components to replace the current ones
     */
    public void setComponentsByCopy(ArrayList<? extends Component> componentsByCopies) {
        // Clears current components and adds the new ones by deep copy
        this.componentsByCopies.clear();

        for (Component component : componentsByCopies) {
            this.componentsByCopies.add(component);
        }
    }

    // Helper class to store components in a Map, allows access by component class
    // type
    private class ReferenceStorage implements Iterable<Component> {

        // Map to store components by their class type as the key
        private Map<Class<? extends Component>, Component> components;

        // Constructor initializes the components map
        public ReferenceStorage() {
            this.components = new HashMap<Class<? extends Component>, Component>();
        }

        // Adds a component to the storage, overwriting any existing component of the
        // same type
        public void add(Component component) {
            this.components.put(component.getClass(), component);
        }

        // Clears all components from the storage
        public void clear() {
            this.components.clear();
        }

        // Allows iteration over the components
        @Override
        public Iterator<Component> iterator() {
            return this.components.values().iterator();
        }
    }
}

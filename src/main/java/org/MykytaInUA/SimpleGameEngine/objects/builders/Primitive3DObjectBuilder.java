package org.mykytainua.simplegameengine.objects.builders;

import java.util.ArrayList;
import org.mykytainua.simplegameengine.objects.Cube;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.Pyramid;
import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * Builder class for constructing primitive 3D objects like Cube and Pyramid.
 * This class extends Object3DBuilder and provides functionality for creating
 * and configuring 3D objects with components.
 */
public class Primitive3dObjectBuilder extends Object3dBuilder {

    // The type of Object3D (Cube, Pyramid, etc.) to be created by this builder
    private Class<? extends Object3D> object3dType;

    /**
     * Default constructor, initializes the builder with empty components storage.
     */
    public Primitive3dObjectBuilder() {
        super();
    }

    /**
     * Constructor allowing the specification of the 3D object type to build.
     *
     * @param object3dType The class type of the 3D object (Cube, Pyramid, etc.)
     */
    public Primitive3dObjectBuilder(Class<? extends Object3D> object3dType) {
        super();
        this.setObject3dType(object3dType);
    }

    /**
     * Constructor for specifying the 3D object type along with reference and copy
     * components.
     *
     * @param object3dType          The class type of the 3D object (Cube, Pyramid,
     *                              etc.)
     * @param componentsByReference List of components to be added by reference
     * @param componentsByCopy      List of components to be added by deep copy
     */
    public Primitive3dObjectBuilder(Class<? extends Object3D> object3dType,
                                    ArrayList<? extends Component> componentsByReference, 
                                    ArrayList<? extends Component> componentsByCopy) {
        super();
        this.setObject3dType(object3dType);
        this.setComponentsByReference(componentsByReference);
        this.setComponentsByCopy(componentsByCopy);
    }

    /**
     * Creates and returns the 3D object based on the specified object3dType. Adds
     * both reference and copied components to the object.
     *
     * @return The constructed Object3D instance
     */
    @Override
    public Object3D getObject() {

        Object3D object = null;

        // Create object based on type (Cube or Pyramid)
        if (object3dType == Cube.class) {
            object = new Cube();
        } else if (object3dType == Pyramid.class) {
            object = new Pyramid();
        }

        // Add deep-copied components
        for (Component iteratiedComponent : componentsByCopies) {
            object.add(iteratiedComponent.deepCopy());
        }

        // Add reference components
        for (Component iteratiedComponent : componentsByReference) {
            object.add(iteratiedComponent);
        }

        return object;
    }

    /**
     * Gets the type of the 3D object being built.
     *
     * @return The class type of the 3D object (Cube, Pyramid, etc.)
     */
    public Class<? extends Object3D> getObject3dType() {
        if (this.object3dType == null) {
            System.out.println("Error:Trying to get 3D object type but get null");
        }
        return object3dType;
    }

    /**
     * Sets the type of the 3D object to be built (Cube, Pyramid, etc.).
     *
     * @param object3dType The class type of the 3D object (Cube, Pyramid, etc.)
     */
    public void setObject3dType(Class<? extends Object3D> object3dType) {
        this.object3dType = object3dType;
    }
}

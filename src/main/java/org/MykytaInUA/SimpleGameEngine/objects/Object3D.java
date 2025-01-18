package org.mykytainua.simplegameengine.objects;

import java.util.ArrayList;
import java.util.List;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.mesh.Mesh;
import org.mykytainua.simplegameengine.objects.components.texture.RenderMaterialComponent;
import org.mykytainua.simplegameengine.objects.components.transform.Transform;

/**
 * The {@code Object3D} class serves as a base class for 3D objects in the game
 * engine. It manages components such as mesh, texture, and transformations,
 * providing functionality to add, retrieve, and manage these components
 * dynamically.
 *
 * <p>This class is designed to be extended by specific 3D object implementations.
 */
public abstract class Object3D {

    /**
     * A list of components associated with this 3D object.
     */
    protected List<Component> components;

    /**
     * The mesh component of the 3D object, representing its geometry.
     */
    protected Mesh mesh;

    /**
     * Default constructor that initializes the {@code components} list to an empty
     * state.
     */
    public Object3D() {
        components = new ArrayList<Component>(0);
    }

    /**
     * Constructs an {@code Object3D} instance with a specified mesh, material, and
     * a list of transformation components.
     *
     * @param mesh                the mesh component representing the object's
     *                            geometry
     * @param texture             the material component for rendering the object
     * @param transformComponents a list of transformation components defining the
     *                            object's position, rotation, and scale
     */
    public Object3D(Mesh mesh, 
                    RenderMaterialComponent texture, 
                    ArrayList<Transform> transformComponents) {

        this.mesh = mesh;

        components.add(mesh);
        components.add(texture);

        for (Transform iteratedComponent : transformComponents) {
            components.add(iteratedComponent);
        }
    }

    /**
     * Retrieves a component of the specified class type from the list of
     * components.
     *
     * @param typeOfComponent the class type of the component to retrieve
     * @return the component of the specified type, or {@code null} if not found
     */
    public Component getComponentByClass(Class<? extends Component> typeOfComponent) {
        for (Component component : components) {
            if (typeOfComponent.isInstance(component)) {
                return component;
            }
        }
        return null;
    }

    /**
     * Retrieves a component of the specified superclass type from the list of
     * components.
     *
     * @param superClassOfComponent the superclass type of the component to retrieve
     * @return the component of the specified superclass type, or {@code null} if
     *         not found
     */
    public Component getComponentBySuperClass(Class<? extends Component> superClassOfComponent) {
        for (Component component : components) {
            if (superClassOfComponent.isAssignableFrom(component.getClass())) {
                return component;
            }
        }
        return null;
    }

    /**
     * Adds a component to the 3D object.
     *
     * @param component the component to add
     */
    public void add(Component component) {
        components.add(component);
    }

    public List<Component> getComponentClasses() {
        return new ArrayList<Component>(components);
    }

    public Mesh getMesh() {
        return this.mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }
}


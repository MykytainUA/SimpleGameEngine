package org.mykytainua.simplegameengine.objects;

import java.util.ArrayList;
import java.util.Random;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.mykytainua.simplegameengine.objects.builders.Primitive3dObjectBuilder;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.texture.SolidColorComponent;
import org.mykytainua.simplegameengine.objects.components.texture.TextureComponent;
import org.mykytainua.simplegameengine.objects.components.transform.PositionComponent;
import org.mykytainua.simplegameengine.objects.components.transform.RotationComponent;
import org.mykytainua.simplegameengine.objects.components.transform.SizeComponent;

/**
 * The {@code RandomObjectGenerator} class provides functionality to generate 3D
 * objects with random properties.
 * 
 * <p>It uses a {@link Primitive3dObjectBuilder} to construct objects by setting
 * randomized components and applying randomness to properties like position,
 * rotation, and color.
 */
public class RandomObjectGenerator {

    private static final Random RANDOMIZER = new Random();
    private static final Primitive3dObjectBuilder OBJECT_BUILDER = new Primitive3dObjectBuilder();

    private static final float MAX_DISTANCE = 1000;
    private static final float MAX_ANGLE_ROTATION = 360;

    /**
     * Constructs an {@code RandomObjectGenerator} and initializes the builder with
     * random components.
     *
     * @param componentTypesByReference a list of component types that will be used
     *                                  as references for the object generation.
     */
    public RandomObjectGenerator(ArrayList<Class<? extends Component>> componentTypesByReference) {
        super();

        ArrayList<Component> componentByReference = new ArrayList<Component>();

        for (Class<? extends Component> componentClass : componentTypesByReference) {
            componentByReference.add(this.getRandomizedComponent(componentClass));
        }

        OBJECT_BUILDER.setComponentsByReference(componentByReference);

        this.setSeed(1);
    }

    /**
     * Generates a random 3D object of the specified type with the given component
     * types.
     *
     * @param objectType           the class type of the 3D object to generate.
     * @param componentTypesByCopy a list of component types to copy and randomize
     *                             for the object.
     * @return a randomly generated {@link Object3D}.
     */
    public Object3D getRandomObject(Class<? extends Object3D> objectType,
            ArrayList<Class<? extends Component>> componentTypesByCopy) {

        OBJECT_BUILDER.setObject3dType(objectType);

        ArrayList<Component> componentByCopy = new ArrayList<Component>();

        for (Class<? extends Component> componentClass : componentTypesByCopy) {
            componentByCopy.add(this.getRandomizedComponent(componentClass));
        }

        OBJECT_BUILDER.setComponentsByCopy(componentByCopy);

        return OBJECT_BUILDER.getObject();
    }

    /**
     * Generates a random component of the specified type.
     *
     * @param componentType the class type of the component to randomize.
     * @return a randomized {@link Component}, or {@code null} if the type is not
     *         supported.
     */
    public Component getRandomizedComponent(Class<? extends Component> componentType) {

        if (componentType == PositionComponent.class) {
            return new PositionComponent(new Vector3f(RANDOMIZER.nextFloat(MAX_DISTANCE),
                    RANDOMIZER.nextFloat(MAX_DISTANCE), RANDOMIZER.nextFloat(MAX_DISTANCE)));

        } else if (componentType == RotationComponent.class) {
            return new RotationComponent(new Vector3f(RANDOMIZER.nextFloat(MAX_ANGLE_ROTATION),
                                                      RANDOMIZER.nextFloat(MAX_ANGLE_ROTATION), 
                                                      RANDOMIZER.nextFloat(MAX_ANGLE_ROTATION)));

        } else if (componentType == SizeComponent.class) {
            return new SizeComponent(new Vector3f(RANDOMIZER.nextFloat(),
                                                  RANDOMIZER.nextFloat(), 
                                                  RANDOMIZER.nextFloat()));

        } else if (componentType == SolidColorComponent.class) {
            return new SolidColorComponent(
                    new Vector4f(RANDOMIZER.nextFloat(), 
                                 RANDOMIZER.nextFloat(), 
                                 RANDOMIZER.nextFloat(), 
                                 1));

        } else if (componentType == TextureComponent.class) {

        }

        return null;
    }

    /**
     * Sets the seed for the random number generator used in object generation.
     *
     * @param seed the seed value for the random number generator.
     */
    public void setSeed(int seed) {
        RANDOMIZER.setSeed(seed);
    }

}

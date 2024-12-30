package org.MykytaInUA.SimpleGameEngine.objects;

import java.util.ArrayList;
import java.util.Random;

import org.MykytaInUA.SimpleGameEngine.objects.builders.Primitive3DObjectBuilder;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.SolidColorComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.TextureComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.PositionComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.RotationComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.SizeComponent;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Object3DRandomGenerator {	

	private static final Random RANDOMIZER = new Random();
	private static final Primitive3DObjectBuilder OBJECT_BUILDER = new Primitive3DObjectBuilder();
	
	private static final float MAX_DISTANCE = 200;
	private static final float MAX_ANGLE_ROTATION = 360;
	
	public Object3DRandomGenerator(ArrayList<Class<? extends Component>> componentTypesByReference) {
		super();
		
		ArrayList<Component> componentByReference = new ArrayList<Component>();
		
		for(Class<? extends Component> componentClass : componentTypesByReference) {
			componentByReference.add(this.getRandomizedComponent(componentClass));
		}
		
		OBJECT_BUILDER.setComponentsByReference(componentByReference);
	}
	
	public Object3D getRandomObject(Class<? extends Object3D> object3DType, 
									ArrayList<Class<? extends Component>> componentTypesByCopy) {
		
		OBJECT_BUILDER.setObject3DType(object3DType);
		
		
		ArrayList<Component> componentByCopy = new ArrayList<Component>();
		
		for(Class<? extends Component> componentClass : componentTypesByCopy) {
			componentByCopy.add(this.getRandomizedComponent(componentClass));
		}
		
		OBJECT_BUILDER.setComponentsByCopy(componentByCopy);
		
		return OBJECT_BUILDER.getObject();
	}
	
	public Component getRandomizedComponent(Class <? extends Component> componentType) {
		
		
		
		if(componentType == PositionComponent.class) {
			return new PositionComponent(new Vector3f(RANDOMIZER.nextFloat(MAX_DISTANCE), 
													  RANDOMIZER.nextFloat(MAX_DISTANCE), 
													  RANDOMIZER.nextFloat(MAX_DISTANCE)));
			
		} else if (componentType == RotationComponent.class) {
			return new RotationComponent(new Vector3f(RANDOMIZER.nextFloat(MAX_ANGLE_ROTATION), 
													  RANDOMIZER.nextFloat(MAX_ANGLE_ROTATION), 
													  RANDOMIZER.nextFloat(MAX_ANGLE_ROTATION)));
			
		} else if (componentType == SizeComponent.class) {
			return new SizeComponent(new Vector3f(1, 1, 1));
			
		} else if (componentType == SolidColorComponent.class) {
			return new SolidColorComponent(new Vector4f(RANDOMIZER.nextFloat(), 
														RANDOMIZER.nextFloat(), 
														RANDOMIZER.nextFloat(),  
														1));
			
		} else if (componentType == TextureComponent.class) {
			
		}
			
		return null;
	}
	
	public void setSeed(int seed) {
		RANDOMIZER.setSeed(seed);
	}
	
}

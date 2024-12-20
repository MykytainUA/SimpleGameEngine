package org.MykytaInUA.SimpleGameEngine.objects.builders;

import java.util.ArrayList;

import org.MykytaInUA.SimpleGameEngine.objects.Cube;
import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.Pyramid;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class Primitive3DObjectBuilder extends Object3DBuilder{
	
	private Class<? extends Object3D> object3DType;

	
	public Primitive3DObjectBuilder() {
		super();
	}
	
	public Primitive3DObjectBuilder(Class<? extends Object3D> object3DType) {
		super();
		this.setObject3DType(object3DType);
	}
	
	public Primitive3DObjectBuilder(Class<? extends Object3D> object3DType,
									ArrayList<? extends Component> componentsByReference,
									ArrayList<? extends Component> componentsByCopy) {
		super();
		this.setObject3DType(object3DType);
		this.setComponentsByReference(componentsByReference);
		this.setComponentsByCopy(componentsByCopy);
	}
	
	@Override
	public Object3D getObject() {
		
		Object3D object = null;
		
		if(object3DType == Cube.class) {
			object = new Cube();
		} else if (object3DType == Pyramid.class) {
			object = new Pyramid();
		}
		
		for(Component iteratiedComponent : componentsByCopies) {
			object.add(iteratiedComponent.deepCopy());
		}
		
		for(Component iteratiedComponent : componentsByReference) {
			object.add(iteratiedComponent);
		}
		
		return object;
	}

	public Class<? extends Object3D> getObject3DType() {
		if(this.object3DType == null) {
			System.out.println("Error:Trying to get 3D object type but get null");
		}
		return object3DType;
	}

	public void setObject3DType(Class<? extends Object3D> object3dType) {
		object3DType = object3dType;
	}
	
	/**
	 * If builder already has this component previous component will be overwritten
	 * @param component to be added by reference
	 */
	public void addComponentByReference(Component component) {
		super.addComponentByReference(component);
	}
	
	/**
	 * If builder already has this component previous component will be overwritten
	 * @param component to be added by DeepCopy
	 */
	public void addComponentByCopy(Component component) {
		super.addComponentByCopy(component);
	}
	
	/**
	 * Removes all previous references and adds new components
	 * @param component to be added by reference
	 */
	public void setComponentsByReference(ArrayList<? extends Component> componentsByReference) {
		super.setComponentsByReference(componentsByReference);
	}
	
	/**
	 * Removes all previous references and adds new components
	 * @param component to be added by copy
	 */
	public void setComponentsByCopy(ArrayList<? extends Component> componentsByCopies) {
		super.setComponentsByCopy(componentsByCopies);
	}
	
}

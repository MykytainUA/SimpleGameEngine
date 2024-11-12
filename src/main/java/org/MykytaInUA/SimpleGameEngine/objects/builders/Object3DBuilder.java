package org.MykytaInUA.SimpleGameEngine.objects.builders;

import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.ComponentsStorage;

public abstract class Object3DBuilder {
	
	protected ComponentsStorage componentsByReference;
	protected ComponentsStorage componentsByCopies;
	
	public Object3DBuilder() {
		this.componentsByReference = new ComponentsStorage();
		this.componentsByCopies = new ComponentsStorage();
	}
	
	public Object3D getObject() {
		return null;
	}
	
	/**
	 * If builder already has this component previous component will be overwritten
	 * @param component to be added by reference
	 */
	public void addComponentByReference(Component component) {
		this.componentsByReference.add(component);
	}
	
	/**
	 * If builder already has this component previous component will be overwritten
	 * @param component to be added by DeepCopy
	 */
	public void addComponentByCopy(Component component) {
		this.componentsByCopies.add(component.deepCopy());
	}


	
}

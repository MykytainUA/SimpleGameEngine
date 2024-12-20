package org.MykytaInUA.SimpleGameEngine.objects.builders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public abstract class Object3DBuilder {
	
	protected ReferenceStorage componentsByReference;
	protected ReferenceStorage componentsByCopies;
	
	public Object3DBuilder() {
		this.componentsByReference = new ReferenceStorage();
		this.componentsByCopies = new ReferenceStorage();
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
	
	/**
	 * Removes all previous references and adds new components
	 * @param component to be added by reference
	 */
	public void setComponentsByReference(ArrayList<? extends Component> componentsByReference) {
		this.componentsByReference.clear();
		
		for(Component component : componentsByReference) {	
			this.componentsByReference.add(component);
		}
	}
	
	/**
	 * Removes all previous references and adds new components
	 * @param component to be added by copy
	 */
	public void setComponentsByCopy(ArrayList<? extends Component> componentsByCopies) {
		this.componentsByCopies.clear();
		
		for(Component component : componentsByCopies) {	
			this.componentsByCopies.add(component);
		}
	}
}

class ReferenceStorage implements Iterable<Component>{
	
	private Map<Class<? extends Component>, Component> components;
	
	public ReferenceStorage() {	
		this.components = new HashMap<Class<? extends Component>, Component>();
	}
	
	public void add(Component component) {		
		this.components.put(component.getClass(), component);
	}
	
	public void clear() {
		this.components.clear();
	}

	@Override
	public Iterator<Component> iterator() {
		return this.components.values().iterator();
	}	
}

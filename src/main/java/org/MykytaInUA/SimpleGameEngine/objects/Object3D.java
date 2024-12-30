package org.MykytaInUA.SimpleGameEngine.objects;

import java.util.ArrayList;
import java.util.List;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.MeshComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.RenderMaterialComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.TransformComponent;

public abstract class Object3D {
	
	protected List<Component> components;
	
	public Object3D() {
		components = new ArrayList<Component>();
	}
	
	public Object3D(MeshComponent mesh, RenderMaterialComponent texture, 
					ArrayList<TransformComponent> transformComponents) {
		
		components.add(mesh);
		components.add(texture);
		
		for(TransformComponent iteratedComponent : transformComponents) {
			components.add(iteratedComponent);
		}
	}
	
	public Component getComponentByClass(Class<? extends Component> typeOfComponent) {
		for(Component component : components) {
			if(typeOfComponent.isInstance(component)) {
				return component;
			}
		}
		return null;
	}
	
	public Component getComponentBySuperClass(Class<? extends Component> superClassOfComponent) {
		for (Component component : components) {
			if(superClassOfComponent.isAssignableFrom(component.getClass())) {
				return component;
			}
		}
		return null;
	}
	
	public void add(Component component) {
		components.add(component);
	}
	
//	public List<Component> getComponentClasses() {
//		return new ArrayList<Component>(components);
//	}
}


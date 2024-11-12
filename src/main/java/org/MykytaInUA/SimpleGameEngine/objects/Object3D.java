package org.MykytaInUA.SimpleGameEngine.objects;

import java.util.ArrayList;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.ComponentsStorage;
import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.MeshComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.RenderMaterialComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.TransformComponent;

public abstract class Object3D {
	
	protected ComponentsStorage components;
	
	public Object3D() {
		this.components = new ComponentsStorage();
	}
	
	public Object3D(MeshComponent mesh, RenderMaterialComponent texture, 
					ArrayList<TransformComponent> transformComponents) {
		
		this.components = new ComponentsStorage();
		this.components.add(mesh);
		this.components.add(texture);
		for(TransformComponent iteratedComponent : transformComponents) {
			this.components.add(iteratedComponent);	
		}
	}
	
	public ComponentsStorage getComponents() {
		return components;
	}
	
	public void add(Component component) {
		components.add(component);
	}
}


package org.MykytaInUA.SimpleGameEngine.objects.components;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.MykytaInUA.SimpleGameEngine.objects.components.texture.RenderMaterialComponent;

public class ComponentsStorage implements Iterable<Component> {
	
	private Map<Class<? extends Component>, Component> components;
	
	public ComponentsStorage() {	
		
		components = new HashMap<Class<? extends Component>, Component>();
	}
	
	
	public void add(Component component) {
		for(Class<? extends Component> iteratedComponent : ComponentValidator.getValidationSet()) {
			if(iteratedComponent.isAssignableFrom(component.getClass())) {
				this.removeComponentBySuperClass(iteratedComponent);
			}
		}
		
		components.put(component.getClass(), component);
	}
	
	public Component getComponentByClass(Class<? extends Component> typeOfComponent) {
		return components.get(typeOfComponent);
	}
	
	public void removeComponentByClass(Class<? extends Component> typeOfComponent) {
		components.remove(typeOfComponent);
	}
	
	public Component getComponentBySuperClass(Class<? extends Component> superClassOfComponent) {
		Set<Class<? extends Component>> keys = components.keySet();
		
		for (Class<? extends Component> key : keys) {
			if(superClassOfComponent.isAssignableFrom(key)) {
				return components.get(key);
			}
		}
		return null;
	}
	
	public void removeComponentBySuperClass(Class<?> superClassOfComponent) {
		Set<Class<? extends Component>> keys = components.keySet();
		
		for (Class<? extends Component> key : keys) {
			if(superClassOfComponent.isAssignableFrom(key)) {
				components.remove(key);
				break;
			}
		}
	}

	@Override
	public Iterator<Component> iterator() {
		return components.values().iterator();
	}
	
	// Prevents componentMap from having same components 
	// with the same nature, like rendering
	
	public static class ComponentValidator {
		private static Set<Class<? extends Component>> validationSet = new HashSet<Class<? extends Component>>();
		
		// add classes for validation here
		static {
			validationSet.add(RenderMaterialComponent.class);
		}
		
		public static Set<Class<? extends Component>> getValidationSet() {
			return validationSet;
		}
		
	}
}


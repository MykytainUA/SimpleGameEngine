package org.MykytaInUA.SimpleGameEngine.objects.builders;

import org.joml.Vector3f;
import org.joml.Vector4f;

import org.MykytaInUA.SimpleGameEngine.objects.Cube;
import org.MykytaInUA.SimpleGameEngine.objects.Object3D;
import org.MykytaInUA.SimpleGameEngine.objects.components.Component;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.SolidColorComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.PositionComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.RotationComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.SizeComponent;

public class CubeBuilder extends Object3DBuilder {
	
	public CubeBuilder() {
		
	}

	@Override
	public Object3D getObject() {
		Cube cube = new Cube();
		
		for(Component iteratiedComponent : componentsByCopies) {
			cube.add(iteratiedComponent.deepCopy());
		}
		
		for(Component iteratiedComponent : componentsByReference) {
			cube.add(iteratiedComponent);
		}
		
		return cube;
	}
}

package org.MykytaInUA.SimpleGameEngine.objects;

import java.util.ArrayList;

import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.IndexedVertexMesh;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.RenderMaterialComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.TransformComponent;

public class Cube extends Object3D {
	
	public Cube() {
		this.add(IndexedVertexMesh.CubeMesh.getMesh());
	}
	
	public Cube(RenderMaterialComponent texture, 
				ArrayList<TransformComponent> transformComponents) {
		
		super(IndexedVertexMesh.CubeMesh.getMesh(), texture, transformComponents);
	}

}

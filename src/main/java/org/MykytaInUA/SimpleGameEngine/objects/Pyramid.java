package org.MykytaInUA.SimpleGameEngine.objects;

import org.MykytaInUA.SimpleGameEngine.objects.components.mesh.IndexedVertexMesh;

public class Pyramid extends Object3D{

	public Pyramid() {
		this.add(IndexedVertexMesh.PyramidMesh.getMesh());
	}
}

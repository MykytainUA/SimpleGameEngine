package org.mykytainua.simplegameengine.objects;

import org.mykytainua.simplegameengine.objects.components.mesh.IndexedVertexMesh;

/**
 * The {@code Pyramid} class represents a 3D pyramid object in the game engine.
 * It extends the {@link Object3D} class and uses a predefined indexed vertex
 * mesh to define the geometry of the pyramid.
 */
public class Pyramid extends Object3D {

    /**
     * Constructs a {@code Pyramid} with a default indexed vertex mesh.
     * 
     * <p>The mesh is predefined in the {@link IndexedVertexMesh.PyramidMesh} and is
     * automatically set during the creation of the pyramid object.
     */
    public Pyramid() {
        this.setMesh(IndexedVertexMesh.generator.getDefaultTiangleMesh());
    }
}

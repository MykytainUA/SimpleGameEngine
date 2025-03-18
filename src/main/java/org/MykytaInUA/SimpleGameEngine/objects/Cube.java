package org.mykytainua.simplegameengine.objects;

import org.mykytainua.simplegameengine.objects.components.mesh.Mesh;

/**
 * The {@code Cube} class represents a 3D cube object in the game engine. It
 * extends the {@link Object3D} class and provides specific implementations for
 * creating a cube using a predefined indexed vertex mesh.
 */
public class Cube extends Object3D {

    /**
     * Constructs a {@code Cube} with a default indexed vertex mesh.
     */
    public Cube() {
        this.setMesh(Mesh.generator.getDefaultCubeMesh());
    }
}

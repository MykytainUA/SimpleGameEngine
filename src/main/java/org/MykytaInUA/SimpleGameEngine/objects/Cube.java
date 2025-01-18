package org.mykytainua.simplegameengine.objects;

import org.mykytainua.simplegameengine.objects.components.mesh.IndexedVertexMesh;

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
        this.setMesh(IndexedVertexMesh.CubeMesh.getMesh());
    }

    /**
     * Constructs a {@code Cube} with the option to specify whether it is instanced.
     *
     * <p>If {@code isInstanced} is {@code true}, the cube is created using the
     * predefined indexed vertex mesh. If {@code isInstanced} is {@code false}, a
     * message is printed prompting the user to define a custom vertex mesh.
     *
     * @param isInstanced a boolean indicating whether the cube should be created
     *                    using the predefined indexed vertex mesh
     */
    public Cube(boolean isInstanced) {
        if (isInstanced) {
            this.setMesh(IndexedVertexMesh.CubeMesh.getMesh());
        } else {
            System.out.println("Define vertex mesh !!!");
        }
    }
}

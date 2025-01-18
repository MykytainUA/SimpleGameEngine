package org.mykytainua.simplegameengine.objects.components.mesh;

import org.mykytainua.simplegameengine.objects.components.Component;

/**
 * The {@code Mesh} interface represents a component that provides the vertex
 * data for a 3D object in a rendering engine.
 *
 * <p>Implementations of this interface should define the vertices that make up
 * the geometry of a 3D object. Each vertex is represented by a set of
 * coordinates, typically including position, and may also include additional
 * attributes like normals or texture coordinates, depending on the specific
 * implementation.</p>
 *
 * <p>This interface extends the {@link Component} interface, meaning that it
 * can be used as a reusable and composable part of a 3D object within the
 * engine.</p>
 */
public interface Mesh extends Component {

    /**
     * Retrieves the array of vertices that make up this mesh.
     *
     * <p>The array should contain the vertex data in a structured format. The
     * specific structure (e.g., 3 floats per vertex for positions) is
     * implementation-dependent.</p>
     *
     * @return a float array representing the vertex data
     */
    public float[] getVertices();
}

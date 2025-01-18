package org.mykytainua.simplegameengine.rendering.shaders;

import static com.jogamp.opengl.GL.GL_TRIANGLES;
import static com.jogamp.opengl.GL.GL_UNSIGNED_INT;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLContext;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.rendering.GPUDataAccessor;
import org.mykytainua.simplegameengine.rendering.buffers.VAOWrapper;

/**
 * The {@code RenderUnit} class manages the rendering of objects with shared
 * components using a single vao and GPU data accessor.
 */
public class RenderUnit {

    private final VAOWrapper vao;
    private final GPUDataAccessor dataSender;
    private final Set<Component> components;
    private int objectsCount;

    /**
     * Constructs a new {@code RenderUnit} for rendering objects with the specified shader.
     *
     * @param boundShaderID The ID of the shader program.
     * @param objects       The array of {@code Object3D} instances to be rendered.
     */
    public RenderUnit(int boundShaderID, Object3D[] objects) {
        this.vao = new VAOWrapper();

        // Initialize GPU data accessor
        this.dataSender = new GPUDataAccessor(
            this.vao,
            objects[0].getComponentClasses().size() + 1,
            boundShaderID,
            objects
        );

        // Store object components
        HashSet<Component> objectComponents = new HashSet<>(objects[0].getComponentClasses());
        this.components = Collections.unmodifiableSet(objectComponents);
        this.objectsCount = objects.length;
    }

    /**
     * Renders all objects managed by this {@code RenderUnit}.
     */
    public void render() {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        this.bindThisRenderUnit();

        gl.glDrawElementsInstanced(
            GL_TRIANGLES,
            this.dataSender.getEbo().getIndicesCount(),
            GL_UNSIGNED_INT,
            0,
            this.objectsCount
        );

        this.dataSender.unbindAllBuffers();
    }

    /**
     * Adds new objects to this render unit, ensuring they share the same components.
     *
     * @param objects The array of {@code Object3D} instances to add.
     * @throws IllegalArgumentException if the objects' components do not match this
     *                                  {@code RenderUnit}.
     */
    public void addObjects(Object3D[] objects) {
        if (!this.components.containsAll(objects[0].getComponentClasses())) {
            throw new IllegalArgumentException(
                "Objects' components do not match this RenderUnit's components. "
                + "No objects were added."
            );
        }

        this.objectsCount += objects.length;
    }

    /**
     * Gets the vao wrapper associated with this render unit.
     *
     * @return The {@code VAOWrapper}.
     */
    public VAOWrapper getVao() {
        return vao;
    }

    /**
     * Gets the number of objects managed by this render unit.
     *
     * @return The object count.
     */
    public int getObjectsCount() {
        return objectsCount;
    }

    /**
     * Gets the immutable set of components shared by all objects in this render unit.
     *
     * @return A {@code Set} of {@code Component} instances.
     */
    public Set<Component> getComponents() {
        return this.components;
    }

    /**
     * Binds the vao associated with this render unit.
     */
    private void bindThisRenderUnit() {
        this.vao.bindVertexArray();
    }
}



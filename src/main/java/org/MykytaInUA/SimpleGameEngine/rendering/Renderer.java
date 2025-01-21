package org.mykytainua.simplegameengine.rendering;


import static com.jogamp.opengl.GL4.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL4.GL_DEPTH_BUFFER_BIT;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import java.util.ArrayList;

import org.mykytainua.simplegameengine.objects.Cube;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.Pyramid;
import org.mykytainua.simplegameengine.objects.RandomObjectGenerator;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.texture.SolidColorComponent;
import org.mykytainua.simplegameengine.objects.components.transform.PositionComponent;
import org.mykytainua.simplegameengine.objects.components.transform.RotationComponent;
import org.mykytainua.simplegameengine.objects.components.transform.SizeComponent;
import org.mykytainua.simplegameengine.rendering.shaders.ShaderProgram;
import org.mykytainua.simplegameengine.utilities.Utils;
import org.mykytainua.simplegameengine.window.GameEngineWindow;

/**
 * The Renderer class is responsible for initializing and managing the OpenGL
 * rendering process, handling object rendering, and setting up shaders. It
 * implements the GLEventListener interface for handling OpenGL events such as
 * initialization, drawing, and resizing.
 */
public class Renderer implements GLEventListener {

    private GameEngineWindow window;

    private ShaderProgram shaderProgram;

    private RandomObjectGenerator generator;

    /**
     * Constructs a Renderer for the given GameEngineWindow.
     *
     * @param window The window in which the rendering occurs.
     */
    public Renderer(GameEngineWindow window) {
        this.window = window;
    }

    /**
     * Initializes OpenGL settings, creates objects, and sets up shaders. This
     * method is called once during the initialization of the OpenGL context.
     *
     * @param drawable The OpenGL drawable context.
     */
    @Override
    public void init(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Ensure all previous OpenGL commands have been completed.
        gl.glFinish();

        // Set up component classes for objects.
        ArrayList<Class<? extends Component>> componentByCopy = new ArrayList<>(0);

        // Add components that are copied to objects.
        componentByCopy.add(SolidColorComponent.class);
        componentByCopy.add(PositionComponent.class);
        componentByCopy.add(RotationComponent.class);
        
        // Add components by reference.
        ArrayList<Class<? extends Component>> componentByReference = new ArrayList<>(0);
        
        // Initialize the random object generator.
        generator = new RandomObjectGenerator(componentByReference);

        // Create and initialize objects with random data.
        Object3D[] objects = new Object3D[100000];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = generator.getRandomObject(Pyramid.class, componentByCopy);
        }

        // Initialize shader program with shaders and objects.
        this.shaderProgram = new ShaderProgram(window.getCamera());

        // Add objects to the shader program for rendering.
        shaderProgram.addObjects(objects);
        
        componentByCopy.add(SizeComponent.class);
        
        objects = new Object3D[100000];
        for (int j = 0; j < objects.length; j++) {
            objects[j] = generator.getRandomObject(Cube.class, componentByCopy);
        }
        
        // Add objects to the shader program for rendering.
        shaderProgram.addObjects(objects);

        // Check for OpenGL errors.
        Utils.checkOpenGLErrors();

        // Resize the window to match the OpenGL context.
        this.window.resizeWindow();
        
    }

    /**
     * Called every frame to render the scene. Clears the screen and renders objects
     * using the shader program.
     *
     * @param drawable The OpenGL drawable context.
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        // Update user input actions on objects.
        this.window.getUserInputListener().updateActionsOnObjects();

        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Clear the color and depth buffers.
        gl.glClear(GL_COLOR_BUFFER_BIT);
        gl.glClear(GL_DEPTH_BUFFER_BIT);

        // Render the objects using the shader program.
        this.shaderProgram.render();

        // Check for OpenGL errors.
        Utils.checkOpenGLErrors();
    }

    /**
     * Called when the window is resized. Updates the camera's aspect ratio.
     *
     * @param drawable The OpenGL drawable context.
     * @param x        The x coordinate of the new window size.
     * @param y        The y coordinate of the new window size.
     * @param width    The new width of the window.
     * @param height   The new height of the window.
     */
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL4 gl = drawable.getGL().getGL4();

        // Update the OpenGL viewport to match the new window size.
        gl.glViewport(0, 0, width, height);
        System.out.println(drawable.getSurfaceWidth() + " - " + width);
        
        // Update the camera's aspect ratio based on the new window size.
        this.window.getCamera().setAspect(width, height);
    }

    /**
     * Disposes of any OpenGL resources when the window is closed or the program is
     * terminated.
     *
     * @param drawable The OpenGL drawable context.
     */
    @Override
    public void dispose(GLAutoDrawable drawable) {
        // Cleanup resources here (currently not implemented).
    }
}

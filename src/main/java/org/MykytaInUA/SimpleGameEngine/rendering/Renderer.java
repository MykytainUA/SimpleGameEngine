package org.mykytainua.simplegameengine.rendering;


import static com.jogamp.opengl.GL4.GL_BACK;
import static com.jogamp.opengl.GL4.GL_BLEND;
import static com.jogamp.opengl.GL4.GL_CCW;
import static com.jogamp.opengl.GL4.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL4.GL_CULL_FACE;
import static com.jogamp.opengl.GL4.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL4.GL_DEPTH_TEST;
import static com.jogamp.opengl.GL4.GL_LESS;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import java.util.ArrayList;
import org.mykytainua.simplegameengine.objects.Cube;
import org.mykytainua.simplegameengine.objects.Object3D;
import org.mykytainua.simplegameengine.objects.RandomObjectGenerator;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.texture.SolidColorComponent;
import org.mykytainua.simplegameengine.objects.components.transform.PositionComponent;
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

        // Initialize OpenGL settings.
        this.initOpenGLSettings(drawable);

        // Set up component classes for objects.
        ArrayList<Class<? extends Component>> componentByCopy = new ArrayList<>(0);
        ArrayList<Class<? extends Component>> componentByReference = new ArrayList<>(0);

        // Add components that are copied to objects.
        componentByCopy.add(SolidColorComponent.class);
        componentByCopy.add(PositionComponent.class);

        // Initialize the random object generator.
        generator = new RandomObjectGenerator(componentByReference);

        // Create and initialize objects with random data.
        Object3D[] objects = new Object3D[1000];
        for (int i = 0; i < objects.length; i++) {
            objects[i] = generator.getRandomObject(Cube.class, componentByCopy);
        }

        // Initialize shader program with shaders and objects.
        this.shaderProgram = new ShaderProgram(".\\src\\main\\java\\shaders\\vertexShader.glsl",
                                               ".\\src\\main\\java\\shaders\\fragmentShader.glsl", 
                                               objects[0].getComponentClasses());

        // Add camera to the shader program.
        this.shaderProgram.addCamera(window.getCamera());

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

    /**
     * Initializes various OpenGL settings such as swap interval, clear color, depth
     * testing, and culling.
     *
     * @param drawable The OpenGL drawable context.
     */
    private void initOpenGLSettings(GLAutoDrawable drawable) {
        GL4 gl = (GL4) GLContext.getCurrentGL();

        // Set the swap interval to enable vsync (1 for enabling, 0 for disabling).
        gl.setSwapInterval(1);

        // Set the clear color to black.
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Enable depth testing and set the depth function.
        gl.glEnable(GL_DEPTH_TEST);
        gl.glDepthFunc(GL_LESS);

        // Disable blending.
        gl.glDisable(GL_BLEND);

        // Enable face culling and set the culling mode to remove back faces.
        gl.glEnable(GL_CULL_FACE);
        gl.glCullFace(GL_BACK);
        gl.glFrontFace(GL_CCW);

        // Set the initial OpenGL viewport.
        gl.glViewport(0, 0, drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
    }
}

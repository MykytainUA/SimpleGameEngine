package org.mykytainua.simplegameengine.rendering;


import static com.jogamp.opengl.GL.GL_BACK;
import static com.jogamp.opengl.GL.GL_CCW;
import static com.jogamp.opengl.GL.GL_LESS;
import static com.jogamp.opengl.GL4.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL4.GL_DEPTH_BUFFER_BIT;
import static com.jogamp.opengl.GL4.GL_FRONT_AND_BACK;
import static com.jogamp.opengl.GL4.GL_FILL;

import com.jogamp.opengl.GL4;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.GLEventListener;
import java.util.ArrayList;

import org.joml.Vector4f;
import org.mykytainua.simplegameengine.objects.Pyramid;
import org.mykytainua.simplegameengine.objects.RandomObjectGenerator;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.texture.SolidColorComponent;
import org.mykytainua.simplegameengine.objects.components.transform.PositionComponent;
import org.mykytainua.simplegameengine.objects.components.transform.RotationComponent;
import org.mykytainua.simplegameengine.objects.components.transform.SizeComponent;
import org.mykytainua.simplegameengine.rendering.shaders.RenderSetting;
import org.mykytainua.simplegameengine.rendering.shaders.ShaderProgram;
import org.mykytainua.simplegameengine.settings.OpenGLSettings;
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
    
    private long frameCount = 0;
    
    ArrayList<Class<? extends Component>> componentByCopy = new ArrayList<>(0);
    ArrayList<Class<? extends Component>> componentByReference = new ArrayList<>(0);

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
        
        RenderSetting renderSetting = new RenderSetting();
        
        renderSetting.setVSync(false);
        renderSetting.setBlending(false);
        renderSetting.setDepthTesting(true, GL_LESS);
        renderSetting.setFaceCulling(true, GL_CCW, GL_BACK);
        
        Vector4f backgroundColor = new Vector4f(0.0f, 0.0f, 0.0f, 1.0f);
        OpenGLSettings.setClearColor(backgroundColor);

        // Ensure all previous OpenGL commands have been completed.
        gl.glFinish();

        // Set up component classes for objects.
        //ArrayList<Class<? extends Component>> componentByCopy = new ArrayList<>(0);

        // Add components that are copied to objects.
        componentByCopy.add(SolidColorComponent.class);
        componentByCopy.add(PositionComponent.class);
        componentByCopy.add(RotationComponent.class);
        componentByCopy.add(SizeComponent.class);
        
        // Add components by reference.
        ArrayList<Class<? extends Component>> componentByReference = new ArrayList<>(0);
        
        // Initialize the random object generator.
        generator = new RandomObjectGenerator(componentByReference);

        // Initialize shader program with shaders and objects.
        this.shaderProgram = new ShaderProgram(renderSetting, window.getCamera());

        // Add objects to the shader program for rendering.
        for(int i = 0; i < 1; i++) {
            this.shaderProgram.addObject(generator.getRandomObject(Pyramid.class, componentByCopy));
        }
        
        
        gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
        

        // Check for OpenGL errors.
        Utils.checkOpenGLErrors();

        // Resize the window to match the OpenGL context.
        this.window.resizeWindow();
        System.out.println(this.shaderProgram);
        
    }

    /**
     * Called every frame to render the scene. Clears the screen and renders objects
     * using the shader program.
     *
     * @param drawable The OpenGL drawable context.
     */
    @Override
    public void display(GLAutoDrawable drawable) {
        frameCount++;
        // Update user input actions on objects.
        this.window.getUserInputListener().updateActionsOnObjects();
        
        this.shaderProgram.addObject(generator.getRandomObject(Pyramid.class, componentByCopy));

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
}

package org.mykytainua.simplegameengine.window;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.Animator;
import java.awt.Dimension;
import org.joml.Vector3f;
import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.rendering.PerspectiveParameters;
import org.mykytainua.simplegameengine.rendering.Renderer;
import org.mykytainua.simplegameengine.userinput.UserInputListener;
import org.mykytainua.simplegameengine.userinput.UserInputNEWTActionListener;
import org.mykytainua.simplegameengine.userinput.UserInputSwingAWTActionListener;

/**
 * Manages the common components of the game window, including the camera,
 * input listener, renderer, and animator.
 * This class is responsible for initializing and configuring these components
 * based on the specific window type.
 */
public class CommonWindowComponents {

    // Game window and associated components
    private GameEngineWindow window;
    private UserInputListener userInputListener;
    private Camera camera;
    private Renderer renderer;
    private Animator animator;

    // Window dimensions
    private Dimension widowDimensionInPixels;

    // Cursor visibility and lock flags
    private boolean isCursorVisible = true;
    private boolean isMouseLocked = false;

    /**
     * Constructs a new instance of CommonWindowComponents for the specified window.
     *
     * @param window The window for which the components are being initialized.
     */
    public CommonWindowComponents(GameEngineWindow window) {
        this.window = window;
        this.widowDimensionInPixels = new Dimension();

        // Initialize camera, input listener, renderer, and animator
        this.initializeCamera();
        this.initializeUserInputController();
        this.initalizeRenderer();
        this.initializeAnimator();
    }

    /**
     * Initializes the camera with default perspective parameters and position.
     */
    private void initializeCamera() {
        PerspectiveParameters cameraPerspectiveParameters = new PerspectiveParameters(
                900, // width
                600, // height
                (float) Math.toRadians(60.0f), // FOV
                0.1f, // zNear
                10000.0f); // zFar

        Vector3f cameraPosition = new Vector3f(0.0f, 0.0f, 3.0f);
        Vector3f targetPosition = new Vector3f(0.0f, 0.0f, 0.0f);
        Vector3f upDirection = new Vector3f(0.0f, 1.0f, 0.0f);

        this.camera = new Camera(cameraPosition, 
                                 targetPosition, 
                                 upDirection, 
                                 cameraPerspectiveParameters);
    }

    /**
     * Initializes the user input controller based on the window type.
     * Adds the appropriate listeners for keyboard, mouse, and motion events.
     */
    private void initializeUserInputController() {
        if (window instanceof MainNEWTWindow) {
            this.userInputListener = new UserInputNEWTActionListener();
            this.userInputListener.add(this.getCamera());

            MainNEWTWindow window = (MainNEWTWindow) this.window;
            window.addKeyListener((UserInputNEWTActionListener) userInputListener);
            window.addMouseListener((UserInputNEWTActionListener) userInputListener);

        } else if (window instanceof GLCanvasWrapper) {
            this.userInputListener = new UserInputSwingAWTActionListener();
            this.userInputListener.add(this.getCamera());

            GLCanvasWrapper window = (GLCanvasWrapper) this.window;
            window.addKeyListener((UserInputSwingAWTActionListener) userInputListener);
            window.addMouseListener((UserInputSwingAWTActionListener) userInputListener);
            window.addMouseMotionListener((UserInputSwingAWTActionListener) userInputListener);
            window.addMouseWheelListener((UserInputSwingAWTActionListener) userInputListener);

        } else if (window instanceof GLPanelWrapper) {
            this.userInputListener = new UserInputSwingAWTActionListener();
            this.userInputListener.add(this.getCamera());

            GLPanelWrapper window = (GLPanelWrapper) this.window;
            window.addKeyListener((UserInputSwingAWTActionListener) userInputListener);
            window.addMouseListener((UserInputSwingAWTActionListener) userInputListener);
            window.addMouseMotionListener((UserInputSwingAWTActionListener) userInputListener);
            window.addMouseWheelListener((UserInputSwingAWTActionListener) userInputListener);
        }
    }

    /**
     * Initializes the renderer and adds it as a GLEventListener to the window.
     */
    private void initalizeRenderer() {
        this.renderer = new Renderer(window);
        GLAutoDrawable drawable = (GLAutoDrawable) window;
        drawable.addGLEventListener(renderer);
    }

    /**
     * Initializes the animator and sets it to update at 1000 FPS.
     */
    private void initializeAnimator() {
        this.animator = new Animator((GLAutoDrawable) window);
        this.animator.setUpdateFPSFrames(1000, System.out);
        GLAutoDrawable drawable = (GLAutoDrawable) window;
        drawable.setAnimator(animator);
    }

    /**
     * Gets the camera associated with this window's components.
     *
     * @return The camera object.
     */
    public Camera getCamera() {
        return this.camera;
    }

    /**
     * Gets the user input listener associated with this window.
     *
     * @return The user input listener object.
     */
    public UserInputListener getUserInputListener() {
        return this.userInputListener;
    }

    /**
     * Gets the animator associated with this window's components.
     *
     * @return The animator object.
     */
    public Animator getAnimator() {
        return this.animator;
    }

    /**
     * Sets the cursor visibility flag.
     *
     * @param isCursorVisible If true, the cursor will be visible.
     */
    public void setCursorVisibleFlag(boolean isCursorVisible) {
        this.isCursorVisible = isCursorVisible;
    }

    /**
     * Sets the mouse lock flag.
     *
     * @param isMouseLocked If true, the mouse will be locked to the window.
     */
    public void setCursorLockedFlag(boolean isMouseLocked) {
        this.isMouseLocked = isMouseLocked;
    }

    /**
     * Gets the cursor visibility flag.
     *
     * @return True if the cursor is visible, false otherwise.
     */
    public boolean isCursorVisible() {
        return this.isCursorVisible;
    }

    /**
     * Gets the mouse lock flag.
     *
     * @return True if the mouse is locked, false otherwise.
     */
    public boolean isMouseLocked() {
        return this.isMouseLocked;
    }

    /**
     * Gets the current dimensions of the window in pixels.
     *
     * @return The window dimensions.
     */
    public Dimension getWidowDimentionInPixels() {
        return widowDimensionInPixels;
    }

    /**
     * Sets the width of the window in pixels.
     *
     * @param width The width of the window.
     */
    public void setWidowWidthInPixels(int width) {
        this.widowDimensionInPixels.width = width;
    }

    /**
     * Sets the height of the window in pixels.
     *
     * @param height The height of the window.
     */
    public void setWidowHeightInPixels(int height) {
        this.widowDimensionInPixels.height = height;
    }
}

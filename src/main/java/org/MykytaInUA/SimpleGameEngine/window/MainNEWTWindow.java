package org.mykytainua.simplegameengine.window;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Set;
import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.userinput.KeyCodeMapper;
import org.mykytainua.simplegameengine.userinput.KeyResponser;
import org.mykytainua.simplegameengine.userinput.MouseResponser;
import org.mykytainua.simplegameengine.userinput.UserInputListener;
import org.mykytainua.simplegameengine.userinput.UserInputResponser;

/**
 * MainNEWTWindow class implements the window behavior for the game engine
 * using JOGL's GLWindow with the NEWT API. It handles user input, camera, 
 * window resizing, and rendering properties.
 */
public class MainNEWTWindow extends GLWindow
                            implements GameEngineWindow, 
                                       UserInputResponser, 
                                       MouseResponser, 
                                       KeyResponser {

    // The components for managing common window properties and user input.
    private CommonWindowComponents windowComponents;

    /**
     * Private constructor to initialize the window with OpenGL capabilities.
     *
     * @param capabilities OpenGL capabilities to configure the window.
     */
    private MainNEWTWindow(GLCapabilities capabilities) {
        super(GLWindow.create(capabilities).getDelegatedWindow());

        // Initialize window components like user input and camera.
        this.windowComponents = new CommonWindowComponents(this);

        // Add a listener to stop the animator when the window is destroyed.
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyed(WindowEvent e) {
                windowComponents.getAnimator().stop();
            }
        });

        // Setup window properties like focus and buffer swap mode.
        this.setupWindowProperties();
    }

    /**
     * Constructor to initialize the window with size parameters.
     *
     * @param capabilities OpenGL capabilities to configure the window.
     * @param width       The width of the window in pixels.
     * @param height      The height of the window in pixels.
     */
    public MainNEWTWindow(GLCapabilities capabilities, int width, int height) {
        this(capabilities);

        // Set window position and size.
        this.setPosition(32, 32);
        this.setSize(100, 100);

        // Set window dimensions using the provided width and height.
        this.windowComponents.setWidowWidthInPixels(width);
        this.windowComponents.setWidowHeightInPixels(height);
    }

    /**
     * Constructor to initialize the window in fullscreen mode.
     *
     * @param capabilities OpenGL capabilities to configure the window.
     * @param isFullScreen Boolean indicating whether the window should be fullscreen.
     */
    public MainNEWTWindow(GLCapabilities capabilities, boolean isFullScreen) {
        this(capabilities);

        // Set initial window size.
        this.setSize(100, 100);

        // Set fullscreen mode based on the provided parameter.
        this.setFullscreen(isFullScreen);
    }

    /**
     * Configures window properties such as close operation, buffer swap mode, and input listener.
     */
    private void setupWindowProperties() {
        this.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
        this.setAutoSwapBufferMode(true);
        this.requestFocus();

        // Register the current instance as a listener for user input.
        this.windowComponents.getUserInputListener().add(this);
    }

    /**
     * Retrieves the camera associated with this window.
     *
     * @return The camera object for the window.
     */
    @Override
    public Camera getCamera() {
        return this.windowComponents.getCamera();
    }

    /**
     * Starts the rendering process for the window's animator.
     */
    @Override
    public void startRendering() {
        this.windowComponents.getAnimator().start();
    }

    /**
     * Retrieves the user input listener for handling events.
     *
     * @return The user input listener.
     */
    @Override
    public UserInputListener getUserInputListener() {
        return this.windowComponents.getUserInputListener();
    }

    /**
     * Displays the window by setting it visible.
     */
    @Override
    public void showWindow() {
        this.setVisible(true);
    }

    /**
     * Resizes the window to match the current surface scale.
     */
    @Override
    public void resizeWindow() {
        Dimension windowDimension = this.windowComponents.getWidowDimentionInPixels();

        float[] surfaceScale = new float[2];
        this.getCurrentSurfaceScale(surfaceScale);

        int windowWidthInScale = (int) (windowDimension.width / surfaceScale[0]);
        int windowHeightInScale = (int) (windowDimension.height / surfaceScale[1]);

        this.setSize(windowWidthInScale, windowHeightInScale);
    }

    /**
     * Locks or unlocks the mouse cursor based on the input parameter.
     *
     * @param lockCursor Boolean flag indicating whether to lock the cursor.
     */
    @Override
    public void lockMouseCursor(boolean lockCursor) {
        this.windowComponents.setCursorLockedFlag(lockCursor);
        this.setMouseCursorVisible(!lockCursor);
        this.confinePointer(lockCursor);
    }

    /**
     * Retrieves the current position of the window on the screen.
     *
     * @return The window position as a Point object.
     */
    @Override
    public Point getWindowPosition() {
        float[] surfaceScale = new float[2];
        this.getCurrentSurfaceScale(surfaceScale);

        return new Point((int) (this.getX() * surfaceScale[0]), 
                         (int) (this.getY() * surfaceScale[1]));
    }

    /**
     * Retrieves the current dimensions of the window in pixels.
     *
     * @return The window's dimension.
     */
    @Override
    public Dimension getWindowDimention() {
        return this.windowComponents.getWidowDimentionInPixels();
    }

    /**
     * Retrieves the center point of the window relative to the display.
     *
     * @return The center point of the window.
     */
    @Override
    public Point getDisplayRelatedWindowCenter() {
        float[] surfaceScale = new float[2];
        this.getCurrentSurfaceScale(surfaceScale);

        return new Point((int) (((this.getWidth() / 2) + this.getX()) * surfaceScale[0]),
                (int) ((this.getHeight() / 2) + this.getY() * surfaceScale[1]));
    }

    /**
     * Retrieves the center point of the window relative to itself.
     *
     * @return The center point relative to the window.
     */
    @Override
    public Point getWindowRelatedWindowCenter() {
        float[] surfaceScale = new float[2];
        this.getCurrentSurfaceScale(surfaceScale);

        return new Point((int) ((this.getWidth() / 2) * surfaceScale[0]),
                (int) ((this.getHeight() / 2) * surfaceScale[1]));
    }

    /**
     * Sets the visibility of the mouse cursor.
     *
     * @param isMouseCursorVisible Boolean flag indicating whether to show the cursor.
     */
    @Override
    public void setMouseCursorVisible(boolean isMouseCursorVisible) {
        this.windowComponents.setCursorVisibleFlag(isMouseCursorVisible);
        this.setPointerVisible(isMouseCursorVisible);
    }

    /**
     * Checks if the mouse cursor is visible.
     *
     * @return True if the mouse cursor is visible, otherwise false.
     */
    @Override
    public boolean isMouseCursorVisible() {
        return this.windowComponents.isCursorVisible();
    }

    /**
     * Checks if the mouse cursor is locked.
     *
     * @return True if the mouse cursor is locked, otherwise false.
     */
    @Override
    public boolean isMouseLocked() {
        return this.windowComponents.isMouseLocked();
    }

    /**
     * Applies the mouse movement to the window.
     * This implementation does not modify anything.
     *
     * @param mousePosition The current mouse position.
     */
    @Override
    public void applyMouseMovement(Point2D mousePosition) {
        // No-op
    }

    /**
     * Applies the mouse wheel movement to the window.
     * This implementation does not modify anything.
     *
     * @param direction The direction of the wheel movement.
     */
    @Override
    public void applyMouseWheelMovement(float direction) {
        // No-op
    }

    /**
     * Applies the pressed keys to the window and handles certain keys.
     *
     * @param pressedKeys Set of pressed keys.
     */
    @Override
    public void applyPressedKeys(Set<Integer> pressedKeys) {
        for (int key : pressedKeys) {
            switch (key) {
                case KeyCodeMapper.VK_ESCAPE:
                    this.destroy(); // Exit the game on pressing ESC.
                    break;
                default:
                    break;
            }
        }
    }
}
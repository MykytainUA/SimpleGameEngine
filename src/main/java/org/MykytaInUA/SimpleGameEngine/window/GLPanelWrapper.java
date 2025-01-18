package org.mykytainua.simplegameengine.window;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.SwingUtilities;
import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.userinput.UserInputListener;

/**
 * GLPanelWrapper class extends GLJPanel and implements the GameEngineWindow interface.
 * It acts as a wrapper around GLJPanel to integrate OpenGL rendering with the game engine.
 * This class provides functionalities such as managing camera, user input, mouse behavior,
 * and window properties specific to the game engine.
 */
public class GLPanelWrapper extends GLJPanel implements GameEngineWindow {

    private static final long serialVersionUID = 1L;

    // Common window components that manage shared functionality like cursor state and dimensions.
    private transient CommonWindowComponents windowComponents;

    // Reference to the parent frame to handle resizing and positioning.
    private final transient MainGLSwingPanelWindow frame;

    /**
     * Constructor initializes the GLPanelWrapper with OpenGL capabilities and parent frame.
     *
     * @param capabilities OpenGL capabilities required for rendering.
     * @param frame        Parent frame associated with this panel.
     */
    public GLPanelWrapper(GLCapabilities capabilities, MainGLSwingPanelWindow frame) {
        super(capabilities); // Initialize the GLJPanel with the specified capabilities.

        this.frame = frame;

        // Initialize common window components to manage shared functionalities.
        this.windowComponents = new CommonWindowComponents(this);
        this.setAutoSwapBufferMode(true); // Enable automatic swapping of buffers.
    }

    /**
     * Displays the panel by making it visible.
     */
    @Override
    public void showWindow() {
        this.setVisible(true);
    }

    /**
     * Returns the camera associated with the panel.
     *
     * @return Camera object managed by the panel.
     */
    @Override
    public Camera getCamera() {
        return this.windowComponents.getCamera();
    }

    /**
     * Retrieves the user input listener for handling input events.
     *
     * @return UserInputListener instance.
     */
    @Override
    public UserInputListener getUserInputListener() {
        return this.windowComponents.getUserInputListener();
    }

    /**
     * Starts the rendering process by starting the animator.
     */
    @Override
    public void startRendering() {
        SwingUtilities.invokeLater(() -> {
            // Start the animator to enable rendering.
            this.windowComponents.getAnimator().start();
        });
    }

    /**
     * Locks or unlocks the mouse cursor within the panel.
     *
     * @param captureCursor True to lock the cursor, false to unlock it.
     */
    @Override
    public void lockMouseCursor(boolean captureCursor) {
        this.windowComponents.setCursorLockedFlag(captureCursor);
    }

    /**
     * Retrieves the screen position of the panel.
     *
     * @return Point representing the top-left corner of the panel on the screen.
     */
    @Override
    public Point getWindowPosition() {
        return this.frame.getLocationOnScreen();
    }

    /**
     * Retrieves the current dimensions of the panel.
     *
     * @return Dimension object representing the width and height of the panel.
     */
    @Override
    public Dimension getWindowDimention() {
        return this.frame.getSize();
    }

    /**
     * Computes the center of the panel relative to the display (screen).
     *
     * @return Point representing the center of the panel on the display.
     */
    @Override
    public Point getDisplayRelatedWindowCenter() {
        Dimension size = this.getWindowDimention();
        Point position = this.getWindowPosition();
        return new Point(position.x + size.width / 2, position.y + size.height / 2);
    }

    /**
     * Computes the center of the panel relative to the window.
     *
     * @return Point representing the center of the panel relative to its size.
     */
    @Override
    public Point getWindowRelatedWindowCenter() {
        Dimension size = this.getWindowDimention();
        return new Point(size.width / 2, size.height / 2);
    }

    /**
     * Sets the visibility of the mouse cursor.
     *
     * @param isCursorVisible True to make the cursor visible, false to hide it.
     */
    @Override
    public void setMouseCursorVisible(boolean isCursorVisible) {
        this.windowComponents.setCursorVisibleFlag(isCursorVisible);
    }

    /**
     * Checks if the mouse cursor is visible.
     *
     * @return True if the cursor is visible, false otherwise.
     */
    @Override
    public boolean isMouseCursorVisible() {
        return this.windowComponents.isCursorVisible();
    }

    /**
     * Checks if the mouse cursor is locked within the panel.
     *
     * @return True if the cursor is locked, false otherwise.
     */
    @Override
    public boolean isMouseLocked() {
        return this.windowComponents.isMouseLocked();
    }

    /**
     * Resizes the panel by delegating the operation to the parent frame.
     */
    @Override
    public void resizeWindow() {
        this.frame.resizeWindow();
    }

    /**
     * Retrieves the dimensions of the panel in pixels.
     *
     * @return Dimension representing the width and height in pixels.
     */
    public Dimension getWindowDimentionInPixels() {
        return this.windowComponents.getWidowDimentionInPixels();
    }

    /**
     * Sets the width of the panel in pixels.
     *
     * @param width Width of the panel in pixels.
     */
    public void setWindowWidthInPixels(int width) {
        this.windowComponents.setWidowWidthInPixels(width);
    }

    /**
     * Sets the height of the panel in pixels.
     *
     * @param height Height of the panel in pixels.
     */
    public void setWindowHeightInPixels(int height) {
        this.windowComponents.setWidowHeightInPixels(height);
    }
}

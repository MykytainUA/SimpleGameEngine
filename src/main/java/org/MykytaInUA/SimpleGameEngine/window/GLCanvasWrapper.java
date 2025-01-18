package org.mykytainua.simplegameengine.window;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.SwingUtilities;
import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.userinput.UserInputListener;

/**
 * GLCanvasWrapper class extends GLCanvas and implements the GameEngineWindow
 * interface. This class serves as a wrapper around GLCanvas to integrate OpenGL
 * rendering into the game engine. It provides functionalities to manage the
 * window, mouse behavior, and common components required for rendering and
 * interaction.
 */
public class GLCanvasWrapper extends GLCanvas implements GameEngineWindow {

    private static final long serialVersionUID = 1L;

    // Common window components managing shared functionality like cursor state and
    // dimensions.
    private transient CommonWindowComponents windowComponents;

    // Reference to the parent frame to handle resizing and positioning.
    private final transient MainAWTCanvasWindow frame;

    /**
     * Constructor initializes the GLCanvasWrapper with OpenGL capabilities and
     * parent frame.
     *
     * @param capabilities OpenGL capabilities required for rendering.
     * @param frame        Parent frame associated with this canvas.
     */
    public GLCanvasWrapper(GLCapabilities capabilities, MainAWTCanvasWindow frame) {
        super(capabilities); // Initialize the GLCanvas with the specified capabilities.

        this.frame = frame;

        // Initialize common window components to manage shared functionalities.
        this.windowComponents = new CommonWindowComponents(this);
        this.setAutoSwapBufferMode(true); // Enable automatic swapping of buffers.
    }

    /**
     * Displays the canvas by making it visible.
     */
    @Override
    public void showWindow() {
        this.setVisible(true);
    }

    /**
     * Retrieves the camera associated with the canvas.
     *
     * @return Camera object managed by the canvas.
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
     * Starts the rendering process by initiating the animator.
     */
    @Override
    public void startRendering() {
        SwingUtilities.invokeLater(() -> {
            // Start the animator to enable rendering.
            this.windowComponents.getAnimator().start();
        });
    }

    /**
     * Locks or unlocks the mouse cursor within the canvas.
     *
     * @param captureCursor True to lock the cursor, false to unlock it.
     */
    @Override
    public void lockMouseCursor(boolean captureCursor) {
        this.windowComponents.setCursorLockedFlag(captureCursor);
    }

    /**
     * Retrieves the screen position of the canvas.
     *
     * @return Point representing the top-left corner of the canvas on the screen.
     */
    @Override
    public Point getWindowPosition() {
        // TODO Implement logic to return the screen position of the canvas.
        return null;
    }

    /**
     * Retrieves the current dimensions of the canvas.
     *
     * @return Dimension object representing the width and height of the canvas.
     */
    @Override
    public Dimension getWindowDimention() {
        // TODO Implement logic to return the dimensions of the canvas.
        return null;
    }

    /**
     * Computes the center of the canvas relative to the display (screen).
     *
     * @return Point representing the center of the canvas on the display.
     */
    @Override
    public Point getDisplayRelatedWindowCenter() {
        // TODO Implement logic to compute the display-related center of the canvas.
        return null;
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
     * Checks if the mouse cursor is locked within the canvas.
     *
     * @return True if the cursor is locked, false otherwise.
     */
    @Override
    public boolean isMouseLocked() {
        return this.windowComponents.isMouseLocked();
    }

    /**
     * Computes the center of the canvas relative to its size.
     *
     * @return Point representing the center of the canvas relative to its
     *         dimensions.
     */
    @Override
    public Point getWindowRelatedWindowCenter() {
        // TODO Implement logic to compute the center of the canvas relative to its
        // dimensions.
        return null;
    }

    /**
     * Resizes the canvas by delegating the operation to the parent frame.
     */
    @Override
    public void resizeWindow() {
        this.frame.resizeWindow();
    }

    /**
     * Retrieves the dimensions of the canvas in pixels.
     *
     * @return Dimension representing the width and height in pixels.
     */
    public Dimension getWidowDimentionInPixels() {
        return this.windowComponents.getWidowDimentionInPixels();
    }

    /**
     * Sets the width of the canvas in pixels.
     *
     * @param width Width of the canvas in pixels.
     */
    public void setWidowWidthInPixels(int width) {
        this.windowComponents.setWidowWidthInPixels(width);
    }

    /**
     * Sets the height of the canvas in pixels.
     *
     * @param height Height of the canvas in pixels.
     */
    public void setWidowHeightInPixels(int height) {
        this.windowComponents.setWidowHeightInPixels(height);
    }
}


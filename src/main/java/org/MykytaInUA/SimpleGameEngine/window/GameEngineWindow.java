package org.mykytainua.simplegameengine.window;

import java.awt.Dimension;
import java.awt.Point;
import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.userinput.UserInputListener;

/**
 * Interface representing a window in the game engine.
 * Provides methods for managing window visibility, dimensions, rendering,
 * input handling, and cursor behavior.
 */
public interface GameEngineWindow {

    /**
     * Makes the window visible on the screen.
     */
    public void showWindow();

    /**
     * Checks if the window is currently visible.
     *
     * @return True if the window is visible, false otherwise.
     */
    public boolean isVisible();

    /**
     * Resizes the window based on its current or predefined dimensions.
     */
    public void resizeWindow();

    /**
     * Retrieves the camera associated with the game engine window.
     *
     * @return The {@link Camera} object.
     */
    public Camera getCamera();

    /**
     * Retrieves the user input listener associated with the window.
     *
     * @return The {@link UserInputListener} object.
     */
    public UserInputListener getUserInputListener();

    /**
     * Retrieves the position of the window relative to the screen.
     *
     * @return The position as a {@link Point}.
     */
    public Point getWindowPosition();

    /**
     * Retrieves the dimensions of the window.
     *
     * @return The dimensions as a {@link Dimension}.
     */
    public Dimension getWindowDimention();

    /**
     * Retrieves the center of the window relative to the display.
     *
     * @return The center as a {@link Point}.
     */
    public Point getDisplayRelatedWindowCenter();

    /**
     * Retrieves the center of the window relative to its own size.
     *
     * @return The center as a {@link Point}.
     */
    public Point getWindowRelatedWindowCenter();

    /**
     * Locks or unlocks the mouse cursor within the window.
     *
     * @param isCursorLocked True to lock the cursor, false to unlock.
     */
    public void lockMouseCursor(boolean isCursorLocked);

    /**
     * Sets the visibility of the mouse cursor.
     *
     * @param isCursorVisible True to make the cursor visible, false to hide it.
     */
    public void setMouseCursorVisible(boolean isCursorVisible);

    /**
     * Checks if the mouse cursor is currently visible.
     *
     * @return True if the cursor is visible, false otherwise.
     */
    public boolean isMouseCursorVisible();

    /**
     * Checks if the mouse cursor is locked within the window.
     *
     * @return True if the mouse is locked, false otherwise.
     */
    public boolean isMouseLocked();

    /**
     * Starts the rendering process for the game engine window.
     */
    public void startRendering();
}

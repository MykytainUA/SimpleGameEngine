package org.mykytainua.simplegameengine.userinput;

import java.awt.Point;

/**
 * Interface that defines methods for responding to user input actions,
 * including updating objects based on input, and managing user input
 * responders.
 */
public interface UserInputListener {

    /**
     * Updates the actions on all objects that respond to user input. This method
     * should be called every frame to process the input and apply changes to
     * objects.
     */
    public void updateActionsOnObjects();

    /**
     * Adds a {@link UserInputResponser} to the list of objects that can respond to
     * user input.
     *
     * @param userInputControllable an object implementing the
     *                              {@link UserInputResponser} interface.
     */
    public void add(UserInputResponser userInputControllable);

    /**
     * Retrieves a {@link UserInputResponser} by its index in the list of input
     * responders.
     *
     * @param index the index of the user input responder to retrieve.
     * @return the {@link UserInputResponser} at the specified index.
     */
    public UserInputResponser get(int index);

    /**
     * Gets the mouse cursor's position relative to the display.
     *
     * @return a {@link Point} representing the mouse cursor position relative to
     *         the display, or {@code null} if the mouse is not detected.
     */
    public Point getMouseCursorDisplayRelatedPosition();

    /**
     * Gets the mouse cursor's position relative to the window.
     *
     * @return a {@link Point} representing the mouse cursor position relative to
     *         the window.
     */
    public Point getMouseCursorWindowRelatedPosition();
}

package org.mykytainua.simplegameengine.userinput;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.MouseInputListener;

/**
 * Handles user input events in a Swing/AWT environment. This class implements
 * various input listeners for keyboard, mouse, and mouse wheel interactions,
 * processes these events, and applies them to a list of user-controllable
 * objects.
 */
public class UserInputSwingAWTActionListener
        implements UserInputListener, KeyListener, MouseInputListener, MouseWheelListener {

    // A set containing the currently pressed keys.
    private Set<Integer> pressedKeys = new HashSet<Integer>();

    // A list of objects that respond to user input events.
    private List<UserInputResponser> userInputControllableList = 
            new ArrayList<UserInputResponser>();

    // Handles relative mouse movement calculations.
    private RelativeMouseMovementListener relativeMouseMovementListener;

    // Stores data related to mouse movement, such as position and wheel rotation.
    private MouseMovementData mouseMovementData = new MouseMovementData();

    /**
     * Initializes the input listener and its dependencies.
     */
    public UserInputSwingAWTActionListener() {
        this.relativeMouseMovementListener = new RelativeMouseMovementListener();
    }

    /**
     * Invoked when a key is typed (not used in this implementation).
     *
     * @param e the KeyEvent object containing details about the event.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Invoked when a key is pressed.
     *
     * @param e the KeyEvent object containing details about the event.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key pressed:" + e.getKeyCode());
        this.pressedKeys.add(KeyCodeMapper.getMappedKeyFromSwing(e.getKeyCode()));
    }

    /**
     * Invoked when a key is released.
     *
     * @param e the KeyEvent object containing details about the event.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Key released:" + e.getKeyCode());
        this.pressedKeys.remove(KeyCodeMapper.getMappedKeyFromSwing(e.getKeyCode()));
    }

    /**
     * Adds a user input responder to the list of controllable objects.
     *
     * @param userInputControllable the UserInputResponser to add.
     */
    @Override
    public void add(UserInputResponser userInputControllable) {
        userInputControllableList.add(userInputControllable);
    }

    /**
     * Retrieves a user input responder from the list by its index.
     *
     * @param index the index of the responder in the list.
     * @return the UserInputResponser at the specified index.
     */
    public UserInputResponser get(int index) {
        return userInputControllableList.get(index);
    }

    /**
     * Updates actions on all registered user input responders based on the current
     * state of inputs. This includes key presses and mouse movements.
     */
    public void updateActionsOnObjects() {
        this.relativeMouseMovementListener.fetchMouseData();

        this.mouseMovementData.setRelativeMouseShiftX(-relativeMouseMovementListener.getXShift());
        this.mouseMovementData.setRelativeMouseShiftY(-relativeMouseMovementListener.getYShift());

        for (UserInputResponser userInputResponser : userInputControllableList) {
            this.applyPressedKeysFor(userInputResponser);
            this.applyMouseMovementFor(userInputResponser);
        }

        // Reset mouse data after applying actions.
        this.mouseMovementData.setMouseWheelMovement(0);
        this.mouseMovementData.setMouseShiftX(0);
        this.mouseMovementData.setMouseShiftY(0);
        this.mouseMovementData.setRelativeMouseShiftX(0);
        this.mouseMovementData.setRelativeMouseShiftY(0);
    }

    /**
     * Applies the pressed keys to a user input responder if it supports key
     * handling.
     *
     * @param userInputResponser the responder to apply the pressed keys to.
     */
    private void applyPressedKeysFor(UserInputResponser userInputResponser) {
        if (KeyResponser.class.isInstance(userInputResponser)) {
            KeyResponser responser = (KeyResponser) userInputResponser;
            responser.applyPressedKeys(this.pressedKeys);
        }
    }

    /**
     * Applies mouse movement and wheel actions to a user input responder if it
     * supports mouse handling.
     *
     * @param userInputResponser the responder to apply the mouse actions to.
     */
    private void applyMouseMovementFor(UserInputResponser userInputResponser) {
        float mouseWheelMovement = this.mouseMovementData.getMouseWheelMovement();
        Point2D.Float relativeMouseShift = this.mouseMovementData.getRelativeMouseShift();

        if (MouseResponser.class.isInstance(userInputResponser)) {
            MouseResponser responser = (MouseResponser) userInputResponser;
            responser.applyMouseWheelMovement(mouseWheelMovement);
            responser.applyMouseMovement(relativeMouseShift);
        }
    }

    /**
     * Invoked when the mouse is clicked (not used in this implementation).
     *
     * @param e the MouseEvent object containing details about the event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button is pressed (not used in this implementation).
     *
     * @param e the MouseEvent object containing details about the event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
    }

    /**
     * Invoked when a mouse button is released (not used in this implementation).
     *
     * @param e the MouseEvent object containing details about the event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Invoked when the mouse enters a component (not used in this implementation).
     *
     * @param e the MouseEvent object containing details about the event.
     */
    @Override
    public void mouseEntered(MouseEvent e) {
    }

    /**
     * Invoked when the mouse exits a component (not used in this implementation).
     *
     * @param e the MouseEvent object containing details about the event.
     */
    @Override
    public void mouseExited(MouseEvent e) {
    }

    /**
     * Invoked when the mouse is dragged (not used in this implementation).
     *
     * @param e the MouseEvent object containing details about the event.
     */
    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Invoked when the mouse is moved. Updates mouse movement data to reflect the
     * change.
     *
     * @param e the MouseEvent object containing details about the event.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Point mousePositionInWindow = this.mouseMovementData.getMousePositionInWindow();
        Point mouseShift = this.mouseMovementData.getMouseShift();

        // Add to shift (shift is reset after every frame)
        this.mouseMovementData.setMouseShiftX(mouseShift.x + mousePositionInWindow.x - e.getX());
        this.mouseMovementData.setMouseShiftY(mouseShift.y + mousePositionInWindow.y - e.getY());

        // Update the current mouse position
        this.mouseMovementData.setMousePositionXInWindow(e.getX());
        this.mouseMovementData.setMousePositionYInWindow(e.getY());
    }

    /**
     * Invoked when the mouse wheel is moved. Updates the mouse wheel movement data.
     *
     * @param e the MouseWheelEvent object containing details about the event.
     */
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        this.mouseMovementData.setMouseWheelMovement(-e.getWheelRotation());
    }

    /**
     * Gets the display-related position of the mouse cursor.
     *
     * @return the position of the mouse cursor relative to the screen, or null if
     *         the mouse is not detected.
     */
    @Override
    public Point getMouseCursorDisplayRelatedPosition() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();

        if (pointerInfo != null) {
            return pointerInfo.getLocation();
        }

        return null; // Mouse is not detected, possibly disconnected
    }

    /**
     * Gets the window-related position of the mouse cursor.
     *
     * @return the position of the mouse cursor relative to the window.
     */
    @Override
    public Point getMouseCursorWindowRelatedPosition() {
        return this.mouseMovementData.getMousePositionInWindow();
    }
}

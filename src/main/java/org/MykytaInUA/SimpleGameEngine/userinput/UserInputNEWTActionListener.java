package org.mykytainua.simplegameengine.userinput;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The UserInputNEWTActionListener class implements user input handling for
 * keyboard and mouse events in a NEWT environment. It integrates with a list of
 * UserInputResponser instances to delegate actions based on the captured input.
 */
public class UserInputNEWTActionListener implements UserInputListener, KeyListener, MouseListener {

    // Set of currently pressed keys
    private Set<Integer> pressedKeys = new HashSet<>();

    // List of objects that respond to user input
    private List<UserInputResponser> userInputControllableList = new ArrayList<>();

    // Listener for relative mouse movement
    private RelativeMouseMovementListener relativeMouseMovementListener;

    // Object to store mouse movement data
    private MouseMovementData mouseMovementData = new MouseMovementData();

    /**
     * Constructs a UserInputNEWTActionListener and initializes the
     * RelativeMouseMovementListener.
     */
    public UserInputNEWTActionListener() {
        this.relativeMouseMovementListener = new RelativeMouseMovementListener();
    }

    /**
     * Adds a UserInputResponser object to the list of controllable objects.
     *
     * @param userInputControllable The UserInputResponser to add.
     */
    @Override
    public void add(UserInputResponser userInputControllable) {
        this.userInputControllableList.add(userInputControllable);
    }

    /**
     * Retrieves a UserInputResponser at the specified index in the list.
     *
     * @param index The index of the UserInputResponser to retrieve.
     * @return The UserInputResponser at the given index.
     */
    public UserInputResponser get(int index) {
        return this.userInputControllableList.get(index);
    }

    /**
     * Handles the event where a key is pressed. Adds the mapped key to the set of
     * pressed keys.
     *
     * @param e The KeyEvent associated with the key press.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (!e.isAutoRepeat()) {
            this.pressedKeys.add(KeyCodeMapper.getMappedKeyFromNEWT(e.getKeyCode()));
        }
    }

    /**
     * Handles the event where a key is released. Removes the mapped key from the
     * set of pressed keys.
     *
     * @param e The KeyEvent associated with the key release.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (!e.isAutoRepeat()) {
            this.pressedKeys.remove(KeyCodeMapper.getMappedKeyFromNEWT(e.getKeyCode()));
        }
    }

    // MouseListener method stubs for additional behavior if needed.
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * Handles mouse movement events, updating the mouse movement data with the
     * current position and shift values.
     *
     * @param e The MouseEvent associated with the mouse movement.
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        Point mousePositionInWindow = this.mouseMovementData.getMousePositionInWindow();
        Point mouseShift = this.mouseMovementData.getMouseShift();

        // Update shift based on the change in position
        this.mouseMovementData.setMouseShiftX(mouseShift.x + mousePositionInWindow.x - e.getX());
        this.mouseMovementData.setMouseShiftY(mouseShift.y + mousePositionInWindow.y - e.getY());

        // Update the current mouse position
        this.mouseMovementData.setMousePositionXInWindow(e.getX());
        this.mouseMovementData.setMousePositionYInWindow(e.getY());
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    /**
     * Handles mouse wheel movement, updating the mouse movement data with the wheel
     * rotation value.
     *
     * @param e The MouseEvent associated with the mouse wheel movement.
     */
    @Override
    public void mouseWheelMoved(MouseEvent e) {
        this.mouseMovementData.setMouseWheelMovement(e.getRotation()[1]);
    }

    /**
     * Updates the actions for all objects in the userInputControllableList based on
     * the current input state. Resets mouse movement and wheel data after applying
     * updates.
     */
    public void updateActionsOnObjects() {
        this.relativeMouseMovementListener.fetchMouseData();

        this.mouseMovementData.setRelativeMouseShiftX(-relativeMouseMovementListener.getXShift());
        this.mouseMovementData.setRelativeMouseShiftY(-relativeMouseMovementListener.getYShift());

        for (UserInputResponser userInputResponser : userInputControllableList) {
            this.applyPressedKeysFor(userInputResponser);
            this.applyMouseMovementFor(userInputResponser);
        }

        // Reset mouse movement data
        this.mouseMovementData.setMouseWheelMovement(0);
        this.mouseMovementData.setMouseShiftX(0);
        this.mouseMovementData.setMouseShiftY(0);
        this.mouseMovementData.setRelativeMouseShiftX(0);
        this.mouseMovementData.setRelativeMouseShiftY(0);
    }

    /**
     * Applies the current set of pressed keys to a UserInputResponser if it
     * implements KeyResponser.
     *
     * @param userInputResponser The UserInputResponser to update.
     */
    private void applyPressedKeysFor(UserInputResponser userInputResponser) {
        if (userInputResponser instanceof KeyResponser) {
            KeyResponser responser = (KeyResponser) userInputResponser;
            responser.applyPressedKeys(this.pressedKeys);
        }
    }

    /**
     * Applies the current mouse movement data to a UserInputResponser if it
     * implements MouseResponser.
     *
     * @param userInputResponser The UserInputResponser to update.
     */
    private void applyMouseMovementFor(UserInputResponser userInputResponser) {
        if (userInputResponser instanceof MouseResponser) {
            MouseResponser responser = (MouseResponser) userInputResponser;
            responser.applyMouseWheelMovement(this.mouseMovementData.getMouseWheelMovement());
            responser.applyMouseMovement(this.mouseMovementData.getRelativeMouseShift());
        }
    }

    /**
     * Retrieves the mouse cursor's position on the display.
     *
     * @return The Point representing the mouse cursor's display position, or null
     *         if the mouse is not detected.
     */
    @Override
    public Point getMouseCursorDisplayRelatedPosition() {
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        return pointerInfo != null ? pointerInfo.getLocation() : null;
    }

    /**
     * Retrieves the mouse cursor's position relative to the window.
     *
     * @return The Point representing the mouse cursor's window position.
     */
    @Override
    public Point getMouseCursorWindowRelatedPosition() {
        return this.mouseMovementData.getMousePositionInWindow();
    }
}

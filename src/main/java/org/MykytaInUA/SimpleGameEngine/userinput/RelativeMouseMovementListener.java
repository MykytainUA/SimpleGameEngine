package org.mykytainua.simplegameengine.userinput;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

/**
 * A listener for relative mouse movement, detecting shifts in the X and Y axes
 * using controllers provided by the JInput library.
 */
public class RelativeMouseMovementListener {

    /** An array of all controllers available in the system. */
    private Controller[] controllers;

    /** The currently active mouse controller. */
    private Controller activeMouse = null;

    /** The current X-axis shift detected by the active mouse. */
    private float shiftX = 0;

    /** The current Y-axis shift detected by the active mouse. */
    private float shiftY = 0;

    /**
     * Initializes the listener by retrieving all controllers available in the
     * system environment.
     */
    public RelativeMouseMovementListener() {
        this.controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
    }

    /**
     * Updates and processes mouse data. 
     * 
     * <p>This method should be called frequently to retrieve the current 
     * relative mouse movements. It attempts to find the active mouse 
     * if none is currently selected, and processes its data if available.
     */
    public void fetchMouseData() {

        // Attempt to find the active mouse if not already set
        if (this.activeMouse == null) {
            this.activeMouse = this.tryToGetCurrentActiveMouse();
        }

        // Process data if an active mouse is found and successfully polled
        if (activeMouse != null && activeMouse.poll()) {

            Component[] components = activeMouse.getComponents();

            // Iterate over components to capture X and Y axis data
            for (Component component : components) {
                if (component.getIdentifier() == Component.Identifier.Axis.X) {

                    this.setXShift(component.getPollData());

                } else if (component.getIdentifier() == Component.Identifier.Axis.Y) {

                    this.setYShift(component.getPollData());
                }
            }
        }
    }

    /**
     * Gets the current X-axis shift detected by the mouse.
     *
     * @return The relative shift on the X-axis as a float value.
     */
    public float getXShift() {
        return shiftX;
    }

    /**
     * Sets the current X-axis shift detected by the mouse.
     *
     * @param shiftX The new X-axis shift value.
     */
    private void setXShift(float shiftX) {
        this.shiftX = shiftX;
    }

    /**
     * Gets the current Y-axis shift detected by the mouse.
     *
     * @return The relative shift on the Y-axis as a float value.
     */
    public float getYShift() {
        return shiftY;
    }

    /**
     * Sets the current Y-axis shift detected by the mouse.
     *
     * @param shiftY The new Y-axis shift value.
     */
    private void setYShift(float shiftY) {
        this.shiftY = shiftY;
    }

    /**
     * Attempts to find the currently active mouse controller.
     *
     * @return The detected active mouse controller, or {@code null} if no active
     *         mouse is found.
     */
    private Controller tryToGetCurrentActiveMouse() {

        Controller activeMouse = null;
        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.MOUSE) {

                controller.poll();
                EventQueue queue = controller.getEventQueue();
                Event mouseEvent = new Event();

                // Check if there is an event in the queue
                while (queue.getNextEvent(mouseEvent)) {
                    activeMouse = controller;
                    // Stop searching after detecting activity
                    break;
                }
                if (activeMouse != null) {
                    break;
                }
            }
        }

        return activeMouse;
    }
}

package org.mykytainua.simplegameengine.userinput;

import java.awt.geom.Point2D;

/**
 * An interface representing an entity that responds to mouse movement and mouse
 * wheel events.
 */
public interface MouseResponser {

    /**
     * Applies the mouse movement to the implementing object.
     *
     * @param mousePosition a {@link Point2D} representing the current mouse
     *                      position.
     */
    public void applyMouseMovement(Point2D mousePosition);

    /**
     * Applies the mouse wheel movement to the implementing object.
     *
     * @param direction a float representing the direction of the mouse wheel
     *                  movement. Positive values indicate scrolling up, and
     *                  negative values indicate scrolling down.
     */
    public void applyMouseWheelMovement(float direction);
}

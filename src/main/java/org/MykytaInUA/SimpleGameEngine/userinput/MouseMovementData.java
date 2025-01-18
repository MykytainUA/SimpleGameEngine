package org.mykytainua.simplegameengine.userinput;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * A data container for tracking mouse movement, position, and wheel movement.
 * Provides methods to retrieve and update mouse-related properties.
 */
public class MouseMovementData {

    /** The current mouse position within the window. */
    private Point mousePositionInWindow = new Point(0, 0);

    /** The shift in the mouse's position since the last update. */
    private Point mouseShift = new Point(0, 0);

    /** The relative shift in the mouse's position as a ratio. */
    private Point2D.Float relativeMouseShift = new Point2D.Float(0.0f, 0.0f);

    /** The movement of the mouse wheel since the last update. */
    private float mouseWheelMovement = 0.0f;

    /**
     * Gets the current mouse position within the window.
     *
     * @return A {@link Point} representing the mouse's position.
     */
    public Point getMousePositionInWindow() {
        return mousePositionInWindow;
    }

    /**
     * Sets the current mouse position within the window.
     *
     * @param mousePositionInWindow A {@link Point} representing the new mouse position.
     */
    public void setMousePositionInWindow(Point mousePositionInWindow) {
        this.mousePositionInWindow = mousePositionInWindow;
    }

    /**
     * Updates the X-coordinate of the mouse's position within the window.
     *
     * @param positionX The new X-coordinate.
     */
    public void setMousePositionXInWindow(int positionX) {
        this.mousePositionInWindow.x = positionX;
    }

    /**
     * Updates the Y-coordinate of the mouse's position within the window.
     *
     * @param positionY The new Y-coordinate.
     */
    public void setMousePositionYInWindow(int positionY) {
        this.mousePositionInWindow.y = positionY;
    }

    /**
     * Gets the shift in the mouse's position since the last update.
     *
     * @return A {@link Point} representing the change in position.
     */
    public Point getMouseShift() {
        return mouseShift;
    }

    /**
     * Sets the shift in the mouse's position since the last update.
     *
     * @param mouseShift A {@link Point} representing the change in position.
     */
    public void setMouseShift(Point mouseShift) {
        this.mouseShift = mouseShift;
    }

    /**
     * Updates the X-coordinate of the mouse's shift.
     *
     * @param shiftX The change in the X-coordinate.
     */
    public void setMouseShiftX(int shiftX) {
        this.mouseShift.x = shiftX;
    }

    /**
     * Updates the Y-coordinate of the mouse's shift.
     *
     * @param shiftY The change in the Y-coordinate.
     */
    public void setMouseShiftY(int shiftY) {
        this.mouseShift.y = shiftY;
    }

    /**
     * Gets the relative shift in the mouse's position as a ratio.
     *
     * @return A {@link Point2D.Float} representing the relative change in position.
     */
    public Point2D.Float getRelativeMouseShift() {
        return relativeMouseShift;
    }

    /**
     * Sets the relative shift in the mouse's position as a ratio.
     *
     * @param relativeMouseShift A {@link Point2D.Float} representing the relative
     *                           change in position.
     */
    public void setRelativeMouseShift(Point2D.Float relativeMouseShift) {
        this.relativeMouseShift = relativeMouseShift;
    }

    /**
     * Updates the relative X-coordinate shift of the mouse.
     *
     * @param relativeXShift The relative change in the X-coordinate.
     */
    public void setRelativeMouseShiftX(float relativeXShift) {
        this.relativeMouseShift.x = relativeXShift;
    }

    /**
     * Updates the relative Y-coordinate shift of the mouse.
     *
     * @param relativeYShift The relative change in the Y-coordinate.
     */
    public void setRelativeMouseShiftY(float relativeYShift) {
        this.relativeMouseShift.y = relativeYShift;
    }

    /**
     * Gets the movement of the mouse wheel since the last update.
     *
     * @return A float value representing the mouse wheel movement.
     */
    public float getMouseWheelMovement() {
        return mouseWheelMovement;
    }

    /**
     * Sets the movement of the mouse wheel.
     *
     * @param mouseWheelMovement A float value representing the mouse wheel movement.
     */
    public void setMouseWheelMovement(float mouseWheelMovement) {
        this.mouseWheelMovement = mouseWheelMovement;
    }
}

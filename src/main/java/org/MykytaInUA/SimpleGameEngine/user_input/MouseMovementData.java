package org.MykytaInUA.SimpleGameEngine.user_input;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 * Simple data class
 * Encapsulating mouse movement data
 */
public class MouseMovementData {
	
	private Point mousePositionInWindow = new Point(0, 0);
	private Point mouseShift = new Point(0, 0);
	private Point2D.Float relativeMouseShift = new Point2D.Float(0.0f, 0.0f);
	private float mouseWheelMovement = 0.0f;
	
	public Point getMousePositionInWindow() {
		return mousePositionInWindow;
	}
	
	public void setMousePositionInWindow(Point mousePositionInWindow) {
		this.mousePositionInWindow = mousePositionInWindow;
	}
	
	public void setMouseXPositionInWindow(int xPosition) {
		this.mousePositionInWindow.x = xPosition;
	}
	
	public void setMouseYPositionInWindow(int yPosition) {
		this.mousePositionInWindow.y = yPosition;
	}
	
	public Point getMouseShift() {
		return mouseShift;
	}
	
	public void setMouseShift(Point mouseShift) {
		this.mouseShift = mouseShift;
	}
	
	public void setMouseXShift(int xShift) {
		this.mouseShift.x = xShift;
	}
	
	public void setMouseYShift(int yShift) {
		this.mouseShift.y = yShift;
	}
	
	public Point2D.Float getRelativeMouseShift() {
		return relativeMouseShift;
	}
	
	public void setRelativeMouseShift(Point2D.Float relativeMouseShift) {
		this.relativeMouseShift = relativeMouseShift;
	}
	
	public void setRelativeMouseXShift(float relativeXShift) {
		this.relativeMouseShift.x = relativeXShift;
	}
	
	public void setRelativeMouseYShift(float relativeYShift) {
		this.relativeMouseShift.y = relativeYShift;
	}
	
	public float getMouseWheelMovement() {
		return mouseWheelMovement;
	}
	
	public void setMouseWheelMovement(float mouseWheelMovement) {
		this.mouseWheelMovement = mouseWheelMovement;
	}
}

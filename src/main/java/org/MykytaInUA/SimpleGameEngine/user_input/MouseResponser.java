package org.MykytaInUA.SimpleGameEngine.user_input;

import java.awt.geom.Point2D;

public interface MouseResponser {
	public void applyMouseMovement(Point2D mousePosition);
	public void applyMouseWheelMovement(float direction);
}

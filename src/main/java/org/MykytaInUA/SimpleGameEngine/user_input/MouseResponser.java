package org.MykytaInUA.SimpleGameEngine.user_input;

import java.awt.Point;

public interface MouseResponser {
	public void applyMouseMovement(Point mousePosition);
	public void applyMouseWheelMovement(float direction);
}

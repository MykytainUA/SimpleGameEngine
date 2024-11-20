package org.MykytaInUA.SimpleGameEngine.user_input;

import java.awt.Point;

public interface UserInputListener {
	/*
	 * Calls every frame, should implement 
	 */
	public void updateActionsOnObjects(); 
	public void add(UserInputResponser userInputControllable);
	public UserInputResponser get(int index);
	
	public Point getMouseCursorDisplayRelatedPosition();
	public Point getMouseCursorWindowRelatedPosition();
}

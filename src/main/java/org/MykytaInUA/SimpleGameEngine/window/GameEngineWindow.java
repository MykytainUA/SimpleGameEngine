package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.Dimension;
import java.awt.Point;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;

public interface GameEngineWindow {
	
	public void showWindow();
	public boolean isVisible();
	public void resizeWindow();
	
	public Camera getCamera();
	public UserInputListener getUserInputListener();
	public Point getWindowPosition();
	public Dimension getWindowDimention();
	public Point getDisplayRelatedWindowCenter();
	public Point getWindowRelatedWindowCenter();
	
	public void lockMouseCursor(boolean isCursorLocked);
	public void setMouseCursorVisible(boolean isCursorVisible);
	public boolean isMouseCursorVisible();
	public boolean isMouseLocked();
	
	public void startRendering();
}

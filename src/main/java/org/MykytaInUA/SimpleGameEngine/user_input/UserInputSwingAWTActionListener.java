package org.MykytaInUA.SimpleGameEngine.user_input;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.event.MouseInputListener;

public class UserInputSwingAWTActionListener implements UserInputListener, KeyListener, MouseInputListener, MouseWheelListener{
	
	private Set<Integer> pressedKeys = new HashSet<Integer>();
	private List<UserInputResponser> userInputControllableList = new ArrayList<UserInputResponser>();
	private Point previousMousePosition = new Point(450, 300);
	private Point currentMousePosition = new Point(450, 300);
	private Point mousePositionDifference = new Point(0, 0);
	private float mouseWheelMovement = 0.0f;

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("Key pressed:" + e.getKeyCode());
		this.pressedKeys.add(KeyCodeMapper.getMappedKeyFromSwing(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("Key released:" + e.getKeyCode());
		this.pressedKeys.remove(KeyCodeMapper.getMappedKeyFromSwing(e.getKeyCode()));
		
	}
	
	@Override
	public void add(UserInputResponser userInputControllable) {
		userInputControllableList.add(userInputControllable);
	}
	
	public UserInputResponser get(int index) {
		return userInputControllableList.get(index);
	}
	
	public void updateActionsOnObjects() {
		for(UserInputResponser userInputResponser: userInputControllableList) {
			if(KeyResponser.class.isInstance(userInputResponser)) {
				KeyResponser responser = (KeyResponser)userInputResponser;
				responser.applyPressedKeys(pressedKeys);
			}
			
			if(MouseResponser.class.isInstance(userInputResponser)) {
				MouseResponser responser = (MouseResponser)userInputResponser;
				
				mousePositionDifference.x = previousMousePosition.x - currentMousePosition.x;
				mousePositionDifference.y = previousMousePosition.y - currentMousePosition.y;
				
				responser.applyMouseWheelMovement(this.mouseWheelMovement);
				responser.applyMouseMovement(this.mousePositionDifference);
			}
		}
		
		// Mouse wheel must be reset after each appliance
		this.mouseWheelMovement = 0;
		this.previousMousePosition.setLocation(this.currentMousePosition);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {}

	@Override
	public void mouseMoved(MouseEvent e) {
		this.currentMousePosition.setLocation(e.getX(), e.getY());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		this.mouseWheelMovement = -e.getWheelRotation();
	}

	@Override
	public void moveMouseCursorTo(Point position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point getMouseCursorDisplayRelatedPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getMouseCursorWindowRelatedPosition() {
		// TODO Auto-generated method stub
		return null;
	}

}

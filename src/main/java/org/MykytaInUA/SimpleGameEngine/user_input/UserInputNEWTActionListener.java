package org.MykytaInUA.SimpleGameEngine.user_input;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.MykytaInUA.SimpleGameEngine.window.GameEngineWindow;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public class UserInputNEWTActionListener implements UserInputListener, KeyListener, MouseListener {
	
	private Set<Integer> pressedKeys = new HashSet<Integer>();
	private List<UserInputResponser> userInputControllableList = new ArrayList<UserInputResponser>();
	private Point mousePositionInWindow = new Point(450, 300);
	private Point mouseShift = new Point(0, 0);
	private float mouseWheelMovement = 0.0f;
	private Robot robot;
	
	public UserInputNEWTActionListener() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void add(UserInputResponser userInputControllable) {
		this.userInputControllableList.add(userInputControllable);	
	}
	
	public UserInputResponser get(int index) {
		return this.userInputControllableList.get(index);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(!e.isAutoRepeat()) {
			System.out.println(e.getKeyCode());
			this.pressedKeys.add(KeyCodeMapper.getMappedKeyFromNEWT((int) e.getKeyCode()));
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(!e.isAutoRepeat()) {
			this.pressedKeys.remove(KeyCodeMapper.getMappedKeyFromNEWT(e.getKeyCode()));	
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	// Called every mouse move
	public void mouseMoved(MouseEvent e) {
		
		// Add to shift (shift is reseted after every frame)
		mouseShift.x += mousePositionInWindow.x - e.getX();
		mouseShift.y += mousePositionInWindow.y - e.getY();	
		
		// Restore previous mouse position
		mousePositionInWindow.x = e.getX();
		mousePositionInWindow.y = e.getY();

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseEvent e) {
		this.mouseWheelMovement = e.getRotation()[1];
	}
	
	// Called every frame
	public void updateActionsOnObjects() {

		for(UserInputResponser userInputResponser: userInputControllableList) {
			this.applyPressedKeysFor(userInputResponser);
			this.applyMouseMovementFor(userInputResponser);
		}
		
		// Mouse wheel must be reset after each appliance
		this.mouseWheelMovement = 0;
		this.mouseShift.x = 0;
		this.mouseShift.y = 0;
	}
	
	private void applyPressedKeysFor(UserInputResponser userInputResponser) {
		if(KeyResponser.class.isInstance(userInputResponser)) {
			KeyResponser responser = (KeyResponser)userInputResponser;
			responser.applyPressedKeys(this.pressedKeys);
		}
	}
	
	private void applyMouseMovementFor(UserInputResponser userInputResponser) {
		if(MouseResponser.class.isInstance(userInputResponser)) {
			
			MouseResponser responser = (MouseResponser)userInputResponser;
			
			responser.applyMouseWheelMovement(this.mouseWheelMovement);
			responser.applyMouseMovement(this.mouseShift);	
		}
	}

	@Override
	public void moveMouseCursorTo(Point position) {
		//this.robot.mouseMove(position.x, position.y);
	}

	@Override
	public Point getMouseCursorDisplayRelatedPosition() {
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        if (pointerInfo != null) {
            return pointerInfo.getLocation();
        }
        return null; // Mouse is not detected, possibly disconnected
	}

	@Override
	public Point getMouseCursorWindowRelatedPosition() {
		return this.mousePositionInWindow;
	}
}

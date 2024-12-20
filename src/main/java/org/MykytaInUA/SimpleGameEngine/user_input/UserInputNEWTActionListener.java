package org.MykytaInUA.SimpleGameEngine.user_input;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;

public class UserInputNEWTActionListener implements UserInputListener, KeyListener, MouseListener {
	
	private Set<Integer> pressedKeys = new HashSet<Integer>();
	private List<UserInputResponser> userInputControllableList = new ArrayList<UserInputResponser>();
	private RelativeMouseMovementListener relativeMouseMovementListener;
	private MouseMovementData mouseMovementData = new MouseMovementData();
	
	public UserInputNEWTActionListener() {	
		this.relativeMouseMovementListener = new RelativeMouseMovementListener();
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
			this.pressedKeys.add(KeyCodeMapper.getMappedKeyFromNEWT(e.getKeyCode()));
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
		
		Point mousePositionInWindow = this.mouseMovementData.getMousePositionInWindow();
		Point mouseShift = this.mouseMovementData.getMouseShift();
		
		// Add to shift (shift is reseted after every frame)
		this.mouseMovementData.setMouseXShift(mouseShift.x + mousePositionInWindow.x - e.getX());
		this.mouseMovementData.setMouseYShift(mouseShift.y + mousePositionInWindow.y - e.getY());	
		
		//System.out.println("Mouse movement data:" + this.mouseMovementData.getMouseShift());
		
		// Restore previous mouse position
		this.mouseMovementData.setMouseXPositionInWindow(e.getX());
		this.mouseMovementData.setMouseYPositionInWindow(e.getY());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseEvent e) {
		this.mouseMovementData.setMouseWheelMovement(e.getRotation()[1]);
	}
	
	// Called every frame
	public void updateActionsOnObjects() {
		
		this.relativeMouseMovementListener.fetchMouseData();
		
		this.mouseMovementData.setRelativeMouseXShift(-relativeMouseMovementListener.getXShift());
		this.mouseMovementData.setRelativeMouseYShift(-relativeMouseMovementListener.getYShift());
		
		for(UserInputResponser userInputResponser: userInputControllableList) {
			
			this.applyPressedKeysFor(userInputResponser);
			this.applyMouseMovementFor(userInputResponser);
		}
		
		// Mouse wheel must be reset after each appliance
		this.mouseMovementData.setMouseWheelMovement(0);
		
		this.mouseMovementData.setMouseXShift(0);
		this.mouseMovementData.setMouseYShift(0);
		
		this.mouseMovementData.setRelativeMouseXShift(0);
		this.mouseMovementData.setRelativeMouseYShift(0);
	}
	
	private void applyPressedKeysFor(UserInputResponser userInputResponser) {
		if(KeyResponser.class.isInstance(userInputResponser)) {
			KeyResponser responser = (KeyResponser)userInputResponser;
			responser.applyPressedKeys(this.pressedKeys);
		}
	}
	
	private void applyMouseMovementFor(UserInputResponser userInputResponser) {
		
		float mouseWheelMovement = this.mouseMovementData.getMouseWheelMovement();
		Point2D.Float relativeMouseShift = this.mouseMovementData.getRelativeMouseShift();
		
		if(MouseResponser.class.isInstance(userInputResponser)) {
			
			MouseResponser responser = (MouseResponser)userInputResponser;
			
			responser.applyMouseWheelMovement(mouseWheelMovement);
			responser.applyMouseMovement(relativeMouseShift);	
		}
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
		return this.mouseMovementData.getMousePositionInWindow();
	}
}

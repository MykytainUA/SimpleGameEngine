package org.MykytaInUA.SimpleGameEngine.user_input;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class RelativeMouseMovementListener {
	
	Controller[] controllers;
	Controller mouse;
	
	//Data
	private float xShift = 0;
	private float yShift = 0;
	
	public RelativeMouseMovementListener() {
		this.controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
        this.mouse = null;

        for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.MOUSE) {
                mouse = controller;
                System.out.println("Mouse Found: " + mouse.getName());
                break;
            }
        }
        
        if (mouse == null) {
            System.out.println("No mouse found!");
            return;
        }
	}
	
	/**
	 * A function that updates data received from mouse
	 * must be called to get current data
	 */
	public void fetchMouseData() {
		if (mouse.poll()) {
			Component[] components = mouse.getComponents();
            
            for (Component component : components) {
                if (component.getIdentifier() == Component.Identifier.Axis.X) {
                	this.setXShift(component.getPollData());
                } else if (component.getIdentifier() == Component.Identifier.Axis.Y){
                	this.setYShift(component.getPollData());
                }
            }
            
        } else {
            System.out.println("Mouse not responding!!!");
        }
	}
	
	public float getXShift() {
		return xShift;
	}

	private void setXShift(float xShift) {
		this.xShift = xShift;
	}

	public float getYShift() {
		return yShift;
	}

	private void setYShift(float yShift) {
		this.yShift = yShift;
	}

}

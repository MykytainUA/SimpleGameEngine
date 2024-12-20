package org.MykytaInUA.SimpleGameEngine.user_input;

import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;

public class RelativeMouseMovementListener {
	
	private Controller[] controllers;
	private Controller activeMouse = null;
	
	private float xShift = 0;
	private float yShift = 0;
	
	public RelativeMouseMovementListener() {
		this.controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
	}
	
	/**
	 * A function that updates data received from mouse
	 * must be called to get current data
	 */
	public void fetchMouseData() {

		if(this.activeMouse == null) {	
			this.activeMouse = this.tryToGetCurrentActiveMouse();
		}
		
		if (activeMouse != null && activeMouse.poll()) {
			
			Component[] components = activeMouse.getComponents();
			
			for (Component component : components) {
				if (component.getIdentifier() == Component.Identifier.Axis.X) {

					this.setXShift(component.getPollData());
					
				} else if (component.getIdentifier() == Component.Identifier.Axis.Y){
					
					this.setYShift(component.getPollData());
				}}}
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
	
	private Controller tryToGetCurrentActiveMouse() {
		
		Controller activeMouse = null;
		for (Controller controller : controllers) {
            if (controller.getType() == Controller.Type.MOUSE) {

                controller.poll();
                EventQueue queue = controller.getEventQueue();
                Event mouseEvent = new Event();
                
                // Check if there is an event in the queue
                while (queue.getNextEvent(mouseEvent)) {
                    activeMouse = controller;
                    // Stop searching after detecting activity
                    break;
                }
                if (activeMouse != null) break;
            }
        }
		
		return activeMouse;
	}

}

package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.SwingUtilities;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;

public class GLPanelWrapper extends GLJPanel implements GameEngineWindow{

private CommonWindowComponents windowComponents;
	
	public GLPanelWrapper(GLCapabilities capabilities) {
		super(capabilities);
		this.windowComponents = new CommonWindowComponents(this);
	}
	
	@Override
	public void showWindow() {
		this.setVisible(true);
	}

	@Override
	public Camera getCamera() {
		return this.windowComponents.getCamera();
	}

	@Override
	public UserInputListener getUserInputListener() {
		return this.windowComponents.getUserInputListener();
	}

	@Override
	public void startRendering() {
		SwingUtilities.invokeLater(() -> {
			this.windowComponents.getAnimator();
		});	
	}

	@Override
	public void captureMouseCursor(boolean captureCursor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point getWindowPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension getWindowDimention() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getDisplayRelatedWindowCenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMouseCursorVisible(boolean captureCursor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseCursorVisible() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMouseLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point getWindowRelatedWindowCenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resizeWindow(Dimension dimention) {
		// TODO Auto-generated method stub
		
	}

}
package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.SwingUtilities;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;

public class GLPanelWrapper extends GLJPanel implements GameEngineWindow {

	private static final long serialVersionUID = 1L;
	private CommonWindowComponents windowComponents;
	private final MainGLSwingPanelWindow frame;
	
	public GLPanelWrapper(GLCapabilities capabilities, MainGLSwingPanelWindow frame) {
		super(capabilities);
		
		this.frame = frame;
		
		this.windowComponents = new CommonWindowComponents(this);
		this.setAutoSwapBufferMode(true);
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
	public void lockMouseCursor(boolean captureCursor) {
		this.windowComponents.setCursorLockedFlag(captureCursor);
		
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
	public void setMouseCursorVisible(boolean isCursorVisible) {
		this.windowComponents.setCursorVisibleFlag(isCursorVisible);
	}

	@Override
	public boolean isMouseCursorVisible() {
		return this.windowComponents.isCursorVisible();
	}

	@Override
	public boolean isMouseLocked() {
		return this.windowComponents.isMouseLocked();
	}

	@Override
	public Point getWindowRelatedWindowCenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resizeWindow() {
		
		this.frame.resizeWindow();
	}
	
	public Dimension getWidowDimentionInPixels() {
		
		return this.windowComponents.getWidowDimentionInPixels();
	}

	public void setWidowWidthInPixels(int width) {
		
		this.windowComponents.setWidowWidthInPixels(width);
	}
	
	public void setWidowHeightInPixels(int height) {
		
		this.windowComponents.setWidowHeightInPixels(height);
	}

}

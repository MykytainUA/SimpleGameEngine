package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.Animator;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.rendering.PerspectiveParameters;
import org.MykytaInUA.SimpleGameEngine.rendering.Renderer;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputSwingAWTActionListener;
import org.joml.Vector3f;

public class MainGLSwingPanelWindow extends JFrame implements GameEngineWindow{
	
	private static final long serialVersionUID = 1L;
	
	private GLPanelWrapper panel;
	
	public MainGLSwingPanelWindow(GLCapabilities capabilities, int width, int height) {
		this.panel = new GLPanelWrapper(capabilities);
		panel.setAutoSwapBufferMode(true);
		
		this.getContentPane().add(panel);
		
		this.setupWindowProperties(width, height);
	}
	
	public MainGLSwingPanelWindow(GLCapabilities capabilities, boolean isFullScreen) {	
		this(capabilities, 900, 600);		
		if(isFullScreen) {		
			this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			this.setUndecorated(true);
		}
	}
	
	private void setupWindowProperties(int width, int height) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.panel.requestFocusInWindow();
	}

	@Override
	public Camera getCamera() {
		return this.panel.getCamera();
	}
	
	@Override
	public void startRendering() {
		SwingUtilities.invokeLater(() -> {
			this.panel.getAnimator().start();
		});
	}

	@Override
	public UserInputListener getUserInputListener() {
		return this.panel.getUserInputListener();
	}
	
	@Override
	public void showWindow() {
		this.setVisible(true);
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

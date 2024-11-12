package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.Animator;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.rendering.PerspectiveParameters;
import org.MykytaInUA.SimpleGameEngine.rendering.Renderer;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputSwingAWTActionListener;
import org.joml.Vector3f;

public class MainGLAWTCanvasWindow extends JFrame implements GameEngineWindow{
	
	private static final long serialVersionUID = 1L;
	
	private GLCanvasWrapper canvas;
	
	public MainGLAWTCanvasWindow(GLCapabilities capabilities, int width, int height) {
		
		this.canvas = new GLCanvasWrapper(capabilities);
		this.canvas.setAutoSwapBufferMode(true);
		
		this.getContentPane().add(canvas);
		
		this.setupWindowProperties(width, height);
	}
	
	public MainGLAWTCanvasWindow(GLCapabilities capabilities, boolean isFullScreen) {	
		this(capabilities, 0, 0);	
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.setSize(screenSize);
		this.setUndecorated(true);
    }
	
	private void setupWindowProperties(int width, int height) {
		this.setSize(width, height);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.canvas.requestFocusInWindow();
	}

	@Override
	public Camera getCamera() {
		return canvas.getCamera();
	}

	@Override
	public void startRendering() {
		SwingUtilities.invokeLater(() -> {
			canvas.getAnimator().start();
		});	
	}

	@Override
	public UserInputListener getUserInputListener() {
		return this.canvas.getUserInputListener();
	}
	
	@Override
	public void showWindow() {
		this.setVisible(true);
		this.canvas.requestFocusInWindow();	
	}

	@Override
	public void captureMouseCursor(boolean captureCursor) {
		if(captureCursor) {
			this.setMouseCursorVisible(false);
			
		} else {
			this.setMouseCursorVisible(true);
		}
		
	}

	@Override
	public Point getWindowPosition() {
		return new Point(this.getX(), this.getY());
	}

	@Override
	public Dimension getWindowDimention() {
		return new Dimension(this.getWidth(), this.getHeight());
	}

	@Override
	public Point getDisplayRelatedWindowCenter() {
		return new Point((this.getWidth()/2) + this.getX(),
				 		(this.getHeight()/2) + this.getY());
	}

	@Override
	public void setMouseCursorVisible(boolean isVisible) {
		
		if(isVisible) {
			this.setCursor(Cursor.getDefaultCursor());
		} else {
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
			
			this.setCursor(blankCursor);
		}
	}

	@Override
	public boolean isMouseCursorVisible() {
		return this.canvas.isMouseCursorVisible();
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

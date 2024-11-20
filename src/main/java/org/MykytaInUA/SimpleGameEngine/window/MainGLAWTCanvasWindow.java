package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GLCapabilities;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.user_input.KeyCodeMapper;
import org.MykytaInUA.SimpleGameEngine.user_input.KeyResponser;
import org.MykytaInUA.SimpleGameEngine.user_input.MouseResponser;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputResponser;

public class MainGLAWTCanvasWindow extends JFrame implements GameEngineWindow, 
															 UserInputResponser, 
															 MouseResponser, 
															 KeyResponser {
	
	private static final long serialVersionUID = 1L;
	
	private Robot robot;
	private GLCanvasWrapper canvas;
	
	public MainGLAWTCanvasWindow(GLCapabilities capabilities) {
		
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		this.canvas = new GLCanvasWrapper(capabilities, this);
		
		this.getContentPane().add(canvas);

	}
	
	public MainGLAWTCanvasWindow(GLCapabilities capabilities, int width, int height) {
		
		this(capabilities);
		
		this.canvas.setWidowWidthInPixels(width);
		this.canvas.setWidowHeightInPixels(height);
		
		this.setupWindowProperties();
	}
	
	public MainGLAWTCanvasWindow(GLCapabilities capabilities, boolean isFullScreen) {	
		this(capabilities);	
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        this.canvas.setWidowWidthInPixels(screenSize.width);
		this.canvas.setWidowHeightInPixels(screenSize.height);
        
        this.setupWindowProperties();
        
		this.setUndecorated(true);
    }
	
	private void setupWindowProperties() {
		
		this.setSize(100, 100); // default size to call init in renderer

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.canvas.requestFocusInWindow();
		
		this.canvas.getUserInputListener().add(this);
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
	public void lockMouseCursor(boolean lockCursor) {
		
		this.canvas.lockMouseCursor(lockCursor);
		this.setMouseCursorVisible(!lockCursor);		
	}

	@Override
	public Point getWindowPosition() {
		
		return new Point(this.getX(), this.getY());
	}

	@Override
	public Dimension getWindowDimention() {
		
		return this.canvas.getWidowDimentionInPixels();
	}

	@Override
	public Point getDisplayRelatedWindowCenter() {
		
		return new Point((this.getWidth()/2) + this.getX(),
				 		(this.getHeight()/2) + this.getY());
	}

	@Override
	public void setMouseCursorVisible(boolean isMouseCursorVisible) {
		this.canvas.setMouseCursorVisible(isMouseCursorVisible);
		
		if(isMouseCursorVisible) {
			
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
		
		return this.canvas.isMouseLocked();
	}

	@Override
	public Point getWindowRelatedWindowCenter() {
		
		return new Point(this.getWidth()/2 , this.getHeight()/2);
	}

	@Override
	public void resizeWindow() {
		
		this.setSize(this.canvas.getWidowDimentionInPixels());
	}

	@Override
	public void applyPressedKeys(Set<Integer> pressedKeys) {
		
		for (int key : pressedKeys) {
			
			switch(key) {
			case KeyCodeMapper.VK_ESCAPE:
				
				this.canvas.getAnimator().stop();
				this.dispose();
			}
		}	
	}

	@Override
	public void applyMouseMovement(Point2D mousePosition) {
		
		Point windowCenter = this.getDisplayRelatedWindowCenter();
		
		if(this.isMouseLocked()) {
			this.robot.mouseMove(windowCenter.x, windowCenter.y);
		}	
	}

	@Override
	public void applyMouseWheelMovement(float direction) {
		// TODO Auto-generated method stub
		
	}
}

package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Set;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.user_input.KeyCodeMapper;
import org.MykytaInUA.SimpleGameEngine.user_input.KeyResponser;
import org.MykytaInUA.SimpleGameEngine.user_input.MouseResponser;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputResponser;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;

// To resize window just change widowSizeInPixels parameter
public class MainGLNEWTWindow extends GLWindow implements GameEngineWindow, 
                                                          UserInputResponser, 
                                                          MouseResponser, 
                                                          KeyResponser {
	
	private CommonWindowComponents windowComponents;

	private MainGLNEWTWindow(GLCapabilities capabilities) {
		super(GLWindow.create(capabilities).getDelegatedWindow());
		
		this.windowComponents = new CommonWindowComponents(this);
		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyed(WindowEvent e) {
            	windowComponents.getAnimator().stop();
        }});
			
		this.setupWindowProperties();	
	}
	
	public MainGLNEWTWindow(GLCapabilities capabilities, int width, int height) {
		
		this(capabilities);	
		
		this.setPosition(32, 32);
		
		this.setSize(100, 100);
		
		this.windowComponents.setWidowWidthInPixels(width);
		this.windowComponents.setWidowHeightInPixels(height);
	}
	
	public MainGLNEWTWindow(GLCapabilities capabilities, boolean isFullScreen) {
		
		this(capabilities);	 
		
		this.setSize(100, 100);
		
		this.setFullscreen(isFullScreen);
	}
	
	private void setupWindowProperties() {
		
		this.setDefaultCloseOperation(WindowClosingMode.DISPOSE_ON_CLOSE);
		this.setAutoSwapBufferMode(true);
		this.requestFocus();
		
		this.windowComponents.getUserInputListener().add(this);
	}

	@Override
	public Camera getCamera() {
		return this.windowComponents.getCamera();
	}

	@Override
	public void startRendering() {
		this.windowComponents.getAnimator().start();
	}

	@Override
	public UserInputListener getUserInputListener() {
		return this.windowComponents.getUserInputListener();
	}

	@Override
	public void showWindow() {
		this.setVisible(true);
	}
	
	@Override
	public void resizeWindow() {
		
		Dimension windowDimension = this.windowComponents.getWidowDimentionInPixels();
		
		float[] surfaceScale = new float[2];
		this.getCurrentSurfaceScale(surfaceScale);
		
		int windowWidthInScale = (int) (windowDimension.width / surfaceScale[0]);
		int windowHeightInScale = (int) (windowDimension.height / surfaceScale[1]);
		
		this.setSize(windowWidthInScale, windowHeightInScale);
	}

	@Override
	public void lockMouseCursor(boolean lockCursor) {
		this.windowComponents.setCursorLockedFlag(lockCursor);
		this.setMouseCursorVisible(!lockCursor);
		this.confinePointer(lockCursor);
	}

	@Override
	public Point getWindowPosition() {
		
		float[] surfaceScale = new float[2];
		this.getCurrentSurfaceScale(surfaceScale);
		
		return new Point((int) (this.getX() * surfaceScale[0]), (int) (this.getY() * surfaceScale[1]));
	}

	@Override
	public Dimension getWindowDimention() {
		return this.windowComponents.getWidowDimentionInPixels();
	}

	@Override
	public Point getDisplayRelatedWindowCenter() {
		
		float[] surfaceScale = new float[2];
		this.getCurrentSurfaceScale(surfaceScale);
		
		return new Point((int) (((this.getWidth()/2) + this.getX()) * surfaceScale[0]),
						 (int) ((this.getHeight()/2) + this.getY() * surfaceScale[1]));
	}
	
	@Override
	public Point getWindowRelatedWindowCenter() {
		
		float[] surfaceScale = new float[2];
		this.getCurrentSurfaceScale(surfaceScale);
		
		return new Point((int) ((this.getWidth()/2) * surfaceScale[0]),
				 		(int) ((this.getHeight()/2)* surfaceScale[1]));
	}

	@Override
	public void setMouseCursorVisible(boolean isMouseCursorVisible) {
		this.windowComponents.setCursorVisibleFlag(isMouseCursorVisible);
		this.setPointerVisible(isMouseCursorVisible);
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
	public void applyMouseMovement(Point2D mousePosition) {
		
	}

	@Override
	public void applyMouseWheelMovement(float direction) {
		
	}

	@Override
	public void applyPressedKeys(Set<Integer> pressedKeys) {
		
		for (int key : pressedKeys) {
			switch(key) {
			case KeyCodeMapper.VK_ESCAPE:
				this.destroy();
			}
		}
	}
}
package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Set;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.user_input.KeyCodeMapper;
import org.MykytaInUA.SimpleGameEngine.user_input.KeyResponser;
import org.MykytaInUA.SimpleGameEngine.user_input.MouseResponser;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputResponser;

import com.jogamp.newt.Display;
import com.jogamp.newt.Screen;
import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;

// To resize window just change widowSizeInPixels parameter
public class MainGLNEWTWindow extends GLWindow implements GameEngineWindow, UserInputResponser , MouseResponser, KeyResponser{
	
	private Dimension widowSizeInPixels = new Dimension();
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
		
		this.widowSizeInPixels.width = width;
		this.widowSizeInPixels.height = height;
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
		//this.resizeWindow(this.getWindowDimention());
	}
	
	@Override
	public void resizeWindow(Dimension dimention) {
		int windowWidthInScale = (int) (widowSizeInPixels.width / this.getCurrentSurfaceScale(new float[2])[0]);
		int windowHeightInScale = (int) (widowSizeInPixels.height / this.getCurrentSurfaceScale(new float[2])[1]);
		this.setSize(windowWidthInScale, windowHeightInScale);
	}

	@Override
	public void captureMouseCursor(boolean captureCursor) {
		this.setMouseCursorVisible(!captureCursor);
		this.confinePointer(captureCursor);
	}

	@Override
	public Point getWindowPosition() {
		return new Point(this.getX(), this.getY());
	}

	@Override
	public Dimension getWindowDimention() {
		return this.widowSizeInPixels;
	}

	@Override
	public Point getDisplayRelatedWindowCenter() {
		return new Point((this.getWidth()/2) + this.getX(),
						 (this.getHeight()/2) + this.getY());
	}
	
	@Override
	public Point getWindowRelatedWindowCenter() {
		return new Point((this.getWidth()/2),
				 		(this.getHeight()/2));
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
	public void applyMouseMovement(Point mousePosition) {
		this.windowComponents.getUserInputListener().moveMouseCursorTo(this.getDisplayRelatedWindowCenter());
		System.out.println("Cursor position:" + this.windowComponents.getUserInputListener().getMouseCursorDisplayRelatedPosition());
		System.out.println("Window left corner position:" + this.getWindowPosition());
		System.out.println("Window center position:" + this.getWindowRelatedWindowCenter());
		System.out.println("Window size:" + this.getWindowDimention());
		System.out.println("Surface scale:" + this.getCurrentSurfaceScale(new float[2])[0] + " " + this.getCurrentSurfaceScale(new float[2])[1]);
	}

	@Override
	public void applyMouseWheelMovement(float direction) {
		// TODO Auto-generated method stub
		
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
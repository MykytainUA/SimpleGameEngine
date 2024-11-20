package org.MykytaInUA.SimpleGameEngine.window;

import java.awt.Dimension;

import org.MykytaInUA.SimpleGameEngine.objects.Camera;
import org.MykytaInUA.SimpleGameEngine.rendering.PerspectiveParameters;
import org.MykytaInUA.SimpleGameEngine.rendering.Renderer;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputNEWTActionListener;
import org.MykytaInUA.SimpleGameEngine.user_input.UserInputSwingAWTActionListener;
import org.joml.Vector3f;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.Animator;

public class CommonWindowComponents {
	
	private GameEngineWindow window;
	private UserInputListener userInputListener;
	private Camera camera;
	private Renderer renderer;
	private Animator animator;
	private Dimension widowDimensionInPixels;
	private boolean isCursorVisible = true;
	private boolean isMouseLocked = false;
	
	public CommonWindowComponents(GameEngineWindow window) {
		this.window = window;
		
		this.widowDimensionInPixels = new Dimension();
		
		this.initializeCamera();
		this.initializeUserInputController();
		this.initalizeRenderer();
		this.initializeAnimator();
	}
	
	private void initializeCamera() {
		PerspectiveParameters cameraPerspectiveParameters;
		cameraPerspectiveParameters = new PerspectiveParameters(900, // width
																600, // height
																(float)Math.toRadians(60.0f), // FOV
																0.1f, // zNear
																10000.0f); // zFar
		
		Vector3f cameraPosition = new Vector3f(0.0f, 0.0f, 3.0f);
		Vector3f targetPosition = new Vector3f(0.0f, 0.0f, 0.0f);
		Vector3f upDirection = new Vector3f(0.0f, 1.0f, 0.0f);
		
		this.camera = new Camera(cameraPosition,
							targetPosition,
							upDirection,
				            cameraPerspectiveParameters);
	}
	
	private void initializeUserInputController() {
		if(window instanceof MainGLNEWTWindow) {
			this.userInputListener = new UserInputNEWTActionListener();
			this.userInputListener.add(this.getCamera());
			
			MainGLNEWTWindow window = (MainGLNEWTWindow) this.window;
			
			window.addKeyListener((UserInputNEWTActionListener)userInputListener);
			window.addMouseListener((UserInputNEWTActionListener)userInputListener);		
			
		} else if (window instanceof GLCanvasWrapper) {
			this.userInputListener = new UserInputSwingAWTActionListener();
			this.userInputListener.add(this.getCamera());
			
			GLCanvasWrapper window = (GLCanvasWrapper) this.window;

			window.addKeyListener((UserInputSwingAWTActionListener) userInputListener);
			window.addMouseListener((UserInputSwingAWTActionListener) userInputListener);
			window.addMouseMotionListener((UserInputSwingAWTActionListener) userInputListener);
			window.addMouseWheelListener((UserInputSwingAWTActionListener) userInputListener);
			
		} else if (window instanceof GLPanelWrapper) {
			this.userInputListener = new UserInputSwingAWTActionListener();
			this.userInputListener.add(this.getCamera());
			
			GLPanelWrapper window = (GLPanelWrapper) this.window;
			
			window.addKeyListener((UserInputSwingAWTActionListener) userInputListener);
			window.addMouseListener((UserInputSwingAWTActionListener) userInputListener);
			window.addMouseMotionListener((UserInputSwingAWTActionListener) userInputListener);
			window.addMouseWheelListener((UserInputSwingAWTActionListener) userInputListener);
		}
	}
	
	private void initalizeRenderer() {
		this.renderer = new Renderer(window);
		GLAutoDrawable drawable = (GLAutoDrawable) window;
		drawable.addGLEventListener(renderer);
	}
	
	private void initializeAnimator() {
		this.animator = new Animator((GLAutoDrawable) window);
		this.animator.setUpdateFPSFrames(1000, System.out);
		GLAutoDrawable drawable = (GLAutoDrawable) window;
		drawable.setAnimator(animator);
	}
	
	public Camera getCamera() {
		return this.camera;
	}
	
	public UserInputListener getUserInputListener() {
		return this.userInputListener;
	}
	
	public Animator getAnimator() {
		return this.animator;
	}
	
	public void setCursorVisibleFlag(boolean isCursorVisible) {
		this.isCursorVisible = isCursorVisible;
	}
	
	public void setCursorLockedFlag(boolean isMouseLocked) {
		this.isMouseLocked = isMouseLocked;
	}
	
	public boolean isCursorVisible() {
		return this.isCursorVisible;
	}
	
	public boolean isMouseLocked() {
		return this.isMouseLocked;
	}

	public Dimension getWidowDimentionInPixels() {
		return widowDimensionInPixels;
	}

	public void setWidowWidthInPixels(int width) {
		this.widowDimensionInPixels.width = width;
	}
	
	public void setWidowHeightInPixels(int height) {
		this.widowDimensionInPixels.height = height;
	}
	
}

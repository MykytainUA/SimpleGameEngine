package org.MykytaInUA.SimpleGameEngine.window;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

// Creates Windows of different types
public class GameEngineWindowFactory {
	
	private static GLProfile profile;
	private static GLCapabilities capabilities;

	
	private static GLProfile getProfile() {
		return profile;
	}

	private static void setProfile(GLProfile profile) {
		GameEngineWindowFactory.profile = profile;
	}

	private static GLCapabilities getCapabilities() {
		return capabilities;
	}

	private static void setCapabilities(GLCapabilities capabilities) {
		GameEngineWindowFactory.capabilities = capabilities;
	}	
	
	
	// Creation of NEWT window

	public static MainGLNEWTWindow getGLNEWTWindow(boolean isFullScreen) {	
		verifyGLCapabilities();
		return new MainGLNEWTWindow(capabilities, isFullScreen);
	}
	
	
	public static MainGLNEWTWindow getGLNEWTWindow(int width, int height) {	
		verifyGLCapabilities();
		return new MainGLNEWTWindow(capabilities, width, height);
	}
		
	// Creation of GLCanvas window
	
	public static MainGLAWTCanvasWindow getGLAWTCanvasWindow(boolean isFullScreen) {
		verifyGLCapabilities();
		return new MainGLAWTCanvasWindow(capabilities, true);
	}
	
	public static MainGLAWTCanvasWindow getGLAWTCanvasWindow(int width, int height) {	
		verifyGLCapabilities();
		return new MainGLAWTCanvasWindow(capabilities, width, height);
	}
	
	// Creation of GLPanel window
	
	public static MainGLSwingPanelWindow getGLSwingPanelWindow(boolean isFullScreen) {
		verifyGLCapabilities();
		return new MainGLSwingPanelWindow(capabilities, isFullScreen);
	}
	
	public static MainGLSwingPanelWindow getGLSwingPanelWindow(int width, int height) {	
		verifyGLCapabilities();
		return new MainGLSwingPanelWindow(capabilities, width, height);
	}
	
	private static void verifyGLCapabilities() {
		if(!isGLCapabilityInitialized()) {
			initializeGLCapabilities();
		}
	}
	
	private static boolean isGLCapabilityInitialized() {
		return capabilities != null ? true : false;
	}
	
	private static void initializeGLCapabilities() {
		setProfile(GLProfile.getDefault());
		setCapabilities(new GLCapabilities(profile));
		getCapabilities().setDoubleBuffered(true);	
		getCapabilities().setDepthBits(32);
	}
	
	
	
}

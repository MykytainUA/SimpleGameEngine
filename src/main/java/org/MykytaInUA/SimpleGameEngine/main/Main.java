package org.MykytaInUA.SimpleGameEngine.main;

import org.MykytaInUA.SimpleGameEngine.window.GameEngineWindow;
import org.MykytaInUA.SimpleGameEngine.window.GameEngineWindowFactory;

import com.jogamp.opengl.GLContext;

public class Main {

	public static void main(String[] args) {
		
		GameEngineWindow window = GameEngineWindowFactory.getGLNEWTWindow(900, 600);
		
		window.showWindow();
		
		window.startRendering();
		
		window.captureMouseCursor(true);
		window.setMouseCursorVisible(true);
	}

}

package org.mykytainua.simplegameengine.main;

import org.mykytainua.simplegameengine.window.GameEngineWindow;
import org.mykytainua.simplegameengine.window.GameEngineWindowFactory;

/**
 * The {@code Main} class serves as the entry point for the Simple Game Engine
 * application.
 *
 *
 * <p>This program demonstrates a basic structure for initializing and running a
 * game engine.
 */
public class Main {

    /**
     * The main method is the entry point of the Java application.
     *
     * 
     * <p>It initializes a game engine window, makes it visible, starts the rendering
     * process, and locks the mouse cursor.
     *
     * @param args command-line arguments for the program (not used in this
     *             implementation)
     */
    public static void main(String[] args) {
        GameEngineWindow window = GameEngineWindowFactory.getNEWTWindow(500,500);

        window.showWindow();

        window.startRendering();

        window.lockMouseCursor(true);
    }
}



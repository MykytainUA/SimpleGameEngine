package org.mykytainua.simplegameengine.window;

import com.jogamp.opengl.GLCapabilities;
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
import org.mykytainua.simplegameengine.objects.Camera;
import org.mykytainua.simplegameengine.userinput.KeyCodeMapper;
import org.mykytainua.simplegameengine.userinput.KeyResponser;
import org.mykytainua.simplegameengine.userinput.MouseResponser;
import org.mykytainua.simplegameengine.userinput.UserInputListener;
import org.mykytainua.simplegameengine.userinput.UserInputResponser;

/**
 * This class represents the main window for the game engine using AWT and
 * GLCanvas. It provides various window and user input management
 * functionalities, including handling mouse and keyboard inputs, window
 * resizing, and fullscreen toggling.
 */
public class MainAWTCanvasWindow extends JFrame
        implements GameEngineWindow, UserInputResponser, MouseResponser, KeyResponser {

    private static final long serialVersionUID = 1L;

    private Robot robot;
    private GLCanvasWrapper canvas;

    /**
     * Constructor that initializes the window with OpenGL capabilities.
     *
     * @param capabilities The OpenGL capabilities for the canvas.
     */
    public MainAWTCanvasWindow(GLCapabilities capabilities) {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        this.canvas = new GLCanvasWrapper(capabilities, this);

        // Add the canvas to the window
        this.getContentPane().add(canvas);
    }

    /**
     * Constructor that initializes the window with specified width and height.
     *
     * @param capabilities The OpenGL capabilities for the canvas.
     * @param width        The width of the window.
     * @param height       The height of the window.
     */
    public MainAWTCanvasWindow(GLCapabilities capabilities, int width, int height) {
        this(capabilities);

        this.canvas.setWidowWidthInPixels(width);
        this.canvas.setWidowHeightInPixels(height);

        // Set up window properties like size and listener.
        this.setupWindowProperties();
    }

    /**
     * Constructor that initializes the window in fullscreen mode.
     *
     * @param capabilities The OpenGL capabilities for the canvas.
     * @param isFullScreen Flag to set fullscreen mode.
     */
    public MainAWTCanvasWindow(GLCapabilities capabilities, boolean isFullScreen) {
        this(capabilities);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        this.canvas.setWidowWidthInPixels(screenSize.width);
        this.canvas.setWidowHeightInPixels(screenSize.height);

        // Set up window properties like size and listener.
        this.setupWindowProperties();

        // Remove the window decoration for fullscreen mode
        this.setUndecorated(true);
    }

    /**
     * Configures default window properties such as size, close operation, and
     * focus.
     */
    private void setupWindowProperties() {
        this.setSize(100, 100); // Default size to initialize renderer
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.canvas.requestFocusInWindow();

        // Add user input listener
        this.canvas.getUserInputListener().add(this);
    }

    /**
     * Retrieves the camera associated with the canvas.
     *
     * @return The camera object.
     */
    @Override
    public Camera getCamera() {
        return canvas.getCamera();
    }

    /**
     * Starts the rendering process by invoking the animator in the Swing event
     * thread.
     */
    @Override
    public void startRendering() {
        SwingUtilities.invokeLater(() -> {
            canvas.getAnimator().start();
        });
    }

    /**
     * Retrieves the user input listener associated with the canvas.
     *
     * @return The user input listener.
     */
    @Override
    public UserInputListener getUserInputListener() {
        return this.canvas.getUserInputListener();
    }

    /**
     * Makes the window visible and requests focus for the canvas.
     */
    @Override
    public void showWindow() {
        this.setVisible(true);
        this.canvas.requestFocusInWindow();
    }

    /**
     * Locks or unlocks the mouse cursor based on the provided flag.
     *
     * @param lockCursor True to lock the cursor, false to unlock.
     */
    @Override
    public void lockMouseCursor(boolean lockCursor) {
        this.canvas.lockMouseCursor(lockCursor);
        this.setMouseCursorVisible(!lockCursor);
    }

    /**
     * Retrieves the current window position relative to the screen.
     *
     * @return The window position as a {@link Point}.
     */
    @Override
    public Point getWindowPosition() {
        return new Point(this.getX(), this.getY());
    }

    /**
     * Retrieves the window's dimensions.
     *
     * @return The window dimensions as a {@link Dimension}.
     */
    @Override
    public Dimension getWindowDimention() {
        return this.canvas.getWidowDimentionInPixels();
    }

    /**
     * Calculates and returns the window's center relative to the display.
     *
     * @return The window center as a {@link Point}.
     */
    @Override
    public Point getDisplayRelatedWindowCenter() {
        return new Point((this.getWidth() / 2) + this.getX(), (this.getHeight() / 2) + this.getY());
    }

    /**
     * Sets the visibility of the mouse cursor.
     *
     * @param isMouseCursorVisible True to make the cursor visible, false to hide
     *                             it.
     */
    @Override
    public void setMouseCursorVisible(boolean isMouseCursorVisible) {
        this.canvas.setMouseCursorVisible(isMouseCursorVisible);

        // Set the system cursor or a custom blank cursor depending on visibility
        if (isMouseCursorVisible) {
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Cursor blankCursor = toolkit.createCustomCursor(cursorImg, new Point(0, 0),
                    "blank cursor");
            this.setCursor(blankCursor);
        }
    }

    /**
     * Retrieves the visibility state of the mouse cursor.
     *
     * @return True if the cursor is visible, false otherwise.
     */
    @Override
    public boolean isMouseCursorVisible() {
        return this.canvas.isMouseCursorVisible();
    }

    /**
     * Checks if the mouse cursor is locked.
     *
     * @return True if the mouse is locked, false otherwise.
     */
    @Override
    public boolean isMouseLocked() {
        return this.canvas.isMouseLocked();
    }

    /**
     * Calculates the center of the window in relation to its own size.
     *
     * @return The center of the window as a {@link Point}.
     */
    @Override
    public Point getWindowRelatedWindowCenter() {
        return new Point(this.getWidth() / 2, this.getHeight() / 2);
    }

    /**
     * Resizes the window to match the canvas dimensions.
     */
    @Override
    public void resizeWindow() {
        this.setSize(this.canvas.getWidowDimentionInPixels());
    }

    /**
     * Handles key press events. Currently handles the ESC key to stop the animator
     * and close the window.
     *
     * @param pressedKeys The set of currently pressed keys.
     */
    @Override
    public void applyPressedKeys(Set<Integer> pressedKeys) {
        for (int key : pressedKeys) {
            switch (key) {
                case KeyCodeMapper.VK_ESCAPE:
                    this.canvas.getAnimator().stop();
                    this.dispose();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Handles mouse movement events, moving the cursor back to the center when
     * locked.
     *
     * @param mousePosition The current mouse position.
     */
    @Override
    public void applyMouseMovement(Point2D mousePosition) {
        Point windowCenter = this.getDisplayRelatedWindowCenter();
        if (this.isMouseLocked()) {
            this.robot.mouseMove(windowCenter.x, windowCenter.y);
        }
    }

    /**
     * Handles mouse wheel movement. Currently unimplemented.
     *
     * @param direction The direction of the mouse wheel movement.
     */
    @Override
    public void applyMouseWheelMovement(float direction) {
        // TODO Auto-generated method stub
    }
}

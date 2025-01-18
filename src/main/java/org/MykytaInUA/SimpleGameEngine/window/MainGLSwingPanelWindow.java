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
 * This class represents a Swing-based window for rendering OpenGL content. It
 * extends JFrame and implements various interfaces for handling user input and
 * managing window behavior.
 */
public class MainGLSwingPanelWindow extends JFrame
                                    implements GameEngineWindow, 
                                               UserInputResponser, 
                                               MouseResponser, 
                                               KeyResponser {

    // Serialization ID for the class
    private static final long serialVersionUID = 1L;

    // GLPanelWrapper for rendering OpenGL content
    private GLPanelWrapper panel;

    // Robot object used for controlling mouse cursor
    private Robot robot;

    /**
     * Constructs a new MainGLSwingPanelWindow with the specified OpenGL
     * capabilities.
     *
     * @param capabilities OpenGL capabilities for the panel.
     */
    public MainGLSwingPanelWindow(GLCapabilities capabilities) {
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        this.panel = new GLPanelWrapper(capabilities, this);
        this.getContentPane().add(panel);
    }

    /**
     * Constructs a new MainGLSwingPanelWindow with the specified OpenGL
     * capabilities, width, and height.
     *
     * @param capabilities OpenGL capabilities for the panel.
     * @param width        The width of the window in pixels.
     * @param height       The height of the window in pixels.
     */
    public MainGLSwingPanelWindow(GLCapabilities capabilities, int width, int height) {
        this(capabilities);
        this.panel.setWindowWidthInPixels(width);
        this.panel.setWindowHeightInPixels(height);
        this.setupWindowProperties();
    }

    /**
     * Constructs a new MainGLSwingPanelWindow with the specified OpenGL
     * capabilities and fullscreen flag.
     *
     * @param capabilities OpenGL capabilities for the panel.
     * @param isFullScreen If true, the window will be fullscreen.
     */
    public MainGLSwingPanelWindow(GLCapabilities capabilities, boolean isFullScreen) {
        this(capabilities);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.panel.setWindowWidthInPixels(screenSize.width);
        this.panel.setWindowHeightInPixels(screenSize.height);

        this.setupWindowProperties();

        this.setUndecorated(true); // Removes the window decorations for fullscreen
    }

    /**
     * Sets up the basic properties for the window, including size and close
     * behavior.
     */
    private void setupWindowProperties() {
        this.setSize(100, 100); // Default size to call init in renderer
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel.requestFocusInWindow();
        this.panel.getUserInputListener().add(this);
    }

    /**
     * Retrieves the camera object associated with the window.
     *
     * @return The camera object.
     */
    @Override
    public Camera getCamera() {
        return this.panel.getCamera();
    }

    /**
     * Starts the rendering process by starting the animator.
     */
    @Override
    public void startRendering() {
        SwingUtilities.invokeLater(() -> {
            this.panel.getAnimator().start();
        });
    }

    /**
     * Retrieves the user input listener associated with the window.
     *
     * @return The user input listener.
     */
    @Override
    public UserInputListener getUserInputListener() {
        return this.panel.getUserInputListener();
    }

    /**
     * Makes the window visible on the screen.
     */
    @Override
    public void showWindow() {
        this.setVisible(true);
    }

    /**
     * Locks or unlocks the mouse cursor to the center of the window.
     *
     * @param lockCursor If true, locks the mouse cursor; otherwise unlocks it.
     */
    @Override
    public void lockMouseCursor(boolean lockCursor) {
        this.panel.lockMouseCursor(lockCursor);
        this.setMouseCursorVisible(!lockCursor);
    }

    /**
     * Retrieves the position of the window on the screen.
     *
     * @return A Point representing the window's position.
     */
    @Override
    public Point getWindowPosition() {
        return new Point(this.getX(), this.getY());
    }

    /**
     * Retrieves the window's dimensions in pixels.
     *
     * @return A Dimension object representing the window's width and height.
     */
    @Override
    public Dimension getWindowDimention() {
        return this.panel.getWindowDimentionInPixels();
    }

    /**
     * Retrieves the center of the window relative to the display.
     *
     * @return A Point representing the window's center relative to the display.
     */
    @Override
    public Point getDisplayRelatedWindowCenter() {
        return new Point((this.getWidth() / 2) + this.getX(), (this.getHeight() / 2) + this.getY());
    }

    /**
     * Sets the visibility of the mouse cursor.
     *
     * @param isMouseCursorVisible If true, the cursor will be visible; otherwise,
     *                             it will be hidden.
     */
    @Override
    public void setMouseCursorVisible(boolean isMouseCursorVisible) {
        this.panel.setMouseCursorVisible(isMouseCursorVisible);

        if (isMouseCursorVisible) {
            this.setCursor(Cursor.getDefaultCursor());
        } else {
            // Create a blank cursor to hide the mouse
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Cursor blankCursor = toolkit.createCustomCursor(cursorImg, 
                                                            new Point(0, 0), 
                                                            "blank cursor");
            this.setCursor(blankCursor);
        }
    }

    /**
     * Checks if the mouse cursor is visible.
     *
     * @return True if the cursor is visible, false otherwise.
     */
    @Override
    public boolean isMouseCursorVisible() {
        return this.panel.isMouseCursorVisible();
    }

    /**
     * Checks if the mouse cursor is locked.
     *
     * @return True if the mouse is locked, false otherwise.
     */
    @Override
    public boolean isMouseLocked() {
        return this.panel.isMouseLocked();
    }

    /**
     * Retrieves the center of the window relative to the window itself.
     *
     * @return A Point representing the window's center.
     */
    @Override
    public Point getWindowRelatedWindowCenter() {
        return new Point(this.getWidth() / 2, this.getHeight() / 2);
    }

    /**
     * Resizes the window to match the dimensions of the GLPanelWrapper.
     */
    @Override
    public void resizeWindow() {
        this.setSize(this.panel.getWindowDimentionInPixels());
    }

    /**
     * Applies the pressed keys by processing them based on their key codes.
     *
     * @param pressedKeys A set of pressed key codes.
     */
    @Override
    public void applyPressedKeys(Set<Integer> pressedKeys) {
        for (int key : pressedKeys) {
            switch (key) {
                case KeyCodeMapper.VK_ESCAPE:
                    // Stop the animator and dispose of the window when the ESC key is pressed
                    this.panel.getAnimator().stop();
                    this.dispose();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Applies mouse movement by adjusting the cursor position if the mouse is
     * locked.
     *
     * @param mousePosition The position of the mouse on the screen.
     */
    @Override
    public void applyMouseMovement(Point2D mousePosition) {
        Point windowCenter = this.getDisplayRelatedWindowCenter();

        if (this.isMouseLocked()) {
            // Move the mouse to the center of the window if it's locked
            this.robot.mouseMove(windowCenter.x, windowCenter.y);
        }
    }

    /**
     * Applies mouse wheel movement. Currently does nothing but can be implemented
     * later.
     *
     * @param direction The direction of the mouse wheel movement.
     */
    @Override
    public void applyMouseWheelMovement(float direction) {
        // TODO: Implement mouse wheel movement functionality
    }
}

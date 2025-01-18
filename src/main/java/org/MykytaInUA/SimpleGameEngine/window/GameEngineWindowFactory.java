package org.mykytainua.simplegameengine.window;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;

/**
 * GameEngineWindowFactory is a factory class for creating various types of game
 * engine windows. It provides methods to generate windows using different
 * rendering backends (NEWT, GLCanvas, and GLPanel). The class ensures that
 * OpenGL capabilities are initialized before creating any windows.
 */
public class GameEngineWindowFactory {

    // OpenGL profile for rendering.
    private static GLProfile profile;

    // OpenGL capabilities, such as double buffering and depth buffer settings.
    private static GLCapabilities capabilities;

    /**
     * Retrieves the current OpenGL profile.
     *
     * @return The current GLProfile instance.
     */
    @SuppressWarnings("unused")
    private static GLProfile getProfile() {
        return profile;
    }

    /**
     * Sets the OpenGL profile.
     *
     * @param profile The GLProfile to set.
     */
    private static void setProfile(GLProfile profile) {
        GameEngineWindowFactory.profile = profile;
    }

    /**
     * Retrieves the current OpenGL capabilities.
     *
     * @return The current GLCapabilities instance.
     */
    private static GLCapabilities getCapabilities() {
        return capabilities;
    }

    /**
     * Sets the OpenGL capabilities.
     *
     * @param capabilities The GLCapabilities to set.
     */
    private static void setCapabilities(GLCapabilities capabilities) {
        GameEngineWindowFactory.capabilities = capabilities;
    }

    // === NEWT Window Creation Methods ===

    /**
     * Creates a NEWT window in fullscreen mode.
     *
     * @param isFullScreen True to create a fullscreen window, false otherwise.
     * @return A new instance of MainNEWTWindow.
     */
    public static MainNEWTWindow getNEWTWindow(boolean isFullScreen) {
        verifyGLCapabilities();
        return new MainNEWTWindow(capabilities, isFullScreen);
    }

    /**
     * Creates a NEWT window with specified dimensions.
     *
     * @param width  The width of the window.
     * @param height The height of the window.
     * @return A new instance of MainNEWTWindow.
     */
    public static MainNEWTWindow getNEWTWindow(int width, int height) {
        verifyGLCapabilities();
        return new MainNEWTWindow(capabilities, width, height);
    }

    // === GLCanvas Window Creation Methods ===

    /**
     * Creates a GLCanvas window in fullscreen mode.
     *
     * @param isFullScreen True to create a fullscreen window, false otherwise.
     * @return A new instance of MainAWTCanvasWindow.
     */
    public static MainAWTCanvasWindow getAWTCanvasWindow(boolean isFullScreen) {
        verifyGLCapabilities();
        return new MainAWTCanvasWindow(capabilities, true);
    }

    /**
     * Creates a GLCanvas window with specified dimensions.
     *
     * @param width  The width of the window.
     * @param height The height of the window.
     * @return A new instance of MainAWTCanvasWindow.
     */
    public static MainAWTCanvasWindow getAWTCanvasWindow(int width, int height) {
        verifyGLCapabilities();
        return new MainAWTCanvasWindow(capabilities, width, height);
    }

    // === GLPanel Window Creation Methods ===

    /**
     * Creates a GLPanel window in fullscreen mode.
     *
     * @param isFullScreen True to create a fullscreen window, false otherwise.
     * @return A new instance of MainGLSwingPanelWindow.
     */
    public static MainGLSwingPanelWindow getGLSwingPanelWindow(boolean isFullScreen) {
        verifyGLCapabilities();
        return new MainGLSwingPanelWindow(capabilities, isFullScreen);
    }

    /**
     * Creates a GLPanel window with specified dimensions.
     *
     * @param width  The width of the window.
     * @param height The height of the window.
     * @return A new instance of MainGLSwingPanelWindow.
     */
    public static MainGLSwingPanelWindow getGLSwingPanelWindow(int width, int height) {
        verifyGLCapabilities();
        return new MainGLSwingPanelWindow(capabilities, width, height);
    }

    // === Helper Methods ===

    /**
     * Ensures that OpenGL capabilities are initialized. If not already initialized,
     * it sets up the default capabilities.
     */
    private static void verifyGLCapabilities() {
        if (!isGLCapabilityInitialized()) {
            initializeGLCapabilities();
        }
    }

    /**
     * Checks whether OpenGL capabilities have been initialized.
     *
     * @return True if capabilities are initialized, false otherwise.
     */
    private static boolean isGLCapabilityInitialized() {
        return capabilities != null;
    }

    /**
     * Initializes OpenGL capabilities with default settings. Configures the
     * profile, enables double buffering, and sets depth buffer bits.
     */
    private static void initializeGLCapabilities() {
        setProfile(GLProfile.getDefault()); // Obtain the default OpenGL profile.
        setCapabilities(new GLCapabilities(profile)); // Create capabilities with the profile.

        // Configure default OpenGL settings.
        
        // Enable double buffering for smoother rendering.
        getCapabilities().setDoubleBuffered(true); 
        
        // Set depth buffer to 32 bits for accurate 3D rendering.
        getCapabilities().setDepthBits(32);
    }
}

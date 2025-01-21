package org.mykytainua.simplegameengine.settings;

import org.joml.Vector3f;

/**
 * Contains the configuration settings for various aspects of the game engine,
 * including mouse and camera settings.
 */
public class Settings {

    // Mouse settings
    // Sensitivity of mouse input, affecting movement speed in the game
    public static float mouseSensitivity = 0.2f;

    // Camera settings
    // The initial position of the camera in 3D space
    public static final Vector3f startingCameraPosition = new Vector3f(0, 0, 3);

    // The initial target position for the camera (the point the camera looks at)
    public static final Vector3f startingCameraTargetPosition = new Vector3f(0, 0, 0);

    // The initial up direction for the camera (usually the Y-axis in most cases)
    public static final Vector3f startingCameraUpDirection = new Vector3f(0, 1, 0);

    // The initial rotation of the camera in 3D space
    public static final Vector3f startingCameraRotation = new Vector3f(0, 0, 0);

    // Camera's field of view in radians (1.047 radians ≈ 60 degrees)
    public static float startingCameraFieldOfView = 1.047f;

    // Camera's aspect ratio, usually width divided by height
    // (e.g., for a 16:9 aspect ratio, it would be 1.78)
    public static float startingCameraAspect = 1.5f;

    // The near plane distance for the camera's view frustum
    // (objects closer than this value will not be rendered)
    public static float startingCameraZNear = 0.1f;

    // The far plane distance for the camera's view frustum
    // (objects farther than this value will not be rendered)
    public static float startingCameraZFar = 1000.0f;

    // Flag to enable or disable logging (useful for debugging or logging events)
    public static final boolean IS_LOGGING_ACTIVE = false;
}

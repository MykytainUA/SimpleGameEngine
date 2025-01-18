package org.mykytainua.simplegameengine.rendering;

import org.mykytainua.simplegameengine.settings.Settings;

/**
 * The PerspectiveParameters class encapsulates the parameters used for setting up
 * the perspective projection matrix for the camera. It includes the field of view, 
 * aspect ratio, and the near and far clipping planes.
 */
public class PerspectiveParameters {
    // The vertical field of view (in degrees) of the camera's perspective
    private float fieldOfView;
    
    // The aspect ratio (width / height) of the camera's view
    private float aspect;
    
    // The near clipping plane distance (the closest point that will be rendered)
    private float nearZ;
    
    // The far clipping plane distance (the furthest point that will be rendered)
    private float farZ;

    /**
     * Default constructor. Initializes the perspective parameters using default
     * values defined in the Settings class.
     */
    public PerspectiveParameters() {
        this.fieldOfView = Settings.startingCameraFieldOfView; // Default FOV from settings
        this.aspect = Settings.startingCameraAspect; // Default aspect ratio from settings
        this.nearZ = Settings.startingCameraZNear; // Default near clipping plane from settings
        this.farZ = Settings.startingCameraZFar; // Default far clipping plane from settings
    }

    /**
     * Constructor that allows setting the field of view, near and far planes, 
     * and calculates the aspect ratio based on the given width and height.
     *
     * @param width The width of the viewport
     * @param height The height of the viewport
     * @param fieldOfView The vertical field of view (in degrees)
     * @param nearZ The near clipping plane distance
     * @param farZ The far clipping plane distance
     */
    public PerspectiveParameters(int width, 
                                 int height, 
                                 float fieldOfView, 
                                 float nearZ, 
                                 float farZ) {
        
        this.fieldOfView = fieldOfView;
        this.aspect = (float) width / height; // Calculate the aspect ratio
        this.nearZ = nearZ;
        this.farZ = farZ;
    }

    /**
     * Constructor that allows setting the field of view, aspect ratio, near and far
     * planes directly.
     *
     * @param fieldOfView The vertical field of view (in degrees)
     * @param aspect      The aspect ratio (width / height)
     * @param nearZ       The near clipping plane distance
     * @param farZ        The far clipping plane distance
     */
    public PerspectiveParameters(float fieldOfView, float aspect, float nearZ, float farZ) {
        this.fieldOfView = fieldOfView;
        this.aspect = aspect;
        this.nearZ = nearZ;
        this.farZ = farZ;
    }

    // Getter and setter methods for each of the perspective parameters:

    /**
     * Gets the field of view of the camera (in radians).
     *
     * @return The field of view of the camera (in radians).
     */
    public float getFieldOfView() {
        return fieldOfView;
    }

    /**
     * Sets the field of view of the camera (in radians).
     *
     * @param fieldOfView The field of view to set (in radians)
     */
    public void setFieldOfView(float fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    /**
     * Sets the aspect ratio of the camera.
     *
     * @param aspect The aspect ratio to set
     */
    public void setAspect(float aspect) {
        this.aspect = aspect;
    }

    /**
     * Sets the aspect ratio of the camera by calculating it from the given width and height.
     *
     * @param width The width of the viewport
     * @param height The height of the viewport
     */
    public void setAspect(int width, int height) {
        this.aspect = (float) width / height;
    }

    /**
     * Gets the aspect ratio of the camera by calculating it from the given width and height.
     *
     * @return The aspect ratio (width / height) of the camera's view.
     */
    public float getAspect() {
        return this.aspect;
    }

    /**
     * Sets the near clipping plane distance.
     *
     * @return The near clipping plane distance.
     */
    public float getNearZ() {
        return nearZ;
    }

    /**
     * Sets the near clipping plane distance.
     *
     * @param nearZ The near clipping plane distance to set
     */
    public void setNearZ(float nearZ) {
        this.nearZ = nearZ;
    }

    /**
     * Sets the far clipping plane distance.
     *
     * @return The far clipping plane distance.
     */
    public float getFarZ() {
        return farZ;
    }

    /**
     * Sets the far clipping plane distance.
     *
     * @param farZ The far clipping plane distance to set
     */
    public void setFarZ(float farZ) {
        this.farZ = farZ;
    }
}

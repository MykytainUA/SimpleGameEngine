package org.mykytainua.simplegameengine.objects;

import java.awt.geom.Point2D;
import java.util.Set;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.mykytainua.simplegameengine.rendering.PerspectiveParameters;
import org.mykytainua.simplegameengine.settings.Settings;
import org.mykytainua.simplegameengine.userinput.KeyCodeMapper;
import org.mykytainua.simplegameengine.userinput.KeyResponser;
import org.mykytainua.simplegameengine.userinput.MouseResponser;
import org.mykytainua.simplegameengine.userinput.UserInputResponser;

/**
 * The Camera class is responsible for managing the camera's position, rotation,
 * and perspective in a 3D space. It includes methods for moving the camera in
 * different directions, rotating it, and updating its view matrices based on
 * user input.
 */
public class Camera implements UserInputResponser, KeyResponser, MouseResponser {

    // ===========================
    // Fields
    // ===========================

    /** The LookAt matrix for the camera's view transformation. */
    private Matrix4f lookAtMatrix;

    /** The perspective projection matrix for the camera. */
    private Matrix4f perspectiveMatrix;

    /** The camera's perspective parameters. */
    private PerspectiveParameters perspectiveParameters;

    /** The camera's current position in world space. */
    private Vector3f cameraPosition;

    /** The target position the camera is looking at. */
    private Vector3f targetPosition;

    /** The up direction vector for the camera. */
    private Vector3f upDirection;

    /** The rotation vector of the camera, represented by pitch, yaw, and roll. */
    private Vector3f rotation;

    /** The last time the camera was updated in nanoseconds. */
    private long lastUpdatedTimeNano = System.nanoTime();

    /** The speed at which the camera moves. */
    private float speed = 2.0f;

    // ===========================
    // Constructors
    // ===========================

    /**
     * Default constructor that initializes the camera with default settings.
     */
    public Camera() {

        // Default parameters and properties
        this.cameraPosition = new Vector3f(Settings.startingCameraPosition);
        this.targetPosition = new Vector3f(Settings.startingCameraTargetPosition);
        this.upDirection = new Vector3f(Settings.startingCameraUpDirection);
        this.rotation = new Vector3f(Settings.startingCameraRotation);

        this.perspectiveParameters = new PerspectiveParameters();

        // Initialize matrices
        this.lookAtMatrix = new Matrix4f();
        this.perspectiveMatrix = new Matrix4f();

        // LookAt matrix is recomputed for every frame,
        // so no need to set it here
        this.lookAtMatrix.identity();

        this.perspectiveMatrix.identity();
        this.perspectiveMatrix.setPerspective(this.perspectiveParameters.getFieldOfView(),
                this.perspectiveParameters.getAspect(), this.perspectiveParameters.getNearZ(),
                this.perspectiveParameters.getFarZ());
    }

    /**
     * Constructor that initializes the camera with specified perspective
     * parameters.
     *
     * @param perspectiveParameters the perspective parameters to set for the camera
     */
    public Camera(PerspectiveParameters perspectiveParameters) {
        this();

        this.perspectiveParameters = perspectiveParameters;

        this.perspectiveMatrix.setPerspective(this.perspectiveParameters.getFieldOfView(),
                this.perspectiveParameters.getAspect(), this.perspectiveParameters.getNearZ(),
                this.perspectiveParameters.getFarZ());
    }

    /**
     * Constructor that initializes the camera with a specified position and
     * perspective parameters.
     *
     * @param cameraPosition        the position of the camera
     * @param perspectiveParameters the perspective parameters to set for the camera
     */
    public Camera(Vector3f cameraPosition, PerspectiveParameters perspectiveParameters) {

        this(perspectiveParameters);

        this.cameraPosition = cameraPosition;
    }

    /**
     * Constructor that initializes the camera with a specified position, target
     * position, up direction, and perspective parameters.
     *
     * @param cameraPosition        the position of the camera
     * @param targetPosition        the target position the camera is looking at
     * @param upDirection           the up direction vector for the camera
     * @param perspectiveParameters the perspective parameters to set for the camera
     */
    public Camera(Vector3f cameraPosition, // Camera position
            Vector3f targetPosition, // Target position
            Vector3f upDirection, // Up vector
            PerspectiveParameters perspectiveParameters) { // Perspective parameters

        this(cameraPosition, perspectiveParameters);

        this.targetPosition = targetPosition;

        this.upDirection = upDirection;
        this.upDirection.normalize();
    }

    // ===========================
    // Getters and Setters
    // ===========================

    /**
     * Gets the current position of the camera.
     *
     * @return the current camera position as a {@link Vector3f}
     */
    public Vector3f getCameraPosition() {
        return cameraPosition;
    }

    /**
     * Sets the position of the camera.
     *
     * @param cameraPosition the new position of the camera
     */
    public void setCameraPosition(Vector3f cameraPosition) {
        this.cameraPosition = cameraPosition;
    }

    /**
     * Gets the LookAt matrix for the camera.
     *
     * @return the LookAt matrix as a {@link Matrix4f}
     */
    public Matrix4f getLookAtMatrix() {

        return this.lookAtMatrix.setLookAt(cameraPosition, this.getRotatedTargetPosition(),
                this.getRotatedUpDirection());
    }

    /**
     * Gets the perspective matrix for the camera.
     *
     * @return the perspective matrix as a {@link Matrix4f}
     */
    public Matrix4f getPerspectiveMatrix() {

        return this.perspectiveMatrix;
    }

    // ===========================
    // Methods
    // ===========================

    /**
     * Computes the camera's direction vector by subtracting the target position
     * from the camera position.
     *
     * @return a normalized direction vector
     */
    private Vector3f computeCameraDirection() {

        Vector3f direction = new Vector3f();
        this.cameraPosition.sub(this.targetPosition, direction);
        return direction.normalize();
    }

    /**
     * Computes the camera's right vector by taking the cross product of the
     * camera's up direction and camera direction vectors.
     *
     * @return a normalized right vector
     */
    private Vector3f computeCameraRight() {

        Vector3f right = new Vector3f();
        this.upDirection.cross(this.computeCameraDirection(), right);
        return right.normalize();
    }

    /**
     * Computes the camera's up vector by taking the cross product of the camera's
     * direction and right vectors.
     *
     * @return a normalized up vector
     */
    private Vector3f computeCameraUp() {

        Vector3f up = new Vector3f();
        this.computeCameraDirection().cross(this.computeCameraRight(), up);
        return up;
    }

    @SuppressWarnings("unused")
    private void printCameraData() {

        System.out.println("cameraPosition:" + this.cameraPosition);
        System.out.println("targetPosition:" + this.getRotatedTargetPosition());
        System.out.println("upDirection:" + this.getRotatedUpDirection());
        System.out.println("angles: x:" 
                            + Math.toDegrees(this.rotation.x) 
                            + " y:" + Math.toDegrees(this.rotation.y)
                            + " z:" + Math.toDegrees(this.rotation.z));
    }

    private Vector3f getRotatedTargetPosition() {

        Vector3f direction = new Vector3f(this.computeCameraDirection());

        direction.rotateX(this.rotation.x);
        direction.rotateY(this.rotation.y);
        direction.rotateZ(this.rotation.z);

        Vector3f rotatedTargetPosition = new Vector3f(this.targetPosition);

        rotatedTargetPosition.set(cameraPosition).sub(direction);

        return rotatedTargetPosition;
    }

    private Vector3f getRotatedUpDirection() {

        Vector3f rotatedUpDirection = new Vector3f(this.upDirection);

        rotatedUpDirection.rotateX(this.rotation.x);
        rotatedUpDirection.rotateY(this.rotation.y);
        rotatedUpDirection.rotateZ(this.rotation.z);

        return rotatedUpDirection;
    }

    /**
     * Sets the aspect ratio for the camera's perspective matrix.
     *
     * @param aspect the new aspect ratio
     */
    public void setAspect(float aspect) {

        this.perspectiveParameters.setAspect(aspect);
        this.perspectiveMatrix.setPerspective(this.perspectiveParameters.getFieldOfView(),
                this.perspectiveParameters.getAspect(), this.perspectiveParameters.getNearZ(),
                this.perspectiveParameters.getFarZ());
    }

    /**
     * Sets the aspect ratio for the camera's perspective matrix using the given
     * width and height.
     *
     * @param width  the width of the viewport
     * @param height the height of the viewport
     */
    public void setAspect(int width, int height) {

        this.perspectiveParameters.setAspect(width, height);
        this.perspectiveMatrix.setPerspective(this.perspectiveParameters.getFieldOfView(),
                this.perspectiveParameters.getAspect(), this.perspectiveParameters.getNearZ(),
                this.perspectiveParameters.getFarZ());
    }

    /**
     * Moves the camera in the specified direction, applying the current rotation.
     *
     * @param direction the direction vector to move the camera
     */
    private void moveCameraTowards(Vector3f direction) {

        direction.rotateX(this.rotation.x);
        direction.rotateY(this.rotation.y);
        direction.rotateZ(this.rotation.z);

        this.cameraPosition.x += direction.x;
        this.targetPosition.x += direction.x;

        this.cameraPosition.y += direction.y;
        this.targetPosition.y += direction.y;

        this.cameraPosition.z += direction.z;
        this.targetPosition.z += direction.z;
    }

    // ===========================
    // Control methods
    // ===========================

    /**
     * Moves the camera along the X, Y, and Z axes based on the specified direction
     * vector.
     *
     * @param direction a {@code Vector3f} representing the direction of movement
     */
    public void moveAlongAllAxes(Vector3f direction) {

        this.cameraPosition.x += direction.x;
        this.targetPosition.x += direction.x;

        this.cameraPosition.y += direction.y;
        this.targetPosition.y += direction.y;

        this.cameraPosition.z += direction.z;
        this.targetPosition.z += direction.z;
    }

    /**
     * Moves the camera forward by the specified distance.
     * 
     * <p>The camera moves along its forward direction, which is calculated
     * internally.
     *
     * @param distance the distance to move forward
     */
    public void moveForward(float distance) {

        Vector3f direction = new Vector3f(this.computeCameraDirection());

        direction.mul(-distance); // minus used for moving in opposite direction

        this.moveCameraTowards(direction);
    }

    /**
     * Moves the camera backward by the specified distance.
     * 
     * <p>The camera moves in the opposite direction of its forward vector.
     *
     * @param distance the distance to move backward
     */
    public void moveBackward(float distance) {

        Vector3f direction = new Vector3f(this.computeCameraDirection());

        direction.mul(distance);

        this.moveCameraTowards(direction);
    }

    /**
     * Moves the camera to the right by the specified distance.
     * 
     * <p>The camera moves along its right vector, which is calculated internally.
     *
     * @param distance the distance to move to the right
     */
    public void moveRight(float distance) {

        Vector3f right = new Vector3f(this.computeCameraRight());

        right.mul(distance);

        this.moveCameraTowards(right);
    }

    /**
     * Moves the camera to the left by the specified distance.
     * 
     * <p>The camera moves in the opposite direction of its right vector.
     *
     * @param distance the distance to move to the left
     */
    public void moveLeft(float distance) {

        Vector3f right = new Vector3f(this.computeCameraRight());

        right.mul(-distance);

        this.moveCameraTowards(right);
    }

    /**
     * Moves the camera upward by the specified distance.
     * 
     * <p>The camera moves along its upward vector, which is calculated internally.
     *
     * @param distance the distance to move upward
     */
    public void moveUp(float distance) {

        Vector3f up = new Vector3f(this.computeCameraUp());

        up.mul(distance);

        this.moveCameraTowards(up);
    }

    /**
     * Moves the camera downward by the specified distance.
     * 
     * <p>The camera moves in the opposite direction of its upward vector.
     *
     * @param distance the distance to move downward
     */
    public void moveDown(float distance) {

        Vector3f up = new Vector3f(this.computeCameraUp());

        up.mul(-distance);

        this.moveCameraTowards(up);
    }

    /**
     * Rotates the camera object around the X-axis by the specified angle in
     * radians.
     * 
     * <p>The angle is normalized to ensure it remains within the range of 0 to 2π
     * radians.
     *
     * @param radians the angle to rotate, in radians
     */
    public void rotateAroundXaxis(float radians) {

        this.rotation.x += radians;
        this.rotation.x %= Math.PI * 2;
    }

    /**
     * Rotates the camera object around the Y-axis by the specified angle in
     * radians.
     * 
     * <p>The angle is normalized to ensure it remains within the range of 0 to 2π
     * radians.
     *
     * @param radians the angle to rotate, in radians
     */
    public void rotateAroundYaxis(float radians) {

        this.rotation.y += radians;
        this.rotation.y %= Math.PI * 2;
    }

    /**
     * Rotates the camera object around the Z-axis by the specified angle in
     * radians.
     * 
     * <p>The angle is normalized to ensure it remains within the range of 0 to 2π
     * radians.
     *
     * @param radians the angle to rotate, in radians
     */
    public void rotateAroundZaxis(float radians) {

        this.rotation.z += radians;
        this.rotation.z %= Math.PI * 2;
    }

    /**
     * Rotates the camera object around the X, Y, and Z axes by the specified angles
     * in radians.
     * 
     * <p>Each angle is normalized to ensure it remains within the range of 0 to 2π
     * radians.
     *
     * @param radiansX the angle to rotate around the X-axis, in radians
     * @param radiansY the angle to rotate around the Y-axis, in radians
     * @param radiansZ the angle to rotate around the Z-axis, in radians
     */
    public void rotateAroundAllAxes(float radiansX, float radiansY, float radiansZ) {

        this.rotation.x += radiansX;
        this.rotation.y += radiansY;
        this.rotation.z += radiansZ;

        this.rotation.x %= Math.PI * 2;
        this.rotation.y %= Math.PI * 2;
        this.rotation.z %= Math.PI * 2;
    }

    @Override
    public void applyPressedKeys(Set<Integer> pressedKeys) {

        float timeIntervalSeconds = (System.nanoTime() - lastUpdatedTimeNano) / 1000000000.0f;
        lastUpdatedTimeNano = System.nanoTime();

        for (int key : pressedKeys) {
            switch (key) {
                case KeyCodeMapper.VK_W:
                    this.moveForward(timeIntervalSeconds * speed);
                    break;
                case KeyCodeMapper.VK_A:
                    this.moveLeft(timeIntervalSeconds * speed);
                    break;
                case KeyCodeMapper.VK_S:
                    this.moveBackward(timeIntervalSeconds * speed);
                    break;
                case KeyCodeMapper.VK_D:
                    this.moveRight(timeIntervalSeconds * speed);
                    break;
                case KeyCodeMapper.VK_SPACE:
                    this.moveAlongAllAxes(new Vector3f(0, timeIntervalSeconds * speed, 0));
                    break;
                case KeyCodeMapper.VK_SHIFT:
                    this.moveAlongAllAxes(new Vector3f(0, -timeIntervalSeconds * speed, 0));
                    break;
                case KeyCodeMapper.VK_1:
                    this.rotateAroundXaxis((float) Math.toRadians(1));
                    break;
                case KeyCodeMapper.VK_2:
                    this.rotateAroundXaxis((float) Math.toRadians(-1));
                    break;
                case KeyCodeMapper.VK_3:
                    this.rotateAroundYaxis((float) Math.toRadians(1));
                    break;
                case KeyCodeMapper.VK_4:
                    this.rotateAroundYaxis((float) Math.toRadians(-1));
                    break;
                case KeyCodeMapper.VK_5:
                    this.rotateAroundZaxis((float) Math.toRadians(1));
                    break;
                case KeyCodeMapper.VK_6:
                    this.rotateAroundZaxis((float) Math.toRadians(-1));
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void applyMouseMovement(Point2D mouseShift) {
        this.rotateAroundYaxis((float) Math.toRadians(mouseShift.getX()) 
                                                      * Settings.mouseSensitivity);
        this.rotateAroundXaxis((float) Math.toRadians(mouseShift.getY()) 
                                                      * Settings.mouseSensitivity);
    }

    @Override
    public void applyMouseWheelMovement(final float direction) {
        this.speed += (Math.abs(this.speed) * direction) / 2;
    }
}

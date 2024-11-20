package org.MykytaInUA.SimpleGameEngine.objects;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.awt.geom.Point2D;
import java.util.Set;

import org.MykytaInUA.SimpleGameEngine.rendering.PerspectiveParameters;
import org.MykytaInUA.SimpleGameEngine.settings.Settings;
import org.MykytaInUA.SimpleGameEngine.user_input.KeyCodeMapper;
import org.MykytaInUA.SimpleGameEngine.user_input.KeyResponser;
import org.MykytaInUA.SimpleGameEngine.user_input.MouseResponser;

/*
 * Attention!!!
 * This camera class applies rotation every call,
 * Starting vectors are unchanged
 */

import org.MykytaInUA.SimpleGameEngine.user_input.UserInputResponser;

public class Camera implements UserInputResponser, KeyResponser, MouseResponser {

	// ===========================
	// Fields
	// ===========================

	// Camera matrices
	private Matrix4f lookAtMatrix;
	private Matrix4f perspectiveMatrix;

	// Camera parameters	
	private PerspectiveParameters perspectiveParameters;
	private Vector3f cameraPosition;
	private Vector3f targetPosition;
	private Vector3f upDirection;
	private Vector3f rotation;
	
	// Global camera parameters
	private long lastUpdatedTimeNano = System.nanoTime();
	private float speed = 2.0f;

	// ===========================
	// Constructors
	// ===========================

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

		this.lookAtMatrix.identity(); // LookAt matrix is recomputed for every frame,
									  // so no need to set it here

		this.perspectiveMatrix.identity();
		this.perspectiveMatrix.setPerspective(this.perspectiveParameters.getFieldOfView(),
				  							  this.perspectiveParameters.getAspect(), 
											  this.perspectiveParameters.getzNear(), 
											  this.perspectiveParameters.getzFar());
	}

	public Camera(PerspectiveParameters perspectiveParameters) {
		this();
		
		this.perspectiveParameters = perspectiveParameters;
		
		this.perspectiveMatrix.setPerspective(this.perspectiveParameters.getFieldOfView(),
											  this.perspectiveParameters.getAspect(), 
											  this.perspectiveParameters.getzNear(), 
											  this.perspectiveParameters.getzFar());
	}

	public Camera(Vector3f cameraPosition, 
				  PerspectiveParameters perspectiveParameters) {
		
		this(perspectiveParameters);

		this.cameraPosition = cameraPosition;
	}

	public Camera(Vector3f cameraPosition,            // Camera position
				  Vector3f targetPosition,   // Target position
				  Vector3f upDirection,               // Up vector
			      PerspectiveParameters perspectiveParameters) { // Perspective parameters

		this(cameraPosition, perspectiveParameters);

		this.targetPosition = targetPosition;
		
		this.upDirection = upDirection;
		this.upDirection.normalize();
	}

	// ===========================
	// Getters and Setters
	// ===========================

	public Vector3f getCameraPosition() {
		return cameraPosition;
	}

	public void setCameraPosition(Vector3f cameraPosition) {
		this.cameraPosition = cameraPosition;
	}

	public Matrix4f getLookAtMatrix() {
		
		return this.lookAtMatrix.setLookAt(cameraPosition, this.getRotatedTargetPosition(),
				this.getRotatedUpDirection());
	}

	public Matrix4f getPerspectiveMatrix() {
		
		return this.perspectiveMatrix;
	}

	// ===========================
	// Methods
	// ===========================

	private Vector3f computeCameraDirection() {
		
		Vector3f direction = new Vector3f();
		this.cameraPosition.sub(this.targetPosition, direction);
		return direction.normalize();
	}

	private Vector3f computeCameraRight() {
		
		Vector3f right = new Vector3f();
		this.upDirection.cross(this.computeCameraDirection(), right);
		return right.normalize();
	}

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
		System.out.println(
				"angles: x:" + Math.toDegrees(this.rotation.x) 
				+ " y:" + Math.toDegrees(this.rotation.y) 
				+ " z:" + Math.toDegrees(this.rotation.z));
	}

	private Vector3f getRotatedTargetPosition() {
		
		Vector3f rotatedTargetPosition = new Vector3f(this.targetPosition);
		Vector3f direction = new Vector3f(this.computeCameraDirection());

		direction.rotateX(this.rotation.x);
		direction.rotateY(this.rotation.y);
		direction.rotateZ(this.rotation.z);

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

	public void setAspect(float aspect) {
		
		this.perspectiveParameters.setAspect(aspect);
		this.perspectiveMatrix.setPerspective(this.perspectiveParameters.getFieldOfView(),
				  							  this.perspectiveParameters.getAspect(), 
				  							  this.perspectiveParameters.getzNear(), 
				  							  this.perspectiveParameters.getzFar());
	}
	
	public void setAspect(int width, int height) {
		
		this.perspectiveParameters.setAspect(width, height);
		this.perspectiveMatrix.setPerspective(this.perspectiveParameters.getFieldOfView(),
				  							  this.perspectiveParameters.getAspect(), 
				  							  this.perspectiveParameters.getzNear(), 
				  							  this.perspectiveParameters.getzFar());
	}

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

	public void moveAlongXYZ(Vector3f direction) {
		
		this.cameraPosition.x += direction.x;
		this.targetPosition.x += direction.x;

		this.cameraPosition.y += direction.y;
		this.targetPosition.y += direction.y;

		this.cameraPosition.z += direction.z;
		this.targetPosition.z += direction.z;
	}

	public void moveForward(float distance) {
		
		Vector3f direction = new Vector3f(this.computeCameraDirection());

		direction.mul(-distance); // minus used for moving in opposite direction

		this.moveCameraTowards(direction);
	}

	public void moveBackward(float distance) {
		
		Vector3f direction = new Vector3f(this.computeCameraDirection());

		direction.mul(distance);

		this.moveCameraTowards(direction);
	}

	public void moveRight(float distance) {
		
		Vector3f right = new Vector3f(this.computeCameraRight());

		right.mul(distance);

		this.moveCameraTowards(right);
	}

	public void moveLeft(float distance) {
		
		Vector3f right = new Vector3f(this.computeCameraRight());

		right.mul(-distance);

		this.moveCameraTowards(right);
	}

	public void moveUp(float distance) {
		
		Vector3f up = new Vector3f(this.computeCameraUp());

		up.mul(distance);

		this.moveCameraTowards(up);
	}

	public void moveDown(float distance) {
		
		Vector3f up = new Vector3f(this.computeCameraUp());

		up.mul(-distance);

		this.moveCameraTowards(up);
	}

	public void rotateX(float radians) {
		
		this.rotation.x += radians;
		this.rotation.x %= Math.PI * 2;
	}

	public void rotateY(float radians) {
		
		this.rotation.y += radians;
		this.rotation.y %= Math.PI * 2;
	}

	public void rotateZ(float radians) {
		
		this.rotation.z += radians;
		this.rotation.z %= Math.PI * 2;
	}

	public void rotateXYZ(float radiansX, float radiansY, float radiansZ) {
		
		this.rotation.x += radiansX;
		this.rotation.y += radiansY;
		this.rotation.z += radiansZ;

		this.rotation.x %= Math.PI * 2;
		this.rotation.y %= Math.PI * 2;
		this.rotation.z %= Math.PI * 2;
	}

	@Override
	public void applyPressedKeys(Set<Integer> pressedKeys) {
		
		float timeIntervalSeconds = (System.nanoTime() - lastUpdatedTimeNano)/1000000000.0f;
		lastUpdatedTimeNano = System.nanoTime();
		
		for (int key : pressedKeys) {
			switch(key) {
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
				this.moveAlongXYZ(new Vector3f(0, timeIntervalSeconds * speed, 0));
				break;
			case KeyCodeMapper.VK_SHIFT:
				this.moveAlongXYZ(new Vector3f(0, -timeIntervalSeconds * speed, 0));
				break;
			case KeyCodeMapper.VK_1:
				this.rotateX((float)Math.toRadians(1));
				break;
			case KeyCodeMapper.VK_2:
				this.rotateX((float)Math.toRadians(-1));
				break;
			case KeyCodeMapper.VK_3:
				this.rotateY((float)Math.toRadians(1));
				break;
			case KeyCodeMapper.VK_4:
				this.rotateY((float)Math.toRadians(-1));
				break;
			case KeyCodeMapper.VK_5:
				this.rotateZ((float)Math.toRadians(1));
				break;
			case KeyCodeMapper.VK_6:
				this.rotateZ((float)Math.toRadians(-1));
				break;
				}
			}
	}

	@Override
	public void applyMouseMovement(Point2D mouseShift) {
		this.rotateY((float)Math.toRadians(mouseShift.getX()) * Settings.mouseSensitivity);
		this.rotateX((float)Math.toRadians(mouseShift.getY()) * Settings.mouseSensitivity);
	}

	@Override
	public void applyMouseWheelMovement(float direction) {
		this.speed += (Math.abs(this.speed) * direction) / 2;
	}
}

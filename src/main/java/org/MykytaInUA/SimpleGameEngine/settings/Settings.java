package org.MykytaInUA.SimpleGameEngine.settings;

import org.joml.Vector3f;

public class Settings {
	
	// Mouse settings
	public static float mouseSensitivity = 0.2f;
	
	//Camera settings
	public static Vector3f startingCameraPosition = new Vector3f(0, 0, 3);
	public static Vector3f startingCameraTargetPosition = new Vector3f(0, 0, 0);
	public static Vector3f startingCameraUpDirection = new Vector3f(0, 1, 0);
	public static Vector3f startingCameraRotation = new Vector3f(0, 0, 0);
	
	public static float startingCameraFieldOfView = 1.047f; // 60 degrees in radians
	public static float startingCameraAspect = 1.5f;
	public static float startingCameraZNear = 0.1f;
	public static float startingCameraZFar = 1000.0f;
}

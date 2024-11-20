package org.MykytaInUA.SimpleGameEngine.rendering;

import org.MykytaInUA.SimpleGameEngine.settings.Settings;

public class PerspectiveParameters {
	private float fieldOfView;
	private float aspect;
	private float zNear;
	private float zFar;
	
	public PerspectiveParameters() {
		
		this.fieldOfView = Settings.startingCameraFieldOfView;
		this.aspect = Settings.startingCameraAspect;
		this.zNear = Settings.startingCameraZNear;
		this.zFar = Settings.startingCameraZFar;
	}
	
	public PerspectiveParameters(int width, int height, float fieldOfView,
								 float zNear, float zFar) {
		this.fieldOfView = fieldOfView;
		this.aspect = (float) width / height;
		this.zNear = zNear;
		this.zFar = zFar;
	}
	
	public PerspectiveParameters(float fieldOfView, float aspect,
			 					 float zNear, float zFar) {
		
		this.fieldOfView = fieldOfView;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
	}
	
	public float getFieldOfView() {
		return fieldOfView;
	}
	
	public void setFieldOfView(float fieldOfView) {
		this.fieldOfView = fieldOfView;
	}
	
	public void setAspect(float aspect) {
		this.aspect = aspect;
	}
	
	public void setAspect(int width, int height) {
		this.aspect = (float) width / height;
	}

	public float getAspect() {
		return this.aspect;
	}
	
	public float getzNear() {
		return zNear;
	}
	
	public void setzNear(float zNear) {
		this.zNear = zNear;
	}
	
	public float getzFar() {
		return zFar;
	}
	
	public void setzFar(float zFar) {
		this.zFar = zFar;
	}
	
}

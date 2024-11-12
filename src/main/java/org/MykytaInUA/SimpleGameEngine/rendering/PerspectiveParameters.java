package org.MykytaInUA.SimpleGameEngine.rendering;

public class PerspectiveParameters {
	private float fieldOfView;
	private float aspect;
	private float zNear;
	private float zFar;
	
	public static final float DEFAULT_ASPECT = 1.5f;
	public static final float DEFAULT_FIELD_OF_VIEW = 1.047f; // 60 degrees in radians
	public static final float DEFAULT_Z_NEAR = 0.1f;
	public static final float DEFAULT_Z_FAR = 1000.0f;
	
	public PerspectiveParameters() {
		
		this.fieldOfView = DEFAULT_FIELD_OF_VIEW;
		this.aspect = DEFAULT_ASPECT;
		this.zNear = DEFAULT_Z_NEAR;
		this.zFar = DEFAULT_Z_FAR;
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

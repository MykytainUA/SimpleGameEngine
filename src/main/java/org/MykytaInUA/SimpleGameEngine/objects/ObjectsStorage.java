package org.MykytaInUA.SimpleGameEngine.objects;

import org.MykytaInUA.SimpleGameEngine.objects.components.texture.SolidColorComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.PositionComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.RotationComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.SizeComponent;

public class ObjectsStorage<T extends Object3D> {
	
	private T[] objects;
	private FloatBufferWrapper objectsPositions;
	private FloatBufferWrapper objectsColors;
	private FloatBufferWrapper objectsRotations;
	private FloatBufferWrapper objectsSizes;
	
	public ObjectsStorage() {
		this.objectsPositions = new FloatBufferWrapper();
		this.objectsColors = new FloatBufferWrapper();
		this.objectsRotations = new FloatBufferWrapper();
		this.objectsSizes = new FloatBufferWrapper();
	}
	
	public Object3D[] getObjects() {
		return objects;
	}
	
	public void setObjects(T[] objects) {
		this.objects = objects;
		this.updateBuffers();
	}

	public FloatBufferWrapper getObjectsPositions() {
		return objectsPositions;
	}

	public FloatBufferWrapper getObjectsColors() {
		return objectsColors;
	}

	public FloatBufferWrapper getObjectsRotations() {
		return objectsRotations;
	}

	public FloatBufferWrapper getObjectsSizes() {
		return objectsSizes;
	}
	
	public void updateBuffers() {
		// Create positionBuffer
		float[] positionsArray = new float[objects.length * 3];
		float[] rotationsArray = new float[objects.length * 3];
		float[] colorsArray = new float[objects.length * 4];
		float[] sizesArray = new float[objects.length * 3];
		
		for (int i = 0; i < objects.length; i++) {
			
			if(objects[i].getComponents().getComponentByClass(PositionComponent.class) != null) {
				PositionComponent positionComponent = (PositionComponent) objects[i].getComponents().getComponentByClass(PositionComponent.class);
				positionsArray[i * 3] = positionComponent.getPosition().x;
				positionsArray[i * 3 + 1] = positionComponent.getPosition().y;
				positionsArray[i * 3 + 2] = positionComponent.getPosition().z;
			} 
			
			if(objects[i].getComponents().getComponentByClass(SolidColorComponent.class) != null) {
				SolidColorComponent solidColorComponent = (SolidColorComponent) objects[i].getComponents().getComponentByClass(SolidColorComponent.class);
				colorsArray[i * 4] = solidColorComponent.getColor().x;
				colorsArray[i * 4 + 1] = solidColorComponent.getColor().y;
				colorsArray[i * 4 + 2] = solidColorComponent.getColor().z;
				colorsArray[i * 4 + 3] = solidColorComponent.getColor().w;
			} 
			
			if(objects[i].getComponents().getComponentByClass(RotationComponent.class) != null) {
				RotationComponent rotationComponent = (RotationComponent) objects[i].getComponents().getComponentByClass(RotationComponent.class);
				rotationsArray[i * 3] = rotationComponent.getRotation().x;
				rotationsArray[i * 3 + 1] =  rotationComponent.getRotation().y;
				rotationsArray[i * 3 + 2] =  rotationComponent.getRotation().z;
			}
			
			if(objects[i].getComponents().getComponentByClass(SizeComponent.class) != null) {
				SizeComponent sizeComponent = (SizeComponent) objects[i].getComponents().getComponentByClass(SizeComponent.class);
				sizesArray[i * 3] = sizeComponent.getSize().x;
				sizesArray[i * 3 + 1] =  sizeComponent.getSize().y;
				sizesArray[i * 3 + 2] =  sizeComponent.getSize().z;
			}
		}
		
		this.objectsPositions.updateAllContents(positionsArray);	
		this.objectsRotations.updateAllContents(rotationsArray);
		this.objectsColors.updateAllContents(colorsArray);
		this.objectsSizes.updateAllContents(sizesArray);
	}
	
}

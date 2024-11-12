package org.MykytaInUA.SimpleGameEngine.objects;

import java.util.Random;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.MykytaInUA.SimpleGameEngine.objects.builders.CubeBuilder;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.RenderMaterialComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.SolidColorComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.texture.TextureComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.PositionComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.RotationComponent;
import org.MykytaInUA.SimpleGameEngine.objects.components.transform.SizeComponent;

public class CubeGenerator {
	
	private static Random rand = new Random();
	
	// Temporary texture coordinates
		public static float[] textureCoordinates = {
			// front side texture coordinates
			   1.0f, 1.0f,  // Front top right
		       1.0f, 0.0f,  // Front bottom right
		       0.0f, 1.0f,  // Front top left
		       0.0f, 0.0f,  // Front bottom left
		        
		       0.0f, 1.0f,  // Back top right
		       0.0f, 0.0f,  // Back bottom right
		       1.0f, 0.0f,  // Back top left
		       1.0f, 1.0f,   // Back bottom left	
		};
	
	public static Cube[] createRandomCubes(int amount, int seed, float distance, float size) {
		rand.setSeed(seed);
		
		Cube[] cubes = new Cube[amount];	
		CubeBuilder cubeBuilder = new CubeBuilder();
		
		PositionComponent positionComponent = new PositionComponent(new Vector3f());
		RotationComponent rotationComponent = new RotationComponent(new Vector3f());
		SizeComponent sizeComponent = new SizeComponent(new Vector3f());
		SolidColorComponent solidColor = new SolidColorComponent(new Vector4f());
		
		// Use the same render material for all cubes
		
		for (int i = 0; i < cubes.length; i++) {
			
			positionComponent.setPosition(new Vector3f(rand.nextFloat(distance), rand.nextFloat(distance), rand.nextFloat(distance)));
			rotationComponent.setRotation(new Vector3f(rand.nextFloat(360), rand.nextFloat(360), rand.nextFloat(360)));
			solidColor.setColor(new Vector4f(rand.nextFloat(), rand.nextFloat(), rand.nextFloat(),  1));
			sizeComponent.setSize(new Vector3f(1, 1, 1));
			
			cubeBuilder.addComponentByCopy(positionComponent);
			cubeBuilder.addComponentByCopy(rotationComponent);
			cubeBuilder.addComponentByCopy(solidColor);
			cubeBuilder.addComponentByCopy(sizeComponent);	
			
			cubes[i] = (Cube) cubeBuilder.getObject();
		}
			
		return cubes;
	}
}

package org.MykytaInUA.SimpleGameEngine.objects.components.mesh;

import java.util.Arrays;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class IndexedVertexMesh implements MeshComponent {
	
	private final int[] indices;
	protected float[] vertices;
	
	public IndexedVertexMesh(float[] vertices, int[] indices) {
		this.vertices = vertices;
		this.indices = indices;
	}	
	
	public float[] getVertices() {
		return this.vertices;
	}
	
	public int[] getIndices() {
		return this.indices;
	}
	
	// Get default vertices for objects
	public static class CubeMesh {
		private static float[] vertices = new float [] {
				1.0f, 1.0f, 1.0f,       // Front top right
				1.0f, -1.0f, 1.0f,      // Front bottom right
				-1.0f, 1.0f, 1.0f,      // Front top left
				-1.0f, -1.0f, 1.0f,     // Front bottom left
				
				1.0f, 1.0f, -1.0f,      // Back top right
				1.0f, -1.0f, -1.0f,     // Back bottom right
				-1.0f, 1.0f, -1.0f,     // Back top left
				-1.0f, -1.0f, -1.0f     // Back bottom left
		};
		
		private static int[] indices = new int[] {
				// Front side
				2, 3, 1,
				2, 1, 0,
				
				// Back side
				4, 5, 7,
				4, 7, 6,
				
				// Right side
				0, 1, 5,
				0, 5, 4,
						
				// Left side
				6, 7, 3,
				6, 3, 2,
				
				// Top side
				2, 0, 4,
				2, 4, 6,
				
				// Bottom side
				7, 5, 1,
				7, 1, 3		
				};
		
		public static IndexedVertexMesh getMesh() {
			return new IndexedVertexMesh(vertices, indices);
		}
		
		public static float[] getVertices() {
			return vertices;
		}
		
		public static int[] getIndices() {
			return indices;
		}
	}
	
	public static class PyramidMesh {
		private static float[] vertices = new float [] {
				1.0f, -1.0f, 1.0f,   // Front right
				-1.0f, -1.0f, 1.0f,  // Front left
				1.0f, -1.0f, -1.0f,  // Back right
				-1.0f, -1.0f, -1.0f, // Back left
				
				0.0f, 1.0f, 0.0f     // Top
		};
		
		private static int[] indices = new int[] {
				// Base
				2, 0, 1,
				2, 1, 3,
				
				// Front side
				4, 1, 0,
				
				// Right side
				4, 0, 2,
				
				// Left side
				4, 3, 1,
				
				// Back side
				4, 2, 3	
				};
		
		public static IndexedVertexMesh getMesh() {
			return new IndexedVertexMesh(vertices, indices);
		}
		
		public static float[] getVertices() {
			return vertices;
		}
		
		public static int[] getIndices() {
			return indices;
		}
	}

	@Override
	public Component deepCopy() {
		float[] copiedVertices = Arrays.copyOf(vertices, vertices.length);
		int[] copiedIndices= Arrays.copyOf(indices, indices.length);
		
		return new IndexedVertexMesh(copiedVertices, copiedIndices);
	}
}


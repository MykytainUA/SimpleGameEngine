package org.MykytaInUA.SimpleGameEngine.objects.components.mesh;

import java.nio.ByteBuffer;
import java.util.Arrays;

import org.MykytaInUA.SimpleGameEngine.objects.components.Component;

public class VertexMesh implements MeshComponent{
	
	protected float[] vertices;

	public VertexMesh(float[] vertices) {
		this.vertices = vertices;
	}
	
	@Override
	public Component deepCopy() {
		
		float[] copiedVertices = Arrays.copyOf(vertices, vertices.length);
		
		return new VertexMesh(copiedVertices);
	}

	@Override
	public int getDataPerVertexSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTotalDataSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeComponentDataToBuffer(ByteBuffer destinationBuffer) {
		// TODO Auto-generated method stub
		
	}

}

package org.mykytainua.simplegameengine.objects.components.mesh;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.mykytainua.simplegameengine.global.DataType;
import org.mykytainua.simplegameengine.global.AttributeDefinition;
import org.mykytainua.simplegameengine.objects.DataProvider;
import org.mykytainua.simplegameengine.objects.LocalDataProvider;
import org.mykytainua.simplegameengine.objects.components.Component;
import org.mykytainua.simplegameengine.objects.components.ComponentLayout;
import org.mykytainua.simplegameengine.objects.components.ComponentMetadata;
import org.mykytainua.simplegameengine.rendering.OpenGLBufferType;

/**
 * {@code IndexedVertexMesh} represents a 3D mesh that uses indexed vertices. It
 * stores vertices and indices to define a shape and provides methods for
 * handling and copying the mesh data.
 */
public class IndexedVertexMesh implements Mesh {
    
    private static final ComponentMetadata METADATA;
    private final DataProvider data;
    
    static {
        METADATA = new ComponentMetadata(new ComponentLayout(
                new AttributeDefinition(DataType.FLOAT_VEC3_BUFFER, 
                                        "vertices",  
                                        OpenGLBufferType.VBO, 
                                        false),
                new AttributeDefinition(DataType.INT_BUFFER, 
                                        "indices",  
                                        OpenGLBufferType.EBO,
                                        false),
                new AttributeDefinition(DataType.FLOAT_BUFFER, 
                                        "UVcoords", 
                                        OpenGLBufferType.VBO,
                                        false)));
    }

    protected final float[] vertices;
    protected final float[] UV;
    private final int[] indices;
    public static final String ATTRIBUTE_POINTER_NAME = "vertices";

    /**
     * Constructs an {@code IndexedVertexMesh} with the given vertex and index data.
     *
     * @param vertices the vertex data (position coordinates)
     * @param indices  the index data that defines the triangles
     */
    public IndexedVertexMesh(float[] vertices, int[] indices, float[] UV) {
        this.data = new LocalDataProvider(METADATA);
        
        int vertexBufferSize = DataType.FLOAT_BUFFER.getBufferByteSize(vertices.length);
        ByteBuffer vertexBuffer = ByteBuffer.allocate(vertexBufferSize).order(ByteOrder.nativeOrder());  
        int indexBufferSize = DataType.INT_BUFFER.getBufferByteSize(indices.length);
        ByteBuffer indexBuffer = ByteBuffer.allocate(indexBufferSize).order(ByteOrder.nativeOrder());  
        int UVSize = DataType.FLOAT_BUFFER.getBufferByteSize(UV.length);
        ByteBuffer UVBuffer = ByteBuffer.allocate(UVSize).order(ByteOrder.nativeOrder());  
        
        for (int i = 0; i < vertices.length; i++) {
            vertexBuffer.putFloat(vertices[i]);
        }
        
        for (int i = 0; i < indices.length; i++) {
            indexBuffer.putInt(indices[i]);
        }
        
        for (int i = 0; i < UV.length; i++) {
            UVBuffer.putFloat(UV[i]);
        }
        
        vertexBuffer.flip(); 
        indexBuffer.flip();
        UVBuffer.flip();
        
        this.data.setRawData(vertexBuffer, "vertices");
        this.data.setRawData(indexBuffer, "indices");
        this.data.setRawData(UVBuffer, "UVcoords");
        
        this.vertices = vertices;
        this.indices = indices;
        this.UV = UV;
    }

    @Override
    public float[] getVertices() {
        ByteBuffer vertexBuffer = this.data.getRawData("vertices");
        FloatBuffer vertexFloatBuffer = vertexBuffer.asFloatBuffer();
        float[] vertexArray = new float[vertexFloatBuffer.limit()]; 
        vertexFloatBuffer.get(vertexArray);
        return vertexArray;
    }

    public int[] getIndices() {
        ByteBuffer indexBuffer = this.data.getRawData("indices");
        IntBuffer indexIntBuffer = indexBuffer.asIntBuffer();
        int[] indexArray = new int[indexIntBuffer.limit()]; 
        indexIntBuffer.get(indexArray);
        return indexArray;
    }
    
    public void setVertices(float[] vertices) {
        
    }

    public void getIndices(int[] indices) {
        
    }

    @Override
    public Component deepCopy() {
        float[] copiedVertices = Arrays.copyOf(this.vertices, this.vertices.length);
        float[] copiedUVs = Arrays.copyOf(this.UV, this.UV.length);
        int[] copiedIndices = Arrays.copyOf(this.indices, this.indices.length);
        return new IndexedVertexMesh(copiedVertices, copiedIndices, copiedUVs);
    }
    
    @Override
    public ByteBuffer getComponentData(String name) {
        return this.data.getRawData(name).order(ByteOrder.nativeOrder());
    }

    @Override
    public ComponentMetadata getComponentMetadata() {
        // TODO Auto-generated method stub
        return METADATA;
    }

    @Override
    public String getPreprocessorDefine() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        IndexedVertexMesh mesh = (IndexedVertexMesh) obj;
        
        if(this.data.equals(mesh.data)) {
            return true;
        }
        
        return false;
    }
}

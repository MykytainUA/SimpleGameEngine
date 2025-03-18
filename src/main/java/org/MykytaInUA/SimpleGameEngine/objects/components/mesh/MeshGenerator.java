package org.mykytainua.simplegameengine.objects.components.mesh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.mykytainua.simplegameengine.objects.components.primitives.Point;
import org.mykytainua.simplegameengine.objects.components.primitives.Triangle;

public class MeshGenerator {
    
    public MeshGenerator() {
        
    }
    
    public Mesh getDefaultCubeMesh() {
        return PredefinedMeshes.CUBE_MESH;
    }
    
    public Mesh getDefaultTiangleMesh() {
        return PredefinedMeshes.PYRAMID_MESH;
    }
    
    public Mesh getCubeMesh(boolean isNormal, boolean isUV) {
        ArrayList<Triangle> triangles = new ArrayList<Triangle>(0);
        Triangle triangle;
        
        // Front face
        float[] triangleData = {1.0f, 1.0f, 1.0f,   // Position
                                0.0f,  0.0f,  1.0f, // Normal
                                0.0f, 0.0f,         // UV
                                -1.0f, 1.0f, 1.0f,  // Position
                                0.0f,  0.0f,  1.0f, // Normal
                                0.0f, 0.0f,         // UV
                                -1.0f, -1.0f, 1.0f, // Position
                                0.0f,  0.0f,  1.0f, // Normal
                                0.0f, 0.0f};        // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);
        
        triangleData = new float[] {-1.0f, -1.0f, 1.0f,     // Position
                                    0.0f,  0.0f,  1.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, -1.0f, 1.0f,    // Position
                                    0.0f,  0.0f,  1.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, 1.0f, 1.0f,   // Position
                                    0.0f,  0.0f,  1.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);
        
        // Right face
        triangleData = new float[] {1.0f, 1.0f, -1.0f,    // Position
                                    1.0f,  0.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, 1.0f, 1.0f,     // Position
                                    0.0f,  0.0f,  1.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, -1.0f, 1.0f,    // Position
                                    0.0f,  0.0f,  1.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);

        triangleData = new float[] {1.0f, -1.0f, 1.0f,    // Position
                                    1.0f,  0.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, -1.0f, -1.0f,   // Position
                                    1.0f,  0.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, 1.0f, -1.0f,    // Position
                                    1.0f,  0.0f,  0.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);
        
        // Back face
        triangleData = new float[] {-1.0f, 1.0f, -1.0f,   // Position
                                    0.0f,  0.0f, -1.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, 1.0f, -1.0f,    // Position
                                    0.0f,  0.0f, -1.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, -1.0f, -1.0f,   // Position
                                    0.0f,  0.0f, -1.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);

        triangleData = new float[] {1.0f, -1.0f, -1.0f,   // Position
                                    0.0f,  0.0f, -1.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, -1.0f, -1.0f,  // Position
                                    0.0f,  0.0f, -1.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, 1.0f, -1.0f,   // Position
                                    0.0f,  0.0f, -1.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);
        
        // Left face
        triangleData = new float[] {-1.0f, 1.0f, 1.0f,    // Position
                                    -1.0f,  0.0f,  0.0f,  // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, 1.0f, -1.0f,   // Position
                                    -1.0f,  0.0f,  0.0f,  // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, -1.0f, -1.0f,  // Position
                                    -1.0f,  0.0f,  0.0f,  // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);

        triangleData = new float[] {-1.0f, -1.0f, -1.0f,  // Position
                                    -1.0f,  0.0f,  0.0f,  // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, -1.0f, 1.0f,   // Position
                                    -1.0f,  0.0f,  0.0f,  // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, 1.0f, 1.0f,    // Position
                                    -1.0f,  0.0f,  0.0f,  // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);
        
        // Top face
        triangleData = new float[] {-1.0f, 1.0f, 1.0f,    // Position
                                    0.0f,  1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, 1.0f, 1.0f,     // Position
                                    0.0f,  1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, 1.0f, -1.0f,    // Position
                                    0.0f,  1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);

        triangleData = new float[] {1.0f, 1.0f, -1.0f,    // Position
                                    0.0f,  1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, 1.0f, -1.0f,   // Position
                                    0.0f,  1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, 1.0f, 1.0f,    // Position
                                    0.0f,  1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);
        
        // Bottom face
        triangleData = new float[] {1.0f, -1.0f, -1.0f,   // Position
                                    0.0f, -1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, -1.0f, 1.0f,    // Position
                                    0.0f, -1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, -1.0f, 1.0f,   // Position
                                    0.0f, -1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);

        triangleData = new float[] {-1.0f, -1.0f, 1.0f,   // Position
                                    0.0f, -1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    -1.0f, -1.0f, -1.0f,  // Position
                                    0.0f, -1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f,           // UV
                                    1.0f, -1.0f, -1.0f,   // Position
                                    0.0f, -1.0f,  0.0f,   // Normal
                                    0.0f, 0.0f};          // UV
        triangle = new Triangle(triangleData);
        triangles.add(triangle);
        
        ArrayList<Point> points = this.getPointsFromTriangles(triangles);        
        ArrayList<Point> uniquePoints = this.getUniquePoints(points);

        Map<Point, Integer> pointIndexMap = new HashMap<>();
        
        for (int i = 0; i < uniquePoints.size(); i++) {
            pointIndexMap.put(uniquePoints.get(i), i);
        }
        
        int[] indices = new int[points.size()];
        
        for (int j = 0; j < points.size(); j++) {
            indices[j] = pointIndexMap.get(points.get(j));
        }
        
        float[] vertices = new float[uniquePoints.size() * Triangle.POINTS_COUNT];
        
        for (int i = 0; i < uniquePoints.size(); i++) {
            Point vertexPoint = uniquePoints.get(i);
            vertices[0 + 3 * i] = vertexPoint.getPosition().x;
            vertices[1 + 3 * i] = vertexPoint.getPosition().y;
            vertices[2 + 3 * i] = vertexPoint.getPosition().z;
        }
        

       return new IndexedVertexMesh(vertices, indices, null);
    }
    
    private ArrayList<Point> getPointsFromTriangles(ArrayList<Triangle> triangles) {
        ArrayList<Point> points = new ArrayList<Point>(triangles.size() * Triangle.POINTS_COUNT);
        
        for(Triangle tempTriangle : triangles) {
            Collections.addAll(points, tempTriangle.getPointAt(0), 
                                       tempTriangle.getPointAt(1), 
                                       tempTriangle.getPointAt(2));
        }
        return points;
    }
    
    private ArrayList<Point> getUniquePoints(ArrayList<Point> points) {
        return new ArrayList<Point>(new LinkedHashSet<Point>(points));
    }
    
    private static class PredefinedMeshes {
        private static final float[] CUBE_VERTICES = {1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 
                                                      -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 
                                                      1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 
                                                      1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 
                                                      -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 
                                                      1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 
                                                      -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 
                                                      -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 
                                                      -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 
                                                      1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 
                                                      1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 
                                                      -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f};
        
        private static final int[] CUBE_INDICES = {0, 1, 2, 3, 4, 5, 
                                                   6, 7, 8, 9, 10, 11, 
                                                   12, 13, 14, 15, 16, 17, 
                                                   18, 19, 20, 21, 22, 23, 
                                                   24, 25, 26, 27, 28, 29, 
                                                   30, 31, 32, 33, 34, 35};
        
        private static final float[] CUBE_UV = {};
        
        private static final float[] PYRAMID_VERTICES = { 1.0f, -1.0f, 1.0f, // Front right
                                                         -1.0f, -1.0f, 1.0f, // Front left
                                                          1.0f, -1.0f, -1.0f, // Back right
                                                         -1.0f, -1.0f, -1.0f, // Back left
                                                          0.0f, 1.0f, 0.0f}; // Top
        
        private static final int[] PYRAMID_INDICES = { 2, 0, 1, 2, 1, 3, // Base
                                                       4, 1, 0, // Front side
                                                       4, 0, 2, // Right side
                                                       4, 3, 1, // Left side
                                                       4, 2, 3}; // Back side

        
        private static final float[] PYRAMID_UV = {};
        
        
        
        public static final IndexedVertexMesh CUBE_MESH = new IndexedVertexMesh(CUBE_VERTICES, CUBE_INDICES, CUBE_UV);
        public static final IndexedVertexMesh PYRAMID_MESH = new IndexedVertexMesh(PYRAMID_VERTICES, PYRAMID_INDICES, PYRAMID_UV);
    }
}

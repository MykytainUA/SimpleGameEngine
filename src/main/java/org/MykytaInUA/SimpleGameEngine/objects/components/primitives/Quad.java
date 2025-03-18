package org.mykytainua.simplegameengine.objects.components.primitives;

import java.util.ArrayList;

public class Quad implements Polygon {
    
    private final static int POINTS_COUNT = 4;
    
    private ArrayList<Point> points;
    
    public Quad() {
        this.points = new ArrayList<Point>(POINTS_COUNT);
    }

    @Override
    public void addPoint(Point point) {
        if(this.points.size() > POINTS_COUNT) {
            System.out.println("Warning point is being ignored, too many elements in the array list!!!");
            return;
        }
        this.points.add(point);
    }

    @Override
    public void setPointAt(int pointIndex, Point point) {
        if(this.points.size() > POINTS_COUNT) {
            System.out.println("Warning point is being ignored, trying to insert beyond poligon size!!!");
            return;
        }
        this.points.add(pointIndex, point);
        
    }

    @Override
    public Point getPointAt(int pointIndex) {
        if(this.points.size() > POINTS_COUNT) {
            System.out.println("Warning returned null, trying to get a point beyond poligon size!!!");
            return null;
        }
        return this.points.get(pointIndex);
    }

    @Override
    public int getPointsCount() {
        return POINTS_COUNT;
    }
    
    public Triangle[] convertToTriangles() {
        Triangle[] triangles = new Triangle[2];
        
        triangles[0] = new Triangle();
        triangles[0].addPoint(points.get(1));
        triangles[0].addPoint(points.get(0));
        triangles[0].addPoint(points.get(2));
        
        triangles[1] = new Triangle();
        triangles[1].addPoint(points.get(1));
        triangles[1].addPoint(points.get(2));
        triangles[1].addPoint(points.get(3));
        
        return triangles;
    }
}

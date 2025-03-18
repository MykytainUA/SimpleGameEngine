package org.mykytainua.simplegameengine.objects.components.primitives;

import java.util.ArrayList;
//Immutable
public class Triangle implements Polygon {
    
    public final static int POINTS_COUNT = 3;
    
    private ArrayList<Point> points;
    
    public Triangle() {
        this.points = new ArrayList<Point>(POINTS_COUNT);
    }
    
    public Triangle(Point point1, Point point2, Point point3) {
        this.points = new ArrayList<Point>(POINTS_COUNT);
        
        this.points.add(point1);
        this.points.add(point2);
        this.points.add(point3);
    }
    
    /**
     * Send data in this order: posX, posY, posZ,
     *                          norX, norY, norZ,
     *                          u, v/
     * @param data must be array of 8 floats
     */
    public Triangle(float[] data) {
        
        this.points = new ArrayList<Point>(POINTS_COUNT);
        
        int dataPerPoint = 8;
        
        for (int i = 0; i < 3; i++) {
            Point point = new Point(data[0 + dataPerPoint * i],
                                    data[1 + dataPerPoint * i],
                                    data[2 + dataPerPoint * i],
                                    data[3 + dataPerPoint * i],
                                    data[4 + dataPerPoint * i],
                                    data[5 + dataPerPoint * i],
                                    data[6 + dataPerPoint * i],
                                    data[7 + dataPerPoint * i]);
            this.points.add(point);
        }
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
    
    public ArrayList<Point> getPoints() {
        return this.points;
    }

    @Override
    public int getPointsCount() {
        return POINTS_COUNT;
    }   
    
    public boolean equals(Triangle triangle) {
        for(int i = 0; i < triangle.getPoints().size(); i++) {
            Point firstPoint = triangle.getPoints().get(i);
            Point secondPoint = this.getPoints().get(i);
            
            if(!firstPoint.equals(secondPoint)) {
                return false;
            }
        }
        return true;
    }
    
    public String toString() {
        String data = "Triangle (polygon) data:";
        for(Point point : this.points) {
            data += point.toString().replace("\n", "\n\t");
        }
        return data;
    }
}

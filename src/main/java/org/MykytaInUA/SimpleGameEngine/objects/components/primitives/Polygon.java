package org.mykytainua.simplegameengine.objects.components.primitives;

public interface Polygon {
    
    public void addPoint(Point point);
    
    public void setPointAt(int pointIndex, Point point);
    
    public Point getPointAt(int pointIndex);
    
    public int getPointsCount();
}

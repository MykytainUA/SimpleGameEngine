package org.mykytainua.simplegameengine.objects.components.primitives;

import java.util.Objects;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Point {
    private Vector3f position;
    private Vector3f normals;
    private Vector2f uv;
    
    public Point() {
        
    }
    
    public Point(float posX, float posY, float posZ) {
        this.position = new Vector3f(posX, posY, posZ);
    }
    
    public Point(float posX, float posY, float posZ, 
                 float norX, float norY, float norZ) {
                 this(posX, posY, posZ);
        this.normals = new Vector3f(norX, norY, norZ);
    }
    
    public Point(float posX, float posY, float posZ, 
                 float norX, float norY, float norZ, 
                 float u, float v) {
        this(posX, posY, posZ, 
             norX, norY, norZ);
        this.uv = new Vector2f(u, v);
    }
    
    public Point(Vector3f position, Vector3f normals, Vector2f uv) {
        this.position = position;
        this.normals = position;
        this.uv = uv;
    }
    
    public Point(Point point) {
        if(point.hasPosition()) {
            this.position = new Vector3f(point.getPosition());
        }
        if(point.hasNormals()) {
            this.normals = new Vector3f(point.getNormals());
        }
        if(point.hasUV()) {
            this.uv = new Vector2f(point.getUV());
        }
    }
    
    public boolean hasPosition() {
        if(position != null) {
            return true;
        }
        return false;
    }
    
    public boolean hasNormals() {
        if(normals != null) {
            return true;
        }
        return false;
    }
    
    public boolean hasUV() {
        if(uv != null) {
            return true;
        }
        return false;
    }
    
    public void setPosition(Vector3f position) {
        if(!isNormalized(position)) {
            System.out.println("WARNING:Point position is not normalized!!!");
        }
        this.position = position;
    }
    
    public void setNormals(Vector3f normals) {
        if(!isNormalized(normals)) {
            System.out.println("WARNING:Point normals are not normalized!!!");
        }
        this.normals = normals;
    }
    
    public void setUV(Vector2f uv) {
        if(!isNormalized(uv)) {
            System.out.println("WARNING:Point UV coordinates are not normalized!!!");
        }
        this.uv = uv;
    }
    
    public Vector3f getPosition() {
        return this.position;
    }
    
    public Vector3f getNormals() {
        return this.normals;
    }
    
    public Vector2f getUV() {
        return this.uv;
    }
    
    public boolean equals(Point point) {
        
        if(!this.getPosition().equals(point.getPosition())) {
            return false;
        } else if ((!this.getNormals().equals(point.getNormals()))) {
            return false;
        } else if ((!this.getUV().equals(point.getUV()))) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(position, normals, uv);
    } 
    
    public String toString() {
        String data = "\nPoint data:";
        data += "\n" + this.getPosition().toString();
        data += "\n" + this.getNormals().toString();
        data += "\n" + this.getUV().toString();
        return data;
    }
    
    
    @SuppressWarnings("unused")
    private static boolean isNormalized(Vector4f data) {
        for (int i = 0; i < data.length(); i++) {
            if(data.get(i) > 1 || data.get(i) < -1) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isNormalized(Vector3f data) {
        for (int i = 0; i < data.length(); i++) {
            if(data.get(i) > 1 || data.get(i) < -1) {
                return false;
            }
        }
        return true;
    }
    
    private static boolean isNormalized(Vector2f data) {
        for (int i = 0; i < data.length(); i++) {
            if(data.get(i) > 1 || data.get(i) < -1) {
                return false;
            }
        }
        return true;
    }
}

package com.thf.physics;

/**
 * Created by marko on 4/15/2017.
 */

public class Vector2 {

    int x;
    int y;

    static Vector2 zero = new Vector2(0, 0);

    public Vector2(){
        x = 0;
        y = 0;
    }

    public Vector2(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void add(Vector2 vector2){
        x += vector2.x;
        y += vector2.y;
    }

    public void subtract(Vector2 vector2){
        x -= vector2.x;
        y -= vector2.y;
    }

    public void multiply(int scalar){
        x = x*scalar;
        y = y*scalar;
    }

    public void divide(int scalar){
        x = x/scalar;
        y = y/scalar;
    }

    public int length(){
        return (int)Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
    }

    public static Vector2 add(Vector2 v1, Vector2 v2){
        return new Vector2(v1.x + v2.x, v1.y + v2.y);
    }

    public static Vector2 subtract(Vector2 v1, Vector2 v2){
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }

    public static Vector2 multiply(Vector2 v, int s){
        return new Vector2(v.x*s, v.y*s);
    }

    public static Vector2 divide(Vector2 v, int s){
        return new Vector2(v.x/s, v.y/s);
    }

    public static Vector2 invert(Vector2 vector2){
        return Vector2.multiply(vector2, -1);
    }

    public static int dotProduct(Vector2 v1, Vector2 v2){
        return (v1.x*v2.x) + (v1.y*v2.y);
    }

    public static int cosAngle(Vector2 v1, Vector2 v2){
        return dotProduct(v1, v2)/(v1.length()*v2.length());
    }
}

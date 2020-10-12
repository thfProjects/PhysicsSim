package com.thf.physics;

/**
 * Created by marko on 4/19/2017.
 */

public class CollisionCircle {

    int centerX;
    int centerY;

    int radius;

    public CollisionCircle(){

    }

    public CollisionCircle(int x, int y, int r){
        centerX = x;
        centerY = y;
        radius = r;
    }

    public Vector2 getCenter(){
        return new Vector2(centerX, centerY);
    }

    public void set(int x, int y, int r){
        centerX = x;
        centerY = y;
        radius = r;
    }

    public boolean intersects(CollisionCircle c){
        if(Vector2.subtract(this.getCenter(), c.getCenter()).length() < this.radius + c.radius){
            return true;
        }else return false;
    }
}

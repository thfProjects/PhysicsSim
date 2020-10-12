package com.thf.physics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by marko on 4/15/2017.
 */

public class PhysicsObject{

    private Vector2 position;

    private Vector2 size;

    private Bitmap sprite;

    private Rect bounds;

    private Vector2 velocity;
    private int mass;
    private Vector2 force;

    private CollisionCircle collCirle;

    private int drawingFrequency;

    public PhysicsObject() {
        defaultInit();
    }

    public PhysicsObject(int x, int y, int w, int h){
        defaultInit();

        setPosition(x, y);
        setSize(w, h);
    }

    private void defaultInit(){
        position = new Vector2(0, 0);
        size = new Vector2(0, 0);
        bounds = new Rect(position.x, position.y, position.x + size.x, position.y + size.y);
        force = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        collCirle = new CollisionCircle(position.x + size.x/2, position.y + size.y/2, Math.max(size.x, size.y)/2);

        drawingFrequency = 1000/PhysicsThread.SLEEP_TIME;
    }

    public void draw(Canvas canvas){
        updateBoundsandCollCircle();
        canvas.drawBitmap(sprite, null, bounds, null);
    }

    public void setSprite(Bitmap bitmap){
        sprite = bitmap;
    }

    public Rect getBounds(){
        return bounds;
    }

    public void setPosition(int x, int y){
        position.x = x;
        position.y = y;

        updateBoundsandCollCircle();
    }

    public Vector2 getPosition(){
        return position;
    }

    public void setSize(int x, int y){
        size.x = x;
        size.y = y;

        updateBoundsandCollCircle();
    }

    public Vector2 getSize(){
        return size;
    }

    public void setVelocity(Vector2 velocity){
        this.velocity = velocity;
    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public void setMass(int mass){
        this.mass = mass;
    }

    public int getMass(){
        return mass;
    }

    public void addForce(Vector2 force){
        this.force.add(force);
    }

    public void removeForce(Vector2 force){
        this.force.subtract(force);
    }

    public Vector2 getForce(){
        return force;
    }

    public void translate(int x, int y){
        position.x += x;
        position.y += y;

        updateBoundsandCollCircle();
    }

    public void translate(Vector2 vector2){
        position.x += vector2.x;
        position.y += vector2.y;

        updateBoundsandCollCircle();
    }

    public void move(){
        translate(velocity.x/drawingFrequency, velocity.y/drawingFrequency);
    }

    public CollisionCircle getCollisionCircle(){
        return collCirle;
    }

    public boolean isColliding(PhysicsObject po){
        return this.getCollisionCircle().intersects(po.getCollisionCircle());
    }

    public void onCollide(PhysicsObject po){
        //collision
    }

    public void onUpdate(){
        //every update
    }

    private void updateBoundsandCollCircle(){
        bounds.set(position.x, position.y, position.x + size.x, position.y + size.y);
        collCirle.set(position.x + size.x/2, position.y + size.y/2, Math.max(size.x, size.y)/2);
    }

    public void update(){
        updateBoundsandCollCircle();
    }

}

package com.thf.physics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by marko on 4/16/2017.
 */

public class PhysicsView extends SurfaceView implements SurfaceHolder.Callback {

    private Context context;
    private PhysicsThread thread;

    private ArrayList<PhysicsObject> scene;

    int canvaswidth = 0;
    int canvasheight = 0;

    //za petlje
    int i;
    int j;
    PhysicsObject object;
    Vector2 velocity;

    Vector2 newVelocity1 = new Vector2();
    Vector2 newVelocity2 = new Vector2();

    Vector2 o1o2 = new Vector2();
    Vector2 collisionVector = new Vector2();

    public PhysicsView(Context context, ArrayList<PhysicsObject> scene) {
        super(context);

        this.context = context;

        this.scene = scene;

        getHolder().addCallback(this);
    }

    public PhysicsView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        getHolder().addCallback(this);
    }


    public void start(){
        if (thread == null)
        {
            thread = new PhysicsThread(this);
            thread.startThread();
        }
    }

    public void stop(){
        if (thread != null)
        {
            thread.stopThread();

            // Waiting for the thread to die by calling thread.join,
            // repeatedly if necessary
            boolean retry = true;
            while (retry)
            {
                try
                {
                    thread.join();
                    retry = false;
                }
                catch (InterruptedException e)
                {
                }
            }
            thread = null;
        }
    }

    public void update(){ //once every 0.02s
        for(i=0;i<scene.size();i++){
            object = scene.get(i);

            if(object.getBounds().bottom == canvasheight && object.getBounds().left >= 0 && object.getBounds().right <= canvaswidth){ //Na zemlji
                velocity = object.getVelocity();
                velocity.add(Vector2.divide(object.getForce(), object.getMass()));
                velocity.y = 0;
                object.setVelocity(velocity);
                object.move();
            }else if(object.getBounds().bottom > canvasheight){
                object.getVelocity().y = 0;
                object.getPosition().y = canvasheight - object.getSize().y;
            }else if(object.getBounds().right > canvaswidth){
                object.getVelocity().x = 0;
                object.getPosition().x = canvaswidth - object.getSize().x;
            }else if(object.getBounds().left < 0){
                object.getVelocity().x = 0;
                object.getPosition().x = 0;
            }else if(object.getBounds().top < 0){
                object.getVelocity().y = 0;
                object.getPosition().y = 0;
            }else  {
                velocity = object.getVelocity();
                velocity.add(Vector2.divide(object.getForce(), object.getMass()));
                object.setVelocity(velocity);
                object.move();
            }

            object.update();
            object.onUpdate();
        }

        for(i=0;i<scene.size();i++) {
            for (j = 0; j < scene.size(); j++) {
                if (i != j && scene.get(i).isColliding(scene.get(j))) {
                    collision(scene.get(i), scene.get(j));
                }
            }
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        if(canvaswidth == 0){
            canvaswidth = canvas.getWidth();
        }
        if(canvasheight == 0){
            canvasheight = canvas.getHeight();
        }

        canvas.drawColor(Color.WHITE);
        for(i = 0;i<scene.size();i++){
            scene.get(i).draw(canvas);
        }
    }

    private void collision(PhysicsObject o1, PhysicsObject o2){


        o1.onCollide(o2);
        o2.onCollide(o1);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stop();
    }

    public PhysicsObject getObjectAtPosition(int x, int y){
        for(int i = scene.size()-1; i>=0;i--){
            if(scene.get(i).getBounds().contains(x, y)){
                return scene.get(i);
            }
        }
        return null;
    }

    public void setScene(ArrayList<PhysicsObject> scene){
        this.scene = scene;
    }

    public Context getPassedContext(){
        return context;
    }
}

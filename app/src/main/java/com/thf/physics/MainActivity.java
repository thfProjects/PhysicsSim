package com.thf.physics;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //UNIT px/s

    static final int GRAVITY_STRENGTH = 30;

    PhysicsView physicsView;

    private ArrayList<PhysicsObject> scene;

    Bitmap objBitmap;

    Vector2 gravity;

    Button addButton;

    PhysicsObject draggedObject;

    GestureDetector physicsGestureDetector;

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PhysicsObject object = new PhysicsObject(physicsView.getWidth()/2 - 25, 200, 50, 50);
            object.setSprite(objBitmap);
            object.addForce(Vector2.multiply(gravity, 2));
            object.setMass(2);

            scene.add(object);
        }
    };

    View.OnTouchListener physicsViewListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(physicsGestureDetector.onTouchEvent(event)){
                return true;
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                if(draggedObject != null){
                    draggedObject.addForce(Vector2.multiply(gravity, draggedObject.getMass()));
                    draggedObject = null;
                }
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scene = new ArrayList<>();

        physicsView = (PhysicsView) findViewById(R.id.physicsview);
        physicsView.setScene(scene);
        physicsView.setOnTouchListener(physicsViewListener);

        addButton = (Button)findViewById(R.id.addButton);
        addButton.setOnClickListener(onClickListener);

        physicsGestureDetector = new GestureDetector(this, new physicsGestureListener());

        gravity = new Vector2(0, GRAVITY_STRENGTH);

        objBitmap = drawableToBitmap(ContextCompat.getDrawable(this, R.drawable.ball));

        draggedObject = null;
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    class physicsGestureListener extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDown(MotionEvent event){
            super.onDown(event);
            if(physicsView.getObjectAtPosition((int)event.getX(), (int)event.getY()) != null){
                draggedObject = physicsView.getObjectAtPosition((int)event.getX(), (int)event.getY());
                draggedObject.removeForce(Vector2.multiply(gravity, draggedObject.getMass()));
                draggedObject.setVelocity(new Vector2(0,0));
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY){
            if(draggedObject != null){
                draggedObject.setPosition((int)e2.getX() - draggedObject.getSize().x/2, (int)e2.getY() - draggedObject.getSize().y/2);
            }
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            if(draggedObject != null){
                draggedObject.setVelocity(new Vector2((int)velocityX, (int)velocityY));
                draggedObject.addForce(Vector2.multiply(gravity, draggedObject.getMass()));
                draggedObject = null;
            }
            return true;
        }
    }

    private void toast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}

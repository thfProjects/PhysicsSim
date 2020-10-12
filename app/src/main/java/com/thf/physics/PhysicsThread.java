package com.thf.physics;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;

/**
 * Created by marko on 4/16/2017.
 */

public class PhysicsThread extends Thread {

    public final static int SLEEP_TIME = 20; //1000/20 = 50 times in a second

    private boolean running = false;
    private PhysicsView canvas = null;
    private SurfaceHolder surfaceHolder = null;

    public PhysicsThread(PhysicsView canvas){
        super();
        this.canvas = canvas;
        this.surfaceHolder = canvas.getHolder();
    }

    public void startThread()
    {
        running = true;
        super.start();
    }

    public void stopThread()
    {
        running = false;
    }

    @Override
    public void run(){
        Canvas c = null;
        while (running)
        {
            c = null;
            try
            {
                c = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    if (c != null)
                    {
                        canvas.update();
                        canvas.onDraw(c);
                    }
                }
                sleep(SLEEP_TIME);
            }
            catch(InterruptedException ie)
            {
            }
            finally
            {
                // do this in a finally so that if an exception is thrown
                // we don't leave the Surface in an inconsistent state
                if (c != null)
                {
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }

}

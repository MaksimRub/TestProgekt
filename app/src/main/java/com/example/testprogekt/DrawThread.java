package com.example.testprogekt;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class DrawThread extends Thread {
    MySurface mySurface;
    SurfaceHolder surfaceHolder;
    boolean isRun = false;
    long nowTime, prevTime, ellapsedTime;

    public DrawThread(MySurface mySurface, SurfaceHolder surfaceHolder) {
        this.mySurface = mySurface;
        this.surfaceHolder = surfaceHolder;
        prevTime = System.currentTimeMillis();

        Log.d("COORDS", "DRAW started!");
    }

    public void setRun(boolean run) {
        isRun = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (isRun){
            if (!surfaceHolder.getSurface().isValid()){
                continue;
            }
            canvas = null;
            nowTime = System.currentTimeMillis();
            ellapsedTime = nowTime - prevTime;
            if (ellapsedTime > 8){
                prevTime = nowTime;
                canvas = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder){
                    mySurface.draw(canvas);
                }
                if (canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}

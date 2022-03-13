package com.example.testprogekt;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    //Переменные для рисования
    float x, y; //текущее положение картинки
    float tx, ty; //точки касания
    float dx, dy; //смещение координат
    float koeff; //коэффициент скорости

    Path path;

    int rotation_degrees=0;

    int x1,y1;

    Rect rect;

    //переменные для картинки
    Bitmap image,image_type;
    Resources res;
    Paint paint;

    //объект потока
    DrawThread drawThread;



    public MySurface(Context context) {
        super(context);

        getHolder().addCallback(this);
        x = 100;
        y = 100;
        x1 = 500;
        y1 = 0;
        koeff = 2;
        res = getResources();
        image = BitmapFactory.decodeResource(res, R.drawable.car);
        image_type=BitmapFactory.decodeResource(res, R.drawable.img);
        paint = new Paint();
        rect=new Rect(getWidth()-getWidth()/4,
                getHeight()-getHeight()/8,getWidth()-1,getHeight()-1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            tx = event.getX();
            ty = event.getY();
        }
        if(event.getAction()==MotionEvent.ACTION_UP){
            tx=0;
            ty=0;
        }



        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        paint.setColor(Color.RED);
        Rect r=new Rect(getWidth()-getWidth()/4,
                getHeight()-getHeight()/8,getWidth()-1,getHeight()-1);
        canvas.drawRect(r,paint);

        if(tx>getWidth()-getWidth()/4&&tx<getWidth()-1&&ty>getHeight()-getHeight()/8&&ty<getHeight()-1){
            rotation_degrees+=2;
        }
        x += image.getWidth() / 2;
        y += image.getHeight() / 2;
        canvas.rotate(rotation_degrees, x , y );
        canvas.drawBitmap(image, x, y, paint);
        //canvas.drawBitmap(image_type,x,0,paint);
        canvas.save();
        canvas.restore();
        calculate();
        
        x += dx;
        y += dy;





        //расчёт смещения
       /* if(tx>getWidth()-getWidth()/4&&tx<getWidth()-1&&ty>getHeight()-getHeight()/8&&ty<getHeight()-1){
            if(y1==0&&x1!=getWidth()+1){
                x1+=10;
            }
            if(x1==getWidth()&&y1<getHeight()+1){
                y1+=10;
            }
            if(y1==getHeight()&&x1!=0){
                x1-=10;
            }
            if(x==0&&y!=0){
                y1-=10;
            }
            calculate();
            x+=dx;
            y+=dy;


        }*/
        //calculate();
        //x += dx;
        //y += dy;

    }

    private void calculate(){
        double g = Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
        dx = (float) (koeff*(x1-x)/g);
        dy = (float) (koeff*(y1-y)/g);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(this, getHolder());
        drawThread.setRun(true);
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean stop = true;
        drawThread.setRun(false);
        while(stop) {
            try {
                drawThread.join();
                stop = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


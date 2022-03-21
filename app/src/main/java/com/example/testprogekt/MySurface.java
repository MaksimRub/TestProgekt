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
import android.os.Build;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.LinkedList;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    //Переменные для рисования
    float x, y; //текущее положение картинки
    double tx, ty; //точки касания
    float dx, dy; //смещение координат
    float koeff; //коэффициент скорости

    Path path;

   double rotation_degrees=0;

    double x1,y1;

    Rect rect;

    //переменные для картинки
    Bitmap image,image_real_road,image_need_road;
    Resources res;
    Paint paint;

    //объект потока
    DrawThread drawThread;








    public MySurface(Context context) {
        super(context);

        getHolder().addCallback(this);

        x = 400;
        y = 400;

        koeff = 2;
        res = getResources();
        image = BitmapFactory.decodeResource(res, R.drawable.car);
        image_real_road=BitmapFactory.decodeResource(res, R.drawable.firstroad);
        paint = new Paint();
        rect=new Rect(getWidth()-getWidth()/4,
                getHeight()-getHeight()/8,getWidth()-1,getHeight()-1);
        path=new Path();
        x1=(x+image.getWidth() / 2)+400000*Math.cos(Math.toRadians(rotation_degrees-90));
        y1=(y)+400000*Math.sin(Math.toRadians(rotation_degrees-90));

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
        int wi=canvas.getWidth();
        int he=canvas.getHeight();
        image_need_road=Bitmap.createScaledBitmap(image_real_road,wi,he,false);

        canvas.drawBitmap(image_need_road,0,0,paint);
        HashMap<String,Integer> coord=new HashMap<>();



        paint.setColor(Color.RED);
        canvas.drawRect(getWidth()-getWidth()/4,
                getHeight()-getHeight()/8,getWidth()-1,getHeight()-1,paint);


        double radian=Math.toRadians(rotation_degrees+270);
        if(tx>getWidth()-getWidth()/4&&tx<getWidth()-1&&ty>getHeight()-getHeight()/8&&ty<getHeight()-1){
            rotation_degrees+=2;

            x1=(x+image.getWidth() / 2)+400000*Math.cos(radian);
            y1=(y)+400000*Math.sin(radian);

        }
        //canvas.drawCircle((float) x1,(float) y1,10,paint);








        //paint.setColor(Color.RED);
        //path.moveTo(0,50);
        //path.lineTo(1000,50);
       //paint.setStyle(Paint.Style.STROKE);
        //canvas.drawPath(path,paint);
        //canvas.drawCircle((float) x1,(float)y1,20,paint);
        //Rect r=new Rect((int)x,(int) y,(int) x+image.getHeight(),(int)y+image.getWidth());
        Rect r=new Rect((int) x,(int) y,(int) x+image.getHeight(),(int) y+image.getWidth());


        calculate();
        x += dx;
        y += dy;
        canvas.rotate((float) rotation_degrees, x+image.getWidth() / 2 , y+image.getHeight()/2 );
        canvas.drawBitmap(image, x, y, paint);
        canvas.save();
        canvas.restore();
        for (int i = (int) x; i <= x+image.getHeight(); i++) {
            for (int j = (int) y; j <= y+image.getWidth(); j++) {
                paint.setColor(Color.YELLOW);
                //canvas.drawPoint((float) i,(float) j,paint);
                int a = image_need_road.getPixel((int)i,(int) j);
                if (a == -8248799) {
                    x=400;
                    y=400;
                    break;
                }
            }
        }



        //float middle_x=x+image.getWidth()/2;
        //float middle_y=y+image.getHeight()/2;

        //paint.setColor(Color.RED);
        //canvas.drawRect(r,paint);
        //canvas.rotate((float) rotation_degrees, 200+image.getWidth() / 2 , 200+image.getHeight()/2 );

        //canvas.save();
        //canvas.restore();





        /*double radius=Math.sqrt((206-middle_x)*(206-middle_x)+(205-middle_y)*(205-middle_y));
        paint.setColor(Color.BLUE);
        canvas.drawCircle(200+image.getWidth()/2,200+image.getHeight()/2,(float) radius,paint);*/

        /*double i1=((200)+radius*Math.cos(radian));
        double j1=((200+image.getHeight()/2)+radius*Math.sin(radian));
        paint.setColor(Color.BLUE);
        canvas.drawCircle((float) i1,(float)j1,10,paint);*/
    }

    private void calculate(){
        double g = Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
        dx = (float) (koeff*(x1-x)/g);
        dy = (float) (koeff*(y1-y)/g);
    }
    /*private double[] pointsOnCircle(double begin_x,double begin_y){
        double[] a=new double[360];
        int i=0;
        for (int j = 0; j < 360; j++) {
            double x_point=(begin_x)+400*Math.cos(j);
            double y_point=(begin_y)+400*Math.sin(j);
        }
        return a;
    }*/

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


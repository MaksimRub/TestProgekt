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

    double deviation_degrees;

    double radius_move;


    double deviation_radian_wall;
    double deviation_degrees_wall;

    float middle_x;
    float middle_y;






    public MySurface(Context context) {
        super(context);

        getHolder().addCallback(this);



        x = 400;
        y = 400;

        koeff = 4;
        res = getResources();
        image = BitmapFactory.decodeResource(res, R.drawable.car_real);
        image_real_road=BitmapFactory.decodeResource(res, R.drawable.firstroad);
        paint = new Paint();
        rect=new Rect(getWidth()-getWidth()/4,
                getHeight()-getHeight()/8,getWidth()-1,getHeight()-1);
        path=new Path();
        double deviation_radian=Math.atan((double) (image.getWidth()/2)/400);
        deviation_degrees=Math.toDegrees(deviation_radian);

        x1=(x+image.getWidth() / 2)+400*Math.cos(Math.toRadians(rotation_degrees+270));
        y1=(y+image.getHeight()/2)+400*Math.sin(Math.toRadians(rotation_degrees+270));
        radius_move=Math.sqrt(400*400+(image.getWidth()/2)*(image.getWidth()/2));

        deviation_radian_wall=Math.atan((double) (image.getWidth()/2)/(double)(image.getHeight()/2));
        deviation_degrees_wall=Math.toDegrees(deviation_radian_wall);



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
        paint.setStyle(Paint.Style.STROKE);
        image_need_road=Bitmap.createScaledBitmap(image_real_road,wi,he,false);

        canvas.drawBitmap(image_need_road,0,0,paint);




        paint.setColor(Color.RED);
        canvas.drawRect(getWidth()-getWidth()/4,
                getHeight()-getHeight()/8,getWidth()-1,getHeight()-1,paint);
        double radian;
        radian=Math.toRadians(rotation_degrees+270);
        if(tx>getWidth()-getWidth()/4&&tx<getWidth()-1&&ty>getHeight()-getHeight()/8&&ty<getHeight()-1){
            rotation_degrees+=2;

            x1=(x+image.getWidth() / 2)+radius_move*Math.cos(radian);
            y1=(y+image.getHeight()/2)+radius_move*Math.sin(radian);

        }
//        canvas.drawCircle((float) x+image.getWidth() / 2,(float)y+image.getHeight()/2,(float) radius_move,paint);
//        canvas.drawCircle((float) x1,(float) y1,10,paint);
//        path.moveTo((float) x+image.getWidth() / 2,(float)y+image.getHeight()/2);
//        path.lineTo((float) x1,(float)y1);
//        paint.setStyle(Paint.Style.STROKE);
//        canvas.drawPath(path,paint);








        //
        //Rect r=new Rect((int)x,(int) y,(int) x+image.getHeight(),(int)y+image.getWidth());
//        Rect r=new Rect((int) x,(int) y,(int) x+image.getHeight(),(int) y+image.getWidth());
//        paint.setColor(Color.RED);
//        canvas.drawRect(r,paint);

        double i1;
        double j1;
        int a;
        middle_x=x+image.getWidth()/2;
        middle_y=y+image.getHeight()/2;
        double radius=Math.sqrt((x-middle_x)*(x-middle_x)+(y-middle_y)*(y-middle_y));

        radian=Math.toRadians(rotation_degrees+270-deviation_degrees_wall);
        canvas.drawCircle(middle_x,middle_y,(float) radius,paint);
        i1=middle_x+radius*Math.cos(radian);
        j1=middle_y+radius*Math.sin(radian);
        paint.setColor(Color.YELLOW);canvas.drawPoint((float) i1,(float)j1,paint);
         a= image_need_road.getPixel((int)i1,(int) j1);
        paint.setColor(Color.RED);
        if (a == -8248799) {
            x=400;
            y=400;
        }
        radian=Math.toRadians(rotation_degrees+270+deviation_degrees_wall);
        i1=middle_x+radius*Math.cos(radian);
        j1=middle_y+radius*Math.sin(radian);
        paint.setColor(Color.YELLOW);
        canvas.drawPoint((float) i1,(float)j1,paint);
        a = image_need_road.getPixel((int)i1,(int) j1);
        paint.setColor(Color.RED);
        if (a == -8248799) {
            x=400;
            y=400;
        }
        radian=Math.toRadians(rotation_degrees+90+deviation_degrees_wall);
        i1=middle_x+radius*Math.cos(radian);
        j1=middle_y+radius*Math.sin(radian);
        paint.setColor(Color.YELLOW);
        canvas.drawPoint((float) i1,(float)j1,paint);
        a = image_need_road.getPixel((int)i1,(int) j1);
        paint.setColor(Color.RED);
        if (a == -8248799) {
            x=400;
            y=400;
        }
        radian=Math.toRadians(rotation_degrees+90-deviation_degrees_wall);
        i1=middle_x+radius*Math.cos(radian);
        j1=middle_y+radius*Math.sin(radian);
        paint.setColor(Color.YELLOW);
        canvas.drawPoint((float) i1,(float)j1,paint);
        a = image_need_road.getPixel((int)i1,(int) j1);
        paint.setColor(Color.RED);
        if (a == -8248799) {
            x=400;
            y=400;
        }
        // canvas.drawCircle((float) x1,(float)y1,20,paint);



        calculate();
        x += dx;
        y += dy;
        canvas.rotate((float) rotation_degrees, x+image.getWidth() / 2 , y+image.getHeight()/2 );
        canvas.drawBitmap(image, x, y, paint);
        canvas.save();
        canvas.restore();






        //
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
        double g = Math.sqrt((x1-middle_x)*(x1-middle_x)+(y1-middle_y)*(y1-middle_y));
        dx = (float) (koeff*(x1-middle_x)/g);
        dy = (float) (koeff*(y1-middle_y)/g);
    }
    private double[] pointsOnCircle(double begin_x,double begin_y){
        double[] a=new double[360];
        int i=0;
        for (int j = 0; j < 360; j++) {
            double x_point=(begin_x)+400*Math.cos(j);
            double y_point=(begin_y)+400*Math.sin(j);
        }
        return a;
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


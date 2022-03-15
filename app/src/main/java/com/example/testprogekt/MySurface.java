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
    double tx, ty; //точки касания
    float dx, dy; //смещение координат
    float koeff; //коэффициент скорости

    Path path;

   double rotation_degrees=0;

    double x1,y1;

    Rect rect;

    //переменные для картинки
    Bitmap image,image_type;
    Resources res;
    Paint paint;

    //объект потока
    DrawThread drawThread;
    int i;





    public MySurface(Context context) {
        super(context);

        getHolder().addCallback(this);
        i=0;
        x = 400;
        y = 400;
        x1=400;
        y1=-1000;
        koeff = 2;
        res = getResources();
        image = BitmapFactory.decodeResource(res, R.drawable.car);
        image_type=BitmapFactory.decodeResource(res, R.drawable.img);
        paint = new Paint();
        rect=new Rect(getWidth()-getWidth()/4,
                getHeight()-getHeight()/8,getWidth()-1,getHeight()-1);
        path=new Path();
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
            rotation_degrees+=1;
            i++;
        }
        //x += image.getWidth() / 2;
        //y += image.getHeight() / 2;

        //
        paint.setColor(Color.BLUE);
        canvas.drawCircle(x+image.getWidth() / 2,y+image.getHeight() / 2,400,paint);
        double cos=Math.cos(rotation_degrees);
        double sin=Math.sin(rotation_degrees);

        x1=(x+image.getWidth() / 2)+400*cos;
        y1=(y+image.getHeight() / 2)+400*sin;
        //double[] points=pointsOnCircle(x+image.getWidth() / 2,y+image.getHeight() / 2);

        paint.setColor(Color.RED);
        path.moveTo(x+image.getWidth() / 2,y+image.getHeight() / 2);
        path.lineTo((float) x1,(float) y1);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path,paint);
        canvas.drawCircle((float) x1,(float)y1,20,paint);
        canvas.rotate((float) rotation_degrees, x+image.getWidth() / 2 , y+image.getHeight() / 2 );
        canvas.drawBitmap(image, x, y, paint);
        canvas.save();
        canvas.restore();
        

        calculate();

        //x += dx;
        //y += dy;



        //calculate();
        //int x2=canvas.getClipBounds().describeContents();
       // int y2=canvas.getClipBounds().centerY();

        //x += dx;
        //y += dy;





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


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
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.LinkedList;

public class MySurface extends SurfaceView implements SurfaceHolder.Callback {
    //Переменные для рисования
    float x, y; //текущее положение картинки
    float dx, dy; //смещение координат
    float koeff; //коэффициент скорости

    double rotation_degrees=0; //угол поворота

    boolean button_state_toRight=false; //состояние кнопки "поворот направо"
    boolean button_state_toLeft=false; //состояние кнопки "поворот налево"

    int numberOfPicture; //номер выбранной трассы

    boolean picture_road_state=false; //создана-ли трасса нужных размеров
    boolean picture_car_state=false; //создана-ли машинка нужных размеров

    double x1,y1; //точки в которые необходимо прийти

    //переменные для картинки
    Bitmap image_car,image_real_car,image_road1,image_real_road,image_road2,image_road3;
    Resources res;
    Paint paint;


    DrawThread drawThread; //объект потока

    double radius_move; //радиус по которому движется точки(в которую нужно прийти)

    double deviation_degrees_wall; //угол для расчета координат углов треугольника

    float middle_x; //середина картинки по x
    float middle_y; //середина картинки по y


    public MySurface(Context context, AttributeSet attrs) {
        super(context, attrs);

        getHolder().addCallback(this);

        x = 400;
        y = 410;

        koeff = 4;

        res = getResources();

        image_car=BitmapFactory.decodeResource(res,R.drawable.norm_car);
        image_road1=BitmapFactory.decodeResource(res, R.drawable.firstroad);
        image_road2=BitmapFactory.decodeResource(res,R.drawable.norm_road);
        paint = new Paint();




    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if(numberOfPicture==1 && !picture_road_state) {
            int wi = canvas.getWidth();
            int he = canvas.getHeight();
            image_real_road = Bitmap.createScaledBitmap(image_road1, wi, he, false);
            picture_road_state=true;

        }
        if(numberOfPicture==2 && !picture_road_state) {
            int wi = canvas.getWidth();
            int he = canvas.getHeight();
            image_real_road= Bitmap.createScaledBitmap(image_road2, wi, he, false);
            picture_road_state=true;


        }
        if(numberOfPicture==3 && !picture_road_state) {
            int wi = canvas.getWidth();
            int he = canvas.getHeight();
            image_real_road = Bitmap.createScaledBitmap(image_road3, wi, he, false);
            picture_road_state=true;


        }
        canvas.drawBitmap(image_real_road, 0, 0, paint);

        if (!picture_car_state){
            int wi = canvas.getWidth();
            int he = canvas.getHeight();
            double ratio=wi/he; //соотношение сторон телефона
            int he_car=(int) (he/5.5)+10;
            int wi_car=wi/(int)ratio/11;
            image_real_car=Bitmap.createScaledBitmap(image_car,wi_car,he_car,false);
            x1=(x+ image_real_car.getWidth() / 2)+40000*Math.cos(Math.toRadians(rotation_degrees+270));
            y1=(y+ image_real_car.getHeight()/2)+40000*Math.sin(Math.toRadians(rotation_degrees+270));

            radius_move=Math.sqrt(40000*40000+( image_real_car.getWidth()/2)*( image_real_car.getWidth()/2));

            double deviation_radian_wall=Math.atan((double) (image_real_car.getWidth()/2)/(double)(image_real_car.getHeight()/2));
            deviation_degrees_wall=Math.toDegrees(deviation_radian_wall);
            picture_car_state=true;
        }

        double radian;
        radian=Math.toRadians(rotation_degrees+270);

        if (button_state_toRight){
            rotation_degrees+=2;

            x1=(x+image_real_car.getWidth() / 2)+radius_move*Math.cos(radian);
            y1=(y+image_real_car.getHeight()/2)+radius_move*Math.sin(radian);

        }
        if (button_state_toLeft){
            rotation_degrees-=2;

            x1=(x+image_real_car.getWidth() / 2)+radius_move*Math.cos(radian);
            y1=(y+image_real_car.getHeight()/2)+radius_move*Math.sin(radian);

        }

        double coordinateAnglePictureX; //координаты углов машинки по x
        double coordinateAnglePictureY; //координаты углов машинки по y
        int colorInAngelPicture; //цвет (числом) в углах машинки
        middle_x=x+image_real_car.getWidth()/2;
        middle_y=y+image_real_car.getHeight()/2;
        double radius=Math.sqrt((x-middle_x)*(x-middle_x)+(y-middle_y)*(y-middle_y));

        radian=Math.toRadians(rotation_degrees+270-deviation_degrees_wall);
        canvas.drawCircle(middle_x,middle_y,(float) radius,paint);
        coordinateAnglePictureX=middle_x+radius*Math.cos(radian);
        coordinateAnglePictureY=middle_y+radius*Math.sin(radian);
        paint.setColor(Color.YELLOW);canvas.drawPoint((float) coordinateAnglePictureX,(float)coordinateAnglePictureY,paint);
         colorInAngelPicture= image_real_road.getPixel((int)coordinateAnglePictureX,(int) coordinateAnglePictureY);
        paint.setColor(Color.RED);
        if (colorInAngelPicture == -4894906) {
            x=400;
            y=410;
            rotation_degrees=0;
            radian=Math.toRadians(rotation_degrees+270);
            x1=(x+image_real_car.getWidth() / 2)+radius_move*Math.cos(radian);
            y1=(y+image_real_car.getHeight()/2)+radius_move*Math.sin(radian);
        }
        radian=Math.toRadians(rotation_degrees+270+deviation_degrees_wall);
        coordinateAnglePictureX=middle_x+radius*Math.cos(radian);
        coordinateAnglePictureY=middle_y+radius*Math.sin(radian);
        paint.setColor(Color.YELLOW);
        canvas.drawPoint((float) coordinateAnglePictureX,(float)coordinateAnglePictureY,paint);
        colorInAngelPicture= image_real_road.getPixel((int)coordinateAnglePictureX,(int) coordinateAnglePictureY);
        paint.setColor(Color.RED);
        if (colorInAngelPicture == -4894906) {
            x=400;
            y=440;
            rotation_degrees=0;
            radian=Math.toRadians(rotation_degrees+270);
            x1=(x+image_real_car.getWidth() / 2)+radius_move*Math.cos(radian);
            y1=(y+image_real_car.getHeight()/2)+radius_move*Math.sin(radian);
        }
        radian=Math.toRadians(rotation_degrees+90+deviation_degrees_wall);
        coordinateAnglePictureX=middle_x+radius*Math.cos(radian);
        coordinateAnglePictureY=middle_y+radius*Math.sin(radian);
        paint.setColor(Color.YELLOW);
        canvas.drawPoint((float) coordinateAnglePictureX,(float)coordinateAnglePictureY,paint);
        colorInAngelPicture = image_real_road.getPixel((int)coordinateAnglePictureX,(int) coordinateAnglePictureY);
        paint.setColor(Color.RED);
        if (colorInAngelPicture == -4894906) {
            x=400;
            y=440;
            rotation_degrees=0;
            radian=Math.toRadians(rotation_degrees+270);
            x1=(x+image_real_car.getWidth() / 2)+radius_move*Math.cos(radian);
            y1=(y+image_real_car.getHeight()/2)+radius_move*Math.sin(radian);
        }
        radian=Math.toRadians(rotation_degrees+90-deviation_degrees_wall);
        coordinateAnglePictureX=middle_x+radius*Math.cos(radian);
        coordinateAnglePictureY=middle_y+radius*Math.sin(radian);
        paint.setColor(Color.YELLOW);
        canvas.drawPoint((float) coordinateAnglePictureX,(float)coordinateAnglePictureY,paint);
        colorInAngelPicture = image_real_road.getPixel((int)coordinateAnglePictureX,(int) coordinateAnglePictureY);
        paint.setColor(Color.RED);
        if (colorInAngelPicture == -4894906) {
            x=400;
            y=440;
            rotation_degrees=0;
            radian=Math.toRadians(rotation_degrees+270);
            x1=(x+image_real_car.getWidth() / 2)+radius_move*Math.cos(radian);
            y1=(y+image_real_car.getHeight()/2)+radius_move*Math.sin(radian);
        }

        calculate();
        x += dx;
        y += dy;
        canvas.rotate((float) rotation_degrees, x+image_real_car.getWidth() / 2 , y+image_real_car.getHeight()/2 );
        canvas.drawBitmap(image_real_car, x, y, paint);
        canvas.save();
        canvas.restore();
    }

    private void calculate(){
        double g = Math.sqrt((x1-middle_x)*(x1-middle_x)+(y1-middle_y)*(y1-middle_y));
        dx = (float) (koeff*(x1-middle_x)/g);
        dy = (float) (koeff*(y1-middle_y)/g);
    }

    public void setButton_state_toRight(boolean button_state_toRight) {
        this.button_state_toRight = button_state_toRight;
    }

    public void setButton_state_toLeft(boolean button_state_toLeft) {
        this.button_state_toLeft = button_state_toLeft;
    }

    public void setNumberOfPicture(int numberOfPicture) {
        this.numberOfPicture = numberOfPicture;
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


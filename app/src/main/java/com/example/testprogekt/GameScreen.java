package com.example.testprogekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import hallianinc.opensource.timecounter.StopWatch;

public class GameScreen extends AppCompatActivity {
    ImageButton toRight,toLeft,stop;
    MySurface mySurface;
    TextView chronometer;
    String time="20 : 10";
    int a;
    StopWatch stopWatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window w=getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скраваем нижнюю панель
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //появляется поверх игры и исчезает

        a=getIntent().getIntExtra("NumberOfPicture",0);

        setContentView(R.layout.activity_game_screen);

        chronometer=findViewById(R.id.chronometer);

        toRight=findViewById(R.id.right);
        toLeft=findViewById(R.id.left);
        stop=findViewById(R.id.stop);

        mySurface=findViewById(R.id.mySurface);
        if (a == 0) {
            Intent intent=new Intent(GameScreen.this,MainScreen.class);
            intent.putExtra("gg",-1);
            startActivity(intent);
        }
        mySurface.setNumberOfPicture(a);
        mySurface.setGameScreen(this);
        stopWatch=new StopWatch(chronometer);

       // chronometer.setOnChronometerTickListener(chronometer -> {
       //     time=chronometer.getText().toString();
       // });

        stop.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                mySurface.setButton_state_stop(true);
            }
            if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                mySurface.setButton_state_stop(false);
            }
            return false;
        });

        toRight.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                mySurface.setButton_state_toRight(true);
            }
            if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                mySurface.setButton_state_toRight(false);
            }

            return false;
        });
        toLeft.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                mySurface.setButton_state_toLeft(true);
            }
            if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                mySurface.setButton_state_toLeft(false);

            }

            return false;
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //chronometer.start();
        stopWatch.start();
    }

    public void win(){
        //chronometer.stop();
        stopWatch.pause();
        FragmentManager manager=getSupportFragmentManager();
        MyAllertDialog myAllertDialog=new MyAllertDialog();
        myAllertDialog.setTime(chronometer.getText().toString());
        myAllertDialog.setGameScreen(this);
        myAllertDialog.show(manager,"победитель");


    }


    public void choice(boolean choice){
        if(choice){
            Intent intent=new Intent(getApplicationContext(),ChoiceScreen.class);
            startActivity(intent);
            finish();
        }else{
           // mySurface.setXY();
            Intent intent=new Intent(getApplicationContext(),GameScreen.class);
            intent.putExtra("NumberOfPicture",a);
            startActivity(intent);
            finish();

        }
    }
}
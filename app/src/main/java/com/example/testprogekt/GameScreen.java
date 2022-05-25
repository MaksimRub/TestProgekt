package com.example.testprogekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import hallianinc.opensource.timecounter.StopWatch;

public class GameScreen extends AppCompatActivity {
    ImageButton toRight,toLeft,stop,leave;
    MySurface mySurface;
    TextView chronometer,oops;

    int a;
    StopWatch stopWatch;

    DrawThread drawThread;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window w=getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скраваем нижнюю панель
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //появляется поверх игры и исчезает

        a=getIntent().getIntExtra("NumberOfPicture",0);

        setContentView(R.layout.activity_game_screen);

        chronometer=findViewById(R.id.chronometer);
        oops=findViewById(R.id.oops);

        toRight=findViewById(R.id.right);
        toLeft=findViewById(R.id.left);
        stop=findViewById(R.id.stop);
        leave=findViewById(R.id.leave);

        mySurface=findViewById(R.id.mySurface);
        if (a == 0) {
            Intent intent=new Intent(GameScreen.this,MainScreen.class);
            intent.putExtra("gg",-1);
            startActivity(intent);
        }
        mySurface.setNumberOfPicture(a);
        mySurface.setGameScreen(this);
        stopWatch=new StopWatch(chronometer);


        preferences=getSharedPreferences("records",MODE_PRIVATE);
        editor=preferences.edit();

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
        leave.setOnClickListener(v -> {
            drawThread.setRun(false);
            Intent intent=new Intent(getApplicationContext(),ChoiceScreen.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //chronometer.start();
        stopWatch.start();
    }

    public void win() {
        stopWatch.pause();
        int min ;
        int sec ;
        int millsec ;

        int min_old ;
        int sec_old ;
        int millsec_old ;

        if(preferences.contains(Integer.toString(a))) {
            String time1 = preferences.getString(Integer.toString(a), "0");
            String time = chronometer.getText().toString();
            if(!time.equals(time1)){
                if (!(Character.toString(time.charAt(1)).equals(":"))) {
                    min = Integer.parseInt(Character.toString(time.charAt(0))+Character.toString(time.charAt(1)));
                } else {
                    min = Integer.parseInt(Character.toString(time.charAt(0)));
                }
                millsec = Integer.parseInt(Character.toString(time.charAt(time.length() - 1)));
                if (!(Character.toString(time.charAt(time.length() - 4)).equals(":"))) {
                    sec = Integer.parseInt(Character.toString(time.charAt(time.length() - 4))+Character.toString(time.charAt(time.length() - 3)));
                } else {
                    sec = Integer.parseInt(Character.toString(time.charAt(time.length() - 3)));
                }

                if (!(Character.toString(time1.charAt(1)).equals(":"))) {
                    min_old = Integer.parseInt(Character.toString(time1.charAt(0))+Character.toString(time1.charAt(1)));
                } else {
                    min_old = Integer.parseInt(Character.toString(time1.charAt(0)));
                }
                millsec_old = Integer.parseInt(Character.toString(time1.charAt(time1.length() - 1)));
                if (!(Character.toString(time1.charAt(time1.length() - 4)).equals(":"))) {
                    sec_old = Integer.parseInt(Character.toString(time1.charAt(time1.length() - 4))+Character.toString(time1.charAt(time1.length() - 3)));
                } else {
                    sec_old = Integer.parseInt(Character.toString(time1.charAt(time1.length() - 3)));
                }


                if (millsec < millsec_old && min<=min_old && sec<=sec_old) {
                    editor.putString(Integer.toString(a), time);
                    editor.apply();
                }else if (sec < sec_old && min<=min_old) {
                    editor.putString(Integer.toString(a), time);
                    editor.apply();
                }else if(min<min_old){
                    editor.putString(Integer.toString(a), time);
                    editor.apply();
                }

            }



        }else{
            editor.putString(Integer.toString(a), chronometer.getText().toString());
            editor.apply();
        }


        FragmentManager manager = getSupportFragmentManager();
        MyAllertDialog myAllertDialog = new MyAllertDialog();
        myAllertDialog.setTime(chronometer.getText().toString());
        myAllertDialog.setGameScreen(this);
        myAllertDialog.show(manager, "победитель");
        }



    public void choice(boolean choice){
        if(choice){
            Intent intent=new Intent(getApplicationContext(),ChoiceScreen.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent=new Intent(getApplicationContext(),GameScreen.class);
            intent.putExtra("NumberOfPicture",a);
            startActivity(intent);
            finish();

        }
    }

    public void setDrawThread(DrawThread drawThread) {
        this.drawThread = drawThread;
    }
}
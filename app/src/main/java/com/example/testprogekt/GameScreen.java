package com.example.testprogekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

public class GameScreen extends AppCompatActivity {
    ImageButton toRight,toLeft,stop;
    MySurface mySurface;
    String time="20 : 10";
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w=getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скраваем нижнюю панель
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //появляется поверх игры и исчезает
        a=getIntent().getIntExtra("NumberOfPicture",0);
        setContentView(R.layout.activity_main);
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

        stop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                    mySurface.setButton_state_stop(true);
                }
                if (motionEvent.getAction()==MotionEvent.ACTION_UP){
                    mySurface.setButton_state_stop(false);
                }
                return false;
            }
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
    public void win(){
       /* FragmentManager manager=getSupportFragmentManager();
        MyAllertDialog myAllertDialog=new MyAllertDialog();
        myAllertDialog.setTime(time);
        myAllertDialog.setGameScreen(this);
        myAllertDialog.show(manager,"победитель");*/
       // Intent intent=new Intent(GameScreen.this,ChoiceScreen.class);
       // startActivity(intent);
        super.onBackPressed();
        finish();
    }
    public void choice(boolean chose){
        if(chose){

        }else{
            Intent intent=new Intent(GameScreen.this,ChoiceScreen.class);
            startActivity(intent);
        }
    }
}
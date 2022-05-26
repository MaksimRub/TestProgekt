package com.example.testprogekt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class MainScreen extends AppCompatActivity {
    Button enter,exit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w=getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скраваем нижнюю панель
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //появляется поверх игры и исчезает
        int a=getIntent().getIntExtra("gg",1);
        setContentView(R.layout.activity_main_screen);
        if(a==-1){
            Toast.makeText(this, "что-то пошло не так", Toast.LENGTH_SHORT).show();
        }
        startService(new Intent(this,MyService.class));

        enter=findViewById(R.id.enter);
        exit=findViewById(R.id.exit);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainScreen.this,ChoiceScreen.class));
                overridePendingTransition(R.anim.enter,R.anim.exit);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        startService(new Intent(this,MyService.class));
    }


}
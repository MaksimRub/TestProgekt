package com.example.testprogekt;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ChoiceScreen extends AppCompatActivity {
    ImageButton picture1,picture2,picture3;
    Bitmap image_road1,image_road2,image_road3;
    LinearLayout linearLayout;


    Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w=getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скраваем нижнюю панель
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //появляется поверх игры и исчезает
        setContentView(R.layout.activity_choice_screen);
        picture1=findViewById(R.id.image1);
        picture2=findViewById(R.id.image2);
        picture3=findViewById(R.id.image3);
        linearLayout=findViewById(R.id.layout1);
        //TODO доделать картинки трас и их описание
            //picture1.setImageBitmap();
            //picture2.setImageBitmap();
            //picture3.setImageBitmap();
        res=getResources();
        image_road2=BitmapFactory.decodeResource(res,R.drawable.norm_road);
        picture2.setImageBitmap(image_road2);
        picture1.setImageBitmap(image_road2);
        picture3.setImageBitmap(image_road2);
        picture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChoiceScreen.this,GameScreen.class);
                intent.putExtra("NumberOfPicture",1);
                startActivity(intent);

            }
        });
        picture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChoiceScreen.this,GameScreen.class);
                intent.putExtra("NumberOfPicture",2);
                startActivity(intent);
            }
        });

        picture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChoiceScreen.this,GameScreen.class);
                intent.putExtra("NumberOfPicture",3);
                startActivity(intent);
            }
        });

    }

}
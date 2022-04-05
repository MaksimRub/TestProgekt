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

public class ChoiceScreen extends AppCompatActivity {
    ImageButton picture1,picture2,picture3;
    Bitmap image_road1,image_road2,image_road3;
    Bitmap image_real_road1,image_real_road2,image_real_road3;

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
        //TODO доделать картинки трас и их описание
            //picture1.setImageBitmap();
            //picture2.setImageBitmap();
            //picture3.setImageBitmap();
        res=getResources();
        image_road2=BitmapFactory.decodeResource(res,R.drawable.norm_road);
        picture2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                picture2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width=picture2.getWidth();
                int height=picture2.getHeight();
                picture2.setMaxHeight(height);
                picture2.setMaxWidth(width);
                image_real_road2=Bitmap.createScaledBitmap(image_road2,width,height,false);
                picture2.setImageBitmap(image_real_road2);
                picture2.getViewTreeObserver().removeOnGlobalLayoutListener(this);

            }
        });
       /* picture2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                int width=picture2.getWidth();
                int height=picture2.getHeight();
                image_real_road2=Bitmap.createScaledBitmap(image_road2,width,height,false);
                picture2.setImageBitmap(image_real_road2);
                picture2.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });*/
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
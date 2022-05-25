package com.example.testprogekt;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

public class ChoiceScreen extends AppCompatActivity {
    ImageButton picture1,picture2,picture3,enter;
    Bitmap image_road1,image_road2,image_road3;
    int numberOfPicture=0;
    TextView image_road_time1,image_road_time2,image_road_time3;



    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    Resources res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w=getWindow();
        w.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // скраваем нижнюю панель
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY); //появляется поверх игры и исчезает

        setContentView(R.layout.activity_choice_screen);

        preferences=getSharedPreferences("records",MODE_PRIVATE);
        image_road_time1=findViewById(R.id.head1);
        image_road_time2=findViewById(R.id.head2);
        image_road_time3=findViewById(R.id.head3);











        picture1=findViewById(R.id.image1);
        picture2=findViewById(R.id.image2);
        picture3=findViewById(R.id.image3);
        enter=findViewById(R.id.enter);
        //TODO доделать картинки трас и их описание
            //picture1.setImageBitmap();
            //picture2.setImageBitmap();
            //picture3.setImageBitmap();
        res=getResources();
        image_road2=BitmapFactory.decodeResource(res,R.drawable.norm_road1);
        image_road1=BitmapFactory.decodeResource(res,R.drawable.first_road);
        picture1.setImageBitmap(image_road1);
        picture2.setImageBitmap(image_road2);
        picture3.setImageBitmap(image_road2);
        Log.i("Choise","is Successful");
        picture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture1.setBackgroundColor(Color.RED);
                picture3.setBackgroundColor(0x00FFFFFF);
                picture2.setBackgroundColor(0x00FFFFFF);
                numberOfPicture=1;
                if(preferences.contains("1")){
                    image_road_time1.setText(preferences.getString("1",""));
                }
                image_road_time2.setText("");
                image_road_time3.setText("");

            }
        });
        picture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture2.setBackgroundColor(Color.RED);
                picture1.setBackgroundColor(0x00FFFFFF);
                picture3.setBackgroundColor(0x00FFFFFF);
                numberOfPicture=2;
                if(preferences.contains("2")){
                    image_road_time2.setText(preferences.getString("2",""));
                }
                image_road_time1.setText("");
                image_road_time3.setText("");
            }
        });

        picture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picture3.setBackgroundColor(Color.RED);
                picture1.setBackgroundColor(0x00FFFFFF);
                picture2.setBackgroundColor(0x00FFFFFF);
                numberOfPicture=3;
                if(preferences.contains("3")){
                    image_road_time3.setText(preferences.getString("3",""));
                }
                image_road_time2.setText("");
                image_road_time1.setText("");

            }
        });

        enter.setOnClickListener(view -> {
            if(numberOfPicture!=0) {
                Intent intent = new Intent(ChoiceScreen.this, GameScreen.class);
                intent.putExtra("NumberOfPicture", numberOfPicture);
                startActivity(intent);
                finish();
            }
        });

    }


}
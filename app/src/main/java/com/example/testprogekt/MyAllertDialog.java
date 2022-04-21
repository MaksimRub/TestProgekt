package com.example.testprogekt;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Objects;

public class MyAllertDialog extends AppCompatDialogFragment {
    Button exit,returns;
    TextView textTime;
    String time;
    GameScreen gameScreen;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog,null);
        builder.setView(view);
        textTime=view.findViewById(R.id.time);
        exit=view.findViewById(R.id.exit);
        returns=view.findViewById(R.id.returnes);

        textTime.setText("Вы проехали за "+time);
        exit.setOnClickListener(view1 -> {
            gameScreen.choice(true);
        });
        returns.setOnClickListener(view1 -> {
            gameScreen.choice(false);
            Objects.requireNonNull(getDialog()).cancel();
        });
        return builder.create();
    }
    public void setTime(String time){
        this.time=time;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}



package com.example.CoolRacer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.Objects;

public class MyAllertDialog extends AppCompatDialogFragment {
    Button exit,returns;
    TextView textTime,win;
    String time;
    GameScreen gameScreen;
    boolean winner=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Objects.requireNonNull(getDialog()).setCanceledOnTouchOutside(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
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
        win=view.findViewById(R.id.head);
        if(winner){
            win.setText("вы побили свой рекорд");
        }else {
            win.setText("старайтесь лучше");
        }

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

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}



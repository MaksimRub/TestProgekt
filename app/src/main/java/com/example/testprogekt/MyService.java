package com.example.testprogekt;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService extends Service {
    MediaPlayer mediaPlayer;
    public MyService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if ((flags & START_FLAG_RETRY) == 0) {
            mediaPlayer=MediaPlayer.create(this,R.raw.fon);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(2,2);
            mediaPlayer.start();
        }
        else {
            mediaPlayer=MediaPlayer.create(this,R.raw.fon);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }
        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        super.onDestroy();
    }
}
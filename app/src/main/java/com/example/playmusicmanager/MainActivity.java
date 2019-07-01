package com.example.playmusicmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener, View.OnLongClickListener {

    private MediaPlayer mediaPlayer;
    private int currentTrack = 0;
    private List<String> songsList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final NotificationManager notifManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);



        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, "channelId")
                        .setAutoCancel(true)
                        .setSmallIcon(R.drawable.ic_notification)
                        .setContentTitle("bensoundbrazilsamba.mp3")
                        .setContentText("Samba\n" +
                                "  is a Brazilian  musical genre\n" +
                                "and   dance   style,   with   its   roots   in\n" +
                                "Africa   via   the   West   African   slave\n" +
                                "trade       and       African       religious\n" +
                                "traditions, particularly of Angola");



        final Button btnPlay = findViewById(R.id.activity_main_btn_play);
        btnPlay.setOnClickListener(this);
        final Button btnStop = findViewById(R.id.activity_main_btn_stop);
        btnStop.setOnClickListener(this);
        final Button btnChangeSong = findViewById(R.id.activity_main__btn__change_song);


        listView = (ListView) findViewById(R.id.activity_main__lv_list_of_songs);
        songsList = new ArrayList<>();
        Field[] fields = R.raw.class.getFields();
        for(int i = 0; i < fields.length; i++) {
            songsList.add(fields[i].getName());
        }

        final String[] songArray = { "bensoundbrazilsamba", "bensoundcountryboy.mp3",
                "bensoundindia.mp3", "bensoundlittleplanet.mp3", "bensoundpsychedelic.mp3",
                "bensoundrelaxing.mp3", "bensoundtheelevatorbossanova.mp3"};

        btnChangeSong.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_main__btn__change_song:
                        if (mediaPlayer.isPlaying()) {

                            try {
                                mediaPlayer.stop();

                                currentTrack += 1;
                                mediaPlayer = new MediaPlayer();
                                mediaPlayer.setDataSource("android.resource://" + getPackageName() + "/R.raw." + songArray[currentTrack]);
                                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                    @Override
                                    public void onPrepared(MediaPlayer mp) {
                                        mp.start();
                                    }
                                });
                                mediaPlayer.prepareAsync();


                            }catch(Exception  e){
                                e.printStackTrace();

                            }
                            return false;

                        }

                        }
                return false;
            }

        });


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        final Uri mediaPath = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bensoundbrazilsamba);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), mediaPath);
            mediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        Toast.makeText(this,"isFinishing",Toast.LENGTH_SHORT).show();
    }}


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_btn_play:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();


                }
                mediaPlayer.prepareAsync();
                break;
            case R.id.activity_main_btn_stop:
                if (mediaPlayer.isPlaying()) {
                    onDestroy();
                }
                break;

        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}

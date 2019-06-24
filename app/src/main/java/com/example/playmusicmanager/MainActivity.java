package com.example.playmusicmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, MediaPlayer.OnPreparedListener {

    private MediaPlayer mediaPlayer;
    private int currentTrack = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final Button btnPlay = findViewById(R.id.activity_main_btn_play);
        btnPlay.setOnClickListener(this);
        final Button btnStop = findViewById(R.id.activity_main_btn_stop);
        btnStop.setOnClickListener(this);
        final Button btnChangeSong = findViewById(R.id.activity_main__btn__change_song);
        btnChangeSong.setOnClickListener(this);


        btnChangeSong.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_main__btn__change_song:
                        if (mediaPlayer.isPlaying()) {
                            try {

                                mediaPlayer = new MediaPlayer();
                                mediaPlayer.setDataSource("android.resource://" + getPackageName() + "/" + R.raw.bensoundrelaxing);
                                mediaPlayer.prepare();
                                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mp) {
                                        currentTrack = (currentTrack +1);
                                        Uri nextTrack = Uri.parse("android.resource://" + getPackageName());
                                        try {
                                            mediaPlayer.setDataSource("android.resource://" + getPackageName() + nextTrack);
                                            mediaPlayer.prepare();
                                            mediaPlayer.start();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

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
            case R.id.activity_main__btn__change_song:
                Uri ringtoneUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                MediaPlayer mediaPlayerSound = new MediaPlayer();
                mediaPlayerSound.setAudioStreamType(AudioManager.STREAM_NOTIFICATION);
                try {
                    mediaPlayerSound.setDataSource(this, ringtoneUri);
                    mediaPlayerSound.setOnPreparedListener(this);
                    mediaPlayerSound.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.activity_main_btn_play_in_service:
                Intent serviceIntent = new Intent(this, MyMultimediaService.class);
                startService(serviceIntent);
                break;
            default:

        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();

    }
}

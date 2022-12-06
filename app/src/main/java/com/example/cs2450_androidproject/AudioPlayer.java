package com.example.cs2450_androidproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.net.ConnectException;
import java.util.Random;

public class AudioPlayer {
    private MediaPlayer memoryPlayer;
    private MediaPlayer[] mediaPlayers = new MediaPlayer[4];
    final int random = new Random().nextInt(4);
    int currentIndex = random;
    int myCurrentPlayback = 0;

    private MediaPlayer getSongSelection(Context c)
    {
        mediaPlayers[0] = MediaPlayer.create(c, R.raw.sweet_kahoot_dreams);
        mediaPlayers[1] = MediaPlayer.create(c, R.raw.haven);
        mediaPlayers[2] = MediaPlayer.create(c, R.raw.bruh);
        mediaPlayers[3] = MediaPlayer.create(c,R.raw.dreamscape);

        return mediaPlayers[currentIndex];
    }
    public void stop() {
        if (memoryPlayer != null)
        {
            memoryPlayer.release();
            memoryPlayer = null;
        }
    }

    public void pause()
    {
        if(memoryPlayer.isPlaying())
        {
            memoryPlayer.pause();
            myCurrentPlayback = memoryPlayer.getCurrentPosition();
        }
    }

    public void play(Context c){
        memoryPlayer = getSongSelection(c);
       // memoryPlayer.setLooping(true);
        memoryPlayer.seekTo(myCurrentPlayback);
        memoryPlayer.start();

        memoryPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int newRandom = new Random().nextInt(4);
                currentIndex = newRandom;
                play(c);
            }
        });
    }

}

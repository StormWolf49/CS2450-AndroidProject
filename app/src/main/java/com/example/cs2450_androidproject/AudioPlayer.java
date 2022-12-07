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
    private int[] mySongSelections = new int[6];
    final int random = new Random().nextInt(mySongSelections.length);
    int currentIndex = random;
    int myCurrentPlayback = 0;

    private int getSongSelection()
    {
        mySongSelections[0] = R.raw.sweet_kahoot_dreams;
        mySongSelections[1] = R.raw.bruh;
        mySongSelections[2] = R.raw.dreamscape;
        mySongSelections[3] = R.raw.haven;
        mySongSelections[4] = R.raw.supernova;
        mySongSelections[5] = R.raw.yugioh;

        return mySongSelections[currentIndex];
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
        if (memoryPlayer != null) {
            if (memoryPlayer.isPlaying()) {
                memoryPlayer.pause();
                myCurrentPlayback = memoryPlayer.getCurrentPosition();
            }
        }
    }

    public void play(Context c){
        memoryPlayer = MediaPlayer.create(c,getSongSelection());
       // memoryPlayer.setLooping(true);
        memoryPlayer.seekTo(myCurrentPlayback);
        memoryPlayer.start();

        memoryPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
        {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int newRandom = new Random().nextInt(mySongSelections.length);
                currentIndex = newRandom;
                play(c);
            }
        });
    }

}

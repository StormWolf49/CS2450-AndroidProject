package com.example.cs2450_androidproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class AudioPlayer {
    private MediaPlayer memoryPlayer;
    Uri testUri = Uri.parse("/Users/gardyion/Desktop/CPP-- Fall 2022/CS 2450-- User Interface Design and Programming/Android Project/CS2450-AndroidProject/app/src/main/res/musicbox/Sweet Kahoot Dreams.mp3");
    public void stop() {
        if (memoryPlayer != null)
        {
            memoryPlayer.release();
            memoryPlayer = null;
        }
    }

    public void play(Context c){
        memoryPlayer = MediaPlayer.create(c,R.raw.sweet_kahoot_dreams);
        memoryPlayer.start();
    }
}

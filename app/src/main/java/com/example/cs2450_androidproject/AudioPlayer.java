package com.example.cs2450_androidproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.Random;

public class AudioPlayer {
    private MediaPlayer memoryPlayer;
    private MediaPlayer[] mediaPlayers = new MediaPlayer[4];
    final int random = new Random().nextInt(4);
    int currentIndex = random;
    public void stop() {
        if (memoryPlayer != null)
        {
            memoryPlayer.release();
            memoryPlayer = null;
        }
    }

    public void play(Context c){
        mediaPlayers[0] = MediaPlayer.create(c, R.raw.sweet_kahoot_dreams);
        mediaPlayers[1] = MediaPlayer.create(c, R.raw.haven);
        mediaPlayers[2] = MediaPlayer.create(c, R.raw.bruh);
        mediaPlayers[3] = MediaPlayer.create(c,R.raw.dreamscape);
        memoryPlayer = mediaPlayers[currentIndex];
        memoryPlayer.setLooping(true);
        memoryPlayer.start();
    }
}

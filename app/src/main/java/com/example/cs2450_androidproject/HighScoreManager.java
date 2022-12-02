package com.example.cs2450_androidproject;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.ArrayList;

public class HighScoreManager
{
    private static final String TAG = "Leaderboard";
    private static final String INITIALFILENAME = "leaderboard";
    private static final String FILETYPE = ".json";
    private static int fileNum;

    private ArrayList<HighScore> mHighScores;
    private LeaderboardIntentJSONSerializer mSerializer;

    private static HighScoreManager sHighScoreManager;
    private Context mAppContext;

    public HighScoreManager(Context appContext, int num)
    {
        mAppContext = appContext;
        fileNum = num;
        mSerializer = new LeaderboardIntentJSONSerializer(mAppContext, INITIALFILENAME + fileNum + FILETYPE);

        try {
            mHighScores = mSerializer.loadHighScores();
        } catch (Exception e) {
            mHighScores = new ArrayList<HighScore>(5);
            Log.e(TAG, "Error loading crimes: ", e);
        }
    }

    public void addHighScore(HighScore s)
    {
        mHighScores.add(s);
    }

    public void clearHighScores()
    {
        mHighScores.clear();
    }

    public ArrayList<HighScore> getHighScores()
    {
        return mHighScores;
    }

    public boolean saveHighScores()
    {
        try {
            mSerializer.saveScores(mHighScores);
            Log.d(TAG, "scores saved to a file! File: " + INITIALFILENAME + fileNum + FILETYPE);
            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG, "Error saving high scores: ", e);
            return false;
        }
    }
}

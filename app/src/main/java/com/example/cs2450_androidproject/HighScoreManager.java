/***************************************************************
 *  file: HighScore.java
 *  author: M. Tran, G. Waughan
 *  class: CS 2450 – User Interface Design and Programing
 *
 *  assignment: Android APP
 *  date last modified: 12/11/2022
 *
 *  purpose: object defining a High Score
 *
 ****************************************************************/

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
    private int fileNum;

    private ArrayList<HighScore> mHighScores;
    private LeaderboardIntentJSONSerializer mSerializer;

    private static HighScoreManager sHighScoreManager;
    private Context mAppContext;

    /**
     * Constructor: HighScoreManager(Context, int)
     * Purpose: initialize HighScoreManager for a set of high scores for one type of game
     **/
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

    /**
     * Method: fillWithDummyData()
     * Purpose: fills chosen leaderboard with dummy data / empty set of scores
     **/
    public void fillWithDummyData()
    {
        clearHighScores();

        addHighScore(new HighScore("ABC", 0));
        addHighScore(new HighScore("ABC", 0));
        addHighScore(new HighScore("ABC", 0));
        addHighScore(new HighScore("ABC", 0));
        addHighScore(new HighScore("ABC", 0));

        saveHighScores();
    }

    /**
     * Method: addHighScore()
     * Purpose: adds object HighScore to an array list
     **/
    public void addHighScore(HighScore s)
    {
        mHighScores.add(s);
    }

    /**
     * Method: checkScore()
     * Purpose: checks if the score can be put on the leaderboard
     **/
    public boolean checkScore(int s)
    {
        for(int n = 0; n < mHighScores.size(); n++)
        {
            if(mHighScores.get(n).getScore() < s)
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Method: addHighScore()
     * Purpose: adds score + initials to new Highscore object to an array list + saves to file
     **/
    public void addHighScore(int s, String initials)
    {
        //goes through highScore ArrayList (redundancy for inquiryForUser method).
        for(int n = 0; n < mHighScores.size(); n++)
        {
            if(mHighScores.get(n).getScore() < s)
            {
                //should set new highScore at current loop iteration.
                mHighScores.set(n, new HighScore(initials, s));

                break;
            }

        }

        saveHighScores();
        Log.d(TAG, "Successfully inputted score.");
    }

    /**
     * Method: clearHighScores()
     * Purpose: clears arraylist of high scores
     **/
    public void clearHighScores()
    {
        mHighScores.clear();
    }

    /**
     * Method: getHighScores()
     * Purpose: returns array list of high scores
     **/
    public ArrayList<HighScore> getHighScores()
    {
        return mHighScores;
    }

    /**
     * Method: saveHighScores()
     * Purpose: saves array list of high scores to file(s) using LeaderboardIntentJSONSerializer
     **/
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

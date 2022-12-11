/***************************************************************
 *  file: LeaderboardIntentJSONSerializer.java
 *  author: M. Tran
 *  class: CS 2450 – User Interface Design and Programing
 *
 *  assignment: Android APP
 *  date last modified: 12/11/2022
 *
 *  purpose: handles saving high scores to files
 *
 ****************************************************************/

package com.example.cs2450_androidproject;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

public class LeaderboardIntentJSONSerializer
{
    private Context mContext;
    private String mFilename;

    /**
     * Constructor: LeaderboardIntentJSONSerializer(Context, String)
     * Purpose: intialize JSONSerializer for HighScoreManager to be able to save high scores
     **/
    public LeaderboardIntentJSONSerializer(Context c, String f){
        mContext = c;
        mFilename = f;
    }

    /**
     * Method: saveScores(ArrayList<HighScore>)
     * Purpose: save high scores to files
     **/
    public void saveScores(ArrayList<HighScore> scores) throws JSONException, IOException
    {
        // Build an array in JSON
        JSONArray array = new JSONArray();
        for(HighScore s : scores)
        {
            array.put(s.toJSON());
        }

        // Write the file to disk
        Writer writer = null;
        try {
            OutputStream out = mContext.openFileOutput(mFilename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        }
        finally
        {
            if (writer != null)
            {
                writer.close();
            }
        }
    }

    /**
     * Method: loadHighScores()
     * Purpose: returns ArrayList<HighScore> after saving scores
     **/
    public ArrayList<HighScore> loadHighScores() throws IOException, JSONException {
        ArrayList<HighScore> scores = new ArrayList<HighScore>();
        BufferedReader reader = null;

        try {
            // Open and read the file into a StringBuilder\
            InputStream in = mContext.openFileInput(mFilename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                // Line breaks are omitted and irrelevant
                jsonString.append(line);
            }
            // Parse the JSON using JSONTokener
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString()).nextValue();

            // Build the arrayy of scores from JSON Objects
            for (int i = 0; i < array.length(); i++) {
                scores.add(new HighScore(array.getJSONObject(i)));
            }
        }
        catch (FileNotFoundException e)
        {
            // Ignore
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }

        return scores;
    }
}

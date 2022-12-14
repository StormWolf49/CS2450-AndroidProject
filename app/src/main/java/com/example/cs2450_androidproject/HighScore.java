/***************************************************************
 *  file: HighScore.java
 *  author: M. Tran, Gary Waughan
 *  class: CS 2450 – User Interface Design and Programing
 *
 *  assignment: Android APP
 *  date last modified: 12/11/2022
 *
 *  purpose: object defining a High Score
 *
 ****************************************************************/

package com.example.cs2450_androidproject;

import org.json.JSONException;
import org.json.JSONObject;

// From previous implementation from Swing Project by Gary Waughan
public class HighScore {

    public static final String JSON_INITIALS = "initials";
    public static final String JSON_SCORE = "score";

    public String mInitials;
    public int mScore;

    /**
     * Constructor: highScore(String, int)
     * Purpose: Creates highScore object,
     * setting initials and score for object.
     **/
    public HighScore(String enteredInitials, int enteredScore) {
        this.mInitials = enteredInitials;
        this.mScore = enteredScore;
    }

    /**
     * Constructor: highScore(JSONObject)
     * Purpose: Creates highScore object (specific constructor for JSONSerializer),
     * setting initials and score for object.
     **/
    public HighScore(JSONObject json) throws JSONException
    {
        this.mInitials = json.getString(JSON_INITIALS);
        this.mScore = json.getInt(JSON_SCORE);
    }

    /**
     * Method: getInitials()
     * Purpose: returns initials for highScore object.
     **/
    public String getInitials() {
        return mInitials;
    }

    /**
     * Method: setInitials(String)
     * Purpose: sets initials for object from input string.
     **/
    public void setInitials(String enteredInitials) {
        this.mInitials = enteredInitials;
    }

    /**
     * Method: getScore()
     * Purpose: returns score for highScore object.
     **/
    public int getScore() {
        return mScore;
    }

    /**
     * Method: setScore(int)
     * Purpose: sets score for object from input int.
     **/
    public void setScore(int enteredScore) {
        this.mScore = enteredScore;
    }

    /**
     * Method: toString()
     * Purpose: overrides String.toString() function for object, to specifically
     * format string with initials first and score in specific format.
     **/
    @Override
    public String toString() {
        String scoreFormat = String.format("%03d", mScore);
        return mInitials + " " + scoreFormat;
    }

    /**
     * Method: toJSON()
     * Purpose: gets JSONObject for saving file purposes
     **/
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_INITIALS, mInitials);
        json.put(JSON_SCORE, mScore);
        return json;
    }
}
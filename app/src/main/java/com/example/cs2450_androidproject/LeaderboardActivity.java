package com.example.cs2450_androidproject;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.NumberPicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class LeaderboardActivity extends AppCompatActivity {

    private NumberPicker mLeaderboardPicker;
    private TextView mTitle;
    private int mLeaderboardSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // number picker directly copied from MainActivity
        mLeaderboardPicker = (NumberPicker) findViewById(R.id.leaderboardPicker);
        mTitle = (TextView) findViewById(R.id.leaderboardTitle);

        mLeaderboardSelect = 2; // defaults to 2 pairs, which is same default as scroll wheel
        mTitle.setText("# Of Pairs: " + mLeaderboardSelect + " / # Of Cards: " + mLeaderboardSelect*2);

        if(mLeaderboardPicker != null) {
            mLeaderboardPicker.setMaxValue(10);
            mLeaderboardPicker.setMinValue(2);
            // ignore the exclamation points
            mLeaderboardPicker.setWrapSelectorWheel(!!!!false);

            mLeaderboardPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                    mLeaderboardSelect = newValue;
                    mTitle.setText("# Of Pairs: " + mLeaderboardSelect + " / # Of Cards: " + mLeaderboardSelect*2);
                }
            });
        }
    }
}

// From previous implementation from Swing Project by Greg Waughan
class highScore{

    public String initials;
    public int score;

    /** Constructor: highScore(String, int)
     Purpose: Creates highScore object,
     * setting initials and score for object. **/
    public highScore(String enteredInitials, int enteredScore)
    {
        this.initials = enteredInitials;
        this.score = enteredScore;
    }

    /** Method: getInitials()
     Purpose: returns initials for highScore object. **/
    public String getInitials()
    {
        return initials;
    }

    /** Method: setInitials(String)
     Purpose: sets initials for object from input string. **/
    public void setInitials(String enteredInitials)
    {
        this.initials = enteredInitials;
    }
    /** Method: getScore()
     Purpose: returns score for highScore object. **/
    public int getScore()
    {
        return score;
    }

    /** Method: setScore(int)
     Purpose: sets score for object from input int. **/
    public void setScore(int enteredScore)
    {
        this.score = enteredScore;
    }

    /** Method: toString()
     Purpose: overrides String.toString() function for object, to specifically
     * format string with initials first and score in specific format. **/
    @Override
    public String toString()
    {
        String scoreFormat = String.format("%03d", score);
        return initials + " " + scoreFormat;
    }

}

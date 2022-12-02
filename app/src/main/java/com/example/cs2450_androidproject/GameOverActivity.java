package com.example.cs2450_androidproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

public class GameOverActivity extends AppCompatActivity{

    private static final String TAG = "Game Over";

    private int mPairAmount;
    private int mUserScore;

    private Button mCheckScoreBtn;
    private Button mChangeScoreBtn;

    private TextView mTextCards;
    private TextView mTextScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        if(savedInstanceState == null)
        {
            mUserScore = getIntent().getIntExtra("user_score", 0);
            mPairAmount = getIntent().getIntExtra("number_of_pairs", -1);
        }

        mCheckScoreBtn = (Button) findViewById(R.id.checkHighScoreBtn);

        mTextCards = (TextView) findViewById(R.id.textCards);
        mTextScore = (TextView) findViewById(R.id.textScore);

        mTextCards.setText("Cards: " + mPairAmount*2);
        mTextScore.setText("Score: " + mUserScore);

        if(mChangeScoreBtn != null)
        {
            mChangeScoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Changing score to test Leaderboard . . .");
                }
            });
        }

        if(mCheckScoreBtn != null)
        {
            mCheckScoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Log.d(TAG, "Checking user's score with high scores. . .");
                   Intent goBackToMenuIntent = new Intent(view.getContext(), MainActivity.class);
                   startActivity(goBackToMenuIntent);
                }
            });
        }

    }
}

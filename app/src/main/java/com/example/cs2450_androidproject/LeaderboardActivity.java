package com.example.cs2450_androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

public class LeaderboardActivity extends AppCompatActivity {

    private static final String TAG = "Leaderboard";

    private NumberPicker mLeaderboardPicker;
    private TextView mTitle;
    private int mLeaderboardSelect;

    private static ArrayList<HighScoreManager> mListOfHighScores;

    private TextView mHighScore1;
    private TextView mHighScore2;
    private TextView mHighScore3;
    private TextView mHighScore4;
    private TextView mHighScore5;

    private Button mBackFromLeaderBoardBtn;
    private Button mTestBtn;
    private Button mClearBtn;

    private int mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        // Variables initialized here
        // number picker directly copied from MainActivity
        mListOfHighScores = new ArrayList<HighScoreManager>(8);

        for(int i = 2; i <= 10; i++)
        {
            mListOfHighScores.add(new HighScoreManager(LeaderboardActivity.this, i));

            if(mListOfHighScores.get(i-2).getHighScores().size() == 0)
            {
                mListOfHighScores.get(i-2).fillWithDummyData();
            }
        }

        mHighScore1 = (TextView) findViewById(R.id.highScore1);
        mHighScore2 = (TextView) findViewById(R.id.highScore2);
        mHighScore3 = (TextView) findViewById(R.id.highScore3);
        mHighScore4 = (TextView) findViewById(R.id.highScore4);
        mHighScore5 = (TextView) findViewById(R.id.highScore5);

        mLeaderboardPicker = (NumberPicker) findViewById(R.id.leaderboardPicker);
        mTitle = (TextView) findViewById(R.id.leaderboardTitle);

        mBackFromLeaderBoardBtn = (Button) findViewById(R.id.backFromLeaderboardBtn);
        mTestBtn = (Button) findViewById(R.id.testBtn);
        mClearBtn = (Button) findViewById(R.id.clearBtn);

        mLeaderboardSelect = 2; // defaults to 2 pairs, which is same default as scroll wheel
        mTitle.setText("# Of Pairs: " + mLeaderboardSelect + " / # Of Cards: " + mLeaderboardSelect*2);

        readThyFile(mListOfHighScores.get(mLeaderboardSelect-2).getHighScores());

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
                    readThyFile(mListOfHighScores.get(mLeaderboardSelect-2).getHighScores());
                }
            });
        }

        if(mBackFromLeaderBoardBtn != null)
        {
            mBackFromLeaderBoardBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }

        if(mTestBtn != null)
        {
            mTestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Log.d(TAG, "Testing User Input");

                    // ------------------ //

                    AlertDialog.Builder builder = new AlertDialog.Builder(LeaderboardActivity.this);
                    builder.setTitle("Manuel Score Input (For Debugging Only)");

                    // Set up the input
                    final EditText input = new EditText(LeaderboardActivity.this);
                    // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                    input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            try
                            {
                                mScore = (int) Integer.parseInt(input.getText().toString());
                            }
                            catch (NumberFormatException e)
                            {
                                mScore = 0;
                                Log.e(TAG, "Error : Test Input Button User Input : " + e);
                                Log.e(TAG, "mScore variable defaulted to 0");
                            }

                            Intent goToGameOverIntent = new Intent(view.getContext(), GameOverActivity.class);
                            goToGameOverIntent.putExtra("number_of_pairs", mLeaderboardSelect);
                            goToGameOverIntent.putExtra("user_score", mScore);
                            startActivity(goToGameOverIntent);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();

                    // ------------------ //
                }
            });
        }

        if(mClearBtn != null)
        {
            mClearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mListOfHighScores.get(mLeaderboardSelect-2).fillWithDummyData();
                    readThyFile(mListOfHighScores.get(mLeaderboardSelect-2).getHighScores());
                }
            });
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        // If 2 is selected, it is mListOfHighScores.get(0);
        mListOfHighScores.get(mLeaderboardSelect-2).saveHighScores();
    }

    // Read file
    /** Method: readThyFile()
     Purpose: read from the leaderboard text file,
     * adding (or setting) initials and scores
     to an ArrayList comprised of HighScore objects. **/
    public void readThyFile(ArrayList<HighScore> highScores)
    {
        //sets highScore objects to jLabels from file.
        mHighScore1.setText(highScores.get(0).toString());
        mHighScore2.setText(highScores.get(1).toString());
        mHighScore3.setText(highScores.get(2).toString());
        mHighScore4.setText(highScores.get(3).toString());
        mHighScore5.setText(highScores.get(4).toString());
    }


}

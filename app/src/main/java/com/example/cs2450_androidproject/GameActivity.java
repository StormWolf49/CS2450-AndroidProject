package com.example.cs2450_androidproject;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;

public class GameActivity extends AppCompatActivity {
    private GameCard mTestCard;
    private int mPairAmount;

    private TableLayout mGameTable;
    private GameCard[][] mCards;

    private ArrayList<String> mPossibleWords;

    private Button endButton;
    private Button newGame;
    private Button tryAgain;

    public static final String[] POSSIBLE_WORDS = new String[] {
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        if(savedInstanceState == null) {
            mPairAmount = getIntent().getIntExtra("number_of_pairs", -1);
        }

        mTestCard = (GameCard) findViewById(R.id.testCard);

        Random rng = new Random(); // random number generator to be used in word gen
        // create possible words array
        mPossibleWords = new ArrayList<String>();
        // populate it with base possible words
        for (String word: POSSIBLE_WORDS) {
            mPossibleWords.add(word);
        }

        // remove words until there is the same number of possible words as pairs
        while(mPossibleWords.size() > mPairAmount) {
            mPossibleWords.remove(rng.nextInt(mPossibleWords.size()));
        }

        // clone the array, then add it to itself to double the possible words
        // wish there was a more elegant solution than this
        ArrayList<String> wordListCopy = new ArrayList<String>(mPossibleWords);
        mPossibleWords.addAll(wordListCopy);

        // hardcoding the number of rows in the table
        int numRows;
        switch(mPairAmount) {
            case 10:
            case 8:
                numRows = 4;
                break;
            case 9:
            case 6:
                numRows = 3;
                break;
            default:
                numRows = 2;
        }
        int numCols = mPairAmount * 2 / numRows;

        mCards = new GameCard[numRows][numCols];

        mGameTable = (TableLayout) findViewById(R.id.cardTable);

        // this for loop populates the table with programmatically created empty cards
        // and saves those new cards to the mCards 2D array
        for(int row = 0; row < numRows; row++) {
            TableRow newRow = new TableRow(getApplicationContext());
            final TableRow.LayoutParams tableParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            newRow.setLayoutParams(tableParams);

            for(int col = 0; col < numCols; col++) {
                mCards[row][col] = new GameCard(getApplicationContext());
                final TableRow.LayoutParams cardParams = new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT
                );
                newRow.setLayoutParams(tableParams);
                mCards[row][col].setLayoutParams(cardParams);
                // the following line of code kinda looks gross
                // it sets the card text to a random word from the possible words array
                // (which is removed, ensuring that the proper number of words is used
                mCards[row][col].setText(mPossibleWords.remove(rng.nextInt(mPossibleWords.size())));
                newRow.addView(mCards[row][col]);
            }

            mGameTable.addView(newRow);
        }

        String someText = "table dimensions: " + numRows + " x " + numCols;
        mTestCard.setText(someText);

        //end button
        endButton = (Button) findViewById(R.id.endBtn);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //flip over all unflipped cards
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        if(mCards[i][j].mFaceDown){
                            mCards[i][j].mFlipButton.performClick();
                        }
                    }
                }
                //timer to wait a few seconds before prompting to save score
                CountDownTimer timer = new CountDownTimer(5000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        //alert dialog to prompt for saving to high scores
                        //in practice would only be called if the player actually got a high score
                        //and would lead to a name/initial input screen
                        FragmentManager fm = getFragmentManager();
                        alertDialogFragment hsPrompt = new alertDialogFragment();
                        hsPrompt.show(getSupportFragmentManager(), "hsPrompt");
                    }
                };
                timer.start();
            }
        });
        //new game button
        //return to menu screen to choose a new game board
        newGame = (Button) findViewById(R.id.startBtn);
        Intent newGameIntent = new Intent(this, MainActivity.class);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getParent() != null) {
                    finishFromChild(getParent().getParent());
                }
                startActivity(newGameIntent);
            }
        });
        //try again
        tryAgain = (Button) findViewById(R.id.startBtn2);
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*flip over all flipped cards that don't match
                (not sure if this should count for all flipped cards or just the 2 most recently flipped)
                (this implementation counts for all flipped cards)
                put all face-up card values in an arraylist*/
                ArrayList<String> faceUp = new ArrayList<String>();
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numCols; j++) {
                        if (!mCards[i][j].mFaceDown) {
                            faceUp.add(mCards[i][j].getText());
                        }
                    }
                }
                if (faceUp.size() > 0) {
                    //create and populate array to store the values of the face-up cards
                    //(have to use an arraylist first to know how many cards are face up)
                    String[] values = new String[faceUp.size()];
                    for (int i = 0; i < faceUp.size(); i++) {
                        values[i] = faceUp.get(i);
                    }
                    //check for matches
                    //if a match is found, replace its string in the values array w/ "-1"
                    //since "-1" shouldn't match any of the card values
                    for (int i = 0; i < faceUp.size(); i++) {
                        for (int j = 0; j < faceUp.size(); j++) {
                            if (values[i].equals(values[j]) && i < j) {
                                values[i] = "-1";
                                values[j] = "-1";
                            }
                        }
                    }
                    //flip unmatched cards
                    for (int i = 0; i < numRows; i++) {
                        for (int j = 0; j < numCols; j++) {
                            for (int r = 0; r < faceUp.size(); r++) {
                                //if the value of the card matches one of the values in the value array
                                //that card is not matched and should be flipped back over
                                if (mCards[i][j].getText().equals(values[r]) && !mCards[i][j].mFaceDown){
                                    mCards[i][j].mFlipButton.performClick();
                                }
                            }
                        }
                    }
                }//if (faceUp.size() > 0) {
            }//public void onClick(View v) {
        });//tryAgain.setOnClickListener(new View.OnClickListener() {

    }
}


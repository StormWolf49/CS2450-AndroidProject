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
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Timer;

public class GameActivity extends AppCompatActivity implements GameCard.CardListener {
    private GameCard mTestCard;
    private int mPairAmount;

    private TableLayout mGameTable;
    private GameCard[][] mCards;
    static int mClicked;
    static String[] mLast2Values;
    static boolean mFromTryAgain;
    static boolean mFromEndGame;

    private AudioPlayer myAudioPlayer = new AudioPlayer();
    Switch mMusicSwitch;

    private ArrayList<String> mPossibleWords;

    private Button mEndButton;
    private Button mNewGameButton;
    private Button mTryAgainButton;

    private GameCard mSavedCard;
    private int mScore;
    private boolean mCanFlipCards; // true if <= 2 unmatched cards are face up

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
        //reset click tracking
        mFromEndGame = false;
        mFromTryAgain = false;
        mClicked = 0;
        mLast2Values = new String[2];
        mLast2Values[0] = "-1";
        mLast2Values[1] = "-2";
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        if(savedInstanceState == null) {
            mPairAmount = getIntent().getIntExtra("number_of_pairs", -1);
        }

        mTestCard = (GameCard) findViewById(R.id.testCard);
        mMusicSwitch = (Switch) findViewById(R.id.musicSwitch);
        //reset music switch
        myAudioPlayer.stop();
        mMusicSwitch.setChecked(false);

        mSavedCard = null;
        mScore = 0;
        mCanFlipCards = true;

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
                mCards[row][col].setListener(this);
            }

            mGameTable.addView(newRow);
        }

        String someText = "table dimensions: " + numRows + " x " + numCols;
        mTestCard.setText(someText);

        //end button
        //waits 5 seconds then opens game over screen
        mEndButton = (Button) findViewById(R.id.endBtn);
        Intent gameOverIntent = new Intent(this, GameOverActivity.class);
        mEndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameOverIntent.putExtra("user_score", mScore);
                gameOverIntent.putExtra("number_of_pairs", mPairAmount);
                mFromEndGame = true;
                //flip over all unflipped cards
                for (GameCard[] row: mCards) {
                    for (GameCard card: row) {
                        card.flipUp();
                    }
                }

                //reset click tracking
                mFromEndGame = false;
                mFromTryAgain = false;
                mClicked = 0;
                mLast2Values[0] = "-1";
                mLast2Values[1] = "-2";
                //timer to wait a few seconds before prompting to save score
                CountDownTimer timer = new CountDownTimer(5000,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        //doesn't need to do anything but needs to be here for the timer to work
                    }

                    @Override
                    public void onFinish() {
                        //stop music after timer ends
                        myAudioPlayer.stop();
                        mMusicSwitch.setChecked(false);
                        //open game over screen
                        if (getParent() != null) {
                            finishFromChild(getParent().getParent());
                        }
                        startActivity(gameOverIntent);
                    }
                };
                timer.start();
            }
        });
        //new game button
        //return to menu screen to choose a new game board
        mNewGameButton = (Button) findViewById(R.id.startBtn);
        Intent newGameIntent = new Intent(this, MainActivity.class);
        mNewGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //reset click tracking and music switch
                myAudioPlayer.stop();
                mMusicSwitch.setChecked(false);
                mFromEndGame = false;
                mFromTryAgain = false;
                mClicked = 0;
                mLast2Values[0] = "-1";
                mLast2Values[1] = "-2";
                if (getParent() != null) {
                    finishFromChild(getParent().getParent());
                }
                startActivity(newGameIntent);
            }
        });
        //try again
        mTryAgainButton = (Button) findViewById(R.id.startBtn2);
        mTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if 2 cards have been flipped up:
                if(!mCanFlipCards) {
                    // for every card:
                    for(GameCard[] row: mCards) {
                        for (GameCard card: row) {
                            // if the card is unmatched and faceup, flip it back down
                            if(!card.isMatched() && !card.isFaceDown()) {
                                card.flipDown();
                            }
                        }
                    }

                    // and now no cards are face up
                    mCanFlipCards = true;
                }
            }
        });//mTryAgainButton.setOnClickListener(new View.OnClickListener() {

        mMusicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mMusicSwitch.isChecked())
                {
                    myAudioPlayer.play(getApplicationContext());
                }

                else
                {
                    myAudioPlayer.pause();
                }
            }
        });
    }

    @Override
    public void onClick(GameCard c) {
        Log.d("GameActivity", "card clicked");
        // a card can only be flipped over if it hasn't been matched and is face down
        if(!c.isMatched() && c.isFaceDown() && mCanFlipCards) {
            Log.d("GameActivity", "card flipped up");
            c.flipUp();

            // in this case, no cards have already been flipped
            // afterwards, one card will be flipped up
            if(mSavedCard == null) {
                // save the card
                mSavedCard = c;
            }
            // otherwise, one card has been flipped up already and we will now flip up another card
            // (leaving two cards flipped up)
            else {
                // successful match
                if(mSavedCard.getText().equals(c.getText())) {
                    mScore += 2; // +2 points for a successful match
                    mSavedCard.setMatched(true);
                    c.setMatched(true);
                }

                // not a match
                else {
                    mScore = Math.max(0, mScore - 1); // -1 points for a failed match, never going below zero
                    mCanFlipCards = false;
                }

                // remove saved card text
                mSavedCard = null;
                Log.d("GameActivity", "current score: " + mScore);
            }
        }
    }
}


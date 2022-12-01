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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private GameCard mTestCard;
    private int mPairAmount;

    private TableLayout mGameTable;
    private GameCard[][] mCards;

    private ArrayList<String> mPossibleWords;

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
    }
}


package com.example.cs2450_androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Button;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    // the scroll wheel that lets you select how many cards to play with
    private NumberPicker mPairNumPicker;
    // saves the value to be passed to the game activity
    private int mNewGamePairAmount;

    private Button mStartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set the scroll wheel to function
        // we can choose between 2-10 pairs (for a 4-20 card game with an even number of cards
        mPairNumPicker = (NumberPicker) findViewById(R.id.cardAmountNumPicker);
        mNewGamePairAmount = 2; // defaults to 2 pairs, which is same default as scroll wheel
        if(mPairNumPicker != null) {
            mPairNumPicker.setMaxValue(10);
            mPairNumPicker.setMinValue(2);
            mPairNumPicker.setWrapSelectorWheel(!!!!false);

            mPairNumPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
                    mNewGamePairAmount = newValue;
                }
            });
        }

        // start button handling
        mStartButton = (Button) findViewById(R.id.startBtn);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View btn) {
                String debugString = "current value: " + mNewGamePairAmount;
                Log.d("MainActivity", debugString);
            }
        });
    }
}
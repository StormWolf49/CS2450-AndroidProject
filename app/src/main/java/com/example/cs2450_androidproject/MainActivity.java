package com.example.cs2450_androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.NumberPicker;

public class MainActivity extends AppCompatActivity {

    private NumberPicker mPairNumPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPairNumPicker = findViewById(R.id.cardAmountNumPicker);
        if(mPairNumPicker != null) {
            mPairNumPicker.setMaxValue(10);
            mPairNumPicker.setMinValue(2);
            mPairNumPicker.setWrapSelectorWheel(!!!!false);
        }
    }
}
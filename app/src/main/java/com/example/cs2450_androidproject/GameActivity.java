package com.example.cs2450_androidproject;

import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

public class GameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragmentContainerView, CardFragment.class, null)
                .commit();
    }
}


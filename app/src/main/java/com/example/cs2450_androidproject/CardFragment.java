package com.example.cs2450_androidproject;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class CardFragment extends Fragment {
    String mCardText;
    Button mCardButton;
    ImageView mCardBack;
    TextView mCardTextBox;

    public CardFragment() {
        super(R.layout.fragment_card);
    }
}
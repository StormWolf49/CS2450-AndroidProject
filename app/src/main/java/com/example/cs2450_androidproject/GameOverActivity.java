package com.example.cs2450_androidproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity{

    private static final String TAG = "Game Over";

    private int mPairAmount;
    private int mUserScore;

    private Button mEndBtn;

    private TextView mTextCards;
    private TextView mTextScore;

    private AlertDialog.Builder mAskUser;
    private AlertDialog.Builder mInputInitials;

    private boolean mInDebugMode;
    private boolean mIsHighScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        if(savedInstanceState == null)
        {
            mInDebugMode = getIntent().getBooleanExtra("debug_mode", false);
            mUserScore = getIntent().getIntExtra("user_score", 0);
            mPairAmount = getIntent().getIntExtra("number_of_pairs", -1);
        }

        mIsHighScore = LeaderboardActivity.getListOfHighScores().get(mPairAmount-2).checkScore(mUserScore);
        mEndBtn = (Button) findViewById(R.id.checkHighScoreBtn);

        mTextCards = (TextView) findViewById(R.id.textCards);
        mTextScore = (TextView) findViewById(R.id.textScore);

        mTextCards.setText("Cards: " + mPairAmount*2);
        mTextScore.setText("Score: " + mUserScore);

        if(mEndBtn != null)
        {
            mEndBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent goBackToMenuIntent = new Intent(GameOverActivity.this, MainActivity.class);
                    goBackToMenuIntent.putExtra("debug_mode", mInDebugMode);
                    goBackToMenuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(goBackToMenuIntent);
                }
            });


            if(mIsHighScore) {
                mEndBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "Checking user's score with high scores. . .");

                        // ---------INITIALIZING DIALOG ASK USER--------- //
                        mAskUser = new AlertDialog.Builder(GameOverActivity.this);
                        mAskUser.setTitle("Would you like to save your Score?");

                        // Set up the buttons
                        mAskUser.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface ask_dialog, int which)
                            {
                                // ---------INITIALIZING DIALOG INITIALS INPUT--------- //
                                mInputInitials = new AlertDialog.Builder(GameOverActivity.this);
                                mInputInitials.setTitle("Enter your initials");
                                View viewInflated2 = LayoutInflater.from(GameOverActivity.this).inflate(R.layout.fragment_initials,
                                        (ViewGroup) findViewById(android.R.id.content), false);
                                // Set up the input
                                final EditText input1 = (EditText) viewInflated2.findViewById(R.id.input);
                                final EditText input2 = (EditText) viewInflated2.findViewById(R.id.input2);
                                final EditText input3 = (EditText) viewInflated2.findViewById(R.id.input3);
                                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                                input1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                                input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
                                input3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);

                                // Only accept character inputs
                                input1.setFilters(new InputFilter[] {
                                        new InputFilter() {
                                            public CharSequence filter(CharSequence src, int start,
                                                                       int end, Spanned dst, int dstart, int dend) {
                                                if(src.equals("")){ // for backspace
                                                    return src;
                                                }
                                                if(src.toString().matches("[a-zA-Z ]+")){
                                                    return src;
                                                }
                                                return input1.getText().toString();
                                            }
                                        }
                                });
                                input2.setFilters(new InputFilter[] {
                                        new InputFilter() {
                                            public CharSequence filter(CharSequence src, int start,
                                                                       int end, Spanned dst, int dstart, int dend) {
                                                if(src.equals("")){ // for backspace
                                                    return src;
                                                }
                                                if(src.toString().matches("[a-zA-Z ]+")){
                                                    return src;
                                                }
                                                return input2.getText().toString();
                                            }
                                        }
                                });
                                input3.setFilters(new InputFilter[] {
                                        new InputFilter() {
                                            public CharSequence filter(CharSequence src, int start,
                                                                       int end, Spanned dst, int dstart, int dend) {
                                                if(src.equals("")){ // for backspace
                                                    return src;
                                                }
                                                if(src.toString().matches("[a-zA-Z ]+")){
                                                    return src;
                                                }
                                                return input3.getText().toString();
                                            }
                                        }
                                });


                                // Centers into the square
                                input1.setPadding(50, 0, 0, 0);
                                input2.setPadding(50, 0, 0, 0);
                                input3.setPadding(50, 0, 0, 0);
                                mInputInitials.setView(viewInflated2);

                                // Basically Key Listeners
                                input1.addTextChangedListener(new TextWatcher() {
                                    public void afterTextChanged(Editable s) {
                                        input1.removeTextChangedListener(this);
                                        if(s.toString().length() > 0)
                                        {
                                            input1.setText(s.toString().substring(0, 1).toUpperCase());
                                        }
                                        input2.requestFocus();
                                        input1.addTextChangedListener(this);
                                    }
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after)
                                    {
                                    }
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    }
                                });

                                input2.addTextChangedListener(new TextWatcher() {
                                    public void afterTextChanged(Editable s) {
                                        input2.removeTextChangedListener(this);
                                        if(s.toString().length() > 0)
                                        {
                                            input2.setText(s.toString().substring(0, 1).toUpperCase());
                                        }
                                        input3.requestFocus();
                                        input2.addTextChangedListener(this);
                                    }
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                                });

                                input3.addTextChangedListener(new TextWatcher() {
                                    public void afterTextChanged(Editable s) {
                                        input3.removeTextChangedListener(this);
                                        if(s.toString().length() > 0)
                                        {
                                            input3.setText(s.toString().substring(0, 1).toUpperCase());
                                        }
                                        input1.requestFocus();
                                        input3.addTextChangedListener(this);
                                    }
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {}
                                });

                                // Set up the buttons
                                mInputInitials.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Save Score and Initials
                                        String i1 = input1.getText().toString();
                                        String i2 = input2.getText().toString();
                                        String i3 = input3.getText().toString();

                                        if(i1.length() == 0) { i1 = "?"; }
                                        if(i2.length() == 0) { i2 = "?"; }
                                        if(i3.length() == 0) { i3 = "?"; }

                                        String initials = i1 + i2 + i3;
                                        Log.d(TAG, initials + " " + mUserScore);

                                        LeaderboardActivity.getListOfHighScores().get(mPairAmount-2).addHighScore(mUserScore, initials);

                                        Intent goBackToMenuIntent = new Intent(GameOverActivity.this, MainActivity.class);
                                        goBackToMenuIntent.putExtra("debug_mode", mInDebugMode);
                                        goBackToMenuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(goBackToMenuIntent);
                                    }
                                });
                                mInputInitials.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Throw user back
                                        dialog.cancel();
                                        mAskUser.show();
                                    }
                                });

                                mInputInitials.show();
                                // ---------END OF INITIALIZATION--------- //
                            }
                        });
                        mAskUser.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                                Log.d(TAG, "Checking user's score with high scores. . .");
                                Intent goBackToMenuIntent = new Intent(GameOverActivity.this, MainActivity.class);
                                goBackToMenuIntent.putExtra("debug_mode", mInDebugMode);
                                goBackToMenuIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(goBackToMenuIntent);
                            }
                        });
                        // ---------END OF INITIALIZATION--------- //

                        mAskUser.show();
                    }
                });
            }
        }

    }
}

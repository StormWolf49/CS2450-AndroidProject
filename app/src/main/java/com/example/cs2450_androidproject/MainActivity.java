package com.example.cs2450_androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Button;
import android.util.Log;
import android.widget.Switch;

import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MAIN";

    // the scroll wheel that lets you select how many cards to play with
    private NumberPicker mPairNumPicker;
    // saves the value to be passed to the game activity
    private int mNewGamePairAmount;

    private Button mStartButton;
    private Button mLeaderboardButton;
    private Switch mDebugSwitch;

    private boolean mInDebugMode = false;

    private final String DEBUG_PASSWORD = "swingbeans";

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
            // ignore the exclamation points
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
        // I don't actually know how to properly do intents
        // so I made the activity class itself an onClickListener so it can pass itself (this keyword)
        // to the Intent constructor
        mStartButton.setOnClickListener(this);

        mLeaderboardButton = (Button) findViewById(R.id.leaderboardBtn);
        mLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent goToLeaderboardIntent = new Intent(view.getContext(), LeaderboardActivity.class);
                goToLeaderboardIntent.putExtra("debug_mode", mInDebugMode);
                startActivity(goToLeaderboardIntent);
            }
        });

        if(savedInstanceState == null)
        {
            mInDebugMode = getIntent().getBooleanExtra("debug_mode", false);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Activate Debug Mode (Password)");

        View viewInflated = LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_userinputdialog,
                (ViewGroup) findViewById(android.R.id.content), false);

        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("CANCEL", null);
        input.setBackgroundColor(TextInputLayout.BOX_BACKGROUND_NONE);
        builder.setView(viewInflated);

        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (input.getText().toString().equals(DEBUG_PASSWORD)) {
                            //CLOSE DIALOG
                            mInDebugMode = true;
                            Log.d(TAG, "Turned on Debug Mode");
                            dialog.dismiss();
                        } else {
                            input.setBackgroundColor(Color.parseColor("#ffffff"));
                            Log.e(TAG, "Password is: " + DEBUG_PASSWORD);
                            //DO NOT CLOSE DIALOG
                        }
                    }
                });

                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //CLOSE THE DIALOG
                        dialog.dismiss();
                    }
                });
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                             @Override
                                             public void onDismiss(DialogInterface dialog) {
                                                 if (!mInDebugMode) {
                                                     mDebugSwitch.setChecked(false);
                                                 }
                                             }
                                         }

        );

        mDebugSwitch = (Switch) findViewById(R.id.debug_switch);
        mDebugSwitch.setChecked(mInDebugMode);
        mDebugSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (!mInDebugMode) {
//                    alertDialog.show();
                    mInDebugMode = true;
                }
                else
                {
                    mInDebugMode = false;
                    Log.d(TAG, "Turned off Debug Mode");
                }
            }
        });

        LeaderboardActivity.initializeListOfHighScores(MainActivity.this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            setContentView(R.layout.activity_main);
        }

        // set the scroll wheel to function
        // we can choose between 2-10 pairs (for a 4-20 card game with an even number of cards
        mPairNumPicker = (NumberPicker) findViewById(R.id.cardAmountNumPicker);
        mNewGamePairAmount = 2; // defaults to 2 pairs, which is same default as scroll wheel
        if(mPairNumPicker != null) {
            mPairNumPicker.setMaxValue(10);
            mPairNumPicker.setMinValue(2);
            // ignore the exclamation points
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
        // I don't actually know how to properly do intents
        // so I made the activity class itself an onClickListener so it can pass itself (this keyword)
        // to the Intent constructor
        mStartButton.setOnClickListener(this);

        mLeaderboardButton = (Button) findViewById(R.id.leaderboardBtn);
        mLeaderboardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent goToLeaderboardIntent = new Intent(view.getContext(), LeaderboardActivity.class);
                goToLeaderboardIntent.putExtra("debug_mode", mInDebugMode);
                startActivity(goToLeaderboardIntent);
            }
        });

        mInDebugMode = getIntent().getBooleanExtra("debug_mode", false);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Activate Debug Mode (Password)");

        View viewInflated = LayoutInflater.from(MainActivity.this).inflate(R.layout.fragment_userinputdialog,
                (ViewGroup) findViewById(android.R.id.content), false);

        // Set up the input
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setPositiveButton("OK", null);
        builder.setNegativeButton("CANCEL", null);
        input.setBackgroundColor(TextInputLayout.BOX_BACKGROUND_NONE);
        builder.setView(viewInflated);

        final AlertDialog alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (input.getText().toString().equals(DEBUG_PASSWORD)) {
                            //CLOSE DIALOG
                            mInDebugMode = true;
                            Log.d(TAG, "Turned on Debug Mode");
                            dialog.dismiss();
                        } else {
                            input.setBackgroundColor(Color.parseColor("#ffffff"));
                            Log.e(TAG, "Password is: " + DEBUG_PASSWORD);
                            //DO NOT CLOSE DIALOG
                        }
                    }
                });

                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //CLOSE THE DIALOG
                        dialog.dismiss();
                    }
                });
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                             @Override
                                             public void onDismiss(DialogInterface dialog) {
                                                 if (!mInDebugMode) {
                                                     mDebugSwitch.setChecked(false);
                                                 }
                                             }
                                         }

        );

        mDebugSwitch = (Switch) findViewById(R.id.debug_switch);
        mDebugSwitch.setChecked(mInDebugMode);
        mDebugSwitch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (!mInDebugMode) {
//                    alertDialog.show();
                    mInDebugMode = true;
                }
                else
                {
                    mInDebugMode = false;
                    Log.d(TAG, "Turned off Debug Mode");
                }
            }
        });

        LeaderboardActivity.initializeListOfHighScores(MainActivity.this);
    }

    @Override
    public void onClick(View btn) {
        String debugString = "current value: " + mNewGamePairAmount;
        Log.d("MainActivity", debugString);
        Intent newGameIntent = new Intent(this, GameActivity.class);
        // transfer the pair amount to the game activity
        newGameIntent.putExtra("number_of_pairs", mNewGamePairAmount);
        startActivity(newGameIntent);
    }
}
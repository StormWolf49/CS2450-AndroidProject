package com.example.cs2450_androidproject;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class GameCard extends FrameLayout {
    String mCardText;
    TextView mCardFront;
    TextView mCardBack;
    Button mFlipButton;

    AnimatorSet mFrontAnim;
    AnimatorSet mBackAnim;

    boolean mFaceDown;

    public GameCard(Context context) {
        super(context);
        init();
    }

    public GameCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // handle initialization here in case we need multiple constructors
    private void init() {
        // use the layout
        inflate(getContext(), R.layout.view_card, this);

        // retrieve views
        mCardFront = (TextView) findViewById(R.id.card_front);
        mCardBack = (TextView) findViewById(R.id.card_back);
        mFlipButton = (Button) findViewById(R.id.flip_button);

        // cards start facedown
        mFaceDown = true;

        float scale = getContext().getResources().getDisplayMetrics().density;

        // required for 3d card flip effect
        mCardFront.setCameraDistance(8000 * scale);
        mCardBack.setCameraDistance(8000 * scale);

        // retrieve animators
        mFrontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.front_animator);
        mBackAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.back_animator);

        // button that flips the card when it's flipped
        mFlipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GameCard", "flip button clicked");

                // select the animation targets for the animators
                // the relative "back" of the card may be the card's actual face or back
                // and the animators have to handle one or the other
                if (GameActivity.clicked < 2 || GameActivity.fromTryAgain) {
                    GameActivity.clicked++;
                    System.out.println(GameActivity.clicked);
                    if (mFaceDown) {
                        mFrontAnim.setTarget(mCardBack);
                        mBackAnim.setTarget(mCardFront);
                        mFaceDown = false;
                    } else {
                        mFrontAnim.setTarget(mCardFront);
                        mBackAnim.setTarget(mCardBack);
                        mFaceDown = true;
                    }
                    mFrontAnim.start();
                    mBackAnim.start();
                    //save first flipped card value
                    if (GameActivity.clicked == 1){
                        GameActivity.last2Values[0] = getText();
                    }
                    //save second flipped card value
                    if (GameActivity.clicked == 2){
                        GameActivity.last2Values[1] = getText();
                        //check if the 2 card values match
                        //if they match, reset click tracking
                        //if they don't, don't allow any more clicks until they click "try again"
                        if (checkMatch(GameActivity.last2Values)){
                            //reset click tracking
                            GameActivity.fromEndGame = false;
                            GameActivity.fromTryAgain = false;
                            GameActivity.clicked = 0;
                            GameActivity.last2Values[0] = "-1";
                            GameActivity.last2Values[1] = "-2";
                        }
                    }
                }
                //don't count clicks for flipping all cards to end the game
                else if(GameActivity.fromEndGame){
                    if (mFaceDown) {
                        mFrontAnim.setTarget(mCardBack);
                        mBackAnim.setTarget(mCardFront);
                        mFaceDown = false;
                    } else {
                        mFrontAnim.setTarget(mCardFront);
                        mBackAnim.setTarget(mCardBack);
                        mFaceDown = true;
                    }
                    mFrontAnim.start();
                    mBackAnim.start();
                }
            }
        });
    }

    // setget functions for the card text:
    public void setText(String newText) {
        mCardText = newText;
        mCardFront.setText(newText);
    }

    public String getText() {
        return mCardText;
    }

    public boolean checkMatch(String[] values){
        if (values[0].equals(values[1])){
            return true;
        }
        return false;
    }
}
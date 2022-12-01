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

    public GameCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // handle initialization here in case we need multiple constructors
    private void init() {
        // use the layout
        inflate(getContext(), R.layout.view_card, this);

        mCardFront = (TextView) findViewById(R.id.card_front);
        mCardBack = (TextView) findViewById(R.id.card_back);
        mFlipButton = (Button) findViewById(R.id.flip_button);

        mFaceDown = true;

        float scale = getContext().getResources().getDisplayMetrics().density;

        mCardFront.setCameraDistance(8000 * scale);
        mCardBack.setCameraDistance(8000 * scale);

        mFrontAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.front_animator);
        mBackAnim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), R.animator.back_animator);

        mFlipButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("GameCard", "flip button clicked");

                if(mFaceDown) {
                    mFrontAnim.setTarget(mCardBack);
                    mBackAnim.setTarget(mCardFront);
                    mFaceDown = false;
                }
                else {
                    mFrontAnim.setTarget(mCardFront);
                    mBackAnim.setTarget(mCardBack);
                    mFaceDown = true;
                }
                mFrontAnim.start();
                mBackAnim.start();
            }
        });
    }
}
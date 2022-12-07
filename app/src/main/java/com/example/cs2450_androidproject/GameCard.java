package com.example.cs2450_androidproject;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GameCard extends FrameLayout {
    String mCardText;
    TextView mCardFront;
    ImageView mCardBack;
    Button mFlipButton;

    AnimatorSet mFrontAnim;
    AnimatorSet mBackAnim;

    CardListener listener;

    boolean mFaceDown; // true when the card is facedown; used for animation

    boolean mMatched; // true if the card has been matched with another

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
        mCardBack = (ImageView) findViewById(R.id.card_back);
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
                if(listener != null) {
                    listener.onClick(GameCard.this);
                }
            }
        });
    }

    // the following two functions have been made public to allow other places to easily
    // flip the card up and down
    public void flipUp() {
        if (mFaceDown) {
            mFrontAnim.setTarget(mCardBack);
            mBackAnim.setTarget(mCardFront);

            mFrontAnim.start();
            mBackAnim.start();

            mFaceDown = false;
        }
    }

    public void flipDown() {
        if (!mFaceDown) {
            mFrontAnim.setTarget(mCardFront);
            mBackAnim.setTarget(mCardBack);

            mFrontAnim.start();
            mBackAnim.start();

            mFaceDown = true;
        }
    }

    // setget functions
    public void setText(String newText) {
        mCardText = newText;
        mCardFront.setText(newText);
    }

    public String getText() {
        return mCardText;
    }

    public void setMatched(boolean newMatched) {
        this.mMatched = newMatched;
    }

    public boolean isMatched() {
        return mMatched;
    }

    public boolean checkMatch(String[] values) {
        if (values[0].equals(values[1])) {
            return true;
        }
        return false;
    }

    public boolean isFaceDown() { return mFaceDown; }

    public void setListener(CardListener listener) {
        this.listener = listener;
    }

    public interface CardListener {
        public void onClick(GameCard card);
    }
}
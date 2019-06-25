package com.taluo.app.feature.tarot1;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.taluo.app.R;

public class TarotAnimActivity extends Activity {

    public static void start(Context context){
        context.startActivity(new Intent(context, TarotAnimActivity.class));
    }

    Button mOkButton;
    RelativeLayout mCardContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarot);
        initView();
        createTarotCardViews();
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCardAnimation();
            }
        });
    }

    private void initView() {
        mOkButton = findViewById(R.id.ok_button);
        mCardContainer = findViewById(R.id.layout_card_container);
    }

    private void createTarotCardViews() {
        mCardContainer.removeAllViews();
        for (int i = 0; i < 19; i++) {
            TarotCardView view = new TarotCardView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(params);
            mCardContainer.addView(view);
        }
    }

    private void startCardAnimation() {
        for (int i = 0; i < mCardContainer.getChildCount(); i++) {
            final TarotCardView tarotCardView = (TarotCardView) mCardContainer.getChildAt(i);
            final View innerCardView = tarotCardView.getInnerCardView();
            final View outerCardView = tarotCardView.getOuterCardView();

            final int duration = 500;
            final int fromPosition = 0;
            int toPosition = -20 * (i + 1);
            final int outSidePosition = -45 + (i * 5);

            if (i == 18) {
                toPosition = 0;
                tarotCardView.setVisibility(View.GONE);
            } else {
                tarotCardView.setVisibility(View.VISIBLE);
            }
            outerCardView.setVisibility(View.GONE);
            startInnerRotationAnim(innerCardView, duration, fromPosition, toPosition, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    innerCardView.setVisibility(View.GONE);
                    outerCardView.setVisibility(View.VISIBLE);
                    tarotCardView.setVisibility(View.VISIBLE);
                    startOuttrotationAmin(outerCardView, duration, fromPosition, outSidePosition);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

        final TarotCardView tarotCardView = (TarotCardView) mCardContainer.getChildAt(5);
        final View lastView = tarotCardView.getLastView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                zoomInReviveCode(lastView);
            }
        }, 4000);

    }


    public void startInnerRotationAnim(final View innerCardView, int duration, int fromPosition, int toPosition, Animator.AnimatorListener listener) {
        ObjectAnimator view1Anim = ObjectAnimator.ofFloat(innerCardView, "rotation", fromPosition, toPosition);
        view1Anim.setDuration(duration);
        view1Anim.setInterpolator(new LinearInterpolator());

        ObjectAnimator view2Anim = ObjectAnimator.ofFloat(innerCardView, "rotation", toPosition, fromPosition);
        view2Anim.setDuration(duration);
        view2Anim.setStartDelay(500);
        view2Anim.setInterpolator(new LinearInterpolator());

        AnimatorSet mAnimSet = new AnimatorSet();
        mAnimSet.playSequentially(view1Anim, view2Anim);
        mAnimSet.addListener(listener);
        mAnimSet.start();


    }

    public void startOuttrotationAmin(View outCardView, int duration, int fromPosition, int toPosition) {
        ObjectAnimator view3Anim = ObjectAnimator.ofFloat(outCardView, "rotation", fromPosition, toPosition);
        view3Anim.setDuration(duration);
        view3Anim.setStartDelay(300);
        view3Anim.setInterpolator(new LinearInterpolator());

        ObjectAnimator view4Anim = ObjectAnimator.ofFloat(outCardView, "rotation", toPosition, fromPosition);
        view4Anim.setStartDelay(300);
        view4Anim.setDuration(duration);
        view4Anim.setInterpolator(new LinearInterpolator());

        AnimatorSet mAnimSet = new AnimatorSet();
        mAnimSet.playSequentially(view3Anim, view4Anim);
        mAnimSet.start();
    }


    public void zoomInReviveCode(final View target) {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(target, "scaleX", 1.25f, 1);
        animator1.setRepeatCount(5);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(target, "scaleY", 1.25f, 1);
        animator2.setRepeatCount(5);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(target, "translationX", 500, 0);
        animator3.setRepeatCount(5);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(target, "rotation", 20, 0);
        animator4.setRepeatCount(5);

        AnimatorSet animSet = new AnimatorSet();
        animSet.setDuration(400);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.playTogether(animator1, animator2, animator3, animator4);
        animSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                target.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animSet.start();
    }
}

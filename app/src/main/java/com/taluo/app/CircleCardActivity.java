package com.taluo.app;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by chenshaolong on 2019/2/27.
 */

public class CircleCardActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, CircleCardActivity.class));
    }

    RelativeLayout mViewContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_card);
        mViewContainer = findViewById(R.id.layout_container);

        int maxSize = 18;
        for (int i = 0; i < maxSize; i++) {
            View view = getCardView();
//            view.setRotation((maxSize - i) * 10 - 60);
            view.setRotation(-60);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dp2px(this, 320), dp2px(this, 440));
            params.setMargins(0, 0, 0, dp2px(this, -250));
            view.setLayoutParams(params);
            mViewContainer.addView(view);
        }

        for (int i = 0; i < mViewContainer.getChildCount(); i++) {
            View view = mViewContainer.getChildAt(i);
            startRotationAnim(view, -60, (maxSize - i) * 10 - 60);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mViewContainer.getChildCount(); i++) {
                    View view = mViewContainer.getChildAt(i);
                    startAnim(view, view.getRotation());
                }
            }
        }, 5000);


    }

    private View getCardView() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        return mInflater.inflate(R.layout.layout_circle_card, null);
    }


    public static int dp2px(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }


    public void startRotationAnim(final View innerCardView, int fromPosition, int toPosition) {
        ObjectAnimator view1Anim = ObjectAnimator.ofFloat(innerCardView, "rotation", fromPosition, toPosition);
        view1Anim.setDuration(1000);
        view1Anim.setInterpolator(new LinearInterpolator());
        AnimatorSet mAnimSet = new AnimatorSet();
        mAnimSet.play(view1Anim);
        mAnimSet.setStartDelay(1000);
        mAnimSet.start();
    }


    public void startAnim(final View innerCardView, float from) {
        ObjectAnimator view1Anim = ObjectAnimator.ofFloat(innerCardView, "rotation", from, from - 50, from, from + 50, from);
        view1Anim.setDuration(5000);
        view1Anim.setInterpolator(new LinearInterpolator());
        AnimatorSet mAnimSet = new AnimatorSet();
        mAnimSet.play(view1Anim);
        mAnimSet.start();
    }

}


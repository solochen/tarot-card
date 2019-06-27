package com.taluo.app.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.taluo.app.feature.tarot2.MyAnimatorListener;

/**
 * Created by chenshaolong on 2019/2/26.
 */

public class AnimHelper {

    public static void startAnimation(int duration, int delayTime, final View mCardView, int goByX, int goByY, int doneY, Animator.AnimatorListener listener) {
        ValueAnimator animator1 = ValueAnimator.ofObject(new PointEvaluator(new PointF(mCardView.getX() + goByX, mCardView.getY() + goByY)),
                new PointF(mCardView.getX(), mCardView.getY()),
                new PointF(mCardView.getX(), mCardView.getY() + doneY));
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mCardView.setX(pointF.x);
                mCardView.setY(pointF.y);
            }
        });


        ValueAnimator animator2 = ValueAnimator.ofObject(new PointEvaluator(new PointF(mCardView.getX() - goByX, mCardView.getY() + goByY)),
                new PointF(mCardView.getX(), mCardView.getY() + doneY),
                new PointF(mCardView.getX(), mCardView.getY()));
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mCardView.setX(pointF.x);
                mCardView.setY(pointF.y);
            }
        });

        ObjectAnimator view1Anim = ObjectAnimator.ofFloat(mCardView, "rotation",
                0, 30, 60, 90, 120, 150, 180, 210, 240, 270, 300, 330, 360,
                390, 420, 450, 480, 510, 540, 570, 600, 630, 660, 690, 720);
        view1Anim.setInterpolator(new LinearInterpolator());

        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setInterpolator(new LinearInterpolator());

        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setInterpolator(new LinearInterpolator());

        AnimatorSet set2 = new AnimatorSet();
        set2.play(animator2).with(view1Anim);

        AnimatorSet set1 = new AnimatorSet();
        set1.play(animator1).with(view1Anim).before(set2);
        set1.setDuration(duration);
        set1.setStartDelay(delayTime);
        set1.addListener(listener);
        set1.start();
    }


    public static void translateXAnim(final View leftView, final View rightView, Animator.AnimatorListener listener) {
        ValueAnimator leftAnimator = ValueAnimator.ofFloat(leftView.getX(), leftView.getX() - 280, leftView.getX());
        leftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float x = (float) valueAnimator.getAnimatedValue();
                leftView.setX(x);
            }
        });

        ValueAnimator rightAnimator = ValueAnimator.ofFloat(rightView.getX(), rightView.getX() + 280, rightView.getX());
        rightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float x = (float) valueAnimator.getAnimatedValue();
                rightView.setX(x);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(leftAnimator).with(rightAnimator);
        animatorSet.setDuration(1500);
        animatorSet.addListener(listener);
        animatorSet.start();
    }


    public static void zoomOutAnimatorWithTranslation(View view, int fromX, int fromY) {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(view, "translationX", fromX, 0);
        translationX.setInterpolator(new LinearInterpolator());

        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", fromY, 0);
        translationY.setInterpolator(new LinearInterpolator());

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.7f);
        scaleX.setInterpolator(new LinearInterpolator());

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.7f);
        scaleY.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.playTogether(translationX, translationY, scaleX, scaleY);
        animatorSet.start();
    }

    public static void zoomOutAnimator(View view, Animator.AnimatorListener listener) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.7f);
        scaleX.setInterpolator(new LinearInterpolator());

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.7f);
        scaleY.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(400);
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.addListener(listener);
        animatorSet.start();
    }

    public static void translateYToTopAnim(final View view, int toY, Animator.AnimatorListener listener) {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", -toY);
        translationY.setInterpolator(new LinearInterpolator());
        translationY.setDuration(400);
        translationY.addListener(listener);
        translationY.start();
    }
}

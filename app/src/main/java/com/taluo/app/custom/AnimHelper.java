package com.taluo.app.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.View;
import android.view.animation.LinearInterpolator;

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
//        view1Anim.setDuration(6000);
        view1Anim.setInterpolator(new LinearInterpolator());

        AnimatorSet set1 = new AnimatorSet();
        AnimatorSet set2 = new AnimatorSet();
        AnimatorSet set3 = new AnimatorSet();
        AnimatorSet set4 = new AnimatorSet();

        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setInterpolator(new LinearInterpolator());
//        animator1.setDuration(3000);

        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setInterpolator(new LinearInterpolator());
//        animator2.setDuration(3000);

        set4.play(animator2).with(view1Anim);
        set3.play(animator1).with(view1Anim).before(set4);

        set2.play(animator2).with(view1Anim).before(set3);
//        set2.setDuration(3000);

        set1.play(animator1).with(view1Anim).before(set2);
        set1.setDuration(duration);
        set1.setStartDelay(delayTime);
        set1.addListener(listener);
        set1.start();
    }


    public static void translateYdownAnim(final View view, Animator.AnimatorListener listener) {
        ValueAnimator animator = ValueAnimator.ofFloat(view.getY(), view.getY() + 50);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float pointY = (float) valueAnimator.getAnimatedValue();
                view.setY(pointY);
            }
        });
        animator.setDuration(500);
        animator.addListener(listener);
        animator.start();
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

    public static void lastAnimation(final View mCardView, int leftTopX, int leftTopY, int rightX, int rightY) {

        float newX = mCardView.getX() - leftTopX;
        float newY = mCardView.getY() - leftTopY;

        ValueAnimator animator1 = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(mCardView.getX(), mCardView.getY()),
                new PointF(newX, newY));
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mCardView.setX(pointF.x);
                mCardView.setY(pointF.y);
            }
        });


        ValueAnimator animator2 = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(newX, newY),
                new PointF(newX + rightX, newY + rightY));
        animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mCardView.setX(pointF.x);
                mCardView.setY(pointF.y);
            }
        });

        ObjectAnimator view1Anim = ObjectAnimator.ofFloat(mCardView, "rotation", 0, 30);
        view1Anim.setInterpolator(new LinearInterpolator());

        ObjectAnimator view2Anim = ObjectAnimator.ofFloat(mCardView, "rotation", 0, -10);
        view2Anim.setInterpolator(new LinearInterpolator());

        AnimatorSet set1 = new AnimatorSet();
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setInterpolator(new LinearInterpolator());
        animator2.setRepeatMode(ValueAnimator.REVERSE);
        animator2.setInterpolator(new LinearInterpolator());

        AnimatorSet set2 = new AnimatorSet();
        set2.play(animator2).with(view2Anim);

        set1.play(animator1).with(view1Anim).before(set2);
        set1.setDuration(1500);
        set1.start();
    }
}

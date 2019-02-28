package com.taluo.app;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.taluo.app.Utils.ScreenUtil;
import com.taluo.app.custom.BezierEvaluator;
import com.taluo.app.listener.OnItemClickListener;
import com.taluo.app.widget.TarotCardLayout;

/**
 * Created by chenshaolong on 2019/2/27.
 */

public class TarotCircleCardActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TarotCircleCardActivity.class));
    }

    TarotCardLayout mTarotCardLayout;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarot_circle_card);
        mContext = this;
        mTarotCardLayout = findViewById(R.id.card_layout);
        mTarotCardLayout.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View chooseView, View outCardView, View innerCardView, int position, View tarotDecodeLayout, View topRightPoint) {
                float centerX = chooseView.getX() + ScreenUtil.dp2px(mContext, 80);
                float centerY = chooseView.getY() + ScreenUtil.dp2px(mContext, 110);
                translateXAnim(chooseView, outCardView, innerCardView, centerX, centerY, tarotDecodeLayout, topRightPoint);
                setCameraDistance(innerCardView, chooseView);
            }
        });
    }


    public void translateXAnim(final View chooseView,
                               final View outCardView,
                               final View innerCardView,
                               float centerX, float centerY,
                               final View tarotDecodeLayout,
                               final View topRightPoint) {
        ValueAnimator animator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(outCardView.getX(), outCardView.getY()),
                new PointF(centerX - ScreenUtil.dp2px(mContext, 80), centerY - ScreenUtil.dp2px(mContext, 110)));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                outCardView.setX(pointF.x);
                outCardView.setY(pointF.y);
            }
        });

        ObjectAnimator view1Anim = ObjectAnimator.ofFloat(outCardView, "rotation", outCardView.getRotation(), 0);
        view1Anim.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(view1Anim);
        animatorSet.setDuration(1000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                rotateAnim(innerCardView, chooseView, tarotDecodeLayout, topRightPoint);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }


    private void rotateAnim(View innerCardView, final View chooseView, final View tarotDecodeLayout, final View topRightPoint) {
        chooseView.setVisibility(View.VISIBLE);
        AnimatorSet mDismissSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.rotate_dismiss);
        AnimatorSet mDisplaySet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.rotate_display);
        mDismissSet.setTarget(innerCardView);
        mDisplaySet.setTarget(chooseView);
        mDisplaySet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tarotDecodeLayout.setVisibility(View.VISIBLE);
                float toX =  (float) (topRightPoint.getX() - (chooseView.getWidth() * 0.6) / 2);
                float toY = (float) (topRightPoint.getY() - (chooseView.getHeight() * 0.6) / 2);
                translateAnim(chooseView, toX, toY);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mDismissSet.start();
        mDisplaySet.start();
    }

    public void translateAnim(final View innerCardView, float x, float y) {
        ValueAnimator animator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(innerCardView.getX(), innerCardView.getY()),
                new PointF(x, y));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                innerCardView.setX(pointF.x);
                innerCardView.setY(pointF.y);
            }
        });

        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(innerCardView, "scaleX", 1f, 0.6f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(innerCardView, "scaleY", 1f, 0.6f);
        scaleXAnim.setInterpolator(new LinearInterpolator());
        scaleYAnim.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(scaleXAnim).with(scaleYAnim);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    // 改变视角距离, 贴近屏幕
    private void setCameraDistance(View innerCardView, View chooseView) {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        chooseView.setCameraDistance(scale);
        innerCardView.setCameraDistance(scale);
    }

}


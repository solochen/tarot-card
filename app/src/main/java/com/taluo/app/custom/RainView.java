package com.taluo.app.custom;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by chenshaolong on 2019/2/22.
 */

public class RainView extends ImageView {

    private ViewGroup mViewGroup;
    int[] durTimes = new int[]{3500, 3600, 3700, 3900, 3900, 4000, 4100, 4200, 4300, 4400, 4600, 5100, 5500, 5800};

    public RainView(Context context, ViewGroup pViewGroup) {
        super(context);
        setScaleType(ImageView.ScaleType.CENTER_CROP);
        setAdjustViewBounds(true);
        initData(pViewGroup);
    }


    /**
     * 初始化数据
     */
    private void initData(ViewGroup pViewGroup) {
        mViewGroup = pViewGroup;
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pViewGroup.addView(this, params);

        int[] _points = new int[2];
        int _containerWidth = pViewGroup.getWidth();
        int _containerHeight = pViewGroup.getHeight();

        float x = getX();
        float y = getY();
        Log.e("solo", "x:" + x);
        Log.e("solo", "y:" + y);
        _points[0] = new Random().nextInt(_containerWidth);
        _points[1] = new Random().nextInt(_containerWidth);
        Log.e("solo", "points0:" + _points[0]);
        Log.e("solo", "points1:" + _points[1]);
        ValueAnimator animator1 = ValueAnimator.ofObject(new BezierEvaluator(), new PointF(_points[0], -90), new PointF(_points[1] - 90, _containerHeight - 50));
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                RainView.this.setX(pointF.x);
                RainView.this.setY(pointF.y);
            }
        });


        AnimatorSet set = new AnimatorSet();
        set.play(animator1);
        animator1.setRepeatMode(ValueAnimator.REVERSE);
        animator1.setDuration(durTimes[randomInt(durTimes.length - 1)]);
        animator1.setTarget(RainView.this);
        set.start();
    }


    private int randomInt(int size) {
        Random r = new Random();
        return r.nextInt(size);
    }
}


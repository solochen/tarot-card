package com.taluo.app.custom;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by chenshaolong on 2019/2/22.
 */

public class BezierEvaluator implements TypeEvaluator<PointF> {

    @Override
    public PointF evaluate(float fraction, PointF startValue,
                           PointF endValue) {
        return BezierCurve.bezier(fraction, startValue, endValue);
    }


}

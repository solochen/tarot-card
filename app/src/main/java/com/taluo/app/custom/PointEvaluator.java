package com.taluo.app.custom;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by chenshaolong on 2019/2/22.
 */

public class PointEvaluator implements TypeEvaluator<PointF> {

    private PointF point1;

    public PointEvaluator(PointF targetPointF){
        point1 = targetPointF;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue,
                           PointF endValue) {
        final float t = fraction;
        float oneMinusT = 1.0f - t;
        PointF point = new PointF();

        PointF point0 = (PointF) startValue;

        PointF point2 = endValue;
//        point2.set(0, 320);

        PointF point3 = (PointF) endValue;

        //2次贝塞尔算法
        point.x = oneMinusT * oneMinusT * (point0.x)
                + 2 * (point1.x) * t *  oneMinusT
                + t * t * (point2.x);

        point.y = oneMinusT * oneMinusT * (point0.y)
                + 2 * (point1.y) * t * oneMinusT
                + t * t * (point2.y);

//        //3次贝塞尔算法
//        point.x = oneMinusT * oneMinusT * oneMinusT * (point0.x)
//                + 3 * oneMinusT * oneMinusT * t * (point1.x)
//                + 3 * oneMinusT * t * t * (point2.x)
//                + t * t * t * (point3.x);
//
//        point.y = oneMinusT * oneMinusT * oneMinusT * (point0.y)
//                + 3 * oneMinusT * oneMinusT * t * (point1.y)
//                + 3 * oneMinusT * t * t * (point2.y)
//                + t * t * t * (point3.y);
        return point;
    }


}

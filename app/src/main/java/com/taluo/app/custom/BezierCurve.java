package com.taluo.app.custom;

import android.graphics.PointF;

/**
 * Created by chenshaolong on 2019/2/22.
 */

public class BezierCurve {

    /**
     * 一次贝塞尔
     */
    public static PointF bezier(float t, PointF point0, PointF point1) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must between 0 and 1");
        }
        float oneMinusT = 1.0f - t;
        PointF point = new PointF();
        point.x = oneMinusT * point0.x + t * point1.x;
        point.y = oneMinusT * point0.y + t * point1.y;
        return point;
    }

    /**
     * 二次贝塞尔
     */
    public static PointF bezier(float t, PointF point0, PointF point1, PointF point2) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must between 0 and 1");
        }
        float oneMinusT = 1.0f - t;
        PointF point = new PointF();
        point.x = oneMinusT * oneMinusT * point0.x
                + 2 * t * oneMinusT * point1.x
                + t * t * point2.x;
        point.y = oneMinusT * oneMinusT * point0.y
                + 2 * t * oneMinusT * point1.y
                + t * t * point2.y;
        return point;
    }

    /**
     * 三次贝塞尔
     */
    public static PointF bezier(float t, PointF point0, PointF point1, PointF point2, PointF point3) {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must between 0 and 1");
        }
        float oneMinusT = 1.0f - t;
        PointF point = new PointF();
        point.x = oneMinusT * oneMinusT * oneMinusT * (point0.x)
                + 3 * oneMinusT * oneMinusT * t * (point1.x)
                + 3 * oneMinusT * t * t * (point2.x)
                + t * t * t * (point3.x);

        point.y = oneMinusT * oneMinusT * oneMinusT * (point0.y)
                + 3 * oneMinusT * oneMinusT * t * (point1.y)
                + 3 * oneMinusT * t * t * (point2.y)
                + t * t * t * (point3.y);
        return point;
    }
}

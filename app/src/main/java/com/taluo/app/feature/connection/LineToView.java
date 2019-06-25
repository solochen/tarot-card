package com.taluo.app.feature.connection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by chenshaolong on 2019/5/15.
 */

public class LineToView extends View {

    boolean cleanCanvas;
    Paint paint;
    Path path;
    float pathX = 0f, pathY = 0f;
    float startX = 0f, startY = 0f;

    public LineToView(Context context) {
        super(context);
        init();
    }

    public LineToView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineToView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setPathEffect(new DashPathEffect(new float[]{20f, 5f}, 0f));
        paint.setStrokeWidth(5);

        path = new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!cleanCanvas) {
            if (startX == 0 || startY == 0) {
                return;
            }

            path.reset();
            path.moveTo(startX, startY);
            path.lineTo(pathX, pathY);
            canvas.drawPath(path, paint);
        }
    }


    public void setStartXY(float x, float y) {
        cleanCanvas = false;
        this.startX = x;
        this.startY = y;
    }

    public void setMoveXY(float x, float y) {
        this.pathX = x;
        this.pathY = y;
    }

    public void clearPathLine() {
        cleanCanvas = true;
        invalidate();
    }

}

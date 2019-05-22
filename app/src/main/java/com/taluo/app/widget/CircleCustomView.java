package com.taluo.app.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by chenshaolong on 2019/2/28.
 */

public class CircleCustomView extends View {

    Context mContext;


    public CircleCustomView(Context context) {
        super(context);
        initView(context);
    }

    public CircleCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CircleCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);

        int x = getScreenWidth(mContext) / 2;
        int y = getScreenHeight(mContext) / 2;
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, x - 50, paint);

        Path path = new Path();
        path.moveTo(100, 100);
        path.lineTo(100, 200);
        path.lineTo(200, 150);
        path.close();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, paint);
    }


    private int getScreenWidth(Context mContext) {
        DisplayMetrics mDisplay = mContext.getResources().getDisplayMetrics();
        return mDisplay.widthPixels;
    }

    private int getScreenHeight(Context mContext) {
        DisplayMetrics mDisplay = mContext.getResources().getDisplayMetrics();
        return mDisplay.heightPixels;
    }
}

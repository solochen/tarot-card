package com.taluo.app.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * Created by chenshaolong on 2019/5/21.
 */

public class GRScrollView extends ScrollView {

    public ScrollViewListener scrollViewListener;

    public GRScrollView(Context context) {
        super(context);
    }

    public GRScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GRScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public interface ScrollViewListener {
        FrameLayout getFirstLayout();

        FrameLayout getSecondLayout();

        FrameLayout getThirdLayout();

        FrameLayout getForthLayout();

        FrameLayout getFifthLayout();

        FrameLayout getSixthLayout();

        FrameLayout getSelfLayout();
    }

    public void setScrollViewListener(ScrollViewListener listener) {
        scrollViewListener = listener;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (isTouchPointInViews(ev.getRawX(), ev.getRawY())) {
                return false; //不需要处理
            } else {
                return true;
            }
        } else {
            return false; //不需要处理
        }
    }


    /**
     * 触摸点是否在View范围内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    public boolean isTouchPointInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }
        return false;
    }

    private boolean isTouchPointInViews(float x, float y) {
        if (scrollViewListener == null) return false;
        boolean isInFirstView = isTouchPointInView(scrollViewListener.getFirstLayout(), x, y);
        boolean isInSecondView = isTouchPointInView(scrollViewListener.getSecondLayout(), x, y);
        boolean isInThirdView = isTouchPointInView(scrollViewListener.getThirdLayout(), x, y);
        boolean isInFourthView = isTouchPointInView(scrollViewListener.getForthLayout(), x, y);
        boolean isInFifthView = isTouchPointInView(scrollViewListener.getFifthLayout(), x, y);
        boolean isInSixthView = isTouchPointInView(scrollViewListener.getSixthLayout(), x, y);
        boolean isInSelfView = isTouchPointInView(scrollViewListener.getSelfLayout(), x, y);
        return isInFirstView || isInSecondView || isInThirdView || isInFourthView || isInFifthView || isInSixthView || isInSelfView;
    }
}


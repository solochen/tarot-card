package com.taluo.app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by chenshaolong on 2019/6/24.
 */

public class SlideScrollView extends HorizontalScrollView {

    private boolean isSlide = true;  //控制是否滚动
    OnScrollListener mListener;

    public SlideScrollView(Context context) {
        super(context);
    }

    public SlideScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SlideScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnScrollListener {
        void onScroll(int scrollX, int scrollY, int oldX, int oldY);
    }

    public void setScrollChangedListener(OnScrollListener listener) {
        mListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.onScroll(l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isSlide ? super.onTouchEvent(ev) : false;
    }

    public void setSlide(boolean slide) {
        isSlide = slide;
    }
}

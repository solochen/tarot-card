package com.taluo.app.feature.connection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.taluo.app.R;

/**
 * Created by chenshaolong on 2019/2/27.
 */

public class FindGRActivity extends Activity implements View.OnTouchListener {

    public static final String TAG = FindGRActivity.class.getSimpleName();

    LineToView mLineToView;

    FrameLayout mFirstLayout;
    FrameLayout mSecondLayout;
    FrameLayout mThirdLayout;
    FrameLayout mFourthLayout;
    FrameLayout mFifthLayout;
    FrameLayout mSixthLayout;
    FrameLayout mSelfLayout;

    ImageView mIvSelf;
    GRScrollView mScrollView;

    public static void start(Context context) {
        context.startActivity(new Intent(context, FindGRActivity.class));
    }

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_gr);
        mContext = this;
        mLineToView = findViewById(R.id.line_to_view);

        mFirstLayout = findViewById(R.id.first_friend_layout);
        mSecondLayout = findViewById(R.id.second_friend_layout);
        mThirdLayout = findViewById(R.id.third_friend_layout);
        mFourthLayout = findViewById(R.id.fourth_friend_layout);
        mFifthLayout = findViewById(R.id.fifth_friend_layout);
        mSixthLayout = findViewById(R.id.sixth_friend_layout);
        mSelfLayout = findViewById(R.id.self_layout);

        mIvSelf = findViewById(R.id.iv_self_avatar);
        mScrollView = findViewById(R.id.scrollview_share);

        mLineToView.setOnTouchListener(this);
        mScrollView.setScrollViewListener(new GRScrollView.ScrollViewListener() {

            @Override
            public FrameLayout getFirstLayout() {
                return mFirstLayout;
            }

            @Override
            public FrameLayout getSecondLayout() {
                return mSecondLayout;
            }

            @Override
            public FrameLayout getThirdLayout() {
                return mThirdLayout;
            }

            @Override
            public FrameLayout getForthLayout() {
                return mFourthLayout;
            }

            @Override
            public FrameLayout getFifthLayout() {
                return mFifthLayout;
            }

            @Override
            public FrameLayout getSixthLayout() {
                return mSixthLayout;
            }

            @Override
            public FrameLayout getSelfLayout() {
                return mSelfLayout;
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                float startX = event.getX();
                float startY = event.getY();
                if (isTouchPointInViews(startX, startY)) {
                    mLineToView.setStartXY(startX, startY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mLineToView.setMoveXY(event.getX(), event.getY());
                mLineToView.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float endY = event.getY();
                if (isTouchPointInViews(endX, endY)) {

                } else {
                    mLineToView.clearPathLine();
                }
                break;
        }
        return false;
    }


    /**
     * 触摸点是否在View范围内
     *
     * @param view
     * @param x
     * @param y
     * @return
     */
    private boolean isTouchPointInView(View view, float x, float y) {
        if (view == null) {
            return false;
        }
        int left = (int) view.getX();
        int top = (int) view.getY();
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        if (y >= top && y <= bottom && x >= left && x <= right) {
            return true;
        }
        return false;
    }

    private boolean isTouchPointInViews(float x, float y) {
        boolean isInFirstView = isTouchPointInView(mFirstLayout, x, y);
        boolean isInSecondView = isTouchPointInView(mSecondLayout, x, y);
        boolean isInThirdView = isTouchPointInView(mThirdLayout, x, y);
        boolean isInFourthView = isTouchPointInView(mFourthLayout, x, y);
        boolean isInFifthView = isTouchPointInView(mFifthLayout, x, y);
        boolean isInSixthView = isTouchPointInView(mSixthLayout, x, y);
        boolean isInSelfView = isTouchPointInView(mSelfLayout, x, y);
        return isInFirstView || isInSecondView || isInThirdView || isInFourthView || isInFifthView || isInSixthView || isInSelfView;
    }
}


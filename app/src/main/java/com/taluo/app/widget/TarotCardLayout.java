package com.taluo.app.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.taluo.app.R;
import com.taluo.app.Utils.ScreenUtil;
import com.taluo.app.listener.OnItemClickListener;

/**
 * Created by chenshaolong on 2019/2/28.
 */

public class TarotCardLayout extends FrameLayout {

    Context mContext;
    private float mLastX;
    private float mLastY;
    private float mStartAngle = 0;
    private float mTmpAngle; //旋转角度
    private int mRadius; //半径
    private int mChildItemCount = 18;
    private boolean mCanScroll = true;
    /**
     * 检测按下到抬起时使用的时间
     */
    private long mDownTime;

    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private static final int FLINGABLE_VALUE = 100;
    /**
     * 当每秒移动角度达到该值时，认为是快速移动
     */
    private int mFlingableValue = FLINGABLE_VALUE;

    /**
     * 判断是否正在自动滚动
     */
    private boolean isFling;
    /**
     * 自动滚动的Runnable
     */
    private AutoFlingRunnable mFlingRunnable;

    /**
     * 如果移动角度达到该值，则屏蔽点击
     */
    private static final int NOCLICK_VALUE = 3;

    OnItemClickListener mItemClickListener;

    public TarotCardLayout(Context context) {
        super(context);
        initView(context);
    }

    public TarotCardLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TarotCardLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < mChildItemCount; i++) {
            final int position = i;
            View view = mInflater.inflate(R.layout.layout_circle_card, this, false);
            final View cardView = view.findViewById(R.id.card_view);
            final View outView = view.findViewById(R.id.outer_card_view);
            final View chooseView = view.findViewById(R.id.tarot_choose_view);
            final View tarotDecodeLayout = view.findViewById(R.id.layout_tarot_decode);
            final View topRightPoint = view.findViewById(R.id.right_top_point);
            if (view != null && cardView != null) {
                cardView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mCanScroll = false;
                            mItemClickListener.onItemClick(chooseView, outView, cardView, position, tarotDecodeLayout, topRightPoint);
                            dismissTarotOtherCards(position);
                        }
                    }
                });
            }
            this.addView(view);
        }

        for (int i = 0; i < getChildCount(); i++) {
            final int position = i;
            View view = getChildAt(i);
            final View cardView = view.findViewById(R.id.card_view);
            final View outView = view.findViewById(R.id.outer_card_view);
            final View chooseView = view.findViewById(R.id.tarot_choose_view);
            final View tarotDecodeLayout = view.findViewById(R.id.layout_tarot_decode);
            final View topRightPoint = view.findViewById(R.id.right_top_point);
            if (view != null && cardView != null) {
                cardView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mCanScroll = false;
                            mItemClickListener.onItemClick(chooseView, outView, cardView, position, tarotDecodeLayout, topRightPoint);
                            dismissTarotOtherCards(position);
                        }
                    }
                });
            }
            startRotationAnim(outView, -60, (getChildCount() - i) * 10 - 60);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(specWidth, specHeight);
        mRadius = Math.max(getMeasuredWidth(), getMeasuredHeight());
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(!mCanScroll) {
            return true;
        }
        float x = ev.getX();
        float y = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mTmpAngle = 0;
                mDownTime = System.currentTimeMillis();
                // 如果当前已经在快速滚动
                if (isFling) {
                    // 移除快速滚动的回调
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float startTouchAngle = getAngle(mLastX, mLastY);
                float endTouchAngle = getAngle(x, y);
                // 如果是一、四象限，则直接end-start，角度值都是正值
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += endTouchAngle - startTouchAngle;
                    mTmpAngle += endTouchAngle - startTouchAngle;
                } else
                // 二、三象限，色角度值是付值
                {
                    mStartAngle += startTouchAngle - endTouchAngle;
                    mTmpAngle += startTouchAngle - endTouchAngle;
                }
                translateView();
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:

//                // 计算，每秒移动的角度
                float anglePerSecond = mTmpAngle * 1000 / (System.currentTimeMillis() - mDownTime);
                // 如果达到该值认为是快速移动
                if (Math.abs(anglePerSecond) > mFlingableValue && !isFling) {
                    // post一个任务，去自动滚动
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));

                    return true;
                }

                // 如果当前旋转角度超过NOCLICK_VALUE屏蔽点击
                if (Math.abs(mTmpAngle) > NOCLICK_VALUE) {
                    return true;
                }

                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    private void translateView() {
        mStartAngle = mStartAngle >= 70 ? 70 : mStartAngle;
        mStartAngle = mStartAngle <= -40 ? -40 : mStartAngle;

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int position = i;
            final View view = getChildAt(i);
            final View cardView = view.findViewById(R.id.card_view);
            final View outView = view.findViewById(R.id.outer_card_view);
            final View chooseView = view.findViewById(R.id.tarot_choose_view);
            final View tarotDecodeLayout = view.findViewById(R.id.layout_tarot_decode);
            final View topRightPoint = view.findViewById(R.id.right_top_point);
            if (view != null && cardView != null) {
                cardView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mItemClickListener != null) {
                            mCanScroll = false;
                            mItemClickListener.onItemClick(chooseView, outView, cardView, position, tarotDecodeLayout, topRightPoint);
                            dismissTarotOtherCards(position);
                        }
                    }
                });
            }
            if (view.getVisibility() == GONE) {
                continue;
            }
            float es = (getChildCount() - i) * 10 - 60;
            outView.setRotation(-mStartAngle + es);
        }
    }


    private void dismissTarotOtherCards(int exceptViewPosition) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (exceptViewPosition == i) {
                continue;
            }
            View view = getChildAt(i);
            TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
            mHiddenAction.setDuration(1000);
            view.setVisibility(View.GONE);
            view.setAnimation(mHiddenAction);
        }
    }

    /**
     * 获取旋转的角度
     *
     * @param xTouch 触摸的x坐标
     * @param yTouch 触摸的y坐标
     * @return
     */
    private float getAngle(float xTouch, float yTouch) {
        double x = xTouch - (mRadius / 2d);
        double y = yTouch - (mRadius / 2d);
        return (float) (Math.asin(y / Math.hypot(x, y)) * 180 / Math.PI);
    }

    /**
     * 根据当前位置计算象限
     *
     * @param x
     * @param y
     * @return
     */
    private int getQuadrant(float x, float y) {
        int tmpX = (int) (x - mRadius / 2);
        int tmpY = (int) (y - mRadius / 2);
        if (tmpX >= 0) {
            return tmpY >= 0 ? 4 : 1;
        } else {
            return tmpY >= 0 ? 3 : 2;
        }

    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    public void startRotationAnim(final View innerCardView, float fromPosition, float toPosition) {
        ObjectAnimator view1Anim = ObjectAnimator.ofFloat(innerCardView, "rotation", fromPosition, toPosition);
        view1Anim.setDuration(1000);
        view1Anim.setInterpolator(new LinearInterpolator());
        AnimatorSet mAnimSet = new AnimatorSet();
        mAnimSet.play(view1Anim);
        mAnimSet.start();
    }

    public void setCanScroll(boolean scroll){
        this.mCanScroll = scroll;
    }

    private class AutoFlingRunnable implements Runnable {

        private float angelPerSecond;

        public AutoFlingRunnable(float velocity) {
            this.angelPerSecond = velocity;
        }

        public void run() {
            // 如果小于20,则停止
            if ((int) Math.abs(angelPerSecond) < 20) {
                isFling = false;
                return;
            }
            isFling = true;
            // 不断改变mStartAngle，让其滚动，/30为了避免滚动太快
            mStartAngle += (angelPerSecond / 40);
            // 逐渐减小这个值
            angelPerSecond /= 1.0666F;
            postDelayed(this, 40);
            translateView();
        }
    }
}

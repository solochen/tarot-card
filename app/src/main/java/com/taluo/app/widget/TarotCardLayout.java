package com.taluo.app.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.taluo.app.R;
import com.taluo.app.custom.BezierEvaluator;
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
    private int mChildItemCount = 26;
    private boolean mCanTouchScroll; //是否可以用手势滑动
    private long mDownTime;
    private int mFlingAbleValue = 100;
    private boolean isFling;
    private int mCardWidth; //卡片宽度
    private int mCardHeight; //卡片高度
    private AutoFlingRunnable mFlingRunnable;
    private static final int MAX_CAN_CLICK_ANGLE = 3;
    private static final int CARD_INIT_ANGLE = -60; //卡片开始展开时的角度
    private static final int CARD_SPACE_ANGLE = 10; //每张卡片相差的角度
    private static int RIGHT_MAX_ANGLE = 130; //滑动到右边的最大角度
    private static int LEFT_MAX_ANGLE = -40; //滑动到左边的最大角度

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
        mCardWidth = mContext.getResources().getDimensionPixelSize(R.dimen.card_width);
        mCardHeight = mContext.getResources().getDimensionPixelSize(R.dimen.card_height);
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < mChildItemCount; i++) {
            final int position = i;
            View view = mInflater.inflate(R.layout.layout_circle_card, this, false);
            final View cardView = view.findViewById(R.id.card_view);
            final View outView = view.findViewById(R.id.outer_card_view);
            final View chooseView = view.findViewById(R.id.tarot_choose_view);
            final View tarotDecodeLayout = view.findViewById(R.id.layout_tarot_decode);
            final View topRightPoint = view.findViewById(R.id.right_top_point);
            if (i % 2 == 0) {
                view.setRotation(2);
            } else if (i % 3 == 0) {
                view.setRotation(-2);
            }
            cardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mCanTouchScroll = false;
                        expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topRightPoint);
                        dismissTarotOtherCards(position);
                    }
                }
            });
            this.addView(view);
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
        if (!mCanTouchScroll) {
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
                if (isFling) {
                    removeCallbacks(mFlingRunnable);
                    isFling = false;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float startTouchAngle = getAngle(mLastX, mLastY);
                float endTouchAngle = getAngle(x, y);
                if (getQuadrant(x, y) == 1 || getQuadrant(x, y) == 4) {
                    mStartAngle += endTouchAngle - startTouchAngle;
                    mTmpAngle += endTouchAngle - startTouchAngle;
                } else {
                    mStartAngle += startTouchAngle - endTouchAngle;
                    mTmpAngle += startTouchAngle - endTouchAngle;
                }
                translateView();
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                float anglePerSecond = mTmpAngle * 1000 / (System.currentTimeMillis() - mDownTime);
                if (Math.abs(anglePerSecond) > mFlingAbleValue && !isFling) {
                    post(mFlingRunnable = new AutoFlingRunnable(anglePerSecond));
                    return true;
                }
                if (Math.abs(mTmpAngle) > MAX_CAN_CLICK_ANGLE) {
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


    /**
     * 开始展牌
     */
    public void startExpendCard() {
        for (int i = 0; i < getChildCount(); i++) {
            final int position = i;
            View view = getChildAt(i);
            view.setRotation(0);
            final View cardView = view.findViewById(R.id.card_view);
            final View outView = view.findViewById(R.id.outer_card_view);
            final View chooseView = view.findViewById(R.id.tarot_choose_view);
            final View tarotDecodeLayout = view.findViewById(R.id.layout_tarot_decode);
            final View topRightPoint = view.findViewById(R.id.right_top_point);
            cardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mItemClickListener != null) {
                        mCanTouchScroll = false;
                        expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topRightPoint);
                        dismissTarotOtherCards(position);
                    }
                }
            });
            startRotationAnim(outView, 0, CARD_INIT_ANGLE, (getChildCount() - i) * CARD_SPACE_ANGLE + CARD_INIT_ANGLE);
        }
    }

    private void translateView() {
        mStartAngle = mStartAngle >= RIGHT_MAX_ANGLE ? RIGHT_MAX_ANGLE : mStartAngle;
        mStartAngle = mStartAngle <= LEFT_MAX_ANGLE ? LEFT_MAX_ANGLE : mStartAngle;

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
                            mCanTouchScroll = false;
                            expendCardAnim(chooseView, outView, cardView, tarotDecodeLayout, topRightPoint);
                            dismissTarotOtherCards(position);
                        }
                    }
                });
            }
            if (view.getVisibility() == GONE) {
                continue;
            }
            float es = (getChildCount() - i) * CARD_SPACE_ANGLE + CARD_INIT_ANGLE;
            outView.setRotation(-mStartAngle + es);
        }
    }


    /**
     * 隐藏其他塔罗牌
     *
     * @param exceptViewPosition
     */
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


    public void startRotationAnim(final View innerCardView, float startAngle, float passAngle, float endAngle) {

        float translateLeftX = mCardWidth / 2;
        float translateBottomY = getScreenHeight(mContext) / 2 - mCardHeight / 2;

        //平移卡片到底部待展开形似
        ObjectAnimator translationXAnim = ObjectAnimator.ofFloat(innerCardView, "translationX", -translateLeftX);
        ObjectAnimator translationYAnim = ObjectAnimator.ofFloat(innerCardView, "translationY", translateBottomY);
        //卡片旋转到初始角度待展开
        ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(innerCardView, "rotation", startAngle, passAngle);
        //卡片开始展开，没张卡片旋转的角度通过公式计算：(getChildCount() - i) * 10 - 60
        ObjectAnimator afterRotationAnim = ObjectAnimator.ofFloat(innerCardView, "rotation", passAngle, endAngle);
        translationXAnim.setInterpolator(new LinearInterpolator());
        translationYAnim.setInterpolator(new LinearInterpolator());
        rotationAnim.setInterpolator(new LinearInterpolator());
        afterRotationAnim.setInterpolator(new LinearInterpolator());
        AnimatorSet mAnimSet = new AnimatorSet();
        mAnimSet.setDuration(1000);
        mAnimSet.play(translationXAnim).with(translationYAnim).with(rotationAnim).before(afterRotationAnim);
        mAnimSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //展牌结束后才可以用手势滑动
                mCanTouchScroll = true;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mAnimSet.start();
    }

    /**
     * 对选择的卡片进行展示动画
     *
     * @param chooseView
     * @param outCardView
     * @param innerCardView
     * @param tarotDecodeLayout
     * @param topRightPoint
     */
    private void expendCardAnim(final View chooseView,
                                final View outCardView,
                                final View innerCardView,
                                final View tarotDecodeLayout,
                                final View topRightPoint) {
        float centerX = chooseView.getX() + mCardWidth / 2;
        float centerY = chooseView.getY() + mCardHeight / 2;
        setCameraDistance(innerCardView, chooseView);

        ValueAnimator animator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(outCardView.getX(), outCardView.getY()),
                new PointF(centerX - mCardWidth / 2, centerY - mCardHeight / 2));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                outCardView.setX(pointF.x);
                outCardView.setY(pointF.y);
            }
        });

        ObjectAnimator view1Anim = ObjectAnimator.ofFloat(outCardView, "rotation", outCardView.getRotation(), 0);
        view1Anim.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(view1Anim);
        animatorSet.setDuration(1000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                cardDanceAnim(innerCardView, chooseView, tarotDecodeLayout, topRightPoint);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }


    /**
     * 卡牌翻转动画
     *
     * @param innerCardView
     * @param chooseView
     * @param tarotDecodeLayout
     * @param topRightPoint
     */
    private void cardDanceAnim(View innerCardView, final View chooseView, final View tarotDecodeLayout, final View topRightPoint) {
        chooseView.setVisibility(View.VISIBLE);
        AnimatorSet mDismissSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.rotate_dismiss);
        AnimatorSet mDisplaySet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.rotate_display);
        mDismissSet.setTarget(innerCardView);
        mDisplaySet.setTarget(chooseView);
        mDisplaySet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                tarotDecodeLayout.setVisibility(View.VISIBLE);
                float toX = (float) (topRightPoint.getX() - (chooseView.getWidth() * 0.6) / 2);
                float toY = (float) (topRightPoint.getY() - (chooseView.getHeight() * 0.6) / 2);
                translateTopRightAnim(chooseView, toX, toY);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        mDismissSet.start();
        mDisplaySet.start();
    }


    /**
     * 卡片翻转后的平移右上角的动画
     *
     * @param innerCardView
     * @param x
     * @param y
     */
    public void translateTopRightAnim(final View innerCardView, float x, float y) {
        ValueAnimator animator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(innerCardView.getX(), innerCardView.getY()),
                new PointF(x, y));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                innerCardView.setX(pointF.x);
                innerCardView.setY(pointF.y);
            }
        });

        ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(innerCardView, "scaleX", 1f, 0.6f);
        ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(innerCardView, "scaleY", 1f, 0.6f);
        scaleXAnim.setInterpolator(new LinearInterpolator());
        scaleYAnim.setInterpolator(new LinearInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator).with(scaleXAnim).with(scaleYAnim);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    /**
     * 翻转卡片时，改变视角距离, 贴近屏幕
     *
     * @param innerCardView
     * @param chooseView
     */
    private void setCameraDistance(View innerCardView, View chooseView) {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        chooseView.setCameraDistance(scale);
        innerCardView.setCameraDistance(scale);
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

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    private class AutoFlingRunnable implements Runnable {
        private float angelPerSecond;

        public AutoFlingRunnable(float velocity) {
            this.angelPerSecond = velocity;
        }

        public void run() {
            if ((int) Math.abs(angelPerSecond) < 20) {
                isFling = false;
                return;
            }
            isFling = true;
            mStartAngle += (angelPerSecond / 40);
            angelPerSecond /= 1.0666F;
            postDelayed(this, 40);
            translateView();
        }
    }
}

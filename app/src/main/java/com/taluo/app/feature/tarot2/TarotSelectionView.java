package com.taluo.app.feature.tarot2;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.taluo.app.R;
import com.taluo.app.custom.BezierEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshaolong on 2019/2/19.
 */

public class TarotSelectionView extends FrameLayout {

    Context mContext;
    TextView mTvSelection;
    FrameLayout mCardContainer;
    SlideScrollView mScrollView;
    ImageView mFirstOpenImage;
    ImageView mSecondOpenImage;
    ImageView mThirdOpenImage;

    ImageView mIvTranslate;

    Group mViewGroup;

    private static final int MAX_CARD_COUNT = 78;       //卡片总数
    int mScreenHalfWidth = 0; //屏幕中间X坐标
    boolean isFirstViewFilled;
    boolean isSecondViewFilled;
    boolean isThirdViewFilled;
    List<CardXLocation> mCardLocations = new ArrayList<>();  //记录每张卡片在X轴坐标上的位置

    public TarotSelectionView(@NonNull Context context) {
        super(context, null);
        initView(context);
    }

    public TarotSelectionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context);
    }

    public TarotSelectionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mScreenHalfWidth = ScreenUtil.getScreenWidth(mContext) / 2;

        View view = inflate(context, R.layout.layout_selection_view, this);
        mScrollView = view.findViewById(R.id.scrollView);
        mTvSelection = view.findViewById(R.id.tv_selection);
        mCardContainer = view.findViewById(R.id.container);
        mFirstOpenImage = view.findViewById(R.id.first_open_image);
        mSecondOpenImage = view.findViewById(R.id.second_open_image);
        mThirdOpenImage = view.findViewById(R.id.third_open_image);

        mIvTranslate = view.findViewById(R.id.iv_translate);

        mViewGroup = view.findViewById(R.id.view_group);

        mScrollView.setScrollChangedListener(new SlideScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldX, int oldY) {
                int cardPosition = mScreenHalfWidth + scrollX;
                for (int i = 0; i < mCardLocations.size(); i++) {
                    CardXLocation loc = mCardLocations.get(i);
                    if (cardPosition >= loc.startX && cardPosition < loc.endX) {
                        mTvSelection.setText("第" + (i + 1) + "张");
                        return;
                    }
                }

            }
        });

        //卡片显示的宽度（卡片总宽是70dip）
        int cardDisplayWidth = ScreenUtil.dip2px(mContext, 30);
        //留出scrollview最后一个卡片离右边的距离
        int rightPadding = mScreenHalfWidth - ScreenUtil.dip2px(mContext, 35);
        int leftPadding = ScreenUtil.getScreenWidth(mContext) / 2 - cardDisplayWidth / 2;
        mCardContainer.setPadding(leftPadding, 0, rightPadding, 0);

        mCardLocations.clear();
        for (int i = 0; i < MAX_CARD_COUNT; i++) {
            final View itemView = inflate(context, R.layout.item_card, null);
            itemView.setTag(false);
            final int currentPosition = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((boolean) itemView.getTag()) {
                        //平移动画的时候禁止滚动
                        mScrollView.setSlide(false);

                        //获取将要抽出的牌在屏幕上的坐标
                        int[] loc = new int[2];
                        itemView.getLocationOnScreen(loc);
                        //设置做平移动画的的View的起始坐标
                        mIvTranslate.setX(loc[0]);
                        mIvTranslate.setY(loc[1]);
                        itemView.setVisibility(View.INVISIBLE);
                        mIvTranslate.setAlpha(1.0f);
                        mIvTranslate.setVisibility(View.VISIBLE);

                        //开始展牌做平移动画
                        if (!isFirstViewFilled) {
                            startCardTranslateAnim(mIvTranslate, mFirstOpenImage);
                            isFirstViewFilled = true;
                        } else if (!isSecondViewFilled) {
                            startCardTranslateAnim(mIvTranslate, mSecondOpenImage);
                            isSecondViewFilled = true;
                        } else {
                            startCardTranslateAnim(mIvTranslate, mThirdOpenImage);
                            isThirdViewFilled = true;
                        }
                        return;
                    }
                    itemView.setTag(true);
                    itemView.setTranslationY(ScreenUtil.dip2px(mContext, 20));
                    resetCardViews(currentPosition);
                }
            });

            //设置初始时每张卡片的位置
            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            p.leftMargin = i * cardDisplayWidth;

            itemView.setLayoutParams(p);
            mCardContainer.addView(itemView);

            //记录每张卡片的最左边X坐标和可视区域最右边X坐标，用来判断滚动到第几张
            CardXLocation location = new CardXLocation();
            location.startX = leftPadding + p.leftMargin;
            location.endX = leftPadding + p.leftMargin + cardDisplayWidth;
            mCardLocations.add(location);
        }
    }

    public void showTarotSelectionView(){
        setVisibility(VISIBLE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mViewGroup.setVisibility(View.VISIBLE);
            }
        }, 300);
    }

    /**
     * 重置其它卡片恢复到原位
     *
     * @param currentPosition
     */
    private void resetCardViews(int currentPosition) {
        int count = mCardContainer.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = mCardContainer.getChildAt(i);
            if (currentPosition != i && (boolean) view.getTag()) {
                view.setTranslationY(0);
                view.setTag(false);
            }
        }
    }


    /**
     * 平移抽中的卡片到指定位置
     *
     * @param startView
     * @param endView
     */
    private void startCardTranslateAnim(final View startView, final View endView) {
        ValueAnimator animator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(startView.getX(), startView.getY()),
                new PointF(endView.getX(), endView.getY()));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                startView.setX(pointF.x);
                startView.setY(pointF.y);
            }
        });
        animator.setDuration(1000);
        animator.addListener(new MyAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //恢复scrollView滚动
                mScrollView.setSlide(true);
                cardDanceAnim(startView, endView);
            }
        });
        animator.start();
    }

    /**
     * 卡牌翻转动画
     *
     * @param originView 翻转前的View
     * @param targetView 翻转后的View
     */
    private void cardDanceAnim(View originView, final View targetView) {
        targetView.setVisibility(View.VISIBLE);
        AnimatorSet mDismissSet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.tarot_rotate_dismiss);
        AnimatorSet mDisplaySet = (AnimatorSet) AnimatorInflater.loadAnimator(mContext, R.animator.tarot_rotate_display);
        mDismissSet.setTarget(originView);
        mDisplaySet.setTarget(targetView);
        mDisplaySet.addListener(new MyAnimatorListener() {});
        mDismissSet.start();
        mDisplaySet.start();
    }


}

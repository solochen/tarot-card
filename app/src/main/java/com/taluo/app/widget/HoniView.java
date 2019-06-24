package com.taluo.app.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taluo.app.CardLocation;
import com.taluo.app.R;
import com.taluo.app.custom.BezierEvaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshaolong on 2019/2/19.
 */

public class HoniView extends LinearLayout {

    private static final int MAX_CARD_COUNT = 78;       //卡片总数

    int mShowLittleCardView; //点击卡片露出的小部分
    int mScrollX = 0;
    Context mContext;
    TextView mTvSelection;
    FrameLayout mCardContainer;
    MuScrollView mScrollView;
    ImageView mFirstOpenImage;
    ImageView mSecondOpenImage;
    ImageView mThirdOpenImage;
    List<CardLocation> mCardLocations = new ArrayList<>();  //记录每张卡片在X轴坐标上的位置

    public HoniView(@NonNull Context context) {
        super(context, null);
        initView(context);
    }

    public HoniView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context);
    }

    public HoniView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mShowLittleCardView = dip2px(mContext, 20);
        //屏幕中间X坐标
        final int screenHalfWidth = getScreenWidth(mContext) / 2;
        View view = inflate(context, R.layout.layout_honi, this);
        mScrollView = view.findViewById(R.id.scrollView);
        mTvSelection = view.findViewById(R.id.tv_selection);
        mCardContainer = view.findViewById(R.id.container);
        mFirstOpenImage = view.findViewById(R.id.first_open_image);
        mSecondOpenImage = view.findViewById(R.id.second_open_image);
        mThirdOpenImage = view.findViewById(R.id.third_open_image);

        mScrollView.setListener(new MuScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldX, int oldY) {
                mScrollX = scrollX;
                int cardPosition = screenHalfWidth + scrollX;
                for (int i = 0; i < mCardLocations.size(); i++) {
                    CardLocation loc = mCardLocations.get(i);
                    if (cardPosition >= loc.startX && cardPosition < loc.endX) {
                        mTvSelection.setText("第" + (i + 1) + "张");
                        return;
                    }
                }

            }
        });

        //留出scrollview最后一个卡片离右边的距离
        int rightPadding = screenHalfWidth - dip2px(mContext, 35);
        mCardContainer.setPadding(0, 0, rightPadding, 0);

        //卡片显示的宽度（卡片总宽是70dip）
        int cardDisplayWidth = dip2px(mContext, 50);
        //左边第一个卡片离左边的距离
        int firstCardLeftMargin = getScreenWidth(mContext) / 2 - cardDisplayWidth / 2;

        mCardLocations.clear();
        for (int i = 0; i < MAX_CARD_COUNT; i++) {
            final View itemView = inflate(context, R.layout.item_honi, null);
//            View card = itemView.findViewById(R.id.view_card);
            itemView.setTag(false);
            final int currentPosition = i;
            itemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((boolean) itemView.getTag()) {
                        setScrollViewHeight(LayoutParams.MATCH_PARENT);
                        //平移动画的时候禁止滚动
                        mScrollView.setSlide(false);

                        expandCardAnim(mFirstOpenImage, itemView);
                        Toast.makeText(mContext, "动画" + (currentPosition + 1), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    itemView.setTag(true);
                    itemView.setTranslationY(mShowLittleCardView);
                    resetCardViews(currentPosition);
                }
            });

            FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (i == 0) {
                p.leftMargin = firstCardLeftMargin;
            } else {
                p.leftMargin = firstCardLeftMargin + i * cardDisplayWidth;
            }

            CardLocation location = new CardLocation();
            location.startX = p.leftMargin;
            location.endX = p.leftMargin + cardDisplayWidth;
            mCardLocations.add(location);

            itemView.setLayoutParams(p);
            mCardContainer.addView(itemView);
        }
    }


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
     * 开始抽牌动画的时候设置父控件的高度铺满屏幕，方便卡片做平移动画
     * 结束动画后恢复原位
     *
     * @param height
     */
    private void setScrollViewHeight(int height) {
        RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) mScrollView.getLayoutParams();
        p.height = height;
        mScrollView.setLayoutParams(p);
    }


    private void expandCardAnim(final ImageView targetCardView, final View cardView) {
        float x = cardView.getX() - mScrollX;
        float y = cardView.getY();
        float tx = targetCardView.getX();
        float ty = targetCardView.getY();

        ValueAnimator animator = ValueAnimator.ofObject(new BezierEvaluator(),
                new PointF(cardView.getX(), cardView.getY()),
                new PointF(targetCardView.getX(), targetCardView.getY()));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                cardView.setX(pointF.x);
                cardView.setY(pointF.y);
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(animator);
        animatorSet.setDuration(1000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //恢复scrollView高度
                setScrollViewHeight(dip2px(mContext, 120));
                //恢复scrollView滚动
                mScrollView.setSlide(true);

                cardView.setVisibility(View.GONE);
                targetCardView.setBackgroundColor(Color.parseColor("#CCCCCC"));
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


    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5F * (float) (dip >= 0.0F ? 1 : -1));
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }
}

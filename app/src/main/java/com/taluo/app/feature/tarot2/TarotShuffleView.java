package com.taluo.app.feature.tarot2;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.taluo.app.R;
import com.taluo.app.custom.AnimHelper;


/**
 * Created by chenshaolong on 2019/2/19.
 */

public class TarotShuffleView extends FrameLayout {

    Context mContext;
    int translate = 20;
    int totalCardCount = 10;
    OnShuffleViewListener mListener;
    LayoutInflater mInflater;

    public TarotShuffleView(@NonNull Context context) {
        super(context, null);
        initView(context);
    }

    public TarotShuffleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context);
    }

    public TarotShuffleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public void setOnShuffleListener(OnShuffleViewListener listener) {
        this.mListener = listener;
    }

    private void initView(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(getContext());
        for (int i = 0; i < totalCardCount; i++) {
            View view = mInflater.inflate(R.layout.item_shuffle_view, this, false);
            ImageView cardView = view.findViewById(R.id.card_view);
            int width = ScreenUtil.dip2px(mContext, Constant.CARD_ZOOM_IN_WIDTH);
            int height = ScreenUtil.dip2px(mContext, Constant.CARD_ZOOM_IN_HEIGHT);
            LayoutParams p = new LayoutParams(width, height);
            p.topMargin = ScreenUtil.dip2px(mContext, Constant.CARD_TOP_MARGIN);
            p.gravity = Gravity.CENTER_HORIZONTAL;
            cardView.setLayoutParams(p);

            cardView.setVisibility(View.GONE);
            if (i >= 0 && i <= 4) {
                cardView.setTranslationX(translate * (4 - i));
                cardView.setTranslationY(translate * (4 - i));
                cardView.setVisibility(VISIBLE);
            }
            this.addView(view);
        }
    }

    public void anim() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            ImageView cardView = view.findViewById(R.id.card_view);
            cardView.setVisibility(VISIBLE);

            //恢复原位
            if (i >= 0 && i <= 4) {
                //变小和平移到原位动效
                AnimHelper.zoomOutAnimatorWithTranslation(cardView, translate * (4 - i), translate * (4 - i));
                continue;
            }

            if (i == getChildCount() - 1) {
                //最后一张卡片
                //变小动效
                AnimHelper.zoomOutAnimator(cardView, new MyAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startShuffleAnimator();
                    }
                });
                continue;
            }

            //变小动效
            AnimHelper.zoomOutAnimator(cardView, listenerNull);
        }
    }

    /**
     * 洗牌动效
     */
    private void startShuffleAnimator() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            int position = count - 1 - i;
            View view = getChildAt(position);
            View cardView = view.findViewById(R.id.card_view);
            //最后一张动画结束之后开始做切牌动效
            if (position == 0) {
                AnimHelper.startAnimation(1000, 1350, cardView, 450, 200, 400, listenerNull);
            } else if (position == 1) {
                AnimHelper.startAnimation(1300, 1300, cardView, 600, 300, 600, new MyAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        cuttingCardAnimator(new MyAnimatorListener() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                translationYToTop();
                            }
                        });
                    }
                });
            } else if (position == 2) {
                AnimHelper.startAnimation(1250, 1050, cardView, 550, 260, 520, listenerNull);
            } else if (position == 3) {
                AnimHelper.startAnimation(1500, 900, cardView, 500, 250, 500, listenerNull);
            } else if (position == 4) {
                AnimHelper.startAnimation(1300, 750, cardView, 580, 280, 560, listenerNull);
            } else if (position == 5) {
                AnimHelper.startAnimation(1400, 600, cardView, 650, 340, 680, listenerNull);
            } else if (position == 6) {
                AnimHelper.startAnimation(1400, 450, cardView, 670, 320, 640, listenerNull);
            } else if (position == 7) {
                AnimHelper.startAnimation(1400, 300, cardView, 630, 320, 640, listenerNull);
            } else if (position == 8) {
                AnimHelper.startAnimation(1300, 150, cardView, 700, 350, 700, listenerNull);
            } else {
                AnimHelper.startAnimation(1500, 0, cardView, 720, 360, 720, listenerNull);
            }
        }
    }

    /**
     * 切牌动效
     *
     * @param listener
     */
    private void cuttingCardAnimator(final MyAnimatorListener listener) {
        final View view1 = getChildAt(0);
        final View view2 = getChildAt(1);
        final View view3 = getChildAt(2);
        final View view4 = getChildAt(3);
        final View view5 = getChildAt(4);
        final View view6 = getChildAt(5);
        final View view7 = getChildAt(6);
        final View view8 = getChildAt(7);
        final View view9 = getChildAt(8);
        final View view10 = getChildAt(9);
        AnimHelper.translateXAnim(view4, view1, listenerNull);
        AnimHelper.translateXAnim(view5, view2, listenerNull);
        AnimHelper.translateXAnim(view6, view3, new MyAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                AnimHelper.translateXAnim(view10, view9, listenerNull);
                AnimHelper.translateXAnim(view8, view7, listenerNull);
                AnimHelper.translateXAnim(view6, view5, listenerNull);
                AnimHelper.translateXAnim(view4, view3, listenerNull);
                AnimHelper.translateXAnim(view2, view1, listener);
            }
        });
    }

    /**
     * 平移到顶部
     */
    private void translationYToTop() {
        //卡片缩小距离原放大时顶部的距离
        int scaleY = Constant.CARD_ZOOM_IN_WIDTH - Constant.CARD_DEFAULT_WIDTH;
        //距离顶部的总距离
        int translateY = scaleY + Constant.CARD_TOP_MARGIN;
        int toY = ScreenUtil.dip2px(mContext, translateY);

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            View cardView = view.findViewById(R.id.card_view);
            //平移到顶部
            if (i == count - 1) {
                AnimHelper.translateYToTopAnim(cardView, toY, new MyAnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (mListener == null) return;
                        mListener.onShuffleAnimDone();
                    }
                });
            } else {
                AnimHelper.translateYToTopAnim(cardView, toY, listenerNull);
            }
        }

    }

    MyAnimatorListener listenerNull = new MyAnimatorListener() {
    };


}

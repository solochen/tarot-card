package com.taluo.app.feature.tarot2;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.taluo.app.R;
import com.taluo.app.custom.AnimHelper;

import java.util.Random;

/**
 * Created by chenshaolong on 2019/2/19.
 */

public class TarotShuffleView extends FrameLayout {

    Context mContext;
    View mCardView1;
    View mCardView2;
    View mCardView3;
    View mCardView4;
    View mCardView5;
    View mCardView6;
    View mCardView7;
    View mCardView8;
    View mCardView9;
    View mCardView10;

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

    private void initView(Context context) {
        mContext = context;
        View view = inflate(context, R.layout.item_shuffle_view, this);
        mCardView1 = view.findViewById(R.id.view_card1);
        mCardView2 = view.findViewById(R.id.view_card2);
        mCardView3 = view.findViewById(R.id.view_card3);
        mCardView4 = view.findViewById(R.id.view_card4);
        mCardView5 = view.findViewById(R.id.view_card5);
        mCardView6 = view.findViewById(R.id.view_card6);
        mCardView7 = view.findViewById(R.id.view_card7);
        mCardView8 = view.findViewById(R.id.view_card8);
        mCardView9 = view.findViewById(R.id.view_card9);
        mCardView10 = view.findViewById(R.id.view_card10);


    }

    public void anim() {
        int duration = 1500;
        int x = 700;
        int y = 350;
        AnimHelper.startAnimation(duration, 0, mCardView10, x, y, x, listenerNull);
        AnimHelper.startAnimation(duration, 150, mCardView9, x - 60, y - 30, x - 60, listenerNull);
        AnimHelper.startAnimation(duration, 300, mCardView8, x - 90, y - 45, x - 90, listenerNull);
        AnimHelper.startAnimation(duration, 450, mCardView7, x - 320, y - 160, x - 320, listenerNull);
        AnimHelper.startAnimation(duration, 600, mCardView6, x - 280, y - 140, x - 280, listenerNull);
        AnimHelper.startAnimation(duration, 750, mCardView5, x - 240, y - 120, x - 240, listenerNull);
        AnimHelper.startAnimation(duration, 900, mCardView4, x - 200, y - 100, x - 200, listenerNull);
        AnimHelper.startAnimation(duration, 1150, mCardView3, x - 160, y - 80, x - 160, listenerNull);
        AnimHelper.startAnimation(duration, 1300, mCardView2, x - 80, y - 40, x - 80, listenerNull);
        AnimHelper.startAnimation(duration, 1450, mCardView1, x - 280, y - 140, x - 280, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                AnimHelper.translateXAnim(mCardView4, mCardView1, listenerNull);
                AnimHelper.translateXAnim(mCardView5, mCardView2, listenerNull);
                AnimHelper.translateXAnim(mCardView6, mCardView3, mListener2);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    Animator.AnimatorListener mListener2 = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            AnimHelper.translateXAnim(mCardView10, mCardView9, listenerNull);
            AnimHelper.translateXAnim(mCardView8, mCardView7, listenerNull);
            AnimHelper.translateXAnim(mCardView6, mCardView5, listenerNull);
            AnimHelper.translateXAnim(mCardView4, mCardView3, listenerNull);
            AnimHelper.translateXAnim(mCardView2, mCardView1, new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });


        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };

    Animator.AnimatorListener listenerNull = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {

        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };
}

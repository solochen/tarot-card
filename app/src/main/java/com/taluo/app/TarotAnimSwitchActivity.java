package com.taluo.app;

import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.taluo.app.custom.AnimHelper;
import com.taluo.app.widget.TarotSelectionView;

public class TarotAnimSwitchActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TarotAnimSwitchActivity.class));
    }

    FrameLayout mContainerLayout;
    Button mBtnStart;
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

    TarotSelectionView mTarotSelectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarot_switch);
        initView();

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim(2000);
            }
        });
    }

    private void initView() {
        mContainerLayout = findViewById(R.id.layout_container);
        mBtnStart = findViewById(R.id.btn_start);
        mCardView1 = findViewById(R.id.view_card1);
        mCardView2 = findViewById(R.id.view_card2);
        mCardView3 = findViewById(R.id.view_card3);
        mCardView4 = findViewById(R.id.view_card4);
        mCardView5 = findViewById(R.id.view_card5);
        mCardView6 = findViewById(R.id.view_card6);
        mCardView7 = findViewById(R.id.view_card7);
        mCardView8 = findViewById(R.id.view_card8);
        mCardView9 = findViewById(R.id.view_card9);
        mCardView10 = findViewById(R.id.view_card10);

        mTarotSelectionView = findViewById(R.id.tarot_selection_view);

    }

    private void anim(int duration) {
        AnimHelper.startAnimation(duration, 0, mCardView10, 850, 400, 800, listenerNull);
        AnimHelper.startAnimation(duration, 400, mCardView9, 850, 500, 1000, listenerNull);
        AnimHelper.startAnimation(duration, 800, mCardView8, 700, 400, 800, listenerNull);
        AnimHelper.startAnimation(duration, 1200, mCardView7, 750, 400, 800, listenerNull);
        AnimHelper.startAnimation(duration, 1600, mCardView6, 900, 400, 800, listenerNull);
        AnimHelper.startAnimation(duration, 2000, mCardView5, 650, 400, 800, listenerNull);
        AnimHelper.startAnimation(duration, 2400, mCardView4, 600, 400, 800, listenerNull);
        AnimHelper.startAnimation(duration, 2800, mCardView3, 810, 300, 600, listenerNull);
        AnimHelper.startAnimation(duration, 3200, mCardView2, 820, 350, 700, listenerNull);
        AnimHelper.startAnimation(duration, 3600, mCardView1, 800, 450, 900, new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                AnimHelper.translateYdownAnim(mContainerLayout, mTranslateYListener);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }


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


    Animator.AnimatorListener mTranslateYListener = new Animator.AnimatorListener() {
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
    };


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
//                    int x = 30, y = -30;
//                    AnimHelper.lastAnimation(mCardView10, 100, 50, x, y);
//                    AnimHelper.lastAnimation(mCardView9, 100, 50, 2 * x, 2 * y);
//                    AnimHelper.lastAnimation(mCardView8, 100, 50, 3 * x, 2 * y);
//                    AnimHelper.lastAnimation(mCardView7, 100, 50, 4 * x, 2 * y);
//                    AnimHelper.lastAnimation(mCardView6, 100, 50, 5 * x, 2 * y);
//                    AnimHelper.lastAnimation(mCardView5, 100, 50, 6 * x, 2 * y);
//                    AnimHelper.lastAnimation(mCardView4, 100, 50, 7 * x, 2 * y);
//                    AnimHelper.lastAnimation(mCardView3, 100, 50, 8 * x, 2 * y);
//                    AnimHelper.lastAnimation(mCardView2, 100, 50, 9 * x, 2 * y);
//                    AnimHelper.lastAnimation(mCardView1, 100, 50, 10 * x, 2 * y);

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


}

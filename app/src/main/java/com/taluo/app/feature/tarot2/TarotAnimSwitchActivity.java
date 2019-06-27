package com.taluo.app.feature.tarot2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.taluo.app.R;

public class TarotAnimSwitchActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TarotAnimSwitchActivity.class));
    }

    Button mBtnStart;


    TarotShuffleView mTarotShuffleView;
    TarotSelectionView mTarotSelectionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarot_switch);
        initView();

        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTarotShuffleView.anim();
            }
        });

        mTarotShuffleView.setOnShuffleListener(new OnShuffleViewListener() {
            @Override
            public void onShuffleAnimDone() {
                mTarotShuffleView.setVisibility(View.GONE);
                mTarotSelectionView.showTarotSelectionView();
                mBtnStart.setVisibility(View.GONE);
            }
        });
    }

    private void initView() {
        mTarotShuffleView = findViewById(R.id.tarot_shuffle_view);
        mBtnStart = findViewById(R.id.btn_start);

        mTarotSelectionView = findViewById(R.id.tarot_selection_view);

    }

}

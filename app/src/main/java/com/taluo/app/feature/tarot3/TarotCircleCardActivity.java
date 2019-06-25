package com.taluo.app.feature.tarot3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.taluo.app.R;

/**
 * Created by chenshaolong on 2019/2/27.
 */

public class TarotCircleCardActivity extends Activity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, TarotCircleCardActivity.class));
    }

    TarotCardLayout mTarotCardLayout;
    Button mBtnCardChoose;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarot_circle_card);
        mContext = this;
        mTarotCardLayout = findViewById(R.id.card_layout);
        mBtnCardChoose = findViewById(R.id.btn_choose);
        mBtnCardChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTarotCardLayout.startExpendCard();
                mBtnCardChoose.setVisibility(View.GONE);
            }
        });

    }


}


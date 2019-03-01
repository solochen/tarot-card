package com.taluo.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBtnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_tarot_anim_1:
                TarotAnimActivity.start(this);
                break;
            case R.id.btn_tarot_anim_2:
                TarotAnimSwitchActivity.start(this);
                break;
            case R.id.btn_tarot_anim_3:
                TarotCircleCardActivity.start(this);
                break;
        }
    }

}

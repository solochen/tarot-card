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
            case R.id.btn_reset:
                TarotAnimActivity.start(this);
                break;
            case R.id.btn_circle_scroll:
                CircleCardActivity.start(this);
                break;
            case R.id.btn_switch:
                TarotAnimSwitchActivity.start(this);
                break;
            case R.id.btn_circle:
                CircleActivity.start(this);
                break;
            case R.id.btn_circle_scroll2:
                TarotCircleCardActivity.start(this);
                break;
        }
    }

}

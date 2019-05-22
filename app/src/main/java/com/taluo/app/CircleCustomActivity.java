package com.taluo.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class CircleCustomActivity extends Activity {

    public static void start(Context context){
        context.startActivity(new Intent(context, CircleCustomActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_custom);


    }


}
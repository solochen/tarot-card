package com.taluo.app.feature.tarot2;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by chenshaolong on 2019/6/27.
 */

public class ScreenUtil {

    public static int dip2px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5F * (float) (dip >= 0.0F ? 1 : -1));
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

}

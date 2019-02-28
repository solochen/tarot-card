package com.taluo.app.Utils;

import android.content.Context;

/**
 * Created by chenshaolong on 2019/2/28.
 */

public class ScreenUtil {

    public static int dp2px(Context context, int dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

}

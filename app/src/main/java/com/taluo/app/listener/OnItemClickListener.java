package com.taluo.app.listener;

import android.view.View;

/**
 * Created by chenshaolong on 2019/2/28.
 */

public interface OnItemClickListener {
    void onItemClick(View chooseView, View outCardView, View innerCardView, int position, View tarotDecodeLayout, View topRightPoint);
}

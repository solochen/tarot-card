package com.taluo.app.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.taluo.app.R;

/**
 * Created by chenshaolong on 2019/2/19.
 */

public class TarotCardView extends LinearLayout {

    LinearLayout mInnerCardView;
    LinearLayout mOuterCardView;
    View mLastView;

    public TarotCardView(@NonNull Context context) {
        super(context, null);
        initView(context);
    }

    public TarotCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context);
    }

    public TarotCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        View view = inflate(context, R.layout.layout_tarot_card, this);
        mInnerCardView = view.findViewById(R.id.inner_card_view);
        mOuterCardView = view.findViewById(R.id.outer_card_view);
        mLastView = view.findViewById(R.id.view_last);
    }

    public View getInnerCardView() {
        return mInnerCardView;
    }

    public View getOuterCardView(){
        return mOuterCardView;
    }

    public View getLastView(){
        return mLastView;
    }


}

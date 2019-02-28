package com.taluo.app;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshaolong on 2019/2/20.
 */

public class UltraPagerAdapter extends PagerAdapter {

    private List<String> mList = new ArrayList<>();

    @Override
    public int getCount() {
        return mList.size();
    }


    public UltraPagerAdapter() {
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final String s = mList.get(position);
        RelativeLayout relativeLayout = (RelativeLayout) LayoutInflater.from(container.getContext()).inflate(R.layout.item_tarot_choose, null);
        TextView mTvContainer = relativeLayout.findViewById(R.id.tv_container);
        if(position % 2 == 0) {
            mTvContainer.setBackgroundColor(Color.parseColor("#33CCFF"));
        } else if(position % 2 == 1){
            mTvContainer.setBackgroundColor(Color.parseColor("#FF2D55"));
        } else {
            mTvContainer.setBackgroundColor(Color.parseColor("#CCCCCC"));
        }
        return relativeLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        RelativeLayout view = (RelativeLayout) object;
        container.removeView(view);
    }

    public void setData(List<String> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
    }

}

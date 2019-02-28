//package com.taluo.app;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//
//import com.tmall.ultraviewpager.UltraViewPager;
//import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class TarotChooseActivity extends Activity {
//
//    public static void start(Context context) {
//        context.startActivity(new Intent(context, TarotChooseActivity.class));
//    }
//
//    private UltraViewPager ultraViewPager;
//    private UltraPagerAdapter pagerAdapter;
//
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_tarot_choose);
//        initView();
//
//        List<String> lists = new ArrayList<>();
//        lists.add("1");
//        lists.add("2");
//        lists.add("3");
//        lists.add("4");
//        lists.add("5");
//        lists.add("6");
//        lists.add("7");
//        lists.add("8");
//        lists.add("9");
//        lists.add("10");
//        pagerAdapter = new UltraPagerAdapter();
//        pagerAdapter.setData(lists);
//
//        ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//        ultraViewPager.setMultiScreen(0.7f);
//        ultraViewPager.setItemRatio(3.0f);
//        ultraViewPager.setRatio(1.0f);
//        ultraViewPager.setMaxHeight(600);
//        ultraViewPager.setAutoMeasureHeight(true);
//        ultraViewPager.setPageTransformer(false, new UltraScaleTransformer());
//
//
//
//        ultraViewPager.setAdapter(pagerAdapter);
//        ultraViewPager.setCurrentItem(1, true);
//        ultraViewPager.setAutoScroll(5000);
//        ultraViewPager.setInfiniteLoop(true);
//        ultraViewPager.getViewPager().getAdapter().notifyDataSetChanged();
//    }
//
//    private void initView() {
//        ultraViewPager = findViewById(R.id.ultra_viewpager);
//    }
//
//}

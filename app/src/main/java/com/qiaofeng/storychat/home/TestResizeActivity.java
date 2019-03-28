package com.qiaofeng.storychat.home;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.qiaofeng.storychat.R;
import com.qiaofeng.storychat.base.AbstractActivity;

/**
 * Created by zhengmj on 19-3-27.
 */

public class TestResizeActivity extends AbstractActivity {
//    private ImageView civ_icon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_resize);
//        civ_icon = findViewById(R.id.civ_icon);
//        ViewTreeObserver viewTreeObserver = civ_icon.getViewTreeObserver();
//        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                Log.d("120","civ width == "+civ_icon.getWidth()+" measureWidth == "+civ_icon.getMeasuredWidth());
//            }
//        });
    }
}

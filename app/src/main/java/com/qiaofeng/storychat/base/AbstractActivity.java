package com.qiaofeng.storychat.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.qiaofeng.storychat.R;

/**
 * Created by Maxim on 19-3-26.
 */

public abstract class AbstractActivity extends Activity {
    private LinearLayout abstract_container;
    private AutoAdaptationToolbox adaptationUtil;
    public void setContentView(@LayoutRes int childLayout){
        ViewGroup g = adaptationUtil.generateAdaptationView(childLayout);
        Log.d("120","g getParent == "+(g.getParent() == null));
        abstract_container.addView(g);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_abstract);
        abstract_container = findViewById(R.id.abstract_container);
        adaptationUtil = new AutoAdaptationToolbox(this);
    }
}

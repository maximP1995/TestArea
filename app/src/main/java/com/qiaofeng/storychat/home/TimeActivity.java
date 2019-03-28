package com.qiaofeng.storychat.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qiaofeng.storychat.time.Axis;

/**
 * Created by zhengmj on 19-1-17.
 */

public class TimeActivity extends AppCompatActivity {
    protected static Axis mTimeAxis;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mTimeAxis == null)mTimeAxis = Axis.getInstance();
    }
    protected void setTimeAxisCallback(View view, Axis.TimeAxisCallback callback, long duration){
        if (view == null || callback == null) return;
        mTimeAxis.addTimeAxisCallback(view,duration,callback);
    }
}

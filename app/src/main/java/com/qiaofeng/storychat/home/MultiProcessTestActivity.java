package com.qiaofeng.storychat.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.qiaofeng.storychat.R;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zhengmj on 19-3-14.
 */

public class MultiProcessTestActivity extends Activity {
    private TextView tv_test;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiprocess);
        tv_test = findViewById(R.id.tv_test);

    }
}

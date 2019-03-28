package com.qiaofeng.storychat.home;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qiaofeng.storychat.R;
import com.qiaofeng.storychat.View.CountDownTextView;
import com.qiaofeng.storychat.View.TestImageView;
import com.qiaofeng.storychat.View.TjrPopLayout;
import com.qiaofeng.storychat.View.TjrPopupView;
import com.qiaofeng.storychat.time.Axis;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zhengmj on 19-1-17.
 */

public class GameActivity extends TimeActivity {
//    private TestImageView imageView;
//    private TjrPopLayout pop;
    private TextView tv_test;
    private ImageView tv_test2;
    private CheckBox cb_test;
    private TjrPopupView tjrPopupView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_test);
//        pop = (TjrPopLayout) findViewById(R.id.pop);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        View textView = LayoutInflater.from(this).inflate(R.layout.test_layout,null);
//        pop.addView(textView,layoutParams);
        View view = LayoutInflater.from(this).inflate(R.layout.test_layout,null);
//        tv_test2 = view.findViewById(R.id.tv_test2);
        cb_test = view.findViewById(R.id.cb_test);

//        tv_test2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(GameActivity.this,"123",Toast.LENGTH_SHORT).show();
//                tjrPopupView.dismiss();
//            }
//        });
        tjrPopupView = new TjrPopupView(this,view);
        tv_test = (TextView) findViewById(R.id.tv_test);
        tjrPopupView.setTrigger(tv_test);
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tjrPopupView.show2();
                TestMP.value = 2;
                Intent intent = new Intent(GameActivity.this,MultiProcessTestActivity.class);
                startActivity(intent);
            }
        });
    }

}

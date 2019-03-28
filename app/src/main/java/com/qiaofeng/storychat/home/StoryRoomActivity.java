package com.qiaofeng.storychat.home;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.qiaofeng.storychat.R;

/**
 * Created by zhengmj on 18-12-19.
 */

public class StoryRoomActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_storyroom);
        getSupportActionBar().setTitle("StoryRoom");
    }
}

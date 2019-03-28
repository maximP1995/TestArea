package com.qiaofeng.storychat.time;

import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zhengmj on 19-1-17.
 */

public class Axis {
    private HashMap<Integer,TimeAxisCallback> callbackMap;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int vid = msg.what;
            if (callbackMap.containsKey(vid)){
                TimeAxisCallback callback = callbackMap.get(vid);
                callback.onDone();
            }
        }
    };
    private static Axis instance;
    public static Axis getInstance(){
        if (instance == null){
            synchronized (Axis.class){
                if (instance == null){
                    instance = new Axis();
                }
            }
        }
        return instance;
    }
    private Axis(){}
    public void addTimeAxisCallback(@NonNull final View view, long duration,@NonNull TimeAxisCallback timeAxisCallback){
        if (callbackMap == null) callbackMap = new HashMap<>();

        callbackMap.put(view.getId(),timeAxisCallback);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = view.getId();
                handler.sendMessage(message);
            }
        },duration);
    }
    public interface TimeAxisCallback{
        void onDone();
    }
}

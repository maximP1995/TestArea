package com.qiaofeng.storychat;

import android.app.ActivityManager;
import android.app.Application;
import android.os.Process;
import android.util.Log;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zhengmj on 19-3-14.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        Iterator i = list.iterator();
        while (i.hasNext()){
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) i.next();
            if (info.pid == Process.myPid()){
                Log.d("120","application start ProcessName == "+info.processName);
            }
        }
    }
}

package com.qiaofeng.storychat.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.qiaofeng.storychat.R;

/**
 * Created by zhengmj on 19-1-17.
 */

public class CountDownTextView extends ProgressBar {
    private ProgressBar progressBar;
    private long duration;
    private long startTime = -1;
    private Paint paint;
    public CountDownTextView(Context context) {
        super(context);
        init(context);
    }

    public CountDownTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
//
//    @Override
//    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
//     int childCount = getChildCount();
//     for (int index = 0;index<childCount;index++){
//         View child = getChildAt(index);
//         child.layout(i,i1,i2,i3);
//     }
//    }


    private void init(Context context){
        paint = new Paint();
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.STROKE);
    }
    public void startCountDown(long duration){
        this.duration = duration;
        invalidate();
    }
}

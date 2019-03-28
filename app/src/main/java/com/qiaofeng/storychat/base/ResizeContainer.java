package com.qiaofeng.storychat.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhengmj on 19-3-28.
 */

public class ResizeContainer extends ViewGroup {
    public ResizeContainer(Context context) {
        super(context);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0;i<childCount;i++){
            View child = getChildAt(i);

        }
    }
    private void resizeWidth(){

    }
}

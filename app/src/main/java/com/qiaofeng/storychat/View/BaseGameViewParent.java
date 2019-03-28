package com.qiaofeng.storychat.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by zhengmj on 19-1-25.
 */

public class BaseGameViewParent extends FrameLayout {
    public BaseGameViewParent(@NonNull Context context) {
        super(context);
    }

    public BaseGameViewParent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
}

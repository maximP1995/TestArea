package com.qiaofeng.storychat.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhengmj on 19-2-26.
 */

public class TestView2 extends View {
    public TestView2(Context context) {
        super(context);
    }

    public TestView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Bitmap bitmap = Bitmap.createBitmap(60,60,Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK);
        paint2.setTextSize(50);
        canvas1.drawColor(Color.parseColor("#ffc90c"));
        canvas1.drawText("答",5,50,paint2);
        canvas.drawBitmap(bitmap,0,0,paint);
    }
}

package com.qiaofeng.storychat.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by zhengmj on 19-2-25.
 */

public class TjrPopLayout extends FrameLayout implements View.OnLayoutChangeListener{
    private Paint paint1;
    private Paint paint2;
    private static  int W = 200;
    private static  int H = 200;
    private int arrowHeight = 20;
    private int topPadding;
    private int botPadding;
    private int leftPadding;
    private int rightPadding;
    private int adjustDistance;
    private Path path;
    private BitmapShader mBG;
    private Bitmap mSrcB;
    private Bitmap mDstB;
    private Context context;
    private int x;
    private int y;
    public TjrPopLayout(@NonNull Context context) {
        this(context,null);
    }

    public TjrPopLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        addOnLayoutChangeListener(this);
    }
    public int getTriggerX(){
        return x;
    }
    public int getTriggerY(){
        return y;
    }
    public void setAdjustArrow(int distance){
        this.adjustDistance = distance;
        invalidate();
    }
    private void drawArrow(){

    }
    public void setTrigger(View view){
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
         x = locations[0];
         y = locations[1];
    }
     Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.RED);
        c.drawRect(0, 0, w, h, p);
        return bm;
    }
     Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(Color.parseColor("#333333"));
        c.drawRoundRect(new RectF(0, 0, w, h-arrowHeight),20,20,p);
        Path path = new Path();
        path.moveTo(w/2-arrowHeight-adjustDistance,h-arrowHeight);
        path.lineTo(w/2+arrowHeight-adjustDistance,h-arrowHeight);
        path.lineTo(w/2-adjustDistance,h);
        path.close();
        Paint pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setStyle(Paint.Style.FILL);
        pathPaint.setColor(Color.parseColor("#333333"));
        c.drawPath(path,pathPaint);
        return bm;
    }
    private void init(int W,int H){
        mSrcB = makeSrc(W, H);
        mDstB = makeDst(W, H);
        Bitmap bm = Bitmap.createBitmap(new int[] { 0xFFFFFFFF, 0xFFCCCCCC,
                        0xFFCCCCCC, 0xFFFFFFFF }, 2, 2,
                Bitmap.Config.RGB_565);
        mBG = new BitmapShader(bm,
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT);

        paint1 = new Paint();
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setColor(Color.BLACK);
        paint1.setShader(null);

        paint2 = new Paint();
        paint2.setStyle(Paint.Style.FILL);
        paint2.setColor(Color.RED);
        paint2.setShader(mBG);

        path = new Path();

    }
    @Override
    protected void dispatchDraw(Canvas canvas) {
//        Log.d("120","dispatchDraw getMeasuredWidth == "+getMeasuredWidth()+" getWidth == "+getWidth());
        init(getMeasuredWidth(),getMeasuredHeight());
        int x = 0;
        int y = 0;
        @SuppressLint("WrongConstant") int sc = canvas.saveLayer(x, y, x + W, y + H, null,
                Canvas.MATRIX_SAVE_FLAG |
                        Canvas.CLIP_SAVE_FLAG |
                        Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                        Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                        Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        canvas.translate(x, y);
        canvas.drawBitmap(mDstB, 0, 0, paint1);
        paint1.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(mSrcB, 0, 0, paint1);
        paint1.setXfermode(null);
        canvas.restoreToCount(sc);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        topPadding = getPaddingTop();
        botPadding = getPaddingBottom();
        leftPadding = getPaddingLeft();
        rightPadding = getPaddingRight();
        int childCount = getChildCount();
        int totalWidth = 0;
        int totalHeight = 0;
//        Log.d("120","childCount == "+childCount);
        for (int i = 0;i<childCount;i++){
            View child = getChildAt(i);
//            measureChild(child,widthMeasureSpec,heightMeasureSpec);
//            Log.d("120","child measureWidth == "+child.getMeasuredWidth()+" getWidth == "+child.getWidth());
            totalWidth += child.getMeasuredWidth();
            totalHeight += child.getMeasuredHeight();
        }
        setMeasuredDimension(measureWidth(widthMeasureSpec,totalWidth),measureHeight(heightMeasureSpec,totalHeight));
    }

    private int measureHeight(int measureSpec, int totalHeight){
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
//            if (H<totalHeight){
            result = totalHeight+topPadding+botPadding+arrowHeight;
//            }
//            result = totalHeight+H/2;
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result,specSize);
            }
        }
        H = result;
        return result;
    }
    private int measureWidth(int measureSpec,int totalWidth){
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else {
//            if (W<totalWidth){
            result = totalWidth + leftPadding+rightPadding;
//            }
            if (specMode == MeasureSpec.AT_MOST){
                result = Math.min(result,specSize);
            }
        }
//        Log.d("120","measureWidth result == "+result);
        W = result;
        return result;
    }
    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom,
                               int oldLeft, int oldTop, int oldRight, int oldBottom) {
//        resetMask();
        postInvalidate();
    }
}

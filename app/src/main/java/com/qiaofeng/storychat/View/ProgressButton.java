package com.qiaofeng.storychat.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by zhengmj on 19-1-25.
 */

public final class ProgressButton extends SurfaceView implements SurfaceHolder.Callback,Runnable{
    private long currentTime;
    private long previousTime = -1;
    private SurfaceHolder holder;
    private Canvas canvas;
    private boolean isDrawing;
    private Paint linePaint;
    private Paint textPaint;
    private Path textPath;
    private String label;
    private double right = 0;
    private int height;
    private double width;
    private double delta;
    private long deltaTime;
    private int time = 1000;
    private int timeCache;

    public ProgressButton(Context context) {
        super(context);
        init(context);
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    private void init(Context context){
        holder = getHolder();
        holder.addCallback(this);
        linePaint = new Paint();
        linePaint.setStyle(Paint.Style.FILL);
        linePaint.setColor(Color.BLACK);

        textPaint = new Paint();
        textPaint.setTextSize(80);
        textPaint.setTextAlign(Paint.Align.CENTER);

        textPath = new Path();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(measureWidth(widthMeasureSpec),measureHeight(heightMeasureSpec));
//    }
//    private int measureWidth(int measureSpec){
//        int result = 0;
//        int specMod = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        if (specMod == MeasureSpec.EXACTLY){
//            result = specSize;
//        }else if (specMod == MeasureSpec.AT_MOST){
//            result = Math.min(200,specSize);
//        }
//        return result;
//    }
//    private int measureHeight(int measureSpec){
//        int result = 0;
//        int specMod = MeasureSpec.getMode(measureSpec);
//        int specSize = MeasureSpec.getSize(measureSpec);
//        if (specMod == MeasureSpec.EXACTLY){
//            result = specSize;
//        }else if (specMod == MeasureSpec.AT_MOST){
//            result = Math.min(100,specSize);
//        }
//        return result;
//    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (!isDrawing){
                    time = 1000;
                    isDrawing = true;
                    new Thread(this).start();
                }

                break;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        isDrawing = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        new Thread(this).start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        isDrawing = false;
    }

    @Override
    public void run() {
        while (isDrawing){
            currentTime = System.currentTimeMillis();
            if (previousTime == -1){
                previousTime = currentTime;
            }else {
                deltaTime = currentTime - previousTime;
                previousTime = currentTime;
            }
            draw();
        }
    }
    private void draw(){
        try {
            height = getHeight();
            width = getWidth();
            if (time == 1000){
                delta = width/1000d;//获得每一毫秒对应的长度
                Log.d("200","width/1000 == "+(width/1000));
            }
            right += delta*deltaTime;
            time -= deltaTime;
            if (time<0){
                time = 0;
                right = width;
            }
            label = ""+time;
            canvas = holder.lockCanvas();
            if (canvas!=null){
                canvas.drawColor(Color.WHITE);
                textPath.moveTo(0,height-250);
                textPath.lineTo((float) width,height-250);
                canvas.drawTextOnPath(label,textPath,0,0,textPaint);
                Rect rect = new Rect(0,height-150,(int) right,height-100);
                canvas.drawRect(rect,linePaint);
                if (right>=width){
                    right = 0;
                    previousTime = -1;
                    isDrawing = false;
                }
            }
        }catch (Exception e){

        }finally {
            if (canvas!=null)holder.unlockCanvasAndPost(canvas);
        }
    }
}

package com.example.paintme.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ColorDrop extends View {

    private Paint mPaint;
    private static Paint mStrokePaint;

    static {
        mStrokePaint = new Paint();
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(20.0f);
        mStrokePaint.setColor(Color.BLACK);
    }

    public ColorDrop(Context context) {
        super(context);
        init();
    }

    public ColorDrop(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10.0f);
        mPaint.setColor(Color.BLUE);
    }

    public void setColor(int color) {
        mPaint.setColor(color);
    }

    public int getColor() {
        return mPaint.getColor();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();


        canvas.drawCircle(width/2, height/2, (width - getPaddingLeft() - getPaddingRight())/2, mStrokePaint);
        canvas.drawCircle(width/2, height/2, (width - getPaddingTop() - getPaddingBottom())/2, mPaint);
    }
}

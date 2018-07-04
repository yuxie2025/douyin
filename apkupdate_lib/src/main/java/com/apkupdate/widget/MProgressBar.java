package com.apkupdate.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Description
 * Roken
 * Date 2017/6/7 0007. 下午 2:33
 */
public class MProgressBar extends ProgressBar {
    String text;
    Paint mPaint;
    private boolean isTextVisibility = true;

    public MProgressBar(Context context) {
        super(context);
        initText();
    }

    public MProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initText();
    }


    public MProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    @Override
    public synchronized void setProgress(int progress) {
        setText(progress);
        super.setProgress(progress);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD);
        this.mPaint.setTypeface(font);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setTextSize(getHeight()*0.8f);
        int x = (getWidth() / 2) - rect.centerX() ;
        int y = (getHeight()/ 2) - rect.centerY();

        if (isTextVisibility) {
            canvas.drawText(this.text, x, y + (getHeight() * 0.2f) / 2, this.mPaint);
        }
    }

    //初始化，画笔
    private void initText(){
        this.mPaint = new Paint();
        this.mPaint.setColor(Color.WHITE);
    }

    private void setText(){
        setText(this.getProgress());
    }

    //设置文字内容
    private void setText(int progress){
        int i = (progress * 100)/this.getMax();
        this.text = String.valueOf(i) + "%";
    }

    public void setTextVisibility(boolean visibility) {
        this.isTextVisibility = visibility;
    }

}

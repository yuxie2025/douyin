package com.b3ad.yuxie.myapplication.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/7/24.
 * 自定义view流程:
 * 1    构造函数 	View初始化
 * 2 	onMeasure 	测量View大小
 * 3 	onSizeChanged 	确定View大小
 * 4 	onLayout 	确定子View布局(自定义View包含子View时有用)
 * 5 	onDraw 	实际绘制内容(invalidate()重新绘制)
 * 6 	提供接口 	控制View或监听View某些状态。
 */

public class MyView extends View implements Runnable {

    private Paint mPaint;
    private Context mContext;
    private float widthSize;
    private float heightSize;
    private float radiu;

    public MyView(Context context) {
        super(context);
        mContext = context;
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //重写绘画方法
        super.onDraw(canvas);

        float x = widthSize / 2;
        float y = heightSize / 2;

        canvas.drawCircle(x, y, radiu, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量view的大小

        //修改view的宽高使用,注释super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);

        //widthMeasureSpec 是测量模式和高度合成的值
        //在int类型的32位二进制位中，31-30这两位表示测量模式,29~0这三十位表示宽和高的实际值

        //MeasureSpec.UNSPECIFIED 0 默认值，父控件没有给子view任何限制，子View可以设置为任意大小。
        //MeasureSpec.EXACTLY 1 表示父控件已经确切的指定了子View的大小。
        //MeasureSpec.AT_MOST 2 表示子View具体大小没有尺寸限制，但是存在上限，上限一般为父View大小。
        //获取测量模式
        int withMode = MeasureSpec.getMode(widthMeasureSpec);
        int higthMode = MeasureSpec.getMode(heightMeasureSpec);

        widthSize = MeasureSpec.getSize(widthMeasureSpec);//获取父控件的宽度
        heightSize = MeasureSpec.getSize(heightMeasureSpec);//获取父控件的高度

//        Log.i("TAG","widthSize:"+widthSize);
//        Log.i("TAG","heightSize:"+heightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //确认view的大小

        //这是因为View的大小不仅由View本身控制，而且受父控件的影响，
        //所以我们在确定View大小的时候最好使用系统提供的onSizeChanged回调函数。
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //确定子View布局(自定义View包含子View时有用)
        super.onLayout(changed, left, top, right, bottom);
    }

    private void initPaint() {
        //实例画笔,并打开抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //Paint.Style.STROKE:描边
        //Paint.Style.FULL_AND_STROKE:描边并填充
        //Paint.Style.FULL :填充
        mPaint.setStyle(Paint.Style.STROKE);//设置画笔样式为,描边
        mPaint.setColor(Color.LTGRAY);//设置画笔颜色为浅灰色
        mPaint.setStrokeWidth(10);//设置画笔的粗细,单位:像素px
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void run() {
        /*
         * 确保线程不断执行不断刷新界面
         */
        while (true) {
            try {
                /*
                 * 如果半径小于200则自加否则大于200后重置半径值以实现往复
                 */
                if (radiu <= 200) {
                    radiu += 10;

                    // 刷新View
                    //invalidate();
                    postInvalidate();//子线程刷新ui
                } else {
                    radiu = 0;
                }

                // 每执行一次暂停40毫秒
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

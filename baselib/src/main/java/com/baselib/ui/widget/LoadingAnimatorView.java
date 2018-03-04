package com.baselib.ui.widget;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
/**
 * 作者: liuhuaqian on 2017/9/20.
 */

public class LoadingAnimatorView extends SurfaceView implements
        SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private Bitmap bitmap;
    private Paint paint1;
    private Paint paint2;
    public boolean flag = true;
    private int y = 100;

    public LoadingAnimatorView(Context context,int imgId ) {
        super(context);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
        holder = this.getHolder();
        holder.addCallback(this);
        paint1 = new Paint();
        paint1.setColor(Color.RED);
        paint2 = new Paint();
        paint2.setColor(Color.GRAY);
        Bitmap bitmap1 = BitmapFactory.decodeStream(context.getResources()
                .openRawResource(imgId));
        bitmap = bitmap1.extractAlpha();// 获取一个透明图片
        y = bitmap.getWidth();//初始化y轴坐标
    }
    //改变裁剪区域
    private void playAnimator() {
        if (y > 0) {
            y-=3;
        }
    }

    public void drawLoadingAnimator() {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            if(canvas != null){
                canvas.drawBitmap(bitmap, 100, 100,null);
                canvas.drawColor(Color.GREEN);
                canvas.drawBitmap(bitmap, 100, 100, paint2);
                canvas.save();
                //裁剪
                canvas.clipRect(100, y+100, bitmap.getWidth()+100,
                        bitmap.getHeight()+100);
                canvas.drawBitmap(bitmap, 100, 100, paint1);
                canvas.restore();
            }
            /*
             * Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
             * Rect dst = new Rect(100, 100, bitmap.getWidth()+100, y+100);
             * canvas.drawBitmap(bitmap, src, dst, paint2);
             */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if (holder != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        new Thread(this).start();//开启绘制线程
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    //绘制动画线程
    @Override
    public void run() {
        while (flag) {
            drawLoadingAnimator();
            playAnimator();
            try {
                Thread.sleep(200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
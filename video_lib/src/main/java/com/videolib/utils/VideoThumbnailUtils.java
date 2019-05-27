package com.videolib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Lankun on 2019/05/27
 */
public class VideoThumbnailUtils {

    public static String getVideoThumbnail(Context context, String videoPath) {

        String fileName;
        if (TextUtils.isEmpty(videoPath)) {
            return "";
        }
        File videoFile = new File(videoPath);
        if (videoFile.getName().contains(".")) {
            fileName = videoFile.getName().substring(0, videoFile.getName().lastIndexOf("."));
        } else {
            fileName = videoFile.getName();
        }

        //如果缩略图 存在 直接返回
        File f = new File(context.getExternalCacheDir() + File.separator + fileName + ".jpg");
        if (f.exists()) {
            return f.getAbsolutePath();
        }

        String file;
        int width = getScreenWidth(context) / 4;
        int height = getScreenHeight(context) / 4;
        Log.d("TAG", "width:" + width + ",height:" + height);
        Bitmap bitmap = getVideoThumbnail(videoPath, width, height, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
        if (bitmap == null) {
            return "";
        }
        file = bitmap2File(context, bitmap, fileName);

        return file;
    }

    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            //noinspection ConstantConditions
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images(Video).Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind); //調用ThumbnailUtils類的靜態方法createVideoThumbnail獲取視頻的截圖；
        if (bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);//調用ThumbnailUtils類的靜態方法extractThumbnail將原圖片（即上方截取的圖片）轉化為指定大小；
        }
        return bitmap;
    }

    /**
     * Bitmap保存成File
     *
     * @param bitmap input bitmap
     * @param name   output file's name
     * @return String output file's path
     */

    public static String bitmap2File(Context context, Bitmap bitmap, String name) {

        File f = new File(context.getExternalCacheDir() + File.separator + name + ".jpg");
        if (f.exists()) {
            return f.getAbsolutePath();
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return "";
        }
        return f.getAbsolutePath();
    }
}

package com.baselib.takephoto.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.model.TResult;

import java.io.File;

/**
 * desc: 多图片选择
 * Created by Lankun on 2018/10/29/029
 */
@SuppressWarnings("unused")
public class MultipleImagesActivity extends TakePhotoActivity {

    /**
     * 选择了相机
     */
    public static final String CAMERA = "CAMERA";
    /**
     * 选择了相册
     */
    public static final String PHOTO = "PHOTO";
    /**
     * 请求码
     */
    public static final int REQUEST_CODE = 1021;
    /**
     * 返回码
     */
    public static final int RESULT_CODE = 6666;

    /**
     * 返回路径
     */
    public static final String PATH = "path";
    public static final String LIMIT = "limit";


    private static Class<?> mCls;

    File file;

    public static void start(Activity activity, Class<?> cls, int limit) {
        Intent intent = new Intent(activity, MultipleImagesActivity.class);
        intent.putExtra(LIMIT, limit);
        mCls = cls;
        activity.startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        selectPhoto(getTakePhoto());
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
        finish();
    }


    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
        finish();
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (result != null) {
            //这里是设置返回页面
            Intent intent = new Intent(MultipleImagesActivity.this, mCls);
            intent.putExtra(PATH, result.getImage().getCompressPath());
            setResult(RESULT_CODE, intent);
        }
        finish();
    }

    private void selectPhoto(TakePhoto takePhoto) {
        file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//        Uri imageUri = Uri.fromFile(file);
////
////        //压缩参数
////        CompressConfig config = new CompressConfig.Builder()
////                .setMaxSize(50 * 1024)//尺寸
////                .setMaxPixel(800)//最大像素
////                .enableReserveRaw(false)//是否保留原文件
////                .create();
////        takePhoto.onEnableCompress(config, false);//压缩是对话框

        takePhoto.onPickMultiple(5);
    }

//    /**
//     * 回调选择头像地址
//     */
//        if (resultCode == SelectPhotoActivity.RESULT_CODE) {
//        switch (requestCode) {
//            case SelectPhotoActivity.REQUEST_CODE:   // 调用相机拍照
//                File file = new File(data.getStringExtra(SelectPhotoActivity.PATH));
//                if (file.exists() && file.length() == 0) {
//                    return;
//                }
//                break;
//        }
//    }
}

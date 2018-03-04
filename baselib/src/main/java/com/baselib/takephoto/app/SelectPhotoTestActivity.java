package com.baselib.takephoto.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.baselib.takephoto.compress.CompressConfig;
import com.baselib.takephoto.model.CropOptions;
import com.baselib.takephoto.model.TResult;
import com.baselib.takephoto.model.TakePhotoOptions;

import java.io.File;

/**
 * 选择头像中间页面
 * 传入值为 Type
 * CAMERA 调用相机
 * PHOTO 调用相册
 *
 */

public class SelectPhotoTestActivity extends TakePhotoActivity {

    File file;

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
            //TODO 这里是设置返回页面
//            Intent intent=new Intent(SelectPhotoActivity.this,HeadPortraitActivity.class);
//            intent.putExtra("path",result.getImage().getCompressPath());
//            setResult(6,intent);
        }
        finish();
    }

    private void selectPhoto(TakePhoto takePhoto) {
        file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);

        //压缩参数
        CompressConfig config = new CompressConfig.Builder()
                .setMaxSize(50 * 1024)//尺寸
                .setMaxPixel(800)//最大像素
                .enableReserveRaw(false)//是否保留原文件
                .create();
        takePhoto.onEnableCompress(config, false);//压缩是对话框

        //是否使用自带相册
        TakePhotoOptions.Builder builder1 = new TakePhotoOptions.Builder();
        builder1.setWithOwnGallery(false);//是否使用TakePhoto自带的相册进行图片选择，默认不使用，但选择多张图片会使用
        takePhoto.setTakePhotoOptions(builder1.create());

        //剪切设置
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setOutputX(400).setOutputY(400);//剪切大小设置
        builder.setWithOwnCrop(true);

        String type = getIntent().getStringExtra("Type");
        if ("CAMERA".equals(type)) {
            takePhoto.onPickFromCaptureWithCrop(imageUri, builder.create());
        } else if ("PHOTO".equals(type)) {
            takePhoto.onPickFromGalleryWithCrop(imageUri, builder.create());
        }else{
            Toast.makeText(this,"缺少Type请用意图传入...",Toast.LENGTH_SHORT).show();
        }

    }
}

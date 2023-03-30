
package com.yuxie.baselib.qrCode;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.huawei.hms.hmsscankit.RemoteView;
import com.huawei.hms.hmsscankit.ScanUtil;
import com.huawei.hms.ml.scan.HmsScan;
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions;
import com.yuxie.baselib.R;

/**
 * 扫码二维码(华为 扫码库 HMS Scankit)
 * 文档地址:
 * https://developer.huawei.com/consumer/cn/doc/development/HMSCore-Guides/android-customized-view-0000001050042012
 */
public final class CaptureActivity extends Activity {

//    private static final String TAG = CaptureActivity.class.getSimpleName();

    private RemoteView remoteView;
    int mScreenWidth;
    int mScreenHeight;
    //The width and height of scan_view_finder is both 240 dp.
    final int SCAN_FRAME_SIZE = 240;

    public static final int RETURN_ALBUM = 10004;

    public static void open(Context mContext) {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        mContext.startActivity(intent);
    }

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.qr_code_activity_capture);

        BarUtils.setStatusBarColor(this, Color.parseColor("#00000000"));

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }

        initView();

        // Bind the camera preview screen.
        FrameLayout frameLayout = findViewById(R.id.preview_view);

        //1. Obtain the screen density to calculate the viewfinder's rectangle.
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        //2. Obtain the screen size.
        mScreenWidth = getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;

        int scanFrameSize = (int) (SCAN_FRAME_SIZE * density);

        //3. Calculate the viewfinder's rectangle, which in the middle of the layout.
        //Set the scanning area. (Optional. Rect can be null. If no settings are specified, it will be located in the middle of the layout.)
        Rect rect = new Rect();
        rect.left = mScreenWidth / 2 - scanFrameSize / 2;
        rect.right = mScreenWidth / 2 + scanFrameSize / 2;
        rect.top = mScreenHeight / 2 - scanFrameSize / 2;
        rect.bottom = mScreenHeight / 2 + scanFrameSize / 2;


        //Initialize the RemoteView instance, and set callback for the scanning result.
        remoteView = new RemoteView.Builder().setContext(this).setBoundingBox(rect).setFormat(HmsScan.ALL_SCAN_TYPE).build();

        // Subscribe to the scanning result callback event.
        remoteView.setOnResultCallback(result -> {
            //Check the result.
            if (result != null && result.length > 0 && result[0] != null && !TextUtils.isEmpty(result[0].getOriginalValue())) {
                remoteView.pauseContinuouslyScan();
                QrCodeResult resultNew = new QrCodeResult(result[0].originalValue);
                dealResult(resultNew);
            }
        });
        // Load the customized view to the activity.
        remoteView.onCreate(savedInstanceState);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        frameLayout.addView(remoteView, params);

    }

    public void initView() {
        RelativeLayout rlTitle = findViewById(R.id.rl_title);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlTitle.getLayoutParams();
        layoutParams.topMargin = BarUtils.getStatusBarHeight();
        rlTitle.setLayoutParams(layoutParams);

        findViewById(R.id.iv_light).setOnClickListener(v -> remoteView.switchLight());

        findViewById(R.id.iv_local).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, RETURN_ALBUM);
        });

        findViewById(R.id.iv_back).setOnClickListener(v -> finish());
    }

    /**
     * Call the lifecycle management method of the remoteView activity.
     */
    @Override
    protected void onStart() {
        super.onStart();
        remoteView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        remoteView.onResume();
        remoteView.resumeContinuouslyScan();
    }

    @Override
    protected void onPause() {
        super.onPause();
        remoteView.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
        remoteView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        remoteView.onDestroy();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != RETURN_ALBUM || resultCode != RESULT_OK || data == null || data.getData() == null) {
            return;
        }
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(CaptureActivity.this.getContentResolver(), data.getData());
            QrCodeResult resultNew = getResultByBitmap(bitmap);
            if (!TextUtils.isEmpty(resultNew.getText())) {
                //图片识别到二维码
                dealResult(resultNew);
            } else {
                //图片未识别到二维码
                handleQrCode(resultNew);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * bitmap识别二维码
     */
    public static QrCodeResult getResultByBitmap(Bitmap bitmap) {
        QrCodeResult resultNew;
        if (bitmap != null) {
            HmsScan[] hmsScans = ScanUtil.decodeWithBitmap(Utils.getApp(),
                    bitmap, new HmsScanAnalyzerOptions.Creator().setPhotoMode(true).create());
            bitmap.recycle();
            if (hmsScans != null && hmsScans.length > 0
                    && hmsScans[0] != null
                    && !TextUtils.isEmpty(hmsScans[0].getOriginalValue())) {
                resultNew = new QrCodeResult(hmsScans[0].getOriginalValue());
                return resultNew;
            }
        }
        resultNew = new QrCodeResult();
        return resultNew;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 处理图片二维码解析的数据
     */
    public void handleQrCode(QrCodeResult result) {
        if (null == result || TextUtils.isEmpty(result.getText())) {
            restartPreviewAfterDelay();
            ToastUtils.showLong("请识别正确的二维码或直接扫码！");
        } else {
            runOnUiThread(() -> dealResult(result));
        }
    }

    //处理扫描二维码之后的结果
    public void dealResult(final QrCodeResult rawResult) {
        if (rawResult == null || TextUtils.isEmpty(rawResult.getText())) {
            restartPreviewAfterDelay();
            ToastUtils.showLong("抱歉,没有扫描到二维码");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("qrCode", rawResult.getText());
        setResult(RESULT_OK, intent);
        ToastUtils.showLong(rawResult.getText());
        finish();
    }


    public void restartPreviewAfterDelay() {
        remoteView.resumeContinuouslyScan();
    }
}

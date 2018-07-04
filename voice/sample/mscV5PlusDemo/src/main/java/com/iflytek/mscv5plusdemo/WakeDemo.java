package com.iflytek.mscv5plusdemo;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.RequestListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechEvent;
import com.iflytek.cloud.VoiceWakeuper;
import com.iflytek.cloud.WakeuperListener;
import com.iflytek.cloud.WakeuperResult;
import com.iflytek.cloud.util.FileDownloadListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;

public class WakeDemo extends Activity implements OnClickListener {
	private String TAG = "ivw";
	private Toast mToast;
	private TextView textView;
	// 语音唤醒对象
	private VoiceWakeuper mIvw;
	// 唤醒结果内容
	private String resultString;
	
	// 设置门限值 ： 门限值越低越容易被唤醒
	private TextView tvThresh;
	private SeekBar seekbarThresh;
	private final static int MAX = 3000;
	private final static int MIN = 0;
	private int curThresh = 1450;
	private String threshStr = "门限值：";
	private String keep_alive = "1";
    private String ivwNetMode = "0";
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wake_activity);
		
		initUi();
		// 初始化唤醒对象
		mIvw = VoiceWakeuper.createWakeuper(this, null);
	}

	@SuppressLint("ShowToast")
	private void initUi() {
		findViewById(R.id.btn_start).setOnClickListener(this);
		findViewById(R.id.btn_stop).setOnClickListener(this);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		textView = (TextView) findViewById(R.id.txt_show_msg);
		tvThresh = (TextView)findViewById(R.id.txt_thresh);
		seekbarThresh = (SeekBar)findViewById(R.id.seekBar_thresh);
		seekbarThresh.setMax(MAX - MIN);
		seekbarThresh.setProgress(25);
		tvThresh.setText(threshStr + curThresh);
		seekbarThresh.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				curThresh = seekbarThresh.getProgress() + MIN;
				tvThresh.setText(threshStr + curThresh);
			}
		});
		
		RadioGroup group = (RadioGroup) findViewById(R.id.ivw_net_mode);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				/**
				 * 闭环优化网络模式有三种：
				 * 模式0：关闭闭环优化功能
				 * 
				 * 模式1：开启闭环优化功能，允许上传优化数据。需开发者自行管理优化资源。
				 * sdk提供相应的查询和下载接口，请开发者参考API文档，具体使用请参考本示例
				 * queryResource及downloadResource方法；
				 * 
				 * 模式2：开启闭环优化功能，允许上传优化数据及启动唤醒时进行资源查询下载；
				 * 本示例为方便开发者使用仅展示模式0和模式2；
				 */
				switch (arg1) {
				case R.id.mode_close:
					ivwNetMode = "0";
					break;
				case R.id.mode_open:
					ivwNetMode = "1";
					break;
				default:
					break;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_start:
			//非空判断，防止因空指针使程序崩溃
			mIvw = VoiceWakeuper.getWakeuper();
			if(mIvw != null) {
				setRadioEnable(false);
				resultString = "";
				textView.setText(resultString);
				
				// 清空参数
				mIvw.setParameter(SpeechConstant.PARAMS, null);
				// 唤醒门限值，根据资源携带的唤醒词个数按照“id:门限;id:门限”的格式传入
				mIvw.setParameter(SpeechConstant.IVW_THRESHOLD, "0:"+ curThresh);
				// 设置唤醒模式
				mIvw.setParameter(SpeechConstant.IVW_SST, "wakeup");
				// 设置持续进行唤醒
				mIvw.setParameter(SpeechConstant.KEEP_ALIVE, keep_alive);
				// 设置闭环优化网络模式
				mIvw.setParameter(SpeechConstant.IVW_NET_MODE, ivwNetMode);
				// 设置唤醒资源路径
				mIvw.setParameter(SpeechConstant.IVW_RES_PATH, getResource());
				// 设置唤醒录音保存路径，保存最近一分钟的音频
				mIvw.setParameter( SpeechConstant.IVW_AUDIO_PATH, Environment.getExternalStorageDirectory().getPath()+"/msc/ivw.wav" );
				mIvw.setParameter( SpeechConstant.AUDIO_FORMAT, "wav" );
				// 如有需要，设置 NOTIFY_RECORD_DATA 以实时通过 onEvent 返回录音音频流字节
				//mIvw.setParameter( SpeechConstant.NOTIFY_RECORD_DATA, "1" );
				
				// 启动唤醒
				mIvw.startListening(mWakeuperListener);
			} else {
				showTip("唤醒未初始化");
			}
			break;
		case R.id.btn_stop:
			mIvw.stopListening();
			setRadioEnable(true);
			break;
		default:
			break;
		}		
	}
	
	/**
	 * 查询闭环优化唤醒资源
	 * 请在闭环优化网络模式1或者模式2使用
	 */
	public void queryResource() {
		int ret = mIvw.queryResource(getResource(), requestListener);
		showTip("updateResource ret:"+ret);
	}
	
	/**
	 * 下载闭环优化唤醒资源
	 * 请在闭环优化网络模式1或者模式2使用
	 * @param uri 查询请求返回下载链接
	 * @param md5 查询请求返回资源md5
	 */
	public void downloadResource(String uri, String md5) {
		if(TextUtils.isEmpty(uri)) {
			showTip("downloaduri is null");
			return;
		}
		String path = Environment
				.getExternalStorageDirectory().getAbsolutePath()
				+ "/msc/res/" + getString(R.string.app_id) + ".jet";
		int ret1 = mIvw.downloadResource(uri, path, md5, downloadListener);
		showTip("downloadResource ret:"+ret1);
	}

	// 查询资源请求回调监听
	private RequestListener requestListener = new RequestListener() {
		@Override
		public void onEvent(int eventType, Bundle params) {
			// 以下代码用于获取查询会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			//if(SpeechEvent.EVENT_SESSION_ID == eventType) {
			// 	Log.d(TAG, "sid:"+params.getString(SpeechEvent.KEY_EVENT_SESSION_ID));
			//}
		}
		
		@Override
		public void onCompleted(SpeechError error) {
			if(error != null) {
				Log.d(TAG, "error:"+error.getErrorCode());
				showTip(error.getPlainDescription(true));
			}
		}
		
		@Override
		public void onBufferReceived(byte[] buffer) {
			try {
				String resultInfo = new String(buffer, "utf-8");
				Log.d(TAG, "resultInfo:"+resultInfo);
				
				JSONTokener tokener = new JSONTokener(resultInfo);
				JSONObject object = new JSONObject(tokener);

				int ret = object.getInt("ret");
				if(ret == 0) {
					String uri = object.getString("dlurl");
					String md5 = object.getString("md5");
					Log.d(TAG,"uri:"+uri);
					Log.d(TAG,"md5:"+md5);
					showTip("请求成功");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	// 下载资源回调监听
	private FileDownloadListener downloadListener = new FileDownloadListener() {
		@Override
		public void onStart() {
			// 下载启动回调
			Log.d(TAG, "download onStart");
			showTip("download onStart");
		}
		
		@Override
		public void onProgress(int percent) {
			// 下载进度信息
			Log.d(TAG, "download onProgress,percent:"+ percent);
			showTip("download onProgress,percent:"+ percent);
		}
		
		@Override
		public void onCompleted(String filePath, SpeechError error) {
			// 下载完成回调
			if(error != null) {
				Log.d(TAG, "error:"+error.getErrorCode());
				showTip(error.getPlainDescription(true));
			} else {
				Log.d(TAG, "download onFinish,filePath:"+ filePath);
				showTip(filePath);
			}
		}
	};

	private WakeuperListener mWakeuperListener = new WakeuperListener() {

		@Override
		public void onResult(WakeuperResult result) {
			Log.d(TAG, "onResult");
			if(!"1".equalsIgnoreCase(keep_alive)) {
				setRadioEnable(true);
			}
			try {
				String text = result.getResultString();
				JSONObject object;
				object = new JSONObject(text);
				StringBuffer buffer = new StringBuffer();
				buffer.append("【RAW】 "+text);
				buffer.append("\n");
				buffer.append("【操作类型】"+ object.optString("sst"));
				buffer.append("\n");
				buffer.append("【唤醒词id】"+ object.optString("id"));
				buffer.append("\n");
				buffer.append("【得分】" + object.optString("score"));
				buffer.append("\n");
				buffer.append("【前端点】" + object.optString("bos"));
				buffer.append("\n");
				buffer.append("【尾端点】" + object.optString("eos"));
				resultString =buffer.toString();
			} catch (JSONException e) {
				resultString = "结果解析出错";
				e.printStackTrace();
			}
			textView.setText(resultString);
		}

		@Override
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
			setRadioEnable(true);
		}

		@Override
		public void onBeginOfSpeech() {
		}

		@Override
		public void onEvent(int eventType, int isLast, int arg2, Bundle obj) {
			switch( eventType ){
			// EVENT_RECORD_DATA 事件仅在 NOTIFY_RECORD_DATA 参数值为 真 时返回
			case SpeechEvent.EVENT_RECORD_DATA:
				final byte[] audio = obj.getByteArray( SpeechEvent.KEY_EVENT_RECORD_DATA );
				Log.i( TAG, "ivw audio length: "+audio.length );
				break;
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			
		}
	};
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy WakeDemo");
		// 销毁合成对象
		mIvw = VoiceWakeuper.getWakeuper();
		if (mIvw != null) {
			mIvw.destroy();
		}
	}
	
	private String getResource() {
		final String resPath = ResourceUtil.generateResourcePath(WakeDemo.this, RESOURCE_TYPE.assets, "ivw/"+getString(R.string.app_id)+".jet");
		Log.d( TAG, "resPath: "+resPath );
		return resPath;
	}

	private void showTip(final String str) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mToast.setText(str);
				mToast.show();
			}
		});
	}
	
	private void setRadioEnable(final boolean enabled) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				findViewById(R.id.ivw_net_mode).setEnabled(enabled);
				findViewById(R.id.btn_start).setEnabled(enabled);
				findViewById(R.id.seekBar_thresh).setEnabled(enabled);
			}
		});
	}
}
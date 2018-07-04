package com.iflytek.mscv5plusdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import com.iflytek.speech.setting.TtsSettings;

public class TtsDemo extends Activity implements OnClickListener {
	private static String TAG = TtsDemo.class.getSimpleName(); 	
	// 语音合成对象
	private SpeechSynthesizer mTts;

	// 默认云端发音人
	public static String voicerCloud="xiaoyan";
	// 默认本地发音人
	public static String voicerLocal="xiaoyan";
		
	
	// 云端发音人列表
	private String[] cloudVoicersEntries;
	private String[] cloudVoicersValue ;
	
	// 本地发音人列表
	private String[] localVoicersEntries;
	private String[] localVoicersValue ;
	
	//缓冲进度
	private int mPercentForBuffering = 0;	
	//播放进度
	private int mPercentForPlaying = 0;
	
	// 云端/本地选择按钮
	private RadioGroup mRadioGroup;
	// 引擎类型
	private String mEngineType = SpeechConstant.TYPE_CLOUD;
	
	private Toast mToast;
	private SharedPreferences mSharedPreferences;
	
	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ttsdemo);
		initLayout();

		// 初始化合成对象
		mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
		
		// 云端发音人名称列表
		cloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
		cloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);
				
		// 本地发音人名称列表
		localVoicersEntries = getResources().getStringArray(R.array.voicer_local_entries);
		localVoicersValue = getResources().getStringArray(R.array.voicer_local_values);
		
		mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, Activity.MODE_PRIVATE);
		mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);
	}

	/**
	 * 初始化Layout。
	 */
	private void initLayout() {
		findViewById(R.id.tts_play).setOnClickListener(this);
		
		findViewById(R.id.tts_cancel).setOnClickListener(this);
		findViewById(R.id.tts_pause).setOnClickListener(this);
		findViewById(R.id.tts_resume).setOnClickListener(this);
		findViewById(R.id.image_tts_set).setOnClickListener(this);

		findViewById(R.id.tts_btn_person_select).setOnClickListener(this);
		
		mRadioGroup=((RadioGroup) findViewById(R.id.tts_rediogroup));
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.tts_radioCloud:
					mEngineType = SpeechConstant.TYPE_CLOUD;
					break;
				case R.id.tts_radioLocal:
					mEngineType =  SpeechConstant.TYPE_LOCAL;
					//启动本地引擎
//					String path = ResourceUtil.TTS_RES_PATH+"="+ ResourceLoader.getResPath(TtsDemo.this,mSharedPreferences,"tts")+","+ResourceUtil.ENGINE_START+"=tts";
//					Boolean  ret = SpeechUtility.getUtility().setParameter(ResourceUtil.ENGINE_START,path);
//					showTip("启动本地引擎结果："+ret);
					break;
				default:
					break;
				}
			}
		} );
	}	

	@Override
	public void onClick(View view) {
		if( null == mTts ){
			// 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
			this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化" );
			return;
		}
		
		switch(view.getId()) {
		case R.id.image_tts_set:
			Intent intent = new Intent(TtsDemo.this, TtsSettings.class);
			startActivity(intent);
			break;
		// 开始合成
		// 收到onCompleted 回调时，合成结束、生成合成音频
        // 合成的音频格式：只支持pcm格式
		case R.id.tts_play:
			String text = ((EditText) findViewById(R.id.tts_text)).getText().toString();
			// 设置参数
			setParam();
			int code = mTts.startSpeaking(text, mTtsListener);
//			/** 
//			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
//			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
//			*/
//			String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
//			int code = mTts.synthesizeToUri(text, path, mTtsListener);
			
			if (code != ErrorCode.SUCCESS) {
				showTip("语音合成失败,错误码: " + code);	
			}
			break;
		// 取消合成
		case R.id.tts_cancel:
			mTts.stopSpeaking();
			break;
		// 暂停播放
		case R.id.tts_pause:
			mTts.pauseSpeaking();
			break;
		// 继续播放
		case R.id.tts_resume:
			mTts.resumeSpeaking();
			break;
		// 选择发音人
		case R.id.tts_btn_person_select:
			showPresonSelectDialog();
			break;
		}
	}

	
	private static int selectedNumCloud=0;
	private static int selectedNumLocal=0;
	/**
	 * 发音人选择。
	 */
	private void showPresonSelectDialog() {
		switch (mRadioGroup.getCheckedRadioButtonId()) {
		// 选择在线合成
		case R.id.tts_radioCloud:			
			new AlertDialog.Builder(this).setTitle("在线合成发音人选项")
			.setSingleChoiceItems(cloudVoicersEntries, // 单选框有几项,各是什么名字
					selectedNumCloud, // 默认的选项
					new DialogInterface.OnClickListener() { // 点击单选框后的处理
				public void onClick(DialogInterface dialog,
						int which) { // 点击了哪一项
					voicerCloud = cloudVoicersValue[which];
					if ("catherine".equals(voicerCloud) || "henry".equals(voicerCloud) || "vimary".equals(voicerCloud)) {
						 ((EditText) findViewById(R.id.tts_text)).setText(R.string.text_tts_source_en);
					}else {
						((EditText) findViewById(R.id.tts_text)).setText(R.string.text_tts_source);
					}
					selectedNumCloud = which;
					dialog.dismiss();
				}
			}).show();
			break;
			
		// 选择本地合成
		case R.id.tts_radioLocal:
			new AlertDialog.Builder(this).setTitle("本地合成发音人选项")
			.setSingleChoiceItems(localVoicersEntries, // 单选框有几项,各是什么名字
					selectedNumLocal, // 默认的选项
					new DialogInterface.OnClickListener() { // 点击单选框后的处理
				public void onClick(DialogInterface dialog,
						int which) { // 点击了哪一项
					voicerLocal = localVoicersValue[which];
					if ("catherine".equals(voicerLocal) || "henry".equals(voicerLocal) || "vimary".equals(voicerLocal)) {
						 ((EditText) findViewById(R.id.tts_text)).setText(R.string.text_tts_source_en);
					}else {
						((EditText) findViewById(R.id.tts_text)).setText(R.string.text_tts_source);
					}
					selectedNumLocal = which;
					dialog.dismiss();
				}
			}).show();
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d(TAG, "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
        		showTip("初始化失败,错误码："+code);
        	} else {
				// 初始化成功，之后可以调用startSpeaking方法
        		// 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
        		// 正确的做法是将onCreate中的startSpeaking调用移至这里
			}		
		}
	};

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		
		@Override
		public void onSpeakBegin() {
			showTip("开始播放");
		}

		@Override
		public void onSpeakPaused() {
			showTip("暂停播放");
		}

		@Override
		public void onSpeakResumed() {
			showTip("继续播放");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
			// 合成进度
			mPercentForBuffering = percent;
			showTip(String.format(getString(R.string.tts_toast_format),
					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
			// 播放进度
			mPercentForPlaying = percent;
			showTip(String.format(getString(R.string.tts_toast_format),
					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onCompleted(SpeechError error) {
			if (error == null) {
				showTip("播放完成");
			} else if (error != null) {
				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			// 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
			// 若使用本地能力，会话id为null
			//	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
			//		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
			//		Log.d(TAG, "session id =" + sid);
			//	}
		}
	};

	private void showTip(final String str){
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mToast.setText(str);
				mToast.show();
			}
		});
	}

	/**
	 * 参数设置
	 * @param param
	 * @return 
	 */
	private void setParam(){
		// 清空参数
		mTts.setParameter(SpeechConstant.PARAMS, null);
		//设置合成
		if(mEngineType.equals(SpeechConstant.TYPE_CLOUD))
		{
			//设置使用云端引擎
			mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
			//设置发音人
			mTts.setParameter(SpeechConstant.VOICE_NAME,voicerCloud);
		}else {
			//设置使用本地引擎
			mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
			//设置发音人资源路径
			mTts.setParameter(ResourceUtil.TTS_RES_PATH,getResourcePath());
			//设置发音人
			mTts.setParameter(SpeechConstant.VOICE_NAME,voicerLocal);
		}
		//设置合成语速
		mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
		//设置合成音调
		mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
		//设置合成音量
		mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "50"));
		//设置播放器音频流类型
		mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
		
		// 设置播放合成音频打断音乐播放，默认为true
		mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");
		
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
		mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
	}
	
	//获取发音人资源路径
	private String getResourcePath(){
		StringBuffer tempBuffer = new StringBuffer();
		//合成通用资源
		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "tts/common.jet"));
		tempBuffer.append(";");
		//发音人资源
		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "tts/"+TtsDemo.voicerLocal+".jet"));
		return tempBuffer.toString();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if( null != mTts ){
			mTts.stopSpeaking();
			// 退出时释放连接
			mTts.destroy();
		}
	}
}

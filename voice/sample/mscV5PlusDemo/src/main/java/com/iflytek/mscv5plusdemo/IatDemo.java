package com.iflytek.mscv5plusdemo;

import java.util.HashMap;
import java.util.LinkedHashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.util.ContactManager;
import com.iflytek.cloud.util.ContactManager.ContactListener;
import com.iflytek.speech.setting.IatSettings;
import com.iflytek.speech.util.FucUtil;
import com.iflytek.speech.util.JsonParser;

public class IatDemo extends Activity implements OnClickListener{
	private static String TAG = "IatDemo";
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog mIatDialog;
	// 听写结果内容
	private EditText mResultText;
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	
	private Toast mToast;

	private SharedPreferences mSharedPreferences;
	private boolean mTranslateEnable = false;
	
	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.iatdemo);
		initLayout();
		// 初始化识别无UI识别对象
		// 使用SpeechRecognizer对象，可根据回调消息自定义界面；
		mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
		
		// 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
		// 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
		mIatDialog = new RecognizerDialog(this,mInitListener);
		
		mSharedPreferences = getSharedPreferences(IatSettings.PREFER_NAME, Activity.MODE_PRIVATE);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);	
		mResultText = ((EditText)findViewById(R.id.iat_text));
	}

	/**
	 * 初始化Layout。
	 */
	private void initLayout(){
		findViewById(R.id.iat_recognize).setOnClickListener(this);
		findViewById(R.id.iat_recognize_stream).setOnClickListener(this);
		findViewById(R.id.iat_upload_contacts).setOnClickListener(this);
		findViewById(R.id.iat_upload_userwords).setOnClickListener(this);
		findViewById(R.id.iat_stop).setOnClickListener(this);
		findViewById(R.id.iat_cancel).setOnClickListener(this);
		findViewById(R.id.image_iat_set).setOnClickListener(this);
	}

	int ret = 0;// 函数调用返回值
	@Override
	public void onClick(View view) {		
		if( null == mIat ){
			// 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
			this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化" );
			return;
		}
		
		switch (view.getId()) {
		// 进入参数设置页面
		case R.id.image_iat_set:
			Intent intents = new Intent(IatDemo.this, IatSettings.class);
			startActivity(intents);
			break;
		// 开始听写
		// 如何判断一次听写结束：OnResult isLast=true 或者 onError
		case R.id.iat_recognize:
			mResultText.setText(null);// 清空显示内容
			mIatResults.clear();
			// 设置参数
			setParam();
			boolean isShowDialog = mSharedPreferences.getBoolean(getString(R.string.pref_key_iat_show), true);
			if (isShowDialog) {
				// 显示听写对话框
				mIatDialog.setListener(mRecognizerDialogListener);
				mIatDialog.show();
				showTip(getString(R.string.text_begin));
			} else {
				// 不显示听写对话框
				ret = mIat.startListening(mRecognizerListener);
				if (ret != ErrorCode.SUCCESS) {
					showTip("听写失败,错误码：" + ret);
				} else {
					showTip(getString(R.string.text_begin));
				}
			}
			break;
		// 音频流识别
		case R.id.iat_recognize_stream:
			mResultText.setText(null);// 清空显示内容
			mIatResults.clear();
			// 设置参数
			setParam();
			// 设置音频来源为外部文件
			mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
			// 也可以像以下这样直接设置音频文件路径识别（要求设置文件在sdcard上的全路径）：
			// mIat.setParameter(SpeechConstant.AUDIO_SOURCE, "-2");
			// mIat.setParameter(SpeechConstant.ASR_SOURCE_PATH, "sdcard/XXX/XXX.pcm");
			ret = mIat.startListening(mRecognizerListener);
			if (ret != ErrorCode.SUCCESS) {
				showTip("识别失败,错误码：" + ret);
			} else {
				byte[] audioData = FucUtil.readAudioFile(IatDemo.this, "iattest.wav");
				
				if (null != audioData) {
					showTip(getString(R.string.text_begin_recognizer));
					// 一次（也可以分多次）写入音频文件数据，数据格式必须是采样率为8KHz或16KHz（本地识别只支持16K采样率，云端都支持），位长16bit，单声道的wav或者pcm
					// 写入8KHz采样的音频时，必须先调用setParameter(SpeechConstant.SAMPLE_RATE, "8000")设置正确的采样率
					// 注：当音频过长，静音部分时长超过VAD_EOS将导致静音后面部分不能识别
					mIat.writeAudio(audioData, 0, audioData.length);
					mIat.stopListening();
				} else {
					mIat.cancel();
					showTip("读取音频流失败");
				}
			}
			break;
		// 停止听写
		case R.id.iat_stop:
			mIat.stopListening();
			showTip("停止听写");
			break;
		// 取消听写
		case R.id.iat_cancel:
			mIat.cancel();
			showTip("取消听写");
			break;
		// 上传联系人
		case R.id.iat_upload_contacts:
			showTip(getString(R.string.text_upload_contacts));
			ContactManager mgr = ContactManager.createManager(IatDemo.this, mContactListener);	
			mgr.asyncQueryAllContactsName();
			break;
			// 上传用户词表
		case R.id.iat_upload_userwords:
			showTip(getString(R.string.text_upload_userwords));
			String contents = FucUtil.readFile(IatDemo.this, "userwords","utf-8");
			mResultText.setText(contents);
			// 指定引擎类型
			mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
			// 置编码类型
			mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
			ret = mIat.updateLexicon("userword", contents, mLexiconListener);
			if (ret != ErrorCode.SUCCESS)
				showTip("上传热词失败,错误码：" + ret);
			break;
		default:
			break;
		}
	}

	/**
	 * 初始化监听器。
	 */
	private InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				showTip("初始化失败，错误码：" + code);
			}
		}
	};

	/**
	 * 上传联系人/词表监听器。
	 */
	private LexiconListener mLexiconListener = new LexiconListener() {

		@Override
		public void onLexiconUpdated(String lexiconId, SpeechError error) {
			if (error != null) {
				showTip(error.toString());
			} else {
				showTip(getString(R.string.text_upload_success));
			}
		}
	};

	/**
	 * 听写监听器。
	 */
	private RecognizerListener mRecognizerListener = new RecognizerListener() {

		@Override
		public void onBeginOfSpeech() {
			// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
			showTip("开始说话");
		}

		@Override
		public void onError(SpeechError error) {
			// Tips：
			// 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
			if(mTranslateEnable && error.getErrorCode() == 14002) {
				showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
			} else {
				showTip(error.getPlainDescription(true));
			}
		}

		@Override
		public void onEndOfSpeech() {
			// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
			showTip("结束说话");
		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {
			if( mTranslateEnable ){
				printTransResult( results );
			}else{
				String text = JsonParser.parseIatResult(results.getResultString());
				mResultText.append(text);
				mResultText.setSelection(mResultText.length());
			}
			
			if(isLast) {
				//TODO 最后的结果
			}
		}

		@Override
		public void onVolumeChanged(int volume, byte[] data) {
			showTip("当前正在说话，音量大小：" + volume);
			Log.d(TAG, "返回音频数据："+data.length);
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
	
	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			Log.d(TAG, "recognizer result：" + results.getResultString());
			
			if( mTranslateEnable ){
				printTransResult( results );
			}else{
				String text = JsonParser.parseIatResult(results.getResultString());
				mResultText.append(text);
				mResultText.setSelection(mResultText.length());
			}
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			if(mTranslateEnable && error.getErrorCode() == 14002) {
				showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
			} else {
				showTip(error.getPlainDescription(true));
			}
		}

	};

	/**
	 * 获取联系人监听器。
	 */
	private ContactListener mContactListener = new ContactListener() {

		@Override
		public void onContactQueryFinish(final String contactInfos, boolean changeFlag) {
			// 注：实际应用中除第一次上传之外，之后应该通过changeFlag判断是否需要上传，否则会造成不必要的流量.
			// 每当联系人发生变化，该接口都将会被回调，可通过ContactManager.destroy()销毁对象，解除回调。
			// if(changeFlag) {
			// 指定引擎类型
			runOnUiThread(new Runnable() {
				public void run() {
					mResultText.setText(contactInfos);
				}
			});
			
			mIat.setParameter(SpeechConstant.ENGINE_TYPE,SpeechConstant.TYPE_CLOUD);
			mIat.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
			ret = mIat.updateLexicon("contact", contactInfos, mLexiconListener);
			if (ret != ErrorCode.SUCCESS) {
				showTip("上传联系人失败：" + ret);
			}
		}
	};

	private void showTip(final String str)
	{
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
	 * @return
	 */
	public void setParam(){
		// 清空参数
		mIat.setParameter(SpeechConstant.PARAMS, null);
		String lag = mSharedPreferences.getString("iat_language_preference", "mandarin");
		// 设置引擎
		mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
		// 设置返回结果格式
		mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

		this.mTranslateEnable = mSharedPreferences.getBoolean( this.getString(R.string.pref_key_translate), false );
		if( mTranslateEnable ){
			Log.i( TAG, "translate enable" );
			mIat.setParameter( SpeechConstant.ASR_SCH, "1" );
			mIat.setParameter( SpeechConstant.ADD_CAP, "translate" );
			mIat.setParameter( SpeechConstant.TRS_SRC, "its" );
		}
		
		if (lag.equals("en_us")) {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
			mIat.setParameter(SpeechConstant.ACCENT, null);
			

			if( mTranslateEnable ){
				mIat.setParameter( SpeechConstant.ORI_LANG, "en" );
				mIat.setParameter( SpeechConstant.TRANS_LANG, "cn" );
			}
		}else {
			// 设置语言
			mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
			// 设置语言区域
			mIat.setParameter(SpeechConstant.ACCENT,lag);
			
			if( mTranslateEnable ){
				mIat.setParameter( SpeechConstant.ORI_LANG, "cn" );
				mIat.setParameter( SpeechConstant.TRANS_LANG, "en" );
			}
		}

		// 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
		mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "4000"));
		
		// 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
		mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));
		
		// 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
		mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));
		
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
		mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
	}
	
	private void printTransResult (RecognizerResult results) {
		String trans  = JsonParser.parseTransResult(results.getResultString(),"dst");
		String oris = JsonParser.parseTransResult(results.getResultString(),"src");

		if( TextUtils.isEmpty(trans)||TextUtils.isEmpty(oris) ){
			showTip( "解析结果失败，请确认是否已开通翻译功能。" );
		}else{
			mResultText.setText( "原始语言:\n"+oris+"\n目标语言:\n"+trans );
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		if( null != mIat ){
			// 退出时释放连接
			mIat.cancel();
			mIat.destroy();
		}
	}
}

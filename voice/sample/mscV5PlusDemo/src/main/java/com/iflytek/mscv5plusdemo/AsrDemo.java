package com.iflytek.mscv5plusdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.LexiconListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.util.ContactManager;
import com.iflytek.cloud.util.ContactManager.ContactListener;
import com.iflytek.cloud.util.ResourceUtil;
import com.iflytek.cloud.util.ResourceUtil.RESOURCE_TYPE;
import com.iflytek.speech.util.FucUtil;
import com.iflytek.speech.util.JsonParser;
import com.iflytek.speech.util.XmlParser;

public class AsrDemo extends Activity implements OnClickListener{
	private static String TAG = AsrDemo.class.getSimpleName();
	// 语音识别对象
	private SpeechRecognizer mAsr;
	private Toast mToast;	
	// 缓存
	private SharedPreferences mSharedPreferences;
	// 本地语法文件
	private String mLocalGrammar = null;
	// 本地词典
	private String mLocalLexicon = null;
	// 云端语法文件
	private String mCloudGrammar = null;
	// 本地语法构建路径	
	private String grmPath = Environment.getExternalStorageDirectory()
								.getAbsolutePath() + "/msc/test";
	// 返回结果格式，支持：xml,json
	private String mResultType = "json";
	
	private  final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
	private  final String GRAMMAR_TYPE_ABNF = "abnf";
	private  final String GRAMMAR_TYPE_BNF = "bnf";

	private String mEngineType = "cloud";
	@SuppressLint("ShowToast")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.isrdemo);
		initLayout();
		
		// 初始化识别对象
		mAsr = SpeechRecognizer.createRecognizer(this, mInitListener);		

		// 初始化语法、命令词
		mLocalLexicon = "张海羊\n刘婧\n王锋\n";
		mLocalGrammar = FucUtil.readFile(this,"call.bnf", "utf-8");
		mCloudGrammar = FucUtil.readFile(this,"grammar_sample.abnf","utf-8");
		
		// 获取联系人，本地更新词典时使用
		ContactManager mgr = ContactManager.createManager(AsrDemo.this, mContactListener);	
		mgr.asyncQueryAllContactsName();
		mSharedPreferences = getSharedPreferences(getPackageName(),	MODE_PRIVATE);
		mToast = Toast.makeText(this,"",Toast.LENGTH_SHORT);	
		
	}
	
	/**
	 * 初始化Layout。
	 */
	private void initLayout(){
		findViewById(R.id.isr_recognize).setOnClickListener(this);
		
		findViewById(R.id.isr_grammar).setOnClickListener(this);
		findViewById(R.id.isr_lexcion).setOnClickListener(this);
		
		findViewById(R.id.isr_stop).setOnClickListener(this);
		findViewById(R.id.isr_cancel).setOnClickListener(this);

		//选择云端or本地
		RadioGroup group = (RadioGroup)this.findViewById(R.id.radioGroup);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(checkedId == R.id.radioCloud)
				{
					((EditText)findViewById(R.id.isr_text)).setText(mCloudGrammar);
					findViewById(R.id.isr_lexcion).setEnabled(false);
					mEngineType = SpeechConstant.TYPE_CLOUD;
				}else if(checkedId == R.id.radioLocal)
				{
					((EditText)findViewById(R.id.isr_text)).setText(mLocalGrammar);
					findViewById(R.id.isr_lexcion).setEnabled(true);
					mEngineType =  SpeechConstant.TYPE_LOCAL;
				}
			}
		});
	}
    
	
	String mContent;// 语法、词典临时变量
    int ret = 0;// 函数调用返回值
	@Override
	public void onClick(View view) {		
		if( null == mAsr ){
			// 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
			this.showTip( "创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化" );
			return;
		}
		
		if(null == mEngineType) {
			showTip("请先选择识别引擎类型");
			return;
		}	
		switch(view.getId())
		{
			case R.id.isr_grammar:
				showTip("上传预设关键词/语法文件");
				// 本地-构建语法文件，生成语法id
				if (mEngineType.equals(SpeechConstant.TYPE_LOCAL)) {
					((EditText)findViewById(R.id.isr_text)).setText(mLocalGrammar);
					mContent = new String(mLocalGrammar);
					mAsr.setParameter(SpeechConstant.PARAMS, null);
					// 设置文本编码格式
					mAsr.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");
					// 设置引擎类型
					mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
					// 设置语法构建路径
					mAsr.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath);
					//使用8k音频的时候请解开注释
//					mAsr.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
					// 设置资源路径
					mAsr.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
					ret = mAsr.buildGrammar(GRAMMAR_TYPE_BNF, mContent, grammarListener);
					if(ret != ErrorCode.SUCCESS){
						showTip("语法构建失败,错误码：" + ret);
					}
				}
				// 在线-构建语法文件，生成语法id
				else {	
					((EditText)findViewById(R.id.isr_text)).setText(mCloudGrammar);
					mContent = new String(mCloudGrammar);
					// 指定引擎类型
					mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
					// 设置文本编码格式
					mAsr.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");
				    ret = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mContent, grammarListener);
					if(ret != ErrorCode.SUCCESS)
						showTip("语法构建失败,错误码：" + ret);
				}
				break;
			// 本地-更新词典
			case R.id.isr_lexcion: 
				((EditText)findViewById(R.id.isr_text)).setText(mLocalLexicon);
				mContent = new String(mLocalLexicon);
				mAsr.setParameter(SpeechConstant.PARAMS, null);
				// 设置引擎类型
				mAsr.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
				// 设置资源路径
				mAsr.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
				//使用8k音频的时候请解开注释
//				mAsr.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
				// 设置语法构建路径
				mAsr.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath);
				// 设置语法名称
				mAsr.setParameter(SpeechConstant.GRAMMAR_LIST, "call");
				// 设置文本编码格式
				mAsr.setParameter(SpeechConstant.TEXT_ENCODING,"utf-8");
				ret = mAsr.updateLexicon("contact", mContent, lexiconListener);
				if(ret != ErrorCode.SUCCESS){
					showTip("更新词典失败,错误码：" + ret);
				}
				break;
			// 开始识别
			case R.id.isr_recognize:
				((EditText)findViewById(R.id.isr_text)).setText(null);// 清空显示内容
				// 设置参数
				if (!setParam()) {
					showTip("请先构建语法。");
					return;
				};
				
				ret = mAsr.startListening(mRecognizerListener);
				if (ret != ErrorCode.SUCCESS) {
					showTip("识别失败,错误码: " + ret);	
				}
				break;
			// 停止识别
			case R.id.isr_stop:
				mAsr.stopListening();
				showTip("停止识别");
				break;
			// 取消识别
			case R.id.isr_cancel:
				mAsr.cancel();
				showTip("取消识别");
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
        		showTip("初始化失败,错误码："+code);
        	}
		}
    };
    	
	/**
     * 更新词典监听器。
     */
	private LexiconListener lexiconListener = new LexiconListener() {
		@Override
		public void onLexiconUpdated(String lexiconId, SpeechError error) {
			if(error == null){
				showTip("词典更新成功");
			}else{
				showTip("词典更新失败,错误码："+error.getErrorCode());
			}
		}
	};
	
	/**
     * 构建语法监听器。
     */
	private GrammarListener grammarListener = new GrammarListener() {
		@Override
		public void onBuildFinish(String grammarId, SpeechError error) {
			if(error == null){
				if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
					Editor editor = mSharedPreferences.edit();
					if(!TextUtils.isEmpty(grammarId))
						editor.putString(KEY_GRAMMAR_ABNF_ID, grammarId);
					editor.commit();
				}
				showTip("语法构建成功：" + grammarId);
			}else{
				showTip("语法构建失败,错误码：" + error.getErrorCode());
			}			
		}
	};
	/**
	 * 获取联系人监听器。
	 */
	private ContactListener mContactListener = new ContactListener() {
		@Override
		public void onContactQueryFinish(String contactInfos, boolean changeFlag) {
			//获取联系人
			mLocalLexicon = contactInfos;
		}		
	};
	/**
     * 识别监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {
        
        @Override
        public void onVolumeChanged(int volume, byte[] data) {
        	showTip("当前正在说话，音量大小：" + volume);
        	Log.d(TAG, "返回音频数据："+data.length);
        }
        
		@Override
		public void onResult(final RecognizerResult result, boolean isLast) {
			if (null != result && !TextUtils.isEmpty(result.getResultString())) {
				Log.d(TAG, "recognizer result：" + result.getResultString());
				String text = "";
				if (mResultType.equals("json")) {
					text = JsonParser.parseGrammarResult(result.getResultString(), mEngineType);
				} else if (mResultType.equals("xml")) {
					text = XmlParser.parseNluResult(result.getResultString());
				}
				// 显示
				((EditText) findViewById(R.id.isr_text)).setText(text);
			} else {
				Log.d(TAG, "recognizer result : null");
			}
		}
        
        @Override
        public void onEndOfSpeech() {
        	// 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入        	
			showTip("结束说话");
        }
        
        @Override
        public void onBeginOfSpeech() {
        	// 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
        	showTip("开始说话");
        }

		@Override
		public void onError(SpeechError error) {
			showTip("onError Code："	+ error.getErrorCode());
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
    
	

	private void showTip(final String str) {
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
	public boolean setParam(){
		boolean result = false;
		// 清空参数
		mAsr.setParameter(SpeechConstant.PARAMS, null);
		// 设置识别引擎
		mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
		if("cloud".equalsIgnoreCase(mEngineType))
		{
			String grammarId = mSharedPreferences.getString(KEY_GRAMMAR_ABNF_ID, null);
			if(TextUtils.isEmpty(grammarId))
			{
				result =  false;
			}else {
				// 设置返回结果格式
				mAsr.setParameter(SpeechConstant.RESULT_TYPE, mResultType);
				// 设置云端识别使用的语法id
				mAsr.setParameter(SpeechConstant.CLOUD_GRAMMAR, grammarId);
				result =  true;
			}
		}
		else
		{
			// 设置本地识别资源
			mAsr.setParameter(ResourceUtil.ASR_RES_PATH, getResourcePath());
			// 设置语法构建路径
			mAsr.setParameter(ResourceUtil.GRM_BUILD_PATH, grmPath);
			// 设置返回结果格式
			mAsr.setParameter(SpeechConstant.RESULT_TYPE, mResultType);
			// 设置本地识别使用语法id
			mAsr.setParameter(SpeechConstant.LOCAL_GRAMMAR, "call");
			// 设置识别的门限值
			mAsr.setParameter(SpeechConstant.MIXED_THRESHOLD, "30");
			// 使用8k音频的时候请解开注释
//			mAsr.setParameter(SpeechConstant.SAMPLE_RATE, "8000");
			result = true;
		}
		
		// 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
		// 注：AUDIO_FORMAT参数语记需要更新版本才能生效
		mAsr.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
		mAsr.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/asr.wav");
		return result;
	}
	
	//获取识别资源路径
	private String getResourcePath(){
		StringBuffer tempBuffer = new StringBuffer();
		//识别通用资源
		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "asr/common.jet"));
		//识别8k资源-使用8k的时候请解开注释
//		tempBuffer.append(";");
//		tempBuffer.append(ResourceUtil.generateResourcePath(this, RESOURCE_TYPE.assets, "asr/common_8k.jet"));
		return tempBuffer.toString();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if( null != mAsr ){
			// 退出时释放连接
			mAsr.cancel();
			mAsr.destroy();
		}
	}
	
}

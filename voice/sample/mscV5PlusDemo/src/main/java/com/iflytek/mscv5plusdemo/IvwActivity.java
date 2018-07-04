package com.iflytek.mscv5plusdemo;

import com.iflytek.cloud.VoiceWakeuper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class IvwActivity extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ivw_activity);
		
		((Button) findViewById(R.id.btn_wake)).setOnClickListener(IvwActivity.this);
		((Button) findViewById(R.id.btn_oneshot)).setOnClickListener(IvwActivity.this);
		
	}

	@Override
	public void onClick(View v) {
		if( null == VoiceWakeuper.createWakeuper(this, null) ){
			// 创建单例失败，与 21001 错误为同样原因，参考 http://bbs.xfyun.cn/forum.php?mod=viewthread&tid=9688
			Toast.makeText( this
					, "创建对象失败，请确认 libmsc.so 放置正确，\n 且有调用 createUtility 进行初始化"
					, Toast.LENGTH_LONG ).show();
			return;
		}
		
		Intent intent = null;
		switch (v.getId()) {
		case R.id.btn_wake:
			intent = new Intent(IvwActivity.this, WakeDemo.class);
			startActivity(intent);
			break;
			
		case R.id.btn_oneshot:
			intent = new Intent(IvwActivity.this, OneShotDemo.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
}

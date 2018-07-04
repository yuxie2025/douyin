package com.iflytek.speech.util;

import java.util.regex.Pattern;
import android.content.Context;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Toast;

/**
 * 输入框输入范围控制
 */
public class SettingTextWatcher implements TextWatcher {
	private int editStart ;
	private int editCount ;
	private EditTextPreference mEditTextPreference;
	int minValue;//最小值
	int maxValue;//最大值
	private Context mContext;
	
	public SettingTextWatcher(Context context,EditTextPreference e,int min, int max) {
		mContext = context;
		mEditTextPreference = e;
		minValue = min;
		maxValue = max;
	 }
	
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
//		Log.e("demo", "onTextChanged start:"+start+" count:"+count+" before:"+before);
		editStart = start;
		editCount = count;
	}
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {		
//		Log.e("demo", "beforeTextChanged start:"+start+" count:"+count+" after:"+after);
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		if (TextUtils.isEmpty(s)) {
			return;
		}
		String content = s.toString();
//		Log.e("demo", "content:"+content);
		if (isNumeric(content)) {
			int num = Integer.parseInt(content);
			if (num > maxValue || num < minValue) {
				s.delete(editStart, editStart+editCount);
				mEditTextPreference.getEditText().setText(s);
				Toast.makeText(mContext, "超出有效值范围", Toast.LENGTH_SHORT).show();
			}
		}else {
			s.delete(editStart, editStart+editCount);
			mEditTextPreference.getEditText().setText(s);
			Toast.makeText(mContext, "只能输入数字哦", Toast.LENGTH_SHORT).show();
		}
	}
	
	/**
	 * 正则表达式-判断是否为数字
	 */
	public static boolean isNumeric(String str){ 
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	 } 

};

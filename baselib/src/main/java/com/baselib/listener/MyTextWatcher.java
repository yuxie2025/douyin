package com.baselib.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by luo on 2018/3/29.
 * 自定义 内容改变监听,用于优化代码
 */

public abstract class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

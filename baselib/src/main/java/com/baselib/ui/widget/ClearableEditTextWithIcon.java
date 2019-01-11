package com.baselib.ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.baselib.R;

/**
 * desc:带有图标和删除符号的可编辑输入框，用户可以自定义传入的显示图标
 * Created by Lankun on 2018/10/29/029
 */
@SuppressWarnings("unused")
public class ClearableEditTextWithIcon extends AppCompatEditText implements View.OnTouchListener, TextWatcher {

    // 删除符号
    Drawable deleteImage = getResources().getDrawable(R.drawable.close_ico);

    Drawable icon;

    public ClearableEditTextWithIcon(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ClearableEditTextWithIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditTextWithIcon(Context context) {
        super(context);
        init();
    }

    private void init() {
        setOnTouchListener(this);
        addTextChangedListener(this);
        setSingleLine(true);
        setMaxLines(1);
        deleteImage.setBounds(0, 0, deleteImage.getIntrinsicWidth() + 10, deleteImage.getIntrinsicHeight());
        manageClearButton();
    }

    /**
     * 传入显示的图标资源id
     *
     * @param id 图标资源id
     */
    public void setIconResource(int id) {
        icon = getResources().getDrawable(id);
        icon.setBounds(0, 0, icon.getIntrinsicWidth(), icon.getIntrinsicHeight());
        manageClearButton();
    }

    /**
     * 传入删除图标资源id
     *
     * @param id 图标资源id
     */
    public void setDeleteImage(int id) {
        deleteImage = getResources().getDrawable(id);
        deleteImage.setBounds(0, 0, deleteImage.getIntrinsicWidth() + 10, deleteImage.getIntrinsicHeight());
        manageClearButton();
    }

    void manageClearButton() {
        if (TextUtils.isEmpty(getText().toString()))
            removeClearButton();
        else
            addClearButton();
    }

    void removeClearButton() {
        setCompoundDrawables(icon, getCompoundDrawables()[1], null, getCompoundDrawables()[3]);
    }

    void addClearButton() {
        setCompoundDrawables(this.icon, getCompoundDrawables()[1], deleteImage,
                getCompoundDrawables()[3]);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ClearableEditTextWithIcon et = ClearableEditTextWithIcon.this;

        if (et.getCompoundDrawables()[2] == null) {
            return false;
        }
        if (event.getAction() != MotionEvent.ACTION_UP) {
            return false;
        }
        if (event.getX() > et.getWidth() - et.getPaddingRight() - deleteImage.getIntrinsicWidth()) {
            et.setText("");
            removeClearButton();
        }
        return false;
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        manageClearButton();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}

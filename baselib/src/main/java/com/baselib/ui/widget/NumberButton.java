package com.baselib.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.R;


/**
 * 加减购买控件
 * Created by Lankun on 2018/10/29/029
 */
@SuppressWarnings("unused")
public class NumberButton extends LinearLayout implements View.OnClickListener, TextWatcher {
    //库存
    private int mInventory = Integer.MAX_VALUE;
    //最大购买数，默认无限制
    private int mBuyMax = Integer.MAX_VALUE;
    private EditText mCount;
    private OnWarnListener mOnWarnListener;

    private Context context;

    public NumberButton(Context context) {
        this(context, null);
    }

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.view_number_button, this);

        TextView addButton = findViewById(R.id.button_add);
        addButton.setOnClickListener(this);
        TextView subButton = findViewById(R.id.button_sub);
        subButton.setOnClickListener(this);

        mCount = findViewById(R.id.text_count);
        mCount.addTextChangedListener(this);
        mCount.setOnClickListener(this);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NumberButton);
        boolean editable = typedArray.getBoolean(R.styleable.NumberButton_editable, false);
        int buttonWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_buttonWidth, -1);
        int textWidth = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textWidth, -1);
        int textSize = typedArray.getDimensionPixelSize(R.styleable.NumberButton_textSize, -1);
        int textColor = typedArray.getColor(R.styleable.NumberButton_textColor, 0xff000000);
        typedArray.recycle();

        setEditable(editable);
        mCount.setTextColor(textColor);
        subButton.setTextColor(textColor);
        addButton.setTextColor(textColor);

        if (textSize > 0)
            mCount.setTextSize(textSize);

        if (buttonWidth > 0) {
            LayoutParams textParams = new LayoutParams(buttonWidth, LayoutParams.MATCH_PARENT);
            subButton.setLayoutParams(textParams);
            addButton.setLayoutParams(textParams);
        }
        if (textWidth > 0) {
            LayoutParams textParams = new LayoutParams(textWidth, LayoutParams.MATCH_PARENT);
            mCount.setLayoutParams(textParams);
        }
    }

    public int getNumber() {
        try {
            return Integer.parseInt(mCount.getText().toString());
        } catch (NumberFormatException ignored) {
        }
        mCount.setText("1");
        return 1;
    }

    public EditText setText(String s) {
        mCount.setText(s);
        return mCount;
    }

    public void setClickable(boolean bool) {
        mCount.setEnabled(bool);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        int count = getNumber();
        if (id == R.id.button_sub) {

            if (count > 1) {
                //正常减
                count = count - 1;
                String countStr = String.valueOf(count);
                mCount.setText(countStr);
                if (changeListener != null) {
                    changeListener.onUpdateNum(count);
                }
            }
        } else if (id == R.id.button_add) {
            if (count < Math.min(mBuyMax, mInventory)) {
                //正常添加
                count = count + 1;
                String countStr = String.valueOf(count);
                mCount.setText(countStr);
                if (changeListener != null) {
                    changeListener.onUpdateNum(count);
                }
            } else if (mInventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }

        } else if (id == R.id.text_count) {
            mCount.setSelection(mCount.getText().toString().length());
        }
    }

    public interface onChangeNumberListener {
        void onUpdateNum(int position);
    }

    private onChangeNumberListener changeListener;

    public void setOnChangeNumberListener(onChangeNumberListener changeListener) {
        this.changeListener = changeListener;
    }


    private void onNumberInput() {
        //当前数量
        int count = getNumber();
        if (count <= 0) {
            //手动输入
            Toast.makeText(context, "数量不能小于1", Toast.LENGTH_SHORT).show();
            mCount.setText("1");
            return;
        }

        int limit = Math.min(mBuyMax, mInventory);
        if (count > limit) {
            //超过了数量
            mCount.setText(String.valueOf(limit));
            if (mInventory < mBuyMax) {
                //库存不足
                warningForInventory();
            } else {
                //超过最大购买数
                warningForBuyMax();
            }
        }

    }

    /**
     * 超过的库存限制
     * Warning for inventory.
     */
    private void warningForInventory() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForInventory(mInventory);
    }

    /**
     * 超过的最大购买数限制
     * Warning for buy max.
     */
    private void warningForBuyMax() {
        if (mOnWarnListener != null) mOnWarnListener.onWarningForBuyMax(mBuyMax);
    }


    private void setEditable(boolean editable) {
        if (editable) {
            mCount.setFocusable(true);
            mCount.setKeyListener(new DigitsKeyListener());
        } else {
            mCount.setFocusable(false);
            mCount.setKeyListener(null);
        }
    }

    public NumberButton setCurrentNumber(int currentNumber) {
        if (currentNumber < 1) mCount.setText("1");
        int number = Math.min(Math.min(mBuyMax, mInventory), currentNumber);
        mCount.setText(String.valueOf(number));
        return this;
    }

    public int getInventory() {
        return mInventory;
    }

    public NumberButton setInventory(int inventory) {
        mInventory = inventory;
        return this;
    }

    public int getBuyMax() {
        return mBuyMax;
    }

    public NumberButton setBuyMax(int buyMax) {
        mBuyMax = buyMax;
        return this;
    }

    public NumberButton setOnWarnListener(OnWarnListener onWarnListener) {
        mOnWarnListener = onWarnListener;
        return this;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        onNumberInput();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public interface OnWarnListener {
        void onWarningForInventory(int inventory);

        void onWarningForBuyMax(int max);
    }
}

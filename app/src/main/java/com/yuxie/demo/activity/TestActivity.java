package com.yuxie.demo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.baselib.base.BaseActivity;
import com.lqr.emoji.EmotionKeyboard;
import com.lqr.emoji.EmotionLayout;
import com.lqr.emoji.IEmotionExtClickListener;
import com.lqr.emoji.IEmotionSelectedListener;
import com.yuxie.demo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends BaseActivity implements IEmotionSelectedListener {

    private static final String TAG = "TestActivity";
    @BindView(R.id.rvMsg)
    RecyclerView rvMsg;
    @BindView(R.id.ivAudio)
    ImageView ivAudio;
    @BindView(R.id.btnAudio)
    Button btnAudio;
    @BindView(R.id.ivEmo)
    ImageView ivEmo;
    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView(R.id.btnSend)
    Button btnSend;
    @BindView(R.id.llContent)
    LinearLayout llContent;
    @BindView(R.id.flEmotionView)
    FrameLayout flEmotionView;
    @BindView(R.id.llRoot)
    LinearLayout llRoot;
    @BindView(R.id.etContent)
    EditText mEtContent;

    @BindView(R.id.elEmotion)
    EmotionLayout mElEmotion;

    @BindView(R.id.llMore)
    LinearLayout mLlMore;

    private EmotionKeyboard mEmotionKeyboard;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mElEmotion.attachEditText(mEtContent);
        initEmotionKeyboard();

        mElEmotion.setEmotionSelectedListener(this);

        ivAudio.setOnClickListener(v -> {
            if (btnAudio.isShown()) {
//                hideAudioButton();
                mEtContent.requestFocus();
                if (mEmotionKeyboard != null) {
                    mEmotionKeyboard.showSoftInput();
                }
            } else {
                mEtContent.clearFocus();
//                showAudioButton();
                hideEmotionLayout();
                hideMoreLayout();
            }
        });

        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mEtContent.getText().toString().trim().length() > 0) {
                    btnSend.setVisibility(View.VISIBLE);
                    ivMore.setVisibility(View.GONE);
                } else {
                    btnSend.setVisibility(View.GONE);
                    ivMore.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void initEmotionKeyboard() {
        Log.i(TAG, "initEmotionKeyboard: ");

        mEmotionKeyboard = EmotionKeyboard.with(this);
        mEmotionKeyboard.bindToEditText(mEtContent);
        mEmotionKeyboard.bindToContent(llContent);
        mEmotionKeyboard.setEmotionLayout(flEmotionView);
        mEmotionKeyboard.bindToEmotionButton(ivEmo, ivMore);
        mEmotionKeyboard.setOnEmotionButtonOnClickListener(view -> {
            Log.i(TAG, "initEmotionKeyboard: " + view.getId());
            switch (view.getId()) {
                case R.id.ivEmo:
                    mEtContent.clearFocus();
                    if (!mElEmotion.isShown()) {
                        if (mLlMore.isShown()) {
                            showEmotionLayout();
                            hideMoreLayout();
//                            hideAudioButton();
                            return true;
                        }
                    } else if (mElEmotion.isShown() && !mLlMore.isShown()) {
                        ivEmo.setImageResource(R.drawable.ic_cheat_emo);
                        return false;
                    }
                    showEmotionLayout();
                    hideMoreLayout();
//                    hideAudioButton();
                    break;
                case R.id.ivMore:
                    mEtContent.clearFocus();
                    if (!mLlMore.isShown()) {
                        if (mElEmotion.isShown()) {
                            showMoreLayout();
                            hideEmotionLayout();
//                            hideAudioButton();
                            return true;
                        }
                    }
                    showMoreLayout();
                    hideEmotionLayout();
//                    hideAudioButton();
                    break;
            }
            return false;
        });
    }

    private void showEmotionLayout() {
        mElEmotion.setVisibility(View.VISIBLE);
        ivEmo.setImageResource(R.drawable.ic_cheat_keyboard);
    }

    private void hideEmotionLayout() {
        mElEmotion.setVisibility(View.GONE);
        ivEmo.setImageResource(R.drawable.ic_cheat_emo);
    }

    private void showMoreLayout() {
        mLlMore.setVisibility(View.VISIBLE);
    }

    private void hideMoreLayout() {
        mLlMore.setVisibility(View.GONE);
    }

    @Override
    public void onEmojiSelected(String key) {

    }

    @Override
    public void onStickerSelected(String categoryName, String stickerName, String stickerBitmapPath) {

    }

    @Override
    public void onBackPressed() {
        if (mElEmotion.isShown() || mLlMore.isShown()) {
            mEmotionKeyboard.interceptBackPress();
            ivEmo.setImageResource(R.drawable.ic_cheat_emo);
        } else {
            super.onBackPressed();
        }
    }
}

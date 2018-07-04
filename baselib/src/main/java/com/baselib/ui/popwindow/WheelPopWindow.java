package com.baselib.ui.popwindow;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baselib.R;
import com.baselib.basebean.StudentEvent;
import com.baselib.baserx.RxBus;
import com.baselib.enums.StudentEnum;
import com.baselib.ui.adapter.WheelAdapter;
import com.wx.wheelview.util.WheelUtils;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;

/**
 * 作者: llk on 2017/9/9.
 */

public class WheelPopWindow implements View.OnClickListener{

    private View parentview;
    private Activity m_activity;
    private FrameLayout m_share_popup;
    private PopupWindow m_share_pop;
    private StudentEnum type;
    private int positions;
    public WheelPopWindow(Activity contex, View parentview){
        m_activity = contex;
        this.parentview = parentview;
    }
    /**
     * 显示popupWindow
     *
     * @param
     */
    public void showPopupWindow(ArrayList<String> create,int size,int selection,StudentEnum type){
      //  positions=selection;
        this.type=type;
        m_share_pop = new android.widget.PopupWindow(m_activity);
        View view = m_activity.getLayoutInflater().inflate(R.layout.pop_bottom, null);
        m_share_popup = (FrameLayout) view.findViewById(R.id.share_layout_id);
        TextView cancel= (TextView) view.findViewById(R.id.cancel);
        TextView determine= (TextView) view.findViewById(R.id.determine);

        WheelView  myWheelView = (WheelView) view.findViewById(R.id.wheelview);
        myWheelView.setWheelAdapter(new WheelAdapter(m_activity));
        myWheelView.setWheelSize(size);
        myWheelView.setSkin(WheelView.Skin.Holo);
        myWheelView.setWheelData(create);
        myWheelView.setSelection(selection);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = Color.parseColor("#353535");
        style.textColor =Color.parseColor("#F86E0C");
        style.selectedTextSize = 20;
        style.holoBorderColor=Color.parseColor("#2C2C2C");
        style.selectedTextColor = Color.parseColor("#F86E0C");
        myWheelView.setStyle(style);
        myWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener<Object>() {
            @Override
            public void onItemSelected(int position, Object data) {
                positions=position;
                WheelUtils.log("selected:" + position);
            }
        });
        cancel.setOnClickListener(this);
        determine.setOnClickListener(this);
        view.setFocusable(true); // 这个很重要
        view.setFocusableInTouchMode(true);
        m_share_pop.setFocusable(true);
        m_share_pop.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        m_share_pop.setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        m_share_pop.setBackgroundDrawable(new BitmapDrawable());
        m_share_pop.setContentView(view);
        m_share_pop.showAtLocation(parentview, Gravity.BOTTOM, 0, 0);
        // 重写onKeyListener
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    //取消订阅

                    m_share_pop.dismiss();
                    m_share_pop = null;
                    return true;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.determine) {
            RxBus.getInstance().post(new StudentEvent(type, positions));
        }
        m_share_pop.dismiss();
        m_share_pop = null;
    }


}

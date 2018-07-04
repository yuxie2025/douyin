package com.baselib.ui.widget.mydatepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baselib.commonutils.MeasureUtil;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * 作者: llk on 2017/9/23.
 * 签到的DatePicker
 */

public class DatePicker extends LinearLayout {
    private DPTManager mTManager;// 主题管理器
    private DPLManager mLManager;// 语言管理器

    private Context mContext;

    private MonthView monthView;// 月视图
//    private TextView tvYear, tvMonth;// 年份 月份显示
//    private TextView tvEnsure;// 确定按钮显示


//    private TextView myMonth; //左边月份显示
//    private TextView myTitle; //中间签到文字显示
//    private RelativeLayout signIn; //右边签到布局
//    private TextView mySignIn; //签到文字显示

    private boolean isSignIn = false; //是否签到

    private OnClickSignIn onClickSignIn;

    private OnDateSelectedListener onDateSelectedListener;// 日期多选后监听

    /**
     * 点击签到触发
     */
    public interface OnClickSignIn{
        void signIn();
    }

    /**
     * 日期单选监听器
     */
    public interface OnDatePickedListener {
        void onDatePicked(String date);
    }

    /**
     * 日期多选监听器
     */
    public interface OnDateSelectedListener {
        void onDateSelected(List<String> date);
    }

    public DatePicker(Context context) {
        this(context, null);
    }

    public DatePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTManager = DPTManager.getInstance();
        mLManager = DPLManager.getInstance();

        mContext = context;

        // 设置排列方向为竖向
        setOrientation(VERTICAL);

        LayoutParams llParams =
                new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        // 标题栏根布局
        RelativeLayout rlTitle = new RelativeLayout(context);
        rlTitle.setBackgroundColor(mTManager.colorTitleBG());
        int rlTitlePadding = MeasureUtil.dp2px(context, 10);
        rlTitle.setPadding(rlTitlePadding, rlTitlePadding, rlTitlePadding, rlTitlePadding);

        // 周视图根布局
        LinearLayout llWeek = new LinearLayout(context);
        llWeek.setBackgroundColor(mTManager.colorTitleBG());
        llWeek.setOrientation(HORIZONTAL);
        int llWeekPadding = MeasureUtil.dp2px(context, 5);
        llWeek.setPadding(0, llWeekPadding, 0, llWeekPadding);
        LayoutParams lpWeek = new LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpWeek.weight = 1;

        // 标题栏子元素布局参数
//        RelativeLayout.LayoutParams lpYear =
//                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
//        lpYear.addRule(RelativeLayout.CENTER_VERTICAL);
//        RelativeLayout.LayoutParams lpMonth =
//                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
//        lpMonth.addRule(RelativeLayout.CENTER_IN_PARENT);
//        RelativeLayout.LayoutParams lpEnsure =
//                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
//        lpEnsure.addRule(RelativeLayout.CENTER_VERTICAL);
//        lpEnsure.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        // --------------------------------------------------------------------------------标题栏
        // 年份显示
//        tvYear = new TextView(context);
//        tvYear.setText("2015");
//        tvYear.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        tvYear.setTextColor(mTManager.colorTitle());
//
//        // 月份显示
//        tvMonth = new TextView(context);
//        tvMonth.setText("六月");
//        tvMonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
//        tvMonth.setTextColor(mTManager.colorTitle());
//
//        // 确定显示
//        tvEnsure = new TextView(context);
//        tvEnsure.setText(mLManager.titleEnsure());
//        tvEnsure.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        tvEnsure.setTextColor(mTManager.colorTitle());
//        tvEnsure.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != onDateSelectedListener) {
//                    onDateSelectedListener.onDateSelected(monthView.getDateSelected());
//                }
//            }
//        });
//
//        rlTitle.addView(tvYear, lpYear);
//        rlTitle.addView(tvMonth, lpMonth);
//        rlTitle.addView(tvEnsure, lpEnsure);


        RelativeLayout.LayoutParams lpMyMonth =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpMyMonth.addRule(RelativeLayout.CENTER_VERTICAL);
        RelativeLayout.LayoutParams lpMyTitle =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpMyTitle.addRule(RelativeLayout.CENTER_IN_PARENT);
        RelativeLayout.LayoutParams lpSignIn =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpSignIn.addRule(RelativeLayout.CENTER_VERTICAL);
        lpSignIn.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        RelativeLayout.LayoutParams lpTvSignIn =
                new RelativeLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        lpTvSignIn.addRule(RelativeLayout.CENTER_VERTICAL);


//        myMonth = new TextView(context);
//        myMonth.setText("1月");
//        myMonth.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        myMonth.setTextColor(mTManager.colorTitle());
//
//        myTitle = new TextView(context);
//        myTitle.setText("已经连续签到8天");
//        myTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        myTitle.setTextColor(mTManager.colorTitle());


//        signIn = new RelativeLayout(context);
//        signIn.setPadding(rlTitlePadding, rlTitlePadding, rlTitlePadding, rlTitlePadding);
        //signIn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.box_gray_solid));

//        mySignIn = new TextView(context);
//        //mySignIn.setText("已签到");
//        mySignIn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
//        mySignIn.setTextColor(mTManager.colorTitle());
//
//        signIn.addView(mySignIn, lpTvSignIn);

        setRightTitle(isSignIn);

//        signIn.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!isSignIn) {
//                    onClickSignIn.signIn();
//                }
//            }
//        });


//        rlTitle.addView(myMonth, lpMyMonth);
////        rlTitle.addView(myTitle, lpMyTitle);
//        rlTitle.addView(signIn, lpSignIn);


//        addView(rlTitle, llParams);

        // --------------------------------------------------------------------------------周视图
        for (int i = 0; i < mLManager.titleWeek().length; i++) {
            TextView tvWeek = new TextView(context);
            tvWeek.setText(mLManager.titleWeek()[i]);
            tvWeek.setGravity(Gravity.CENTER);
            tvWeek.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
            tvWeek.setTextColor(mTManager.colorTitle());
            llWeek.addView(tvWeek, lpWeek);
        }
        addView(llWeek, llParams);

        // ------------------------------------------------------------------------------------月视图
        monthView = new MonthView(context);
        monthView.setOnDateChangeListener(new MonthView.OnDateChangeListener() {
            @Override
            public void onMonthChange(int month) {
                //tvMonth.setText(mLManager.titleMonth()[month - 1]);
            }

            @Override
            public void onYearChange(int year) {
                String tmp = String.valueOf(year);
                if (tmp.startsWith("-")) {
                    tmp = tmp.replace("-", mLManager.titleBC());
                }
                //tvYear.setText(tmp);
            }

            @Override
            public void onAllChange(int year, int month) {

            }
        });
        addView(monthView, llParams);
    }

    /**
     * 设置初始化年月日期
     *
     * @param year  ...
     * @param month ...
     */
    public void setDate(int year, int month) {
        if (month < 1) {
            month = 1;
        }
        if (month > 12) {
            month = 12;
        }
        monthView.setDate(year, month);
    }

    public void setDPDecor(DPDecor decor) {
        monthView.setDPDecor(decor);
    }

    /**
     * 设置日期选择模式
     *
     * @param mode ...
     */
    public void setMode(DPMode mode) {
        if (mode != DPMode.MULTIPLE) {
            //tvEnsure.setVisibility(GONE);
        }
        monthView.setDPMode(mode);
    }

    /**
     * 节日标识
     * @param isFestivalDisplay
     */
    public void setFestivalDisplay(boolean isFestivalDisplay) {
        monthView.setFestivalDisplay(isFestivalDisplay);
    }

    /**
     * 今天标识
     * @param isTodayDisplay
     */
    public void setTodayDisplay(boolean isTodayDisplay) {
        monthView.setTodayDisplay(isTodayDisplay);
    }

    /**
     * 假期标识
     * @param isHolidayDisplay
     */
    public void setHolidayDisplay(boolean isHolidayDisplay) {
        monthView.setHolidayDisplay(isHolidayDisplay);
    }

    /**
     * 补休标识
     * @param isDeferredDisplay
     */
    public void setDeferredDisplay(boolean isDeferredDisplay) {
        monthView.setDeferredDisplay(isDeferredDisplay);
    }


    /**
     * 是否允许滑动切换日期和年份
     * @param isScroll
     */
    public void setIsScroll(boolean isScroll){
        monthView.setIsScroll(isScroll);
    }


    public void setIsSelChangeColor(boolean isSelChangeColor,int selChangeTextColor) {
        monthView.setIsSelChangeColor(isSelChangeColor,selChangeTextColor);
    }

    /**
     * 设置单选监听器
     *
     * @param onDatePickedListener ...
     */
    public void setOnDatePickedListener(OnDatePickedListener onDatePickedListener) {
        if (monthView.getDPMode() != DPMode.SINGLE) {
            throw new RuntimeException(
                    "Current DPMode does not SINGLE! Please call setMode set DPMode to SINGLE!");
        }
        monthView.setOnDatePickedListener(onDatePickedListener);
    }

    /**
     * 设置多选监听器
     *
     * @param onDateSelectedListener ...
     */
    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        if (monthView.getDPMode() != DPMode.MULTIPLE) {
            throw new RuntimeException(
                    "Current DPMode does not MULTIPLE! Please call setMode set DPMode to MULTIPLE!");
        }
        this.onDateSelectedListener = onDateSelectedListener;
    }


    public void setOnClickSignIn(OnClickSignIn onClickSignIn) {
        this.onClickSignIn = onClickSignIn;
    }


    /**
     * 左边标题的文字
     */
    public void setLeftTitle(String title){
//        myMonth.setText(title);
    }

    /**
     * 中间标题的文字
     * @param title
     */
    public void setMiddleTitle(String title){
//        myTitle.setText(title);
    }


    /**
     * 设置右边是否签到
     * @param flag ture表示签到  false表示未签到
     */
    public void setRightTitle(boolean flag){
        this.isSignIn = flag;
        if(isSignIn){
//            mySignIn.setText("已签到");
//            mySignIn.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//            signIn.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.box_blue_solid));
        }else{
//            mySignIn.setText("未签到");
//            mySignIn.setTextColor(mContext.getResources().getColor(R.color.gray_normal));
//            signIn.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.box_gray_solid));
        }
    }







}

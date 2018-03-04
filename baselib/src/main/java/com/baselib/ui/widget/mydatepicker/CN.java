package com.baselib.ui.widget.mydatepicker;

/**
 * 中文的默认实现类
 * 如果你想实现更多的语言请参考Language{@link DPLManager}
 * 作者: liuhuaqian on 2017/9/23.
 */

public class CN extends DPLManager {
    @Override
    public String[] titleMonth() {
        return new String[]{"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    }

    @Override
    public String titleEnsure() {
        return "确定";
    }

    @Override
    public String titleBC() {
        return "公元前";
    }

    @Override
    public String[] titleWeek() {
        return new String[]{"日", "一", "二", "三", "四", "五", "六"};
    }
}

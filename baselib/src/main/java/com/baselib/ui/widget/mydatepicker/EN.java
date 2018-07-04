package com.baselib.ui.widget.mydatepicker;

/**
 * 英文的默认实现类
 * 如果你想实现更多的语言请参考Language{@link DPLManager}
 * 作者: llk on 2017/9/23.
 */

public class EN extends DPLManager {
    @Override
    public String[] titleMonth() {
        return new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    }

    @Override
    public String titleEnsure() {
        return "Ok";
    }

    @Override
    public String titleBC() {
        return "B.C.";
    }

    @Override
    public String[] titleWeek() {
        return new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    }
}


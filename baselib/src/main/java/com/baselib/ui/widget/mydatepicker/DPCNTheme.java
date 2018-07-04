package com.baselib.ui.widget.mydatepicker;

/**
 * 作者: llk on 2017/9/23.
 *  天朝日历主题类
 * 其他国家的主题不需要这样去处理
 */

public class DPCNTheme extends DPBaseTheme {
    /**
     * 农历文本颜色
     *
     * Color of Lunar text
     *
     * @return 16进制颜色值 hex color
     */
    public int colorL() {
        return 0xFFF86E0C;
    }

    /**
     * 补休日期背景颜色
     *
     * Color of Deferred background
     *
     * @return 16进制颜色值 hex color
     */
    public int colorDeferred() {
        return 0xFFFFFFFF;
    }
}
package com.baselib.commonutils;

import android.text.TextUtils;

/**
 * 作者: liuhuaqian on 2017/9/8.
 * 验证手机格式
 */

public class FormatUtil {
    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
		/*
	    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、178
	    联通：130、131、132、152、155、156、185、186、176
	    电信：133、153、180、189、（1349卫通）、177
	    总结起来就是第一位必定为1，第二位必定为3或5或8或7，其他位置的可以为0-9
		 */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }
}

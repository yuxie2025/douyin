package com.yuxie.demo.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    // 随机延时
    public static void random(int start, int end) {
        try {
            int time = new Random().nextInt(end - start) + start;
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
        }
    }

    public static String getMatches(String content, String regex) {
        Pattern pattern = Pattern.compile(regex);//匹配的模式
        //通配符中也要加入转移字符 (.+?)代表要查找的内容
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(1); //每次返回第一个即可 可用groupcount()方法来查看捕获的组数 个数
        }
        return "";

    }
}

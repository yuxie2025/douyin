package com.yuxie.demo.utils;

import java.util.Random;

public class CommonUtils {

    // 随机延时
    public static void random(int start, int end) {
        try {
            int time = new Random().nextInt(end - start) + start;
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
        }
    }
}

package com.yuxie.demo.sy;

import com.blankj.utilcode.util.FileIOUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommonUtils {

    /**
     * 获取随机字母数字组合
     *
     * @param length 字符串长度
     * @return
     */
    public static String getRandomCharAndNumr(int length) {
        StringBuffer str = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符
                int choice = random.nextBoolean() ? 65 : 97; // 取得65大写字母还是97小写字母
                str.append((char) (choice + random.nextInt(26)));// 取得大写字母
            } else { // 数字
                str.append(String.valueOf(random.nextInt(10)));
            }
        }
        return str.toString();
    }

    /**
     * 获取账号
     *
     * @param filePath
     * @return
     */
    public static List<WxAccount> getAccount(String filePath) {
        List<WxAccount> datas = new ArrayList<WxAccount>();

        List<String> strings = FileIOUtils.readFile2List(filePath, "UTF-8");
        if (strings.size() == 0) {
            return datas;
        }
        String string = "";
        for (int i = 0; i < strings.size(); i++) {
            string = strings.get(i);
            if (string != "" && string.contains(",")) {
                String[] strs = string.split(",");
                if (strs[0] != "" && strs[1] != "") {
                    datas.add(new WxAccount(strs[0], strs[1]));
                }
            }
        }
        return datas;
    }

    public static List<WxAccount> getAccount() {
        String filePath = "D:\\duoqi\\wx.txt";
        return getAccount(filePath);
    }

    public static WxAccount getWxAccount() {
        WxAccount wxAccount = null;
        List<WxAccount> datas = getAccount();
        if (datas.size() > 0) {
            wxAccount = datas.get(new Random().nextInt(datas.size() - 1));
        }
        return wxAccount;
    }

    /**
     * 获取签名
     *
     * @param filePath
     * @return
     */
    public static String getSign(String filePath) {
        String re = "";
        List<String> strings = FileIOUtils.readFile2List(filePath, "UTF-8");
        if (strings.size() > 0) {
            re = strings.get(new Random().nextInt(strings.size() - 1));
        }
        return re;
    }

    /**
     * 获取签名
     *
     * @return
     */
    public static String getSign() {
        String filePath = "D:\\duoqi\\sign.txt";
        return getSign(filePath);
    }

    public static String URLEncoder(String content) {
        try {
            content = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 获取密码
     *
     * @return
     */
    public static String getRandomPwd() {

        String filePath = "D:\\duoqi\\pwd.txt";
        List<String> datas = FileIOUtils.readFile2List(filePath, "UTF-8");

        if (datas.size() == 0) {
            throw new IllegalArgumentException("没有获取到密码!");
        }

        int position = new Random().nextInt(datas.size() - 1);
        return datas.get(position);
    }

    // 随机执行
    public static boolean randmonExe(int ratio) {

        boolean re = false;
        if (ratio > 100) {
            throw new IllegalArgumentException("参数不对,范围0-100");
        }

        if (ratio == 0) {
            re = false;
        } else if (ratio == 100) {
            re = true;
        } else {
            int randomInt = new Random().nextInt(100);

            if (randomInt <= ratio) {
                re = true;
            }
        }
        return re;
    }

    // 随机延时
    public static void random(int start, int end) {
        try {
            int time = new Random().nextInt(end - start) + start;
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
        }
    }

    public static String getContent() {

        String filePath = Sy.BASE_PATH + "评论.txt";
        List<String> datas = FileIOUtils.readFile2List(filePath, "UTF-8");

        if (datas.size() == 0) {
            throw new IllegalArgumentException("没有获取到密码!");
        }

        int position = new Random().nextInt(datas.size());
        return datas.get(position);
    }

}

package com.yuxie.demo.dq;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;

@SuppressWarnings("unused")
public class DqTest {

    public static final String queryVideoListUrl = "http://t1.miaozhaotv.com:8080/sheding/queryVideoList";
    public static final String dianZanUrl = "http://t1.miaozhaotv.com:8080/sheding/dianzan";
    public static final String addReadRecordUrl = "http://t1.miaozhaotv.com:8080/sheding/addReadRecord";
    public static final String fenxiangUrl = "http://t1.miaozhaotv.com:8080/sheding/fenxiang";
    public static final String guanzhuUserUrl = "http://t1.miaozhaotv.com:8080/sheding/guanzhuUser";
    public static final String queryDaishouCoinListUrl = "http://t1.miaozhaotv.com:8080/sheding/queryDaishouCoinList";
    public static final String shouquDaibiUrl = "http://t1.miaozhaotv.com:8080/sheding/shouquDaibi";
    public static final String loginnewUrl = "http://t1.miaozhaotv.com:8080/sheding/loginnew";
    public static final String addZanUrl = "http://t1.miaozhaotv.com:8080/sheding/addZan";
    public static final String shouquFenhongUrl = "http://t1.miaozhaotv.com:8080/sheding/shouquFenhong";
    public static final String sendDuoqiYZMYuYinUrl = "http://t1.miaozhaotv.com:8080/sheding/sendDuoqiYZMYuYin";
    public static final String regUrl = "http://t1.miaozhaotv.com:8080/sheding/registered";
    public static final String qiandaoUrl = "http://t1.miaozhaotv.com:8080/sheding/updateqiandao";
    public static final String getWalletMessageUrl = "http://t1.miaozhaotv.com:8080/sheding/getWalletMessage";
    public static final String bindWxUrl = "http://t1.miaozhaotv.com:8080/sheding/wechat";
    public static final String editMessageWithoutTouxiangUrl = "http://t1.miaozhaotv.com:8080/sheding/editMessageWithoutTouxiang";

    public static boolean isNew = false;

    public static int startAcount = 0;

    public static void main(String[] args) {

        // TODO
        // isNew = true;

        // 从第多少个账号开始执行(账号行数减一)
		 startAcount = 148;

        // 登录账号
        // login();

        // 绑定微信任务
        // exeBindWx();

        // 执行点赞任务
//		 exeDianZanTask();

        // 执行收趣币任务
//		 exeShouQubiTask();

        // 指定视频点赞任务
//        String videoId = "894930";
//        exeDianZanTask(videoId);

        // 注册账号并登陆
//		regAccount();

        // 每日任务刷赞
        // exeDayTask();

    }

    public static String getRandomPwd() {

        String filePath = "D:\\duoqi\\pwd.txt";
        List<String> datas = FileIOUtils.readFile2List(filePath, "UTF-8");

        if (datas.size() == 0) {
            throw new IllegalArgumentException("没有获取到密码!");
        }

        int position = new Random().nextInt(datas.size());
        return datas.get(position);
    }

    @SuppressWarnings("resource")
    public static void regAccount() {

        while (true) {
            System.out.print("输入手机号:\n");
            Scanner scan = new Scanner(System.in);
            String phone = scan.nextLine();
            reg(phone);
        }
    }

    /**
     * 注册账号并登陆
     */
    @SuppressWarnings("resource")
    public static void reg(String phone) {

        String pwd = getRandomPwd();
        String yzm = "";

        String yqm = "148330";
        if (randmonExe(50)) {
            yqm = "222542";
        }

        // 发送验证码
        boolean codeRe = sendCode(phone);
        if (!codeRe) {
            System.out.println("发送验证码失败");
            return;
        }

        System.out.print("输入验证码:\n");
        Scanner scan = new Scanner(System.in);
        yzm = scan.nextLine();

        // 注册账号
        boolean re = reg(phone, pwd, yzm, yqm);
        if (re) {
            String content = phone + "|" + pwd + "\n";
            FileIOUtils.writeFileFromString("D:\\duoqi\\reg_succeed.txt",
                    content, true);
            random(1, 2);
            loginnew(phone, pwd);
        } else {
            System.out.println("注册账号失败");
        }
    }

    public static void exeDayTask() {
        boolean isExe = true;
        String file = "D:\\duoqi\\token_day.txt";
        while (isExe) {

            List<User> users = getUser(file);

            if (users.size() == 0) {
                return;
            }

            List<VideoList.DataBean> videos = getVideoList(users.get(0));
            for (int i = 0; i < videos.size(); i++) {
                for (int j = 0; j < users.size(); j++) {

                    if (randmonExe(10)) {
                        continue;
                    }

                    exeDianZanTask(i, users.get(j), videos.get(i).getId());
                    random(3, 5);
                }
                random(2 * 60, 3 * 60);
            }

        }

    }

    /**
     * 执行点赞任务
     */
    public static void exeDianZanTask() {

        System.out.println("------开始执行点赞任务------");

        List<User> datas = getUser();
        User user;
        for (int i = 0; i < datas.size(); i++) {
            user = datas.get(i);

            queryVideoList(user);

            random(1, 2);
        }
        System.out.println("------执行点赞任务完成------");
    }

    /**
     * 指定视频点赞
     *
     * @param videoId
     */
    public static void exeDianZanTask(String videoId) {

        System.out.println("------开始执行指定视频点赞任务------");

        List<User> datas = getUser();
        User user;
        for (int i = 0; i < datas.size(); i++) {
            user = datas.get(i);
            exeDianZanTask(i, user, videoId);
        }
        System.out.println("------执行点赞任务完成------");
    }

    public static boolean exeDianZanTask(int i, User user, String videoId) {

        addReadRecord(user, videoId);

        addZan(user);

        random(1, 2);

        if (randmonExe(10)) {
            random(1, 2);
            fenxiang(user, videoId);
        }

        random(1, 2);

        boolean isSucced = dianZan(user, videoId);
        String msg = "";
        if (!isSucced) {
            // 点赞不足,提前退出
            msg = "第" + (i + 1) + "次," + user.getData().getPhone()
                    + " 账号,现有赞数不足,执行点赞失败";
        } else {
            msg = "第" + (i + 1) + "次," + user.getData().getPhone()
                    + " 账号,执行点赞成功";
        }
        ToastUtils.showShort(msg);
        return isSucced;
    }

    /**
     * 执行收趣币任务
     */
    public static void exeShouQubiTask() {

        System.out.println("------开始执行收趣币任务------");

        List<User> datas = getUser();
        if (datas.size() == 0) {
            return;
        }
        User user;
        for (int i = 0; i < datas.size(); i++) {
            user = datas.get(i);

            // 收取趣币
            queryDaishouCoinList(user);

            // 收取分红
            shouquFenhong(user);

            getWalletMessage(user);

        }
        System.out.println("------执行收趣币任务完成------");

    }

    /**
     * 执行绑定微信任务
     */
    public static void exeBindWx() {

        System.out.println("------开始执行绑定任务------");

        List<User> datas = getUser();
        User user;
        for (int i = 0; i < datas.size(); i++) {
            user = datas.get(i);
            bindWx(user);
            random(1, 2);
        }
        System.out.println("------执行绑定任务完成------");
    }

    /**
     * 获取保存在本地的账号信息
     *
     * @return
     */
    public static List<User> getUser() {
        String file;
        if (isNew) {
            file = "D:\\duoqi\\token_new.txt";
        } else {
            file = "D:\\duoqi\\token.txt";
        }
        return getUser(file);
    }

    public static List<User> getUser(String filePath) {

        List<User> users = new ArrayList<>();

        List<String> datas = FileIOUtils.readFile2List(filePath, "UTF-8");
        if (datas.size() == 0) {
            return users;
        }
        String data = "";
        for (int i = startAcount; i < datas.size(); i++) {
            data = datas.get(i).trim();

            // System.out.println("data:" + data);

            if (data == null || data == "") {
                continue;
            }

            // 去除前面的影响字符
            data = data.substring(data.indexOf("{"), data.length());

            User user = new Gson().fromJson(data, User.class);
            users.add(user);
        }
        return users;
    }

    /**
     * 登录账号,保证token在本地
     */
    public static void login() {

        List<String> datas = FileIOUtils.readFile2List(
                "D:\\duoqi\\account.txt", "UTF-8");
        if (datas.size() == 0) {
            return;
        }
        String data = "";
        for (int i = 0; i < datas.size(); i++) {
            data = datas.get(i).trim();

            if (data == "") {
                continue;
            }

            if (data.contains("|")) {
                String[] sp = data.split("\\|");

                loginnew(sp[0], sp[1]);
                random(10, 12);
            }
        }

    }

    /**
     * 获取视频列表
     *
     * @param user
     * @return
     */
    public static List<VideoList.DataBean> getVideoList(User user) {
        List<VideoList.DataBean> datas = new ArrayList<>();

        String params = getCommonParams(user) + "pageNo=1";
        String re = Utils.post(queryVideoListUrl, params);
        System.out.println("getVideoList:" + re);

        VideoList videoList = new Gson().fromJson(re, VideoList.class);

        if (videoList != null) {
            datas = videoList.getData();
        }
        return datas;

    }

    /**
     * 查询视频列表,关注,点赞,分享
     *
     * @param user
     */
    public static void queryVideoList(User user) {

        List<VideoList.DataBean> datas = getVideoList(user);
        for (int i = 0; i < datas.size(); i++) {
            String videoId = datas.get(i).getId();
            String guanzhuUserID = datas.get(i).getCreateUser().getId();
            System.out.println(user.getData().getPhone() + " 账号,开始执行点赞任务...");
            // System.out.println("getVideoUrl:" + datas.get(i).getVideoUrl());

            if (i > 1) {
                break;
            }

            addReadRecord(user, videoId);

            random(1, 2);

            if (randmonExe(100)) {
                random(1, 2);
                fenxiang(user, videoId);
            }
            if (randmonExe(100)) {
                random(1, 2);
                guanzhuUser(user, guanzhuUserID);
            }

            boolean isSucced = dianZan(user, videoId);
            if (!isSucced) {
                // 点赞不足,提前退出
                System.out.println(user.getData().getPhone()
                        + " 账号,现有赞数不足,执行完成");
                break;
            } else {
                System.out.println(user.getData().getPhone() + " 账号,执行点赞成功");
            }

        }

    }

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

    public static void random(int start, int end) {
        try {
            int time = new Random().nextInt(end - start) + start;
            Thread.sleep(time * 1000);
        } catch (InterruptedException e) {
        }
    }

    public static void addReadRecord(User user, String videoId) {

        int time = new Random().nextInt(1000);
        int timeT = (new Random().nextInt(20) + 10) * 1000;

        String readTime = timeT + time + "";

        double durationD=(new Random().nextInt(3) + 10)+new Random().nextDouble();
        String duration=String.format("%.3f", durationD);
        System.out.println("duration:"+duration);

        double currentPlaybackTimeD=(new Random().nextInt(10))+new Random().nextDouble();
        String currentPlaybackTime=String.format("%.3f", currentPlaybackTimeD);
        System.out.println("currentPlaybackTime:"+currentPlaybackTime);

        String params = getCommonParams(user) + "&state=1&duration="+duration+"&currentPlaybackTime="+currentPlaybackTime+"&videoId=" + videoId
                + "&readTime=" + readTime;

        String re = Utils.post(addReadRecordUrl, params);
//
        System.out.println("addReadRecord---re:" + re);

    }

    public static boolean dianZan(User user, String videoId) {

        String params = getCommonParams(user) + "videoId=" + videoId;
        String re = Utils.post(dianZanUrl, params);

         System.out.println("dianZan--re:" + re);

        if (re.contains("1002")) {
            return false;
        }
        return true;
    }

    public static void fenxiang(User user, String videoId) {

        String params = getCommonParams(user) + "videoId=" + videoId
                + "&shareType=1";
        String re = Utils.post(fenxiangUrl, params);

        System.out.println("分享链接:" + re);

    }

    public static void guanzhuUser(User user, String guanzhuUserId) {

        String params = getCommonParams(user) + "&guanzhuUserId="
                + guanzhuUserId;
        String re = Utils.post(guanzhuUserUrl, params);

        System.out.println("关注用户:" + re);

    }

    /**
     * 查询代收趣币
     *
     * @param user
     */
    public static void queryDaishouCoinList(User user) {

        String params = getCommonParams(user);
        String re = Utils.post(queryDaishouCoinListUrl, params);

        System.out.println("queryDaishouCoinList--re:" + re);

        CoinList datas = new Gson().fromJson(re, CoinList.class);

        if (datas.getData().size() == 0) {
            System.out.println(user.getData().getPhone() + " 账号,没有可收取趣币");
            return;
        }

        for (int i = 0; i < datas.getData().size(); i++) {
            String count = datas.getData().get(i).getCount();
            String id = datas.getData().get(i).getId();
            shouquDaibi(user, id);
        }

    }

    /**
     * 收趣币
     *
     * @param user
     * @param id
     */
    public static void shouquDaibi(User user, String id) {

        String params = getCommonParams(user) + "&id=" + id;
        String re = Utils.post(shouquDaibiUrl, params);

        System.out.println("shouquDaibi--re:" + re);

    }

    /**
     * 添加赞
     *
     * @param user
     */
    public static void addZan(User user) {

        String params = getCommonParams(user);
        String re = Utils.post(addZanUrl, params);

         System.out.println("shouquDaibi--re:" + re);

    }

    /**
     * 收取分红
     *
     * @param user
     */
    public static void shouquFenhong(User user) {

        String params = getCommonParams(user);
        String re = Utils.post(shouquFenhongUrl, params);

        System.out.println("shouquFenhong--re:" + re);

    }

    /**
     * 签到
     *
     * @param user
     */
    public static void qiandao(User user) {

        StringBuilder sb = new StringBuilder();
        sb.append("user_id=").append(user.getData().getAutoId())
                .append("&token=").append(user.getData().getToken())
                .append("&imei=").append(user.getData().getImei())
                .append("&platform=Android&version=1.2.2");

        String params = sb.toString();
        String re = Utils.post(qiandaoUrl, params);

        System.out.println("qiandaoUrl:" + qiandaoUrl);
        System.out.println("params:" + params);
        System.out.println("qiandao--re:" + re);
    }

    /**
     * 获取钱包信息
     *
     * @param user
     */
    public static void getWalletMessage(User user) {

        String params = getCommonParams(user);
        String re = Utils.post(getWalletMessageUrl, params);

        System.out.println("getWalletMessage--re:" + re);
    }

    /**
     * 修改昵称和签名
     *
     * @param user
     * @param name
     * @param sign
     */
    public static void editMessageWithoutTouxiang(User user, String name,
                                                  String sign) {

        String params = getCommonParams(user) + "name=" + name + "&sign="
                + sign;
        String re = Utils.post(editMessageWithoutTouxiangUrl, params);

        System.out.println("editMessageWithoutTouxiang--re:" + re);
    }

    /**
     * 发送验证码
     *
     * @param phone
     * @return
     */
    public static boolean sendCode(String phone) {

        String timestamp = String.valueOf(System.currentTimeMillis());

        String sign = phone + timestamp;

        String params = "userPhone=" + Rsa.encrypt(sign) + "&timestamp="
                + timestamp;
        String re = Utils.post(sendDuoqiYZMYuYinUrl, params);

        System.out.println("sendCode--re:" + re);

        if (re.contains("1200")) {
            return true;
        }
        return false;
    }

    public static boolean bindWx(User user) {
        String userId = user.getData().getAutoId();
        String token = user.getData().getToken();

        String openId = CommonUtils.getRandomCharAndNumr(28);
        WxAccount wxAccount = CommonUtils.getWxAccount();
        String name = wxAccount.getName();
        String iconurl = wxAccount.getPic();
        String gender = "男";
        boolean b = new Random().nextBoolean();
        if (b) {
            gender = "女";
        }
        String province = "";
        String city = "";
        String country = "CN";

        boolean re = bindWx(userId, token, openId, name, iconurl, gender,
                province, country, city);
        if (re) {
            String sign = CommonUtils.getSign();
            editMessageWithoutTouxiang(user, CommonUtils.URLEncoder(name),
                    CommonUtils.URLEncoder(sign));
        }
        return re;
    }

    public static boolean bindWx(String userId, String token, String openid,
                                 String name, String iconurl, String gender, String province,
                                 String country, String city) {

        StringBuffer sb = new StringBuffer();
        sb.append("user_id=").append(userId);
        sb.append("&token=").append(token);
        sb.append("&openid=").append(openid);
        sb.append("&name=").append(CommonUtils.URLEncoder(name));
        sb.append("&iconurl=").append(iconurl);
        sb.append("&gender=").append(CommonUtils.URLEncoder(gender));
        sb.append("&province=").append(province);
        sb.append("&country=").append(country);
        sb.append("&city=").append(city);

        String params = sb.toString();
        String re = Utils.post(bindWxUrl, params);

        System.out.println("bindWx--re:" + re);
        if (re.contains(":200")) {
            return true;
        }
        return false;
    }

    /**
     * 注册账号
     *
     * @param phone
     * @param pwd
     * @param yzm
     * @param yqm
     * @return
     */
    public static boolean reg(String phone, String pwd, String yzm, String yqm) {

        String timestamp = String.valueOf(System.currentTimeMillis());

        String sign = pwd + timestamp;

        String params = "phone=" + phone + "&yzm=" + yzm + "&yqm=" + yqm
                + "&sign=" + Rsa.encrypt(sign) + "&timestamp=" + timestamp;
        String re = Utils.post(regUrl, params);

        System.out.println("reg--re:" + re);

        if (re.contains("200")) {
            return true;
        }
        return false;
    }

    public static void loginnew(String phone, String pwd) {

        String timestamp = String.valueOf(System.currentTimeMillis());

        String sign = pwd + timestamp;

        String imei = CommonUtils.getRandomCharAndNumr(15).toLowerCase();

        StringBuilder sb = new StringBuilder();
        sb.append("phone=").append(phone);
        sb.append("&timestamp=").append(timestamp);
        sb.append("&sign=").append(Rsa.encrypt(sign));
        sb.append("&imei=").append(imei);
        sb.append("&type=2&version=1.2.2&platform=Android");

        String params = sb.toString();
        String re = Utils.post(loginnewUrl, params);

        User user = new Gson().fromJson(re, User.class);

        if (user.getCode() == 200) {
            user.getData().setImei(imei);
        }
        random(1, 3);

        bindWx(user);

        String content = new Gson().toJson(user) + "\n";

        FileIOUtils.writeFileFromString("D:\\duoqi\\token.txt", content, true);

        System.out.println("loginnew--content:" + content);

    }

    public static String getCommonParams(User user) {

        String params = "";
        if (user == null) {
            return params;
        }
        // user_id=c215d19d355249f19f61e3c1d7a0a109&token=36048e26db7f38af245007c47bc101e5bbd76746d2cf14e98612a3d2
        // &videoId=702812&platform=Android&imei=1a0b4b013c72aa2a&version=1.2.2

        StringBuilder sb = new StringBuilder();
        sb.append("user_id=").append(user.getData().getId()).append("&token=")
                .append(user.getData().getToken()).append("&imei=")
                .append(user.getData().getImei())
                .append("&platform=Android&version=1.2.2&");
        params = sb.toString();

        return params;
    }

}

package com.yuxie.demo.sy;

import android.content.Context;
import android.os.Environment;

import com.baselib.basebean.BaseRespose;
import com.baselib.baserx.RxSchedulers;
import com.baselib.baserx.RxSubscriber;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.yuxie.demo.api.server.ServerApi;
import com.yuxie.demo.sy.NewsListBean.DataBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sy {

    public static final String likesUrl = "https://lfront.soubiji.com/v1/likes/contents/";
    public static final String newsUrl = "https://lfront.soubiji.com/v1/contents";
    public static final String mineContensUrl = "https://lfront.soubiji.com/v1/mine/contents";

    public static final String readUrl = "https://lfront.soubiji.com/v1/contents/";
    public static final String sharedUrl = "https://lfront.soubiji.com/v1/callbacks/shared";
    public static final String commentsUrl = "https://lfront.soubiji.com/v1/comments";
    public static final String bindWxUrl = "https://lfront.soubiji.com/v1/wechat_users";

    public static final String userPendingTokensUrl = "https://lfront.soubiji.com/v1/user_pending_tokens";

    public static final String putTokenUrl = "https://lfront.soubiji.com/v1/user_pending_tokens";

    public static final String missionsUrl = "https://lfront.soubiji.com/v1/missions/16";
    public static final String userPendingCashesUrl = "https://lfront.soubiji.com/v1/user_pending_cashes";

    public static final String userTicketsUrl = "https://lfront.soubiji.com/v1/user_tickets";

    public static final String userPendingTAllUrl = "https://lfront.soubiji.com/v1/user_pending_tokens/all";

    public static final String regUrl = "https://lfront.soubiji.com/v1/users";

    public static final String wechatSessionsUrl = "https://lfront.soubiji.com/v1/wechat_sessions";

    // public static final String giftsUrl ="https://lfront.soubiji.com/v1/gifts";

    public static final String BASE_PATH = Environment.getExternalStorageDirectory() + "/sy/";

    public static void main() {

        // 阅读任务
        read();

        // 读文章任务
        // 327704 327707 327709 327713
        // String newsId = "";
        // readTask(newsId);

        // String token = "";
//         getNewsId(token);

    }

    /**
     * 获取发布新闻id 并执行 每日任务
     *
     * @param token
     */
    public static void getNewsId(String token) {

        if (!"".equals(token)) {
            List<NewsListBean.DataBean> datas = mineContens(token);
            for (int i = 0; i < datas.size(); i++) {
                if ("2".equals(datas.get(i).getStatus())) {
                    System.out.println(datas.get(i).getId());
                }
            }
            return;
        }
    }


    public static void read() {

        List<User> users = getUser();
        String token;
        for (int i = 0; i < 21; i++) {
            System.out.println("第" + (i + 1) + "次阅读任务");

            token = users.get(i).getToken();
            List<DataBean> news = newsList(token);
            for (int j = 0; j < news.size(); j++) {
                String newsId = news.get(j).getId();

                for (int k = 0; k < users.size(); k++) {
                    token = users.get(k).getToken();
                    System.out.println("token:" + token + " newsId:" + newsId);
                    // 读新闻
                    read(newsId, token);
                }
                CommonUtils.random(8, 10);
            }
        }
    }

    // 每日任务
    @SuppressWarnings("unused")
    public static void dayTask(Context context, String token, boolean isBig) {

        List<DataBean> news = newsList(token);

        System.out.println("news:" + news);

        int likeNumber = 0;
        int sharedNumber = 0;
        int commentsNumber = 0;

        for (int i = 0; i < news.size(); i++) {
            String newsId = news.get(i).getId();
            System.out.println("token:" + token + " newsId:" + newsId);
            //阅读新闻
            read(newsId, token);
            CommonUtils.random(1, 2);

            if (likeNumber <= 12) {
                likeNumber++;
                // 点赞新闻
                like(newsId, token);
                CommonUtils.random(1, 2);
            }

            if (sharedNumber <= 3) {
                sharedNumber++;
                // 分享新闻
                shared(newsId, token);
                CommonUtils.random(1, 2);
            }

            if (isBig && commentsNumber <= 6) {
                // 评论
                String imei = "866174010882153";
                comments(context, imei, token, newsId);
                commentsNumber++;
                CommonUtils.random(1, 2);
            }
            CommonUtils.random(6, 8);
        }
    }

    /**
     * 收取盐票
     *
     * @param token
     * @return
     */
    public static boolean userPendingTAll(String token) {
        StringBuffer sb = new StringBuffer();
        sb.append("token=" + token);

        String url = userPendingTAllUrl + "?" + sb.toString();

        String reString = Utils.putDataNet(url);

        System.out.println("userPendingTAll--reString:" + reString);

        return checkRe(reString);
    }

    // 读文章任务
    public static void readTask(String newsId) {

        List<User> users = getUser();

        String token;
        for (int i = 0; i < users.size(); i++) {
            token = users.get(i).getToken();

            // 读新闻
            read(newsId, token);
            CommonUtils.random(1, 3);

            if (CommonUtils.randmonExe(80)) {
                // 点赞新闻
                like(newsId, token);
                CommonUtils.random(1, 3);
            }

            if (CommonUtils.randmonExe(10)) {
                // 分享新闻
                shared(newsId, token);
                CommonUtils.random(1, 3);
            }
            CommonUtils.random(2, 3);
        }
        System.out.println("----------阅读任务完成----------");

    }

    /**
     * 点赞
     *
     * @param newsId
     * @param token
     * @return
     */
    public static boolean like(String newsId, String token) {

        HashMap hashMap = new HashMap();
        hashMap.put("status", "like");
        hashMap.put("token", token);

        String params = getParams(hashMap);

        String url = likesUrl + newsId + "?" + params;

        String reString = Utils.putDataNet(url);

        System.out.println("like--reString:" + reString);

        return checkRe(reString);

    }

    /**
     * 阅读
     *
     * @param newsId
     * @param token
     * @return
     */
    public static boolean read(String newsId, String token) {

        HashMap hashMap = new HashMap();
        hashMap.put("token", token);
        String params = getParams(hashMap);

        String url = readUrl + newsId + "/read?" + params;

        String reString = Utils.putDataNet(url);

        System.out.println("read--reString:" + reString);

        return checkRe(reString);

    }

    /**
     * 分享内容
     *
     * @param newsId
     * @param token
     * @return
     */
    public static boolean shared(String newsId, String token) {

        HashMap hashMap = new HashMap();
        hashMap.put("token", token);
        hashMap.put("share_id", newsId);
        hashMap.put("share_type", "Content");
        hashMap.put("share_platform", "QQ");
        String params = getParams(hashMap);

        String reString = Utils.post(sharedUrl, params);

        System.out.println("shared--reString:" + reString);

        return checkRe(reString);

    }

    /**
     * 评论
     *
     * @param newsId
     * @param content
     * @param token
     * @return
     */
    public static boolean comments(String newsId, String content, String token) {

        HashMap hashMap = new HashMap();
        hashMap.put("token", token);
        hashMap.put("quote_id", "");
        hashMap.put("content", CommonUtils.URLEncoder(content));
        hashMap.put("content_id", newsId);
        String params = getParams(hashMap);

        String reString = Utils.post(commentsUrl, params);

        System.out.println("comments--params:" + params);
        System.out.println("comments--reString:" + reString);

        return checkRe(reString);

    }

    /**
     * 领取 任务奖励
     *
     * @param token
     * @return
     */
    public static boolean missions(String token) {

        HashMap hashMap = new HashMap();
        hashMap.put("token", token);
        String params = getParams(hashMap);

        String reString = Utils.post(missionsUrl, params);

        System.out.println("missions--reString:" + reString);

        return checkRe(reString);

    }

    /**
     * 获取发布新闻列表
     *
     * @param token
     * @return
     */
    public static List<NewsListBean.DataBean> mineContens(String token) {

        String url = mineContensUrl + "?token=" + token;

        String reString = Utils.getDataNet(url, "");

        System.out.println("mineContens--reString:" + reString);

        NewsListBean bean = new Gson().fromJson(reString, NewsListBean.class);

        return bean.getData();

    }

    /**
     * 收取回馈
     *
     * @param token
     * @return
     */
    public static boolean userPendingCashes(String token) {

        StringBuffer sb = new StringBuffer();
        sb.append("token=").append(token);
        String reString = Utils.putDataNet(userPendingCashesUrl + "?"
                + sb.toString());

        System.out.println("userPendingCashes--reString:" + reString);

        return checkRe(reString);

    }

    /**
     * 获取新闻列表
     *
     * @param token
     * @return
     */
    public static List<NewsListBean.DataBean> newsList(String token) {

        String reString = Utils.getDataNet(newsUrl + "?token=" + token, "");

        NewsListBean re = new Gson().fromJson(reString, NewsListBean.class);

        System.out.println("reString:" + reString);

        return re.getData();

    }

    public static void comments(Context context, String imei, String token, String content_id) {

//        String imei = "866174010882153";
//        String token = "4d570255ffc544163b32f68c8edfbfe0";
//        String content_id = "510827";

        String content = CommonUtils.getContent();

        SPUtils.getInstance().put("imei", imei);
        SPUtils.getInstance().put("token", token);

        ServerApi.getInstance().comments(content_id, content, "")
                .compose(RxSchedulers.io_io())
                .subscribe(new RxSubscriber<String>(context, false) {
                    @Override
                    protected void _onNext(String str) {
                        LogUtils.d("str:" + str);
                    }

                    @Override
                    protected void _onError(String message) {
                        LogUtils.d("message:" + message);
                    }
                });
    }

    public static boolean checkRe(String reStr) {
        boolean re = false;
        if (reStr != null && reStr != "" && reStr.contains("success")) {
            re = true;
        }
        return re;
    }

    /**
     * 获取默认执行用户
     *
     * @return
     */
    public static List<User> getUser() {
        String filePath = BASE_PATH + "token.txt";
        return getUser(filePath);
    }

    /**
     * 根据路径获取用户
     *
     * @param filePath
     * @return
     */
    public static List<User> getUser(String filePath) {

        List<User> users = new ArrayList<>();

        List<String> datas = FileIOUtils.readFile2List(filePath, "UTF-8");
        if (datas.size() == 0) {
            return users;
        }
        String data = "";
        for (int i = 0; i < datas.size(); i++) {
            data = datas.get(i).trim();

            if (data == null || data == "") {
                continue;
            }

            // 去除前面的影响字符
            data = data.trim();

            User user = new User(data);
            users.add(user);
        }
        return users;
    }

    private static String getParams(HashMap hashMap) {
        hashMap.put("app_channel", "home");
        hashMap.put("app_os", "android");
        hashMap.put("app_version", "1.0.7");
        hashMap.put("secret", "andy888");
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
        String str = Sign.toString(hashMap);

        StringBuffer sb = new StringBuffer(str);
        sb.append("&sign=" + Sign.sign(sb.toString()));

        return sb.toString();
    }


}
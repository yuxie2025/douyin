package com.yuxie.demo.sy;

import com.google.gson.Gson;
import com.yuxie.demo.sy.UserPendingTokensBean.DataBean.PendingTokensBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SyTest {

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

    public static void main(String[] args) {
        // //绑定微信
        // String token = "dae21eef4c91651cae7170b46f1dc410";
        // boolean re = bindWx(token);
        // System.out.println("re:" + re);

        // regWx("15886287304", "913875");

        // String mobile = "15703134726";
        // String captcha = "498012";
        // String code = "189608";
        // reg(mobile, captcha, code);

        // wechatSessions();

        // dae21eef4c91651cae7170b46f1dc410
        // String token1 = "ed6010ba0d6293728a409190e41fd4aa";
        // userPendingTAll(token1);


        // String token = "ed6010ba0d6293728a409190e41fd4aa";
        // userTicket(token);
    }

    public static boolean wechatSessions() {

        String unionid = CommonUtils.getRandomCharAndNumr(28);
        String openid = CommonUtils.getRandomCharAndNumr(28);
        String refreshToken = CommonUtils.getRandomCharAndNumr(28);
        String accessToken = CommonUtils.getRandomCharAndNumr(28);

        StringBuffer sb = new StringBuffer();
        sb.append("unionid=" + unionid);
        sb.append("&openid=" + openid);
        sb.append("&refreshToken=" + refreshToken);
        sb.append("&accessToken=" + accessToken);

        String params = sb.toString();

        System.out.println("params:" + params);

        String reString = Utils.post(wechatSessionsUrl, params);

        System.out.println("missions--reString:" + reString);

        return checkRe(reString);

    }


    public static boolean reg(String mobile, String captcha, String code) {

        StringBuffer sb = new StringBuffer();
        sb.append("mobile=").append(mobile);
        sb.append("&captcha=").append(captcha);
        sb.append("&code=").append(code);
        String reString = Utils.post(regUrl, sb.toString());

        System.out.println("reg--reString:" + reString);

        return checkRe(reString);

    }

    public static boolean userTicket(String token) {
        String articleId = "188673";
        String giftId = "5";
        String userId = "188673";

        StringBuffer sb = new StringBuffer();
        sb.append("article_id=").append(articleId);
        sb.append("&gift_id=").append(giftId);
        sb.append("&user_id=").append(userId);
        sb.append("&app_channel=QQ");
        sb.append("&app_os=android");
        sb.append("&app_version=1.0.7");
        sb.append("&secret=andy888");
        sb.append("&share_platform=QQ");
        sb.append("&timestamp=" + String.valueOf(System.currentTimeMillis()));

        sb.append("&token=" + token);
        sb.append("&sign=" + Sign.sign(sb.toString()));

        String reString = Utils.delete(userTicketsUrl + "?" + sb.toString());

        System.out.println("userTicket--reString:" + userTicketsUrl + "?"
                + sb.toString());

        return checkRe(reString);

    }

    public static boolean checkRe(String reStr) {
        boolean re = false;
        if (reStr != null && reStr != "" && reStr.contains("success")) {
            re = true;
        }
        return re;
    }

    public static boolean tokenTask(String token) {
        List<PendingTokensBean> tokens = userPendingTokens(token);
        String pendingTokenId;
        for (int i = 0; i < tokens.size(); i++) {
            pendingTokenId = tokens.get(i).getId();
            putToken(token, pendingTokenId);
        }

        if (tokens == null || tokens.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    // 查询盐丸
    public static List<PendingTokensBean> userPendingTokens(String token) {

        // ?token=678d3a87e042c504d60d35e5a212bb95&_=1544449897000

        String url = userPendingTokensUrl + "?token=" + token + "&_="
                + System.currentTimeMillis();

        String reString = Utils.getDataNet(url, "");

        System.out.println("userPendingTokens:" + reString);

        UserPendingTokensBean re = new Gson().fromJson(reString,
                UserPendingTokensBean.class);

        return re.getData().getPending_tokens();

    }

    public static List<PendingTokensBean> putToken(String token,
                                                   String pendingTokenId) {

        // {"token":"678d3a87e042c504d60d35e5a212bb95","pending_token_id":33621902}
        StringBuffer sb = new StringBuffer();
        sb.append("token=").append(token);
        sb.append("&pending_token_id=").append(pendingTokenId);
        String reString = Utils.putDataNet(putTokenUrl + "?" + sb.toString());

        UserPendingTokensBean re = new Gson().fromJson(reString,
                UserPendingTokensBean.class);

        if (re.getData() == null) {
            return new ArrayList<>();
        }

        return re.getData().getPending_tokens();

    }

    public static boolean bindWx(String token) {

        String openId = CommonUtils.getRandomCharAndNumr(28);
        String unionid = CommonUtils.getRandomCharAndNumr(28);

        WxAccount wxAccount = CommonUtils.getWxAccount();
        String name = wxAccount.getName();
        String iconurl = wxAccount.getPic();
        String gender = "男";
        boolean b = new Random().nextBoolean();
        if (b) {
            gender = "女";
        }
        String province = "广东省";
        String city = "广州市";
        String country = "CN";

        boolean re = bindWx(unionid, token, openId, name, iconurl, gender,
                province, country, city);
        return re;
    }

    public static boolean regWx(String mobile, String captcha) {

        String openId = CommonUtils.getRandomCharAndNumr(28);
        String unionid = CommonUtils.getRandomCharAndNumr(28);

        WxAccount wxAccount = CommonUtils.getWxAccount();
        String name = wxAccount.getName();
        String iconurl = wxAccount.getPic();
        String gender = "男";
        boolean b = new Random().nextBoolean();
        if (b) {
            gender = "女";
        }
        String province = "广东省";
        String city = "广州市";
        String country = "CN";

        boolean re = regWx(mobile, captcha, unionid, openId, name, iconurl,
                gender, province, country, city);
        return re;
    }

    private static boolean bindWx(String unionid, String token, String openid,
                                  String name, String iconurl, String gender, String province,
                                  String country, String city) {

        StringBuffer sb = new StringBuffer();
        sb.append("app_channel=QQ");
        sb.append("&app_os=android");
        sb.append("&app_version=1.1.0");
        sb.append("&imei=").append(
                CommonUtils.getRandomCharAndNumr(16).toLowerCase());
        sb.append("&timestamp=" + String.valueOf(System.currentTimeMillis()));
        sb.append("&token=" + token);
        sb.append("&avatar=").append(iconurl);
        sb.append("&city=").append(CommonUtils.URLEncoder(city));
        sb.append("&country=").append(country);
        sb.append("&gender=").append(CommonUtils.URLEncoder(gender));
        sb.append("&nickname=").append(CommonUtils.URLEncoder(name));
        sb.append("&openid=").append(openid);
        sb.append("&province=").append(CommonUtils.URLEncoder(province));
        sb.append("&secret=andy888");
        sb.append("&share_platform=QQ");
        sb.append("&unionid=").append(unionid);

        String refreshToken = CommonUtils.getRandomCharAndNumr(28);
        String accessToken = CommonUtils.getRandomCharAndNumr(28);

        sb.append("&refreshToken=").append(refreshToken);
        sb.append("&accessToken=").append(accessToken);
        sb.append("&sign=" + Sign.sign(sb.toString()).toUpperCase());

        String params = sb.toString();
        String re = Utils.putDataNet(bindWxUrl + "?" + params);

        System.out.println("bindWx--re:" + bindWxUrl + "?" + params);
        System.out.println("bindWx--re:" + re);
        if (re.contains(":200")) {
            return true;
        }
        return false;
    }

    public static boolean regWx(String mobile, String captcha, String unionid,
                                String openid, String name, String iconurl, String gender,
                                String province, String country, String city) {

        StringBuffer sb = new StringBuffer();
        sb.append("app_channel=QQ");
        sb.append("&app_os=android");
        sb.append("&app_version=1.1.0");
        sb.append("&avatar=").append(iconurl);
        sb.append("&city=").append(CommonUtils.URLEncoder(city));
        sb.append("&country=").append(country);
        sb.append("&gender=").append(CommonUtils.URLEncoder(gender));
        sb.append("&nickname=").append(CommonUtils.URLEncoder(name));
        sb.append("&openid=").append(openid);
        sb.append("&province=").append(CommonUtils.URLEncoder(province));
        sb.append("&secret=andy888");
        sb.append("&share_platform=QQ");
        sb.append("&timestamp=" + String.valueOf(System.currentTimeMillis()));
        sb.append("&unionid=").append(unionid);
        sb.append("&mobile=").append(mobile);
        sb.append("&captcha=").append(captcha);
        sb.append("&imei=").append(
                CommonUtils.getRandomCharAndNumr(16).toLowerCase());
        sb.append("&code=").append("189608");

        String refreshToken = CommonUtils.getRandomCharAndNumr(28);
        String accessToken = CommonUtils.getRandomCharAndNumr(28);

        sb.append("&refreshToken=").append(refreshToken);
        sb.append("&accessToken=").append(accessToken);
        sb.append("&sign=" + Sign.sign(sb.toString()));

        String params = sb.toString();
        String re = Utils.post(bindWxUrl, params);

        System.out.println("bindWx--re:" + bindWxUrl + "?" + params);
        System.out.println("bindWx--re:" + re);
        if (re.contains(":200")) {
            return true;
        }
        return false;
    }

}
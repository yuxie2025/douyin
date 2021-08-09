package com.yuxie.demo.utils.douyin;

import android.util.Log;

import com.blankj.utilcode.util.EncryptUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.yuxie.demo.utils.douyin.bean.ApiResult;
import com.yuxie.demo.utils.douyin.bean.ItemList;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * 抖音视频去除水印工具.
 *
 * @author lys
 * @date 2020-12-01 15:28
 */
public class Douyin {


    public static final String UA = "Mozilla/5.0 (iPhone; CPU iPhone OS 12_1_4 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Mobile/16D57 Version/12.0 Safari/604.1";
    public static final String API = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids=";
    private static final String TAG = "Douyin";

    static class Result {
        public String videoUrl;
        public String musicUrl;
        public String name;

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Result{");
            sb.append("videoUrl='").append(videoUrl).append('\'');
            sb.append(", musicUrl='").append(musicUrl).append('\'');
            sb.append(", name='").append(name).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }


    public static void main(String[] args) throws IOException {
        String msgFromDouyin = "大家帮我们看看昨天去看的房，两居室的那个房子看中了，就是周边不咋地，有懂的吗？#沪漂 #买房 #宝妈分享  https://v.douyin.com/JbKWX3g/ 复制此链接，打开抖音搜索，直接观看视频！";
        Douyin.downloadVideo(msgFromDouyin, "/Users/apple_mini/Desktop");
    }

    /**
     * 远程获取无水印视频地址.
     *
     * @param shareInfo
     * @return
     */
    public static ApiResult fetchVideoScheme(String shareInfo) {
        try {
            String shortUrl = extractUrl(shareInfo);
            String originUrl = convertUrl(shortUrl);
            String itemId = parseItemIdFromUrl(originUrl);
            ApiResult apiBean = requestToAPI(itemId);
            return apiBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Result fetch(String shareInfo) {
        try {
            String shortUrl = extractUrl(shareInfo);
            String originUrl = convertUrl(shortUrl);
            String itemId = parseItemIdFromUrl(originUrl);
            ApiResult apiBean = requestToAPI(itemId);
            Result result = new Result();
            ItemList item = apiBean.getItemList().get(0);
            result.name = item.getShareInfo().getShareTitle();
            String originVideoUrl = item.getVideo().getPlayAddr().getUrlList().get(0);
            // 去水印视频转换.
            result.videoUrl = originVideoUrl.replace("playwm", "play");
            result.musicUrl = item.getMusic().getPlayUrl().getUri();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isExists(String shareInfo, String saveToFolder) {
        String shortUrl = extractUrl(shareInfo);
        if (StringUtils.isEmpty(shortUrl)) {
            //下载链接为空
            return false;
        }

        File file = new File(saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".mp4");
        if (file.exists()) {
            //下载过了
            return true;
        }
        return false;
    }

    /**
     * 下载抖音无水印视频到某个路径下.
     *
     * @param shareInfo
     * @param saveToFolder
     */
    public static boolean downloadVideo(String shareInfo, String saveToFolder) throws IOException {

        //创建目录
        FileUtils.createOrExistsDir(saveToFolder);

        if (isExists(shareInfo, saveToFolder)) {
            //下载过了
            return true;
        }

        String shortUrl = extractUrl(shareInfo);
        File file = new File(saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".mp4");
        File fileTemp = new File(saveToFolder + "/" + EncryptUtils.encryptMD5ToString(shortUrl) + ".temp");

        Result result = fetch(shareInfo);
        if (result != null) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Connection", "keep-alive");
            headers.put("Host", "aweme.snssdk.com");
            headers.put("User-Agent", UA);
            InputStream in = get(result.videoUrl, headers);
            if (in == null) {
                return false;
            }
            //删除再创建，缓存文件
            FileUtils.delete(fileTemp);
            FileUtils.createOrExistsFile(fileTemp);
            Log.i(TAG, "file_path:" + fileTemp.getAbsolutePath());

            boolean writeRe = FileIOUtils.writeFileFromIS(fileTemp, in);
            if (writeRe) {
                boolean re = FileUtils.copy(fileTemp, file);
                if (re) {
                    FileUtils.delete(fileTemp);
                    return true;
                }
                FileUtils.delete(file);
            }
            return false;
        }
        return false;
    }

    public static InputStream get(String url, Map<String, String> headers) {
        try {
            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(60000);
            conn.setDoInput(true);
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
                Log.i(TAG, "header_key:" + entry.getKey() + ",value:" + entry.getValue());
            }
            int code = conn.getResponseCode();
            Log.i(TAG, "url:" + url);
            Log.i(TAG, "code:" + code);
            if (code == 200) {
                InputStream in = conn.getInputStream();
                return in;
            }
            if (code == 302) {
                //如果会重定向，保存302重定向地址，以及Cookies,然后重新发送请求(模拟请求)
                String locationUrl = conn.getHeaderField("Location");
                Log.i(TAG, "locationUrl:" + locationUrl);
                return get(locationUrl, new HashMap<>());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 从路径中提取itemId
     *
     * @param url
     * @return
     */
    public static String parseItemIdFromUrl(String url) {
        // https://www.iesdouyin.com/share/video/6519691519585160455/?region=CN&mid=6519692104368098051&u_code=36fi3lehcdfb&titleType=title
        String ans = "";
        String[] firstSplit = url.split("\\?");
        if (firstSplit.length > 0) {
            String[] strings = firstSplit[0].split("/");
            // after video.
            for (String string : strings) {
                if (isNumeric(string)) {
                    return string;
                }
            }
        }
        return ans;
    }

    public static boolean isNumeric(CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return false;
        } else {
            int sz = cs.length();

            for (int i = 0; i < sz; ++i) {
                if (!Character.isDigit(cs.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 短连接转换成长地址
     *
     * @param shortURL
     * @return
     * @throws IOException
     */
    public static String convertUrl(String shortURL) throws IOException {
        URL inputURL = new URL(shortURL);
        URLConnection urlConn = inputURL.openConnection();
        System.out.println("Short URL: " + shortURL);
        urlConn.getHeaderFields();
        String ans = urlConn.getURL().toString();
        System.out.println("Original URL: " + ans);
        return ans;
    }

    /**
     * 抽取URL
     *
     * @param rawInfo
     * @return
     */
    public static String extractUrl(String rawInfo) {
        if (StringUtils.isEmpty(rawInfo)) {
            return "";
        }
        for (String string : rawInfo.split(" ")) {
            if (string.startsWith("http")) {
                return string;
            }
        }
        return "";
    }

    /**
     * 解析抖音API获取视频结果.
     *
     * @param itemId
     * @return
     * @throws Exception
     */
    public static ApiResult requestToAPI(String itemId) throws Exception {
        String url = API + itemId;
        HttpURLConnection httpClient =
                (HttpURLConnection) new URL(url).openConnection();
        // optional default is GET
        httpClient.setRequestMethod("GET");

        //add request header
        httpClient.setRequestProperty("User-Agent", UA);

        int responseCode = httpClient.getResponseCode();
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            //print result
            return new Gson().fromJson(response.toString(), ApiResult.class);
        }
    }
}

package com.yuxie.baselib.webView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件对应 MIME类型 工具
 */
public class WebViewFileChooseUtils {

    private static final String[][] MIME_MapTable = {
            //{后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"image/*", "image/*"},//所有图片
            {"audio/*", "audio/*"},//所有音频
            {"video/*", "video/*"},//所以视频
            {"*/*", "*/*"} //所以文件
    };

    /**
     * 获取扩展类型 "|" 隔开
     *
     * @param acceptTypes acceptTypes
     */
    public static String getType(String[] acceptTypes) {
        if (acceptTypes == null) return "";
        StringBuilder sb = new StringBuilder();
        for (String type : acceptTypes) {
            String mime = getMIMEbyFileSuffix(type);
            if (!"".equals(mime)) {
                sb.append(mime);
                sb.append("|");
            }
        }
        if (sb.toString().length() != 0) {
            return sb.substring(0, sb.toString().length() - 1);
        }
        return "";
    }

    /**
     * 获取扩展类型
     *
     * @param acceptTypes acceptTypes
     */
    public static String[] getExtra(String[] acceptTypes) {
        String[] re = new String[]{};
        if (acceptTypes == null) return re;
        List<String> list = new ArrayList<>();
        for (String type : acceptTypes) {
            String mime = getMIMEbyFileSuffix(type);
            if (!"".equals(mime)) {
                list.add(mime);
            }
        }
        return list.toArray(re);
    }

    /**
     * 通过后缀找 MIME类型
     *
     * @param fileSuffix fileSuffix
     */
    public static String getMIMEbyFileSuffix(String fileSuffix) {
        String re = "";
        if (fileSuffix == null) {
            return "";
        }
        fileSuffix = fileSuffix.toLowerCase().replace(",", "").
                replace("，", "");

        for (String[] mime : MIME_MapTable) {
            if (fileSuffix.equals(mime[0])) {
                return mime[1];
            }
            if (fileSuffix.equals(mime[1])) {
                return mime[1];
            }
        }
        return re;
    }

    public static void main(String[] args) {
        String name = getMIMEbyFileSuffix(",");
        System.out.println("name:" + name);
        String getType = getType(new String[]{".pdf", ".png"});
        System.out.println("getType:" + getType);

        String[] getExtra = getExtra(new String[]{".pdf", ".png"});
        System.out.println("getExtra:" + Arrays.toString(getExtra));
    }

}

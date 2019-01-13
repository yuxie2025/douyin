package com.yuxie.demo.sy;

import com.blankj.utilcode.util.EncryptUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/* compiled from: ServiceVerificationUtill */
public class Sign {

    public static String toString(HashMap<String, String> hashMap) {
        List<Entry<String, String>> arrayList = new ArrayList(hashMap.entrySet());

        Collections.sort(arrayList, new Comparator<Entry<String, String>>() {
            /* renamed from: a */
            public int compare(Entry<String, String> entry, Entry<String, String> entry2) {
                return ((String) entry.getKey()).toString().compareTo((String) entry2.getKey());
            }
        });

        StringBuffer stringBuffer = new StringBuffer();
        for (Entry entry : arrayList) {
            StringBuilder stringBuilder;
            if (stringBuffer.length() == 0) {
                stringBuilder = new StringBuilder();
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append((String) entry.getValue());
                stringBuffer.append(stringBuilder.toString());
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append("&");
                stringBuilder.append((String) entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append((String) entry.getValue());
                stringBuffer.append(stringBuilder.toString());
            }
        }
        return stringBuffer.toString();
    }

    public static String toStringAndSign(HashMap<String, String> hashMap) {
        String re = toString(hashMap);
        return sign(re);
    }

    public static String sign(String paramString) {
        try {
            String str = EncryptUtils.encryptMD5ToString(paramString)
                    .toUpperCase();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
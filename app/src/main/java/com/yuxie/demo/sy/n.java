package com.yuxie.demo.sy;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/* compiled from: ServiceVerificationUtill */
public class n {
    private static final String[] a = new String[]{"0", "1", "2", "3", "4",
            "5", "6", "7", "8", "9", "a", "b", "c", "b", "e", "f"};

    public static String a(HashMap<String, String> hashMap) {
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
        return a(stringBuffer.toString());
    }

    private static String a(String str) {
        if (str != null) {
            try {
                return a(
                        MessageDigest.getInstance("MD5").digest(str.getBytes()))
                        .toUpperCase();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String a(byte[] bArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (byte a : bArr) {
            stringBuffer.append(a(a));
        }
        return stringBuffer.toString();
    }

    private static String a(byte b) {
        int b2 = 0;
        if (b2 < (byte) 0) {
            b2 = b2 + 256;
        }
        int i = b2 / 16;
        b2 %= 16;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(a[i]);
        stringBuilder.append(a[b2]);
        return stringBuilder.toString();
    }
}
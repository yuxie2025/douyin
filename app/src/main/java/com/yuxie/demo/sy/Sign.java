package com.yuxie.demo.sy;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Sign {
    private static final String[] a = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    private static String a(byte paramByte) {
        if (paramByte < 0)
            paramByte += 256;
        int i = paramByte / 16;
        int j = paramByte % 16;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(a[i]);
        localStringBuilder.append(a[j]);
        return localStringBuilder.toString();
    }

    private static String a(String paramString) {
        if (paramString != null)
            try {
                String str = a(MessageDigest.getInstance("MD5").digest(paramString.getBytes())).toUpperCase();
                return str;
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        return null;
    }

    public static String a(HashMap<String, String> paramHashMap) {
        ArrayList localArrayList = new ArrayList(paramHashMap.entrySet());
        Collections.sort(localArrayList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return 0;
            }

            public int a(Map.Entry<String, String> paramAnonymousEntry1, Map.Entry<String, String> paramAnonymousEntry2) {
                return ((String) paramAnonymousEntry1.getKey()).toString().compareTo((String) paramAnonymousEntry2.getKey());
            }
        });
        StringBuffer localStringBuffer = new StringBuffer();
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext()) {
            Map.Entry localEntry = (Map.Entry) localIterator.next();
            if (localStringBuffer.length() == 0) {
                StringBuilder localStringBuilder1 = new StringBuilder();
                localStringBuilder1.append((String) localEntry.getKey());
                localStringBuilder1.append("=");
                localStringBuilder1.append((String) localEntry.getValue());
                localStringBuffer.append(localStringBuilder1.toString());
            } else {
                StringBuilder localStringBuilder2 = new StringBuilder();
                localStringBuilder2.append("&");
                localStringBuilder2.append((String) localEntry.getKey());
                localStringBuilder2.append("=");
                localStringBuilder2.append((String) localEntry.getValue());
                localStringBuffer.append(localStringBuilder2.toString());
            }
        }
        return a(localStringBuffer.toString());
    }

    private static String a(byte[] paramArrayOfByte) {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i = 0; i < paramArrayOfByte.length; i++)
            localStringBuffer.append(a(paramArrayOfByte[i]));
        return localStringBuffer.toString();
    }
}

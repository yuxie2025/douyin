package com.baselib.commonutils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * desc:类转换初始化
 * Created by Lankun on 2018/10/29/029
 */
@SuppressWarnings("unused")
public class TUtil {
    public static <T> T getT(Object o, int i) {
        Type type = o.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            try {
                return ((Class<T>) ((ParameterizedType) type).getActualTypeArguments()[i]).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}


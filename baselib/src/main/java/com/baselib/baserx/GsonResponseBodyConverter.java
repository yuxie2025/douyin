package com.baselib.baserx;


import com.baselib.basebean.BaseRespose;
import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 作者: llk on 2017/10/8.
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;


    GsonResponseBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        try {

            Type typeToken = new TypeToken<BaseRespose>() {
            }.getType();

            if (type == typeToken) {
                BaseRespose baseRespose = gson.fromJson(response, type);
                if (baseRespose.success()) {
                    return gson.fromJson(response, type);
                } else {
                    throw new ResultException(baseRespose.getMessage(), !baseRespose.success());
                }
            } else {

                Type parametType = ((ParameterizedType) type).getActualTypeArguments()[0];

                BaseRespose result = gson.fromJson(response, BaseRespose.class);
                String rows = result.getData().toString().trim();

                if (result.success() && "[]".equals(rows)) {
                    if (parametType.toString().contains("List")) {
                        return (T) result;
                    } else {
                        response = response.replace("[]", "null");
                    }
                    return (T) gson.fromJson(response, BaseRespose.class);
                }

                if (result.success()) {
                    return gson.fromJson(response, type);
                } else {
                    LogUtils.d("HttpManager,返回err==：" + response);
                    throw new ResultException(result.getMessage(), !result.success());
                }
            }
        } finally {
            value.close();
        }
    }
}

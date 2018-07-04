package com.baselib.baserx;

/**
 * 作者: llk on 2017/10/8.
 */

import com.baselib.basebean.BaseRespose;
import com.baselib.commonutils.LogUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseGeneralBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final Type type;

    GsonResponseGeneralBodyConverter(Gson gson, Type type) {
        this.gson = gson;
        this.type = type;
    }

    /**
     * 针对数据返回成功、错误不同类型字段处理
     */
    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();
        LogUtils.logd("--HttpManager,返回err==：" + response);
        try {
            T result = gson.fromJson(response, type);
            return gson.fromJson(response, type);
        } catch (Exception e) {
            LogUtils.logd("HttpManager,返回err==：" + response);
            throw new ResultException("000000", true);
        } finally {
            value.close();
        }
    }
}

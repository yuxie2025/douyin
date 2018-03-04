package com.baselib.baserx;

/**
 * 作者: liuhuaqian on 2017/10/8.
 */

import android.util.Log;

import com.baselib.basebean.BaseRespose;
import com.baselib.commonutils.LogUtils;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

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
            BaseRespose result = gson.fromJson(response, BaseRespose.class);

            String rows = result.getRows().toString().trim();

            if (result.success() && !"[]".equals(rows) && !"[{}]".contains(rows)) {
                return gson.fromJson(response, type);
            } else {
                LogUtils.logd("HttpManager,返回err==：" + response);
                throw new ResultException(result.getErrorMsg(), !result.success());
            }
        } finally {
            value.close();
        }
    }
}

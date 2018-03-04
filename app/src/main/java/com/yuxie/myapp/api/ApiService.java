package com.yuxie.myapp.api;

import java.util.Map;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by luo on 2018/3/3.
 */
public interface ApiService {
    @FormUrlEncoded
    @POST("")
    Observable<Result<String>> getBlogs(@Url String url, @FieldMap Map<String, String> options);

    @POST("")
    Observable<Result<String>> getBlogs(@Url String url, @Body String options);
}

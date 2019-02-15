package com.yuxie.demo.api;

import com.baselib.basebean.BaseRespose;
import com.yuxie.demo.entity.UrlLibBean;
import com.yuxie.demo.novel.Txt;
import com.yuxie.demo.novel.TxtDir;

import java.util.List;
import java.util.Map;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by luo on 2018/3/3.
 */
public interface ServerApiService {
    @FormUrlEncoded
    @POST("")
    Observable<Result<String>> getSmsApi(@Url String url, @FieldMap Map<String, String> options);

    @GET("")
    Observable<Result<String>> getSmsApi(@Url String url);

    @GET("")
    Observable<String> getUrl(@Url String url);


    //    http://49.4.70.94:8080/globestart/app/vehicle/vehicleList.mvc
//    accountId   1248119110873466880
//    token          158949eb3716484bb1bee84149c02f1a
//    Authorization    MTI0ODExOTExMDg3MzQ2Njg4MCFAIyQlVWlLbF4mKigpXyo5OUNCYGAwMD8/Ljw+
//    app      1.0.0
    @POST("globestart/app/vehicle/vehicleList.mvc")
    Observable<BaseRespose>
    vehicleList(@Header("accountId") String accountId,
                @Header("token") String token,
                @Header("Authorization") String Authorization,
                @Header("app") String app);

    /**
     * 提交信息
     *
     * @param longitude
     * @param latitude
     * @param address
     * @param imei
     * @return
     */
    @POST("read/info")
    @FormUrlEncoded
    Observable<BaseRespose>
    info(@Field("longitude") double longitude,
         @Field("latitude") double latitude,
         @Field("address") String address,
         @Field("imei") String imei);

    /**
     * 获取信息
     *
     * @return
     */
    @POST("read/getInfo")
    Observable<BaseRespose>
    getInfo();

    @POST("read/sendPic")
    @FormUrlEncoded
    Observable<BaseRespose>
    sendPic(@Field("imgData") String imgData);


    @POST("read/search")
    @FormUrlEncoded
    Observable<BaseRespose<List<Txt>>>
    search(@Field("key") String key);

    @POST("read/dir")
    @FormUrlEncoded
    Observable<BaseRespose<List<TxtDir>>>
    dir(@Field("url") String url);


    @POST("read/getContent")
    @FormUrlEncoded
    Observable<BaseRespose<String>>
    getContent(@Field("url") String url);

    @POST("read/like")
    @FormUrlEncoded
    Observable<BaseRespose>
    like(@Field("status") String status,
         @Field("imei") String imei);

    @POST("read/getUrlLib")
    Observable<BaseRespose<List<UrlLibBean>>>
    getUrlLib();


    @POST("v1/comments")
    @FormUrlEncoded
    Observable<String>
    comments(
            @Field("content_id") String content_id,
            @Field("content") String content,
            @Field("quote_id") String quote_id);


    //    grant_type=client_credentials&client_id=Va5yQRHlA4Fq5eR3LT0vuXV4&client_secret=0rDSjzQ20XUj5itV6WRtznPQSzr5pVw2
    @POST("oauth/2.0/token")
    @FormUrlEncoded
    Observable<String>
    getToken(@Field("grant_type") String grantType,
             @Field("client_id") String contentId,
             @Field("client_secret") String clientSecret);


//    https://aip.baidubce.com/rest/2.0/face/v2/detect

    @POST("rest/2.0/face/v3/detect")
    @FormUrlEncoded
    Observable<String>
    detect(@Field("image") String image,
           @Field("access_token") String access_token,
           @Field("max_face_num") String max_face_num,
           @Field("client_secret") String face_fields);


}

package com.yuxie.demo.api;

import com.baselib.basebean.BaseRespose;

import java.util.Map;

import retrofit2.adapter.rxjava.Result;
import retrofit2.http.Body;
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


}

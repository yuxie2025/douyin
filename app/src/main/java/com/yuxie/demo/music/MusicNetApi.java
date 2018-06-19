package com.yuxie.demo.music;

import com.yuxie.demo.entity.MusicInfo;
import com.yuxie.demo.entity.Musics;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/08/22.
 */

public interface MusicNetApi {

//    http://s.music.163.com/search/get/?type=1&s=%22%E4%BD%A0%E5%A5%BD%22


    @GET("search/get")
    Call<Musics> queryMp3List(@Query("type") String type,
                              @Query("s") String key,
                              @Query("limit") String limit);


//   http://music.163.com/weapi/song/enhance/player/url?csrf_token=
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=GBK")
    @FormUrlEncoded
    @POST("weapi/song/enhance/player/url?csrf_token=")
    Call<MusicInfo> queryMusicInfo(@Field(value="params",encoded = true) String params,
                                 @Field("encSecKey") String encSecKey);

}

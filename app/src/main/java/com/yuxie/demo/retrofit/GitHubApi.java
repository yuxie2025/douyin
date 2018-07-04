package com.yuxie.demo.retrofit;


import com.yuxie.demo.music.RetrofitBean;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


/**
 * Created by Administrator on 2017/7/26.
 */

public interface GitHubApi {

    //@GET()get请求("存放url")一般是不以"/"开头
    //@Headers()添加请求头
    //@Path请求路径
    //@QueryMap 请求map
    //@Query 请求参数
    //@Url 声明传入完整的URL(2.0新增)

//    @Query、@QueryMap：用于Http Get请求传递参数
//    @Field,@FieldMap：用于Post方式传递参数,需要在请求接口方法上添加@FormUrlEncoded,即以表单的方式传递参数
//    @Body：用于Post,根据转换方式将实例对象转化为对应字符串传递参数.比如Retrofit添加GsonConverterFactory则是将body转化为gson字符串进行传递
//    @Path：用于URL上占位符
//    @Part：配合@Multipart使用,一般用于文件上传
//    @Header：添加http header
//    @Headers：跟@Header作用一样,只是使用方式不一样,@Header是作为请求方法的参数传入,@Headers是以固定方式直接添加到请求方法上

    //返回集合已经解决json数据
    @GET("repos/{owner}/{repo}/contributors")
    Call<List<Contributor>> contributorsBySimpleGetList(@Path("owner") String owner, @Path("repo") String repo);


    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: RetrofitBean-Sample-App",
            "name:ljd"
    })
    @GET("repos/{owner}/{repo}/contributors")
    Call<ResponseBody> contributorsBySimpleGetString(@Path("owner") String owner, @Path("repo") String repo);


    @GET("search/repositories")
    Call<RetrofitBean> queryRetrofitByGetCallMap(@QueryMap Map<String, String> map);

    @GET("search/repositories")
    Call<RetrofitBean> queryRetrofitByGetCall(@Query("q") String owner,
                                              @Query("since") String time,
                                              @Query("page") int page,
                                              @Query("per_page") int per_Page);
    @GET
    Call<ResponseBody> queryRetrofitByGetCall(@Url String url);


    @FormUrlEncoded
    @POST("user/edit")
    Call<User> updateUser(@FieldMap Map<String,String> fieldMap);


    @FormUrlEncoded
    @POST("user/edit")
    Call<User> updateUser(@Field("first_name") String firstName,@Field("last_name") String lastName);

//    @Multipart
//    @PUT("/user/photo")
//    User updateUser(@Part("photo") TypedFile photo, @Part("description") TypedString description);


}

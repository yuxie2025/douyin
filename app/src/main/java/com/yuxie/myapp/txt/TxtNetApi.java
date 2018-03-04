package com.yuxie.myapp.txt;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/08/24.
 */

public interface TxtNetApi {

    //http://www.gxwztv.com/search.htm?keyword=%E5%A4%A7%E4%B8%BB%E5%AE%B0
    @GET("search.htm")
    @Headers({"Accept:text/html,application/xhtml+xml,application/xml",
            "User-Agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3",
            "Accept-Charset:UTF-8",
            "Keep-Alive:300",
            "Connection:Keep-Alive",
            "Cache-Control:no-cache"})
    Call<ResponseBody> queryTxtList(@Query("keyword") String keyword);

//    http://zhannei.baidu.com/cse/search?q=%E6%AD%A6%E7%BB%83%E5%B7%85%E5%B3%B0&click=1&entry=1&s=11815863563564650233&nsid=
//    Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3
    @GET("cse/search")
    @Headers({"Accept:text/html,application/xhtml+xml,application/xml",
            "User-Agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3",
            "Accept-Charset:UTF-8",
            "Keep-Alive:300",
            "Connection:Keep-Alive",
            "Cache-Control:no-cache"})
    Call<ResponseBody> queryBQGTxtList(@Query("q") String keyword, @Query("s") String s);

    @GET("{url}")
    @Headers({
            "User-Agent:Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3",
            "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8",
//            "Accept-Language: zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3",
            "Accept-Charset:UTF-8",
//            "Accept-Encoding:gzip,deflate",
            "Keep-Alive:300",
            "Connection:Keep-Alive",
            "Cache-Control:no-cache"})
    Call<ResponseBody> queryUrlString(@Path("url") String url);

    @GET("{url}")
    Call<ResponseBody> queryDirUrlString(@Path("url") String url);

}

package com.yuxie.demo.api.server;

import android.util.SparseArray;

import com.yuxie.demo.api.ServerApiService;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * 作者: llk on 2017/9/8.
 */

public class ServerApi extends Api<ServerApiService> {


    private static SparseArray<ServerApi> apis = new SparseArray<>(HostType.HOST_COUNT);


    private ServerApi(int type) {
        super(HostType.getHost(type));
    }

    private ServerApi(String url) {
        super(url);
    }

    public static ServerApi getInstance(int type) {
        ServerApi api = apis.get(type);
        if (api == null) {
            api = new ServerApi(type);
            apis.put(type, api);
        }
        return api;
    }

    public static ServerApiService getInstance() {
        return getInstance(HostType.HOST_TYPE_COMMON_FLAG).getApiService();
    }

    public static ServerApiService getInstance(String url) {
        return new ServerApi(url).getApiService();
    }

    /**
     * 清除网络配置,重新获取
     */
    public static void clearServerApi(int type) {
        apis.delete(type);
    }
}

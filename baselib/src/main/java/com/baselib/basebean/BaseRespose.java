package com.baselib.basebean;

import java.io.Serializable;

/**
 * 作者: llk on 2017/9/8.
 * 封装服务器返回数据 单个对象
 */
@SuppressWarnings("unused")
public class BaseRespose<T> implements Serializable {
    /**
     * 返回状态码
     */
    private String error_code;
    /**
     * errorMsg
     */
    private String message;
    /**
     * 返回记录条目
     */
    private String status;
    /**
     * 返回列表数据，JSON数组
     */
    private T data;


    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean success() {
        return "0".equals(error_code);
    }


    @Override
    public String toString() {
        return "BaseRespose{" +
                "error_code='" + error_code + '\'' +
                ", message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                '}';
    }
}

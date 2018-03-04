package com.baselib.basebean;

import java.io.Serializable;

/**
 * 作者: liuhuaqian on 2017/9/8.
 * 封装服务器返回数据 单个对象
 */

public class BaseRespose<T> implements Serializable {
    //    字段说明：
//    code：返回状态码。999999:错误，000000：正常返回
//    errorMsg： 错误处理结果消息。
//    total：返回记录条目
//    rows：返回列表数据，JSON数组

    /**
     * 返回状态码
     */
    private String code;
    /**
     * errorMsg
     */
    private String errorMsg;
    /**
     * 返回记录条目
     */
    private String total;
    /**
     * 返回列表数据，JSON数组
     */
    private T rows;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public boolean success() {
        return "000000".equals(code);
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", total='" + total + '\'' +
                ", rows=" + rows +
                '}';
    }
}

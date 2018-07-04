package com.baselib.basebean;

import java.io.Serializable;
import java.util.List;

/**
 * 作者: llk on 2017/9/23.
 * 封装服务器返回数据 多个对象
 */

public class BaseListRespose<T> implements Serializable {
    //    字段说明：
//    code：返回状态码。999999:错误，000000：正常返回
//    errorMsg： 错误处理结果消息。
//    data：扩展数据
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
     * 扩展数据
     */
    private String data;
    /**
     * 返回列表数据，JSON数组
     */
    private List<T> rows;

    @Override
    public String toString() {
        return "BaseRespose{" +
                "code='" + code + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", total='" + total + '\'' +
                ", data='" + data + '\'' +
                ", rows=" + rows +
                '}';
    }

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public boolean success() {
        return "000000".equals(code);
    }
}

package com.yuxie.demo.sy;

public class BaseSyBean<T> {

    private int code;
    private String message;

    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSucceed() {
        boolean re = false;
        if (code == 200) {
            re = true;
        }
        return re;
    }

    @Override
    public String toString() {
        return "BaseSyBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}

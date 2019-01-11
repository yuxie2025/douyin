package com.baselib.basebean;

import com.baselib.enums.BaseMsgEnum;

/**
 * 作者: llk on 2017/9/8.
 */

public class BaseMsgEvent<T> {

    private T msgEntity;

    private String message;

    private BaseMsgEnum id;

    public BaseMsgEvent(BaseMsgEnum id, T msgEntity) {
        this.id = id;
        this.msgEntity = msgEntity;
    }

    public BaseMsgEvent(String message, BaseMsgEnum id) {
        this.message = message;
        this.id = id;
    }

    public T getMsgEntity() {
        return msgEntity;
    }

    public void setMsgEntity(T msgEntity) {
        this.msgEntity = msgEntity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseMsgEnum getId() {
        return id;
    }

    public void setId(BaseMsgEnum id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseMsgEvent{" +
                "msgEntity=" + msgEntity +
                ", message='" + message + '\'' +
                ", id=" + id +
                '}';
    }
}

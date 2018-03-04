package com.baselib.basebean;

import com.baselib.enums.StudentEnum;

/**
 * 作者: liuhuaqian on 2017/9/8.
 */

public class StudentEvent<T> {
  private T msgEntity;
    private StudentEnum id;
    private int position;

    public StudentEvent(StudentEnum id,int position) {
        this.position = position;
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    private String message;

    @Override
    public String toString() {
        return "StudentEvent{" +
                "msgEntity=" + msgEntity +
                ", id=" + id +
                ", position=" + position +
                ", message='" + message + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;

    public StudentEvent(StudentEnum id, T msgEntity) {
        this.id = id;
        this.msgEntity = msgEntity;
    }
    public StudentEvent(StudentEnum id,String message, T msgEntity) {
        this.id = id;
        this.msgEntity = msgEntity;
        this.message = message;
    }
    public StudentEvent(StudentEnum id, String message) {
        this.id = id;
        this.message = message;
    }
    public StudentEvent(StudentEnum id, String message,String type) {
        this.id = id;
        this.message = message;
        this.type = type;
    }
    public StudentEvent(StudentEnum id) {
        this.id = id;

    }

    public T getMsgEntity() {
        return msgEntity;
    }

    public void setMsgEntity(T msgEntity) {
        this.msgEntity = msgEntity;
    }

    public StudentEnum getId() {
        return id;
    }

    public void setId(StudentEnum id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

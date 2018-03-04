package com.yuxie.myapp.mvp.bean;

public class UserBean {
    private String mFirstName;
    private String mLastName;
    private String address;

    public UserBean(String mFirstName, String mLastName, String address) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.address = address;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

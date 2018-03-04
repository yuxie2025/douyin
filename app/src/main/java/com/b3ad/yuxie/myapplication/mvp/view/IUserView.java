package com.b3ad.yuxie.myapplication.mvp.view;

public interface IUserView {
    int getID();

    String getFristName();

    String getLastName();

    void setFirstName(String firstName);

    void setLastName(String lastName);

    String getAddress();

    void setAddress(String address);

}

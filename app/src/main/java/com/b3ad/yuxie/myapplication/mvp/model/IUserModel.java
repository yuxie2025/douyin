package com.b3ad.yuxie.myapplication.mvp.model;


import com.b3ad.yuxie.myapplication.mvp.bean.UserBean;

public interface IUserModel {
	void setID(int id);

	void setFirstName(String firstName);

	void setLastName(String lastName);

	void setAddress(String address);

	UserBean load(int id);
}

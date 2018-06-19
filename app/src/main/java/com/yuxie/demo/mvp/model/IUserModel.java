package com.yuxie.demo.mvp.model;


import com.yuxie.demo.mvp.bean.UserBean;

public interface IUserModel {
	void setID(int id);

	void setFirstName(String firstName);

	void setLastName(String lastName);

	void setAddress(String address);

	UserBean load(int id);
}

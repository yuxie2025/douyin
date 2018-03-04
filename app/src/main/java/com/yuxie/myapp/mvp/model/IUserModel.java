package com.yuxie.myapp.mvp.model;


import com.yuxie.myapp.mvp.bean.UserBean;

public interface IUserModel {
	void setID(int id);

	void setFirstName(String firstName);

	void setLastName(String lastName);

	void setAddress(String address);

	UserBean load(int id);
}

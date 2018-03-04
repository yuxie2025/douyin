package com.b3ad.yuxie.myapplication.mvp.persenter;


import com.b3ad.yuxie.myapplication.mvp.bean.UserBean;
import com.b3ad.yuxie.myapplication.mvp.model.IUserModel;
import com.b3ad.yuxie.myapplication.mvp.model.UserModel;
import com.b3ad.yuxie.myapplication.mvp.view.IUserView;

public class UserPresenter {
	private IUserView mUserView;
	private IUserModel mUserModel;

	public UserPresenter(IUserView view) {
		mUserView = view;
		mUserModel = new UserModel();
	}

	public void saveUser(int id, String firstName, String lastName,String address) {
		mUserModel.setID(id);
		mUserModel.setFirstName(firstName);
		mUserModel.setLastName(lastName);
		mUserModel.setAddress(address);
	}

	public void loadUser(int id) {
		UserBean user = mUserModel.load(id);
		mUserView.setFirstName(user.getFirstName());
		mUserView.setLastName(user.getLastName());
		mUserView.setAddress(user.getAddress());
	}
}

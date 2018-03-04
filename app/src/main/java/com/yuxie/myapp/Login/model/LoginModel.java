package com.yuxie.myapp.Login.model;

import android.text.TextUtils;

import com.yuxie.myapp.Login.contract.LoginContract;
import com.yuxie.myapp.Login.presenter.LoginPresenter;

/**
 * Created by Administrator on 2017/7/25.
 */

public class LoginModel implements LoginContract.Model {
    @Override
    public void login(String name, String psw, LoginPresenter mLoginPresenter) {
            if (TextUtils.isEmpty(name)){
                mLoginPresenter.onfail("用户名不能为空!");
                return;
            }
            if (TextUtils.isEmpty(psw)){
                mLoginPresenter.onfail("密码不能为空!");
                return;
            }
            if(name.equals("admin")&&psw.equals("admin")){
                mLoginPresenter.onSuccess();
            }
    }
}

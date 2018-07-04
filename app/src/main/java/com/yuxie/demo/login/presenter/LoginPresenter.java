package com.yuxie.demo.login.presenter;


import com.yuxie.demo.login.contract.LoginContract;
import com.yuxie.demo.login.model.LoginModel;
import com.yuxie.demo.login.view.LoginActivity;

/**
 * Created by Administrator on 2017/7/25.
 */

public class LoginPresenter implements LoginContract.Presenter {

    LoginActivity mLoginActivity;
    LoginModel mLoginModel;

    public LoginPresenter(LoginActivity loginActivity) {
        this.mLoginActivity = loginActivity;
        mLoginModel = new LoginModel();
    }

    /**
     * 在这定义一个登陆的方法
     */
    public void login() {
        //将view中的参数获取出来。
        String name = mLoginActivity.getName();
        String psw = mLoginActivity.getPassWord();
        /**
         * 调用一下业务处理model层的登陆方法
         * 参数一：账号
         * 参数二：密码
         * 参数三：登陆状态监听器
         */
        mLoginModel.login(name, psw, this);
    }

    @Override
    public void onSuccess() {
        mLoginActivity.onSuccess();
    }

    @Override
    public void onfail(String msg) {
        mLoginActivity.showToast(msg);
    }
}

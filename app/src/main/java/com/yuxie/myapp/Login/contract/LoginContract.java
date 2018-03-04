package com.yuxie.myapp.Login.contract;

import com.yuxie.myapp.Login.presenter.LoginPresenter;

/**
 * Created by Administrator on 2017/7/25.
 */

public interface LoginContract {
    interface Model {
        void login(String name, String psw, LoginPresenter mLoginPresenter);
    }

    interface View {
        void showToast(String msg);

        void onSuccess();

        String getName();

        String getPassWord();
    }

    interface Presenter {
        void onSuccess();//登陆成功

        void onfail(String msg);//登陆失败
    }
}

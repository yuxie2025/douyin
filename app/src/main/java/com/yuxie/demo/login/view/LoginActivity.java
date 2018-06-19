package com.yuxie.demo.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.yuxie.demo.R;
import com.yuxie.demo.login.contract.LoginContract;
import com.yuxie.demo.login.presenter.LoginPresenter;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private EditText etUsername;//用户名
    private EditText etPassword;//密码

    private LoginPresenter mloginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mloginPresenter = new LoginPresenter(this);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
    }

    public void btn_login(View view) {
        mloginPresenter.login();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this, "登录成功!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public String getName() {
        return etUsername.getText().toString();
    }

    @Override
    public String getPassWord() {
        return etPassword.getText().toString();
    }
}

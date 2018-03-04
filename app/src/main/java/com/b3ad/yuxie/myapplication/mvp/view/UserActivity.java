package com.b3ad.yuxie.myapplication.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.b3ad.yuxie.myapplication.R;
import com.b3ad.yuxie.myapplication.mvp.persenter.UserPresenter;


public class UserActivity extends Activity implements OnClickListener,
        IUserView {

    private EditText mFirstNameEditText, mLastNameEditText, mIdEditText,mAddressEditText;
    private Button mSaveButton, mLoadButton;
    private UserPresenter mUserPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp_user);
        findWidgets();
        mUserPresenter = new UserPresenter(this);
        mSaveButton.setOnClickListener(this);
        mLoadButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.saveButton:
                mUserPresenter.saveUser(getID(), getFristName(),
                        getLastName(),getAddress());
                break;
            case R.id.loadButton:
                mUserPresenter.loadUser(getID());
                break;
            default:
                break;
        }
    }

    @Override
    public void setFirstName(String firstName) {
        // TODO Auto-generated method stub
        mFirstNameEditText.setText(firstName);
    }

    @Override
    public void setLastName(String lastName) {
        // TODO Auto-generated method stub
        mLastNameEditText.setText(lastName);
    }

    @Override
    public int getID() {
        // TODO Auto-generated method stub
        return Integer.parseInt(mIdEditText.getText().toString());
    }

    @Override
    public String getFristName() {
        // TODO Auto-generated method stub
        return mFirstNameEditText.getText().toString();
    }

    @Override
    public String getLastName() {
        // TODO Auto-generated method stub
        return mLastNameEditText.getText().toString();
    }

    @Override
    public String getAddress() {
        return mAddressEditText.getText().toString();
    }

    @Override
    public void setAddress(String address) {
        mAddressEditText.setText(address);
    }

    void findWidgets() {
        mFirstNameEditText = (EditText) findViewById(R.id.first_name_edt);
        mLastNameEditText = (EditText) findViewById(R.id.last_name_edt);
        mIdEditText = (EditText) findViewById(R.id.id_edt);
        mAddressEditText = (EditText) findViewById(R.id.address_edt);

        mSaveButton = (Button) findViewById(R.id.saveButton);
        mLoadButton = (Button) findViewById(R.id.loadButton);
    }
}

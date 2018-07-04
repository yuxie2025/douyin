package com.yuxie.demo.controlpc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baselib.base.BaseActivity;
import com.yuxie.demo.R;

import java.net.DatagramSocket;
import java.net.InetAddress;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemoteControlActivity extends BaseActivity {

    @BindView(R.id.et_socket)
    EditText et_socket;
    @BindView(R.id.btn_connect)
    Button btn_connect;
    @BindView(R.id.et_ip)
    EditText et_ip;
    @BindView(R.id.title)
    TextView title;
    private Context mContext = this;

    @Override
    public int getLayoutId() {
        return R.layout.activity_remote_control;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        title.setText("控制电脑");
    }

    @OnClick({R.id.rl_left, R.id.btn_connect})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_left:
                finish();
                break;
            case R.id.btn_connect:

                String ip = et_ip.getText().toString();
                int socketNum = Integer.parseInt(et_socket.getText().toString());
                if (TextUtils.isEmpty(ip) || socketNum == 0) {
                    Toast.makeText(mContext, "请输入ip地址和端口!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Settings.ipnum = ip;
                Settings.scoketnum = socketNum;

                try {
                    //首先创建一个DatagramSocket对象
                    DatagramSocket socket = new DatagramSocket();
                    //创建一个InetAddree
                    InetAddress serverAddress = InetAddress.getByName(ip);

                    Intent intent = new Intent(mContext, ControlActivity.class);
                    mContext.startActivity(intent);

                    Toast.makeText(mContext, "连接电脑成功!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "连接电脑失败! " + e.toString(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

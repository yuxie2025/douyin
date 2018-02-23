package com.b3ad.yuxie.myapplication.controlpc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.b3ad.yuxie.myapplication.R;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class RemoteControlActivity extends AppCompatActivity {

    private EditText et_ip;
    private EditText et_socket;
    private Button btn_connect;
    private Context mContext=this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_control);

        et_ip= (EditText) findViewById(R.id.et_ip);
        et_socket= (EditText) findViewById(R.id.et_socket);
        btn_connect= (Button) findViewById(R.id.btn_connect);

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ip=et_ip.getText().toString();
                int socketNum=Integer.parseInt(et_socket.getText().toString());
                if (TextUtils.isEmpty(ip)||socketNum==0){
                    Toast.makeText(mContext,"请输入ip地址和端口!",Toast.LENGTH_SHORT).show();
                    return;
                }
                Settings.ipnum=ip;
                Settings.scoketnum=socketNum;

                try {
                    //首先创建一个DatagramSocket对象
                    DatagramSocket socket = new DatagramSocket();
                    //创建一个InetAddree
                    InetAddress serverAddress = InetAddress.getByName(ip);

                    Intent intent=new Intent(mContext,ControlActivity.class);
                    mContext.startActivity(intent);

                    Toast.makeText(mContext,"连接电脑成功!",Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext,"连接电脑失败! "+e.toString(),Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}

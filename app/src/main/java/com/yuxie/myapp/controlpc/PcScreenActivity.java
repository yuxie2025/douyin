package com.yuxie.myapp.controlpc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.yuxie.myapp.R;

import java.lang.ref.WeakReference;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class PcScreenActivity extends AppCompatActivity {

    ImageView iv_pc_screen;
    DatagramSocket socket;

    // 定义每个数据报的最大大小为4KB
    private static final int DATA_LEN = 1*1024*1024;
    // 定义接收网络数据的字节数组
    byte[] inBuff = new byte[DATA_LEN];
    // 以指定的字节数组创建准备接收数据的DatagramPacket对象
    private DatagramPacket inPacket =
            new DatagramPacket(inBuff , inBuff.length);

    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pc_screen);

        iv_pc_screen= (ImageView) findViewById(R.id.iv_pc_screen);

        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        new Thread(new InnerRunnable("hello")).start();

    }

    private class InnerRunnable implements Runnable {

        String str = "";

        public InnerRunnable(String str) {
            this.str = str;
        }

        @Override
        public void run() {
//            try {
//                // 首先创建一个DatagramSocket对象
//
//                // 创建一个InetAddree
//                InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
//                byte data[] = str.getBytes();
//                // 创建一个DatagramPacket对象，并指定要讲这个数据包发送到网络当中的哪个地址，以及端口号
//                DatagramPacket packet = new DatagramPacket(data, data.length,
//                        serverAddress, Settings.scoketnum);
//                // 调用socket对象的send方法，发送数据
//                socket.send(packet);
//                Log.i("TAG", "sendMessage: Srt:" + str);
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.i("TAG", "sendMessage: e:" + e.toString());
//            }

            try {

                // 创建一个InetAddree
                InetAddress serverAddress = InetAddress.getByName(Settings.ipnum);
                byte data[] = str.getBytes();
                // 创建一个DatagramPacket对象，并指定要讲这个数据包发送到网络当中的哪个地址，以及端口号
                DatagramPacket packet = new DatagramPacket(data, data.length,
                        serverAddress, Settings.scoketnum);
                // 调用socket对象的send方法，发送数据
                socket.send(packet);


                while (true) {

                    socket.receive(inPacket);

                    new String(inBuff , 0 , inPacket.getLength());

                    bitmap=BitmapFactory.decodeByteArray(inBuff, 0, inPacket.getLength());

                    mHandler.obtainMessage(0).sendToTarget();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    /**
//     * 转换byte数组为Image
//     *
//     * @param bytes
//     * @return Image
//     */
//    public static Image bytesToImage(byte[] bytes) {
//        Image image = Toolkit.getDefaultToolkit().createImage(bytes);
//        try {
//            MediaTracker mt = new MediaTracker(new Label());
//            mt.addImage(image, 0);
//            mt.waitForAll();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        return image;
//    }
//}

    private Handler mHandler=new InnerHandler(this);

    private static class InnerHandler extends Handler {
        WeakReference<PcScreenActivity> weakReference=null;
        //非静态内部类隐式,持有外部类的引用
        //使用弱引用持有activity对象的引用,避免handler引起的内存泄露
        public InnerHandler( PcScreenActivity activity) {
            weakReference=new WeakReference<PcScreenActivity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            PcScreenActivity activity=weakReference.get();
            if (activity!=null){
                if (msg.what==0){
                    activity.iv_pc_screen.setImageBitmap(activity.bitmap);
                }
            }
        }
    }
}

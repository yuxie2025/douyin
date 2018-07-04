package com.yuxie.demo.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.yuxie.demo.R;

public class GlideAndPicassoActivity extends AppCompatActivity {

    ImageView iv_glide;
    ImageView iv_picasso;
    Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_and_picasso);

        iv_glide = (ImageView) findViewById(R.id.iv_glide);
        iv_picasso = (ImageView) findViewById(R.id.iv_picasso);

        //Glide加载图片
        Glide.with(mContext)
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")//图片的下载地址
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//不同尺寸的图片,各下载(缓存)一份
                .into(iv_glide);//下载的图标加载到对应的ImageView

//       Glide 是 Google 员工的开源项目，被一些 Google App 使用，在Google I/O 上被推荐

        //Picasso加载图片
        Picasso.with(mContext)
                .load("http://inthecheesefactory.com/uploads/source/glidepicasso/cover.jpg")//图片的下载地址
//                .resize(768, 432)//指定图片大小
                .into(iv_picasso);//下载的图标加载到对应的ImageView

//      Picasso 是 Square 开源的项目，且他的主导者是 JakeWharton，所以广为人知。

//      虽然两者看起来一样，但是Glide更易用，因为Glide的with方法不光接受Context，还接受Activity 和 Fragment，Context会自动的从他们获取。
//      同时将Activity/Fragment作为with()参数的好处是：图片加载会和Activity/Fragment的生命周期保持一致，
//      比如Paused状态在暂停加载，在Resumed的时候又自动重新加载。所以我建议传参的时候传递Activity 和 Fragment给Glide，而不是Context。
//      Glide远比Picasso快，虽然需要更大的空间来缓存。推荐使用Glide

        //可以加载网络图片,本地文件,本地资源等...
//        .load(String string) string可以为一个文件路径、uri或者url
//        .load(Uri uri) uri类型
//        .load(File file) 文件
//        .load(Integer resourceId) 资源Id, R.drawable.xxx或者R.mipmap.xxx
//        .load( byte[] model)byte[] 类型
//        .load(T model) 自定义类型

    }
}

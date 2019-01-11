package com.baselib.uitls;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.baselib.BuildConfig;
import com.baselib.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * Created by luo on 2018/1/16.
 */
@SuppressWarnings("unused")
public class GlideUtil {

    private static final String URL_HEAD = BuildConfig.HOST;

    /**
     * 显示圆形头像(有默认图)
     *
     * @param url       地址
     * @param imageView 图片控件
     */
    public static void showHead(String url, ImageView imageView, int defaultPic) {
        initGlide(url, imageView, defaultPic);
    }

    /**
     * 显示圆形头像(无默认图)
     *
     * @param url       地址
     * @param imageView 图标控件
     */
    public static void showHead(String url, ImageView imageView) {
        initGlide(url, imageView, 0);
    }


    /**
     * 显示图片
     *
     * @param url       地址
     * @param imageView 图片控件
     */
    public static void showImageView(String url, ImageView imageView) {
        int defaultPic = R.drawable.list_empt_pic;
        initGlide(url, imageView, defaultPic);
    }

    /**
     * 显示图片
     *
     * @param url        地址
     * @param imageView  图片控件
     * @param defaultPic 默认图片
     */
    public static void showImageView(String url, ImageView imageView, int defaultPic) {
        initGlide(url, imageView, defaultPic);
    }

    /**
     * 显示对象图片
     *
     * @param model     任意类型
     * @param imageView 图片控件
     */
    public static void showImageViewObject(Object model, ImageView imageView) {
        initGlides(model, imageView, R.drawable.list_empt_pic);
    }

    /**
     * 显示对象图片
     *
     * @param model     任意类型
     * @param imageView 图片控件
     */
    public static void showImageViewObject(Object model, ImageView imageView, int defaultPic) {
        initGlides(model, imageView, defaultPic);
    }

    /**
     * 处理 url 并初始化Glide
     *
     * @param url        url
     * @param imageView  图片控件
     * @param defaultPic 默认图片
     */
    private static void initGlide(String url, ImageView imageView, int defaultPic) {
        url = getUrl(url);
        initGlides(url, imageView, defaultPic);
    }

    /**
     * 获取图标连接
     *
     * @param url 图片url 完整或不完整url
     * @return 图标URL
     */
    private static String getUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            return null;
        }

        if (url.contains("http") || url.contains("https")) {
            return url;
        } else {
            return URL_HEAD + url;
        }
    }

    /**
     * 初始化Glide
     */
    private static void initGlides(Object model, ImageView imageView, int defaultPic) {

        final Context context = imageView.getContext();

        RequestOptions options;

        if (defaultPic == 0) {
            options = new RequestOptions()
                    .dontAnimate()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        } else {
            options = new RequestOptions()
                    .dontAnimate()
                    .placeholder(defaultPic)
                    .error(defaultPic)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        }

        Glide.with(context)
                .load(model)
                .apply(options)
                .listener(requestListener)
                .into(imageView);
    }

    /**
     * 错误监听器
     */
    private static RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {

        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
        }

        @Override
        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };

}


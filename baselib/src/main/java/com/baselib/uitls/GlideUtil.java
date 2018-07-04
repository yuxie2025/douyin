package com.baselib.uitls;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.baselib.BuildConfig;
import com.baselib.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by luo on 2018/1/16.
 */


public class GlideUtil {

    public static final String URL_HEAD = "http://" + BuildConfig.HOST;

    /**
     * 显示圆形头像(有默认图)
     *
     * @param url
     * @param imageView
     */
    public static void showHead(String url, ImageView imageView, int defaultPic) {
        initGlide(url, imageView, defaultPic);
    }

    private static String getUrl(String url) {

        if (TextUtils.isEmpty(url)) {
            return URL_HEAD;
        }

        if (url.contains("http")) {
            return url;
        } else {
            return URL_HEAD + url;
        }
    }

    /**
     * 显示圆形头像(无默认图)
     *
     * @param url
     * @param imageView
     */
    public static void showHead(String url, ImageView imageView) {
        initGlide(url, imageView, 0);
    }

    /**
     * 显示方形
     *
     * @param url
     * @param imageView
     */
    public static void showRect(String url, ImageView imageView, int defaultPic) {
        initGlide(url, imageView, defaultPic);
    }

    /**
     * 显示方形
     *
     * @param url
     * @param imageView
     */
    public static void showRect(String url, ImageView imageView) {
        int defaultPic = R.drawable.list_empt_pic;
        initGlide(url, imageView, defaultPic);
    }

    /**
     * 显示
     *
     * @param url
     * @param imageView
     * @param defaultPic
     */
    public static void showImageView(String url, ImageView imageView, int defaultPic) {
        initGlide(url, imageView, defaultPic);
    }

    public static void showImageView(Object model, ImageView imageView) {
        int defaultPic = R.drawable.list_empt_pic;
        initGlides(model, imageView, defaultPic);
    }

    private static void initGlide(String url, ImageView imageView, int defaultPic) {
        url = getUrl(url);
        initGlides(url, imageView, defaultPic);
    }

    /**
     * 初始化Glide
     *
     * @param model
     * @param imageView
     * @param defaultPic
     */
    private static void initGlides(Object model, ImageView imageView, int defaultPic) {

        final Context context = imageView.getContext();

        RequestOptions options;

        if (defaultPic == 0) {
            options = new RequestOptions()
                    .centerCrop()
                    .dontAnimate()
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        } else {
            options = new RequestOptions()
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(defaultPic)
                    .error(defaultPic)
                    .priority(Priority.HIGH)
                    .diskCacheStrategy(DiskCacheStrategy.ALL);
        }

        Glide.with(context)
                .load(model)
                .apply(options)
                .into(imageView);
    }

}


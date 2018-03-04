package com.baselib.uitls;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

/**
 * Created by luo on 2018/1/16.
 */


public class GlideUtil {

    /**
     * 显示圆形头像
     *
     * @param url
     * @param imageView
     */
    public static void showHead(String url, ImageView imageView, int defaultPic) {
        final Context context = imageView.getContext();
        Uri uri = !TextUtils.isEmpty(url) ? Uri.parse(url) : null;
        Glide.with(context).load(uri).asBitmap().thumbnail(0.6f).centerCrop().placeholder(defaultPic).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);

                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                getView().setImageDrawable(roundedBitmapDrawable);
            }
        });
    }

    public static void showHead1(String url, ImageView imageView, int defaultPic) {
        final Context context = imageView.getContext();
        Glide.with(context).load(url).asBitmap().thumbnail(0.6f).centerCrop().placeholder(defaultPic).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);

                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                getView().setImageDrawable(roundedBitmapDrawable);
            }
        });
    }

    public static void showHead1(int url, ImageView imageView) {
        final Context context = imageView.getContext();
        Glide.with(context).load(url).asBitmap().thumbnail(0.6f).centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);

                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                getView().setImageDrawable(roundedBitmapDrawable);
            }
        });
    }

    public static void showHead(Uri uri, ImageView imageView, int defaultPic) {
        final Context context = imageView.getContext();
        Glide.with(context).load(uri).asBitmap().thumbnail(0.6f).centerCrop().placeholder(defaultPic).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                roundedBitmapDrawable.setCircular(true);
                getView().setImageDrawable(roundedBitmapDrawable);
            }
        });
    }

    /**
     * 显示方形
     *
     * @param url
     * @param imageView
     */
    public static void showRect(String url, ImageView imageView, int defaultPic) {
        Context context = imageView.getContext();
        Uri uri = !TextUtils.isEmpty(url) ? Uri.parse(url) : null;
        Glide.with(context).load(uri).asBitmap().centerCrop().placeholder(defaultPic).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                super.setResource(resource);
            }
        });
    }

    /**
     * 显示方形
     *
     * @param url
     * @param imageView
     */
    public static void showRect(String url, ImageView imageView) {
        Context context = imageView.getContext();
        Uri uri = !TextUtils.isEmpty(url) ? Uri.parse(url) : null;
        Glide.with(context).load(uri).fitCenter().into(imageView);
    }

    /**
     * 显示
     *
     * @param url
     * @param imageView
     * @param defaultPic
     */
    public static void showImageView(String url, ImageView imageView, int defaultPic) {

        Context context = imageView.getContext();
        Uri uri = !TextUtils.isEmpty(url) ? Uri.parse(url) : null;
        Glide.with(context).load(uri).centerCrop().dontAnimate().placeholder(defaultPic).into(imageView);

    }

}


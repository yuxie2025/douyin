package com.baselib.baserx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 作者: llk on 2017/9/8.
 * RxJava调度管理
 */
@SuppressWarnings("unused")
public class RxSchedulers {
    public static <T> Observable.Transformer<T, T> io_main() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

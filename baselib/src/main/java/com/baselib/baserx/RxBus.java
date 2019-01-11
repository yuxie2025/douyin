package com.baselib.baserx;

import android.support.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by llk on 2017/9/6.
 */
@SuppressWarnings("unused")
public class RxBus {
    private volatile static RxBus instance;
    private Subject bus;

    public static RxBus getInstance() {
        if (null == instance) {
            synchronized (RxBus.class) {
                instance = new RxBus();
            }
        }
        return instance;
    }

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    @SuppressWarnings("rawtypes")
    private ConcurrentHashMap<Object, List<Subject>> subjectMapper = new ConcurrentHashMap<>();

    /**
     * 订阅事件源
     */
    public RxBus OnEvent(Observable<?> mObservable, Action1<Object> mAction1) {
        mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(mAction1, Throwable::printStackTrace);
        return getInstance();
    }

    /**
     * 注册事件源
     */
    @SuppressWarnings({"rawtypes"})
    public <T> Observable<T> register(@NonNull Object tag) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
            subjectMapper.put(tag, subjectList);
        }
        Subject<T, T> subject;
        subjectList.add(subject = PublishSubject.create());
        return subject;
    }

    @SuppressWarnings("rawtypes")
    public void unregister(@NonNull Object tag) {
        List<Subject> subjects = subjectMapper.get(tag);
        if (null != subjects) {
            subjectMapper.remove(tag);
        }
    }

    /**
     * 取消监听
     */
    @SuppressWarnings("rawtypes")
    public RxBus unregister(@NonNull Object tagName,
                            @NonNull Observable<?> observable) {
        List<Subject> subjects = subjectMapper.get(tagName);
        if (subjects != null) {
            subjects.remove(observable);
            if (isEmpty(subjects)) {
                subjectMapper.remove(tagName);
                LogUtils.d("unregister" + tagName + "  size:" + subjects.size());
            }
        }
        return getInstance();
    }

    /**
     * 发送消息
     */
    public void post(Object object) {

        bus.onNext(object);

    }

    /**
     * 触发事件
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjectList = subjectMapper.get(tag);
        if (!isEmpty(subjectList)) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Collection<Subject> collection) {
        return null == collection || collection.isEmpty();
    }

    /**
     * 接收消息
     */
    public <T> Observable<T> toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);
    }
}

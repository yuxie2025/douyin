package com.yuxie.myapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yuxie.myapp.R;


public class RxjavaActivity extends AppCompatActivity {

//    private Observable<String> observable;
//    private Observer<String> observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
    }

    public void doClick(View View) {

//        //被观察者
//        observable = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                //执行一些其他操作
//                // .............
//                // 执行完毕，触发回调，通知观察者
//                e.onNext("我来发射数据");
//            }
//        });
//        //观察者
//        observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                Log.i("TAG", "onSubscribe");
//            }
//
//            @Override
//            public void onNext(String aLong) {//观察者接收到通知,进行相关操作
//                Log.i("TAG", "aLong:"+aLong);
//                Log.i("TAG", "我接收到数据了");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.i("TAG", "onError");
//            }
//
//            @Override
//            public void onComplete() {
//                Log.i("TAG", "onComplete");
//            }
//        };
//
//        observable.subscribe(observer);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        observable.unsubscribeOn()
    }
}

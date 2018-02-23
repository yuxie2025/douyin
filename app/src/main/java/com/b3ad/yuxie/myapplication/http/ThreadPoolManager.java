package com.b3ad.yuxie.myapplication.http;

import android.util.Log;

import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2017/09/11.
 */

public class ThreadPoolManager {

    private  static  ThreadPoolManager instance=new ThreadPoolManager();

    private LinkedBlockingDeque<Future> mTaskQuene=new LinkedBlockingDeque<>();


    private ThreadPoolExecutor mThreadPoolExecutor;

    public  static ThreadPoolManager getInstance(){
        return  instance;
    }
    private ThreadPoolManager(){
        mThreadPoolExecutor=new ThreadPoolExecutor(4,10,10, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(4),handler);
        mThreadPoolExecutor.execute(runnable);
    }

    public <T> void execte(FutureTask<T> futureTask) throws InterruptedException{
        mTaskQuene.add(futureTask);
    }

    private  Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while (true){
                FutureTask futureTask=null;
                try {
                    futureTask=(FutureTask)mTaskQuene.take();
                    Log.i("TAG","等待队列     "+mTaskQuene.size());
                    mThreadPoolExecutor.execute(futureTask);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("TAG","线程池大小      "+mThreadPoolExecutor.getPoolSize());
            }
        }
    };

    private RejectedExecutionHandler handler=new RejectedExecutionHandler() {
        @Override
        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            mTaskQuene.add(new FutureTask<Object>(runnable,null));
        }
    };

}

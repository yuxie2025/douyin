package com.baselib.uitls;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by luo on 2018/1/30.
 * 用于解决,java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid item position 150(offset:150).state:153 bug
 * bug原因,就是说Adapter有个size，你的集合有个size。这两个size，在调用Adapter的notifyxxxx时候必须保持相同。
 * 需调用三个参数的才有用
 */
@SuppressWarnings("unused")
public class NoBugLinearLayoutManager extends LinearLayoutManager {

    public NoBugLinearLayoutManager(Context context) {
        super(context, LinearLayoutManager.VERTICAL, false);
    }

    public NoBugLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NoBugLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            //try catch一下
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            return super.scrollVerticallyBy(dy, recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}

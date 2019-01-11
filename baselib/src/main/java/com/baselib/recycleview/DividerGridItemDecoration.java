package com.baselib.recycleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {

    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int mDividerHeight = 2;

    public DividerGridItemDecoration(Context context, int drawableId) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        //旧的getDrawable方法弃用了，这个是新的
        mDivider = ContextCompat.getDrawable(context, drawableId);
        mDividerHeight = mDivider.getMinimumHeight();
        a.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, State state) {
        drawVertical(c, parent);
    }

    /**
     * 获取列数
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {

            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager)
                    .getSpanCount();
        }
        return spanCount;
    }

    /**
     * 行与行直接画间隔线
     */
    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        final int spanCount = getSpanCount(parent);
        //最后一项后面不画
        for (int i = 0; i < childCount - 1; i++) {
            //每列的后面不画
            if ((i + 1) % spanCount == 0) {
                continue;
            }

            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                    .getLayoutParams();
            final int left = child.getRight() + params.rightMargin / 2;
            final int top = child.getTop() - params.topMargin + 50;
            final int right = left + mDivider.getIntrinsicWidth();
            final int bottom = child.getBottom() + params.bottomMargin - 50;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }


}

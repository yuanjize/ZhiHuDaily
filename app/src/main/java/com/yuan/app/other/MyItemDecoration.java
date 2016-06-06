package com.yuan.app.other;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by yjz on 2016/6/5.
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private int margin;

    public MyItemDecoration(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = margin;
    }
}

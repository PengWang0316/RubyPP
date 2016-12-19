package com.example.pengwang.rubypp.decorations;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Peng on 12/19/2016.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private static final int VERTICAL_ITEM_SPACE = 25;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.bottom= VERTICAL_ITEM_SPACE;
    }
}

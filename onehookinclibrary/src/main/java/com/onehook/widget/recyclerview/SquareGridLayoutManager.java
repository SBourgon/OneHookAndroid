package com.onehook.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

/**
 * Created by eaglediaotomore on 2016-04-14.
 */
public class SquareGridLayoutManager extends GridLayoutManager {

    public interface ISquareGridLayoutSizeOffsetLookup {
        int getOffsetForView(final View view, final int adapterPosition);
    }

    private ISquareGridLayoutSizeOffsetLookup mOffsetLookup;

    public SquareGridLayoutManager(Context context, int spanCount, int orientation,
                                   boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void setSizeOffsetLookup(final ISquareGridLayoutSizeOffsetLookup lookup) {
        mOffsetLookup = lookup;
    }


    @Override
    public int getDecoratedMeasuredHeight(View child) {
        int index = -1;
        for (int i = 0; i < getItemCount(); i++) {
            if (child == findViewByPosition(i)) {
                index = i;
                break;
            }
        }
        int spanSize = 1;
        if (index >= 0) {
            spanSize = getSpanSizeLookup().getSpanSize(index);
        }

        int offset = 0;
        if(mOffsetLookup != null) {
            offset = mOffsetLookup.getOffsetForView(child, index);
        }

        final int height = getDecoratedMeasuredWidth(child);
        child.measure(View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(height / spanSize + offset, View.MeasureSpec.EXACTLY));

        return height / spanSize + offset;
    }

}

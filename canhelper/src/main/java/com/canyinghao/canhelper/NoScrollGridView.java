package com.canyinghao.canhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 不可滚动的GridView
 * @author canyinghao
 *
 */
public class NoScrollGridView extends GridView {
    public NoScrollGridView(Context context) {
        super(context);
    }

    public NoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}

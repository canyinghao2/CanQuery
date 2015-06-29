package com.canyinghao.canhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

/**
 * 不可滚动的GridView
 * @author canyinghao
 *
 */
public class CustomNoScrollGridView extends LinearLayout {
    private BaseAdapter adapter;
    private int numColumns = 2;
    private int spacing = 2;
    private Context context;

    public CustomNoScrollGridView(Context context) {
        super(context);
        this.context = context;
        removeAllViews();
        setOrientation(LinearLayout.VERTICAL);
    }

    public CustomNoScrollGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        removeAllViews();
        setOrientation(LinearLayout.VERTICAL);

    }

    public void setAdapter(BaseAdapter adapter) {
        removeAllViews();
        this.adapter = adapter;
        initView();

    }

    public void setSpaceing(int spacing) {
        this.spacing = spacing;

    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    private void initView() {

        int count = adapter.getCount();

        int size = (int) (count / (double) numColumns + 0.9999);
        int m = 0;

        for (int i = 0; i < size; i++) {
            int columns = numColumns;
            if (i == size - 1) {
                columns = count - i * numColumns;
            }
            LinearLayout ll = new LinearLayout(context);

            ll.setOrientation(LinearLayout.HORIZONTAL);
            ll.setWeightSum(numColumns);
            ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
            for (int j = 0; j < columns; j++) {

                View view = adapter.getView(m, null, ll);

                LayoutParams params = new LayoutParams(0,
                        LayoutParams.WRAP_CONTENT, 1);
                params.setMargins(spacing, spacing, spacing, spacing);
                view.setLayoutParams(params);

                ll.addView(view);

                m++;

            }

            addView(ll);

        }

    }

}

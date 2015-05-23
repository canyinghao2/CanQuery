package com.canyinghao.canhelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 用FrameLayout自定义一个带有EmptyView的ListView
 * @author canyinghao
 *
 */
public class EmptyListView extends FrameLayout {

    private ListView listView;
    private LinearLayout ll1;
    private LinearLayout ll2;

    public ListView getListView() {
        return listView;
    }

    public EmptyListView(Context context) {
        super(context);
        addListView();
    }

    public EmptyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addListView();
    }

    public EmptyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addListView();
    }

    private void addListView() {

        listView = new ListView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        listView.setLayoutParams(params);
        addView(listView);

    }

    public void setProgressView(String str) {

        setProgressView(null, str);

    }

    public void setProgressView(View animeView, String str) {

        ll1 = new LinearLayout(getContext());
        ll1.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        ll1.setGravity(Gravity.CENTER);
        ll1.setLayoutParams(params);
        if (animeView == null) {
        	animeView = new ProgressBar(getContext());
        }

        TextView textView = new TextView(getContext());

        textView.setText(str);
        textView.setGravity(Gravity.CENTER);

        ll1.addView(animeView);
        ll1.addView(textView);
        if (ll1 != null) {
            removeView(ll1);
        }
        if (ll2 != null) {

            removeView(ll2);

        }
        addView(ll1);
        listView.setEmptyView(ll1);

    }

    public void setEmptyView(String str) {
        setEmptyView(-1, str,null);

    }

    public void setEmptyView(int rid, String str,OnClickListener click) {


        ll2 = new LinearLayout(getContext());
        ll2.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        ll2.setGravity(Gravity.CENTER);
        ll2.setLayoutParams(params);
        if (click!=null) {
            ll2.setOnClickListener(click);
        }

        if (rid != -1) {

            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(rid);
            ll2.addView(imageView);
        }
        TextView textView = new TextView(getContext());
        textView.setText(str);
        textView.setGravity(Gravity.CENTER);

        ll2.addView(textView);

        if (ll1 != null) {
            removeView(ll1);
        }
        if (ll2 != null) {
            removeView(ll2);
        }

        addView(ll2);

        listView.setEmptyView(ll2);

    }

}

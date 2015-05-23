package com.canyinghao.canhelper;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * @author canyinghao
 *
 * CanBaseAdapter的帮助类，用来缓存view并提供一些view相关方法。
 * @param <T>
 */
public class AdapterHelper<T> {

    private SparseArray<View> views;
    private Context context;

    private List<T> list;

    private View v;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public View getV() {
        return v;
    }

    public void setV(View v) {
        this.v = v;
    }

    public AdapterHelper() {
        super();
        views = new SparseArray<View>();
    }

    public AdapterHelper(Context context, List<T> list, View v) {
        super();
        this.context = context;
        this.list = list;
        this.v = v;
        views = new SparseArray<View>();
    }

    public void setTextViewText(int rid, CharSequence string) {
        View view = views.get(rid);
        if (view != null && view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setText(string);

        } else {
            views.put(rid, v.findViewById(rid));
        }

    }

    public void setImageViewResource(int rid, int resid) {
        View view = views.get(rid);
        if (view != null && view instanceof ImageView) {
            ImageView iv = (ImageView) view;
            iv.setImageResource(resid);

        } else {
            views.put(rid, v.findViewById(rid));
        }

    }

    public void setViewBackgroundResource(int rid, int resid) {
        View view = views.get(rid);
        if (view != null) {

            view.setBackgroundResource(resid);

        } else {
            views.put(rid, v.findViewById(rid));
        }

    }

    public View getViewById(int rid) {
        View view = views.get(rid);
        if (view == null) {
            view = v.findViewById(rid);
        }
        return view;

    }

}

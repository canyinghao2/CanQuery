package com.canyinghao.canhelper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 一个快速构建的Adapter
 * @author canyinghao
 *
 * @param <T>
 */
public abstract class CanBaseAdapter<T> extends BaseAdapter {

    public Context context;
    public List<T> list;
    public AdapterHelper<T> helper;
    public int rid;

    public CanBaseAdapter(Context c, int rid,List<T> list) {
        context = c;
        this.list = list;
        this.rid=rid;

        helper = new AdapterHelper<T>(c, list, null);
    }

    public void clear() {
        list = new ArrayList<T>();
        notifyDataSetChanged();
    }

    public void addAll(List<T> data) {

        if (list == null) {
            list = new ArrayList<T>();
        }
        list.addAll(data);
        notifyDataSetChanged();
    }

    public void refreshView(List<T> data) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        list = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v==null&&rid!=-1) {
             v = LayoutInflater.from(context).inflate(rid, null);
            helper.setV(v);
        }

        initViewItem(helper, position,v);



        return v;
    }

    protected abstract void initViewItem(AdapterHelper<T> helper, int position,View v);
}

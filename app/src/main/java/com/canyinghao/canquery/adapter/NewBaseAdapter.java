package com.canyinghao.canquery.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

public abstract class NewBaseAdapter<T> extends BaseAdapter {

	public Context context;
	public List<T> list;
	public LayoutInflater inflater;

	public NewBaseAdapter(Context c, List<T> list) {
		context = c;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	public void clear() {
		list = new ArrayList<T>();
		notifyDataSetChanged();
	}

	public void addAll(List data) {

		if (list == null) {
			list = new ArrayList<T>();
		}
		list.addAll(data);
		notifyDataSetChanged();
	}

	public void refreshView(List data) {
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
	public long getItemId(int arg0) {

		return 0;
	}

}

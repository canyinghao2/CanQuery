package com.canyinghao.canquery.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.model.ExpressageInfo;

import java.util.List;

public class ExpressageAdapter extends NewBaseAdapter {

	public ExpressageAdapter(Context c, List list) {
		super(c, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		
		if (v==null) {
			ViewCache cache = new ViewCache();
			v=inflater.inflate(R.layout.query_expressage_result_list_item, null);
			
			cache.line_1=v.findViewById(R.id.line_1);
			cache.line_2=v.findViewById(R.id.line_2);
			cache.circle_gray=v.findViewById(R.id.circle_gray);
			cache.circle_green=v.findViewById(R.id.circle_green);
			cache.tv_message=(TextView) v.findViewById(R.id.tv_message);
			cache.tv_time=(TextView) v.findViewById(R.id.tv_time);
			
			v.setTag(cache);
		}
		
		ViewCache cache=(ViewCache) v.getTag();
		
		if (position==0) {
			cache.line_1.setVisibility(View.INVISIBLE);
		}else {
			cache.line_1.setVisibility(View.VISIBLE);
		}
		
		
		
		ExpressageInfo info =  (ExpressageInfo) list.get(position);
		cache.tv_message.setText(info.getRemark());
		cache.tv_time.setText(info.getDatetime());
		
		if (position==0) {
			cache.circle_green.setVisibility(View.VISIBLE);
			cache.circle_gray.setVisibility(View.GONE);
			int color = context.getResources().getColor(R.color.blue_gray_500);
			cache.tv_message.setTextColor(color);
			cache.tv_time.setTextColor(color);
		}else {
			cache.circle_green.setVisibility(View.GONE);
			cache.circle_gray.setVisibility(View.VISIBLE);
			cache.tv_message.setTextColor(Color.BLACK);
			cache.tv_time.setTextColor(Color.BLACK);
		}
	
		return v;
	}

	class ViewCache{
		
		private View line_1;
		private View line_2;
		private View circle_green;
		private View circle_gray;
		private TextView tv_message;
		private TextView tv_time;
		
		
	}
}

package com.canyinghao.canquery.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canyinghao.canquery.model.BoxOfficeInfo;
import com.canyinghao.canquery.R;

public class BoxOfficeAdapter extends NewBaseAdapter{
	public BoxOfficeAdapter(Context c, List list) {
		super(c, list);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
		
		if (v==null) {
			ViewCache cache = new ViewCache();
			v=inflater.inflate(R.layout.query_box_office_list_item, null);
			
		
			cache.tv_1=(TextView) v.findViewById(R.id.tv_1);
			cache.tv_2=(TextView) v.findViewById(R.id.tv_2);
			cache.tv_3=(TextView) v.findViewById(R.id.tv_3);
			cache.tv_4=(TextView) v.findViewById(R.id.tv_4);
			cache.tv_5=(TextView) v.findViewById(R.id.tv_5);
			
			v.setTag(cache);
		}
		
		ViewCache cache=(ViewCache) v.getTag();
		BoxOfficeInfo info=(BoxOfficeInfo) list.get(position);
		cache.tv_1.setText(info.getName());
		cache.tv_2.setText(info.getWk());
		cache.tv_3.setText(info.getWboxoffice());
		cache.tv_4.setText(info.getTboxoffice());
		cache.tv_5.setText("ON."+(position+1));
		if (position<3) {
			cache.tv_5.setBackgroundResource(R.drawable.community_bluegray_round_shape);
		}else {
			cache.tv_5.setBackgroundResource(R.drawable.community_gray_round_shape);
		}
		
		
		
		
	
		return v;
	}

	class ViewCache{
		
	
		private TextView tv_1;
		private TextView tv_2;
		private TextView tv_3;
		private TextView tv_4;
		private TextView tv_5;
		
		
		
	}
}

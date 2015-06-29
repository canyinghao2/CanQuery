package com.canyinghao.canquery.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canyinghao.canquery.model.PostcodeCityInfo;
import com.canyinghao.canquery.adapter.NewBaseAdapter;
import com.canyinghao.canquery.R;

public class PostCodeCityAdapter extends NewBaseAdapter {
	private int type;
	
	public PostCodeCityAdapter(Context c, final List list,int type) {
		super(c, list);

		this.type=type;

	}

	@Override
	public View getView(final int position, View v, ViewGroup arg2) {

		if (v == null) {

			ViewCache cache = new ViewCache();
			v = inflater.inflate(R.layout.community_location_city_item, null);

			cache.tv_city = (TextView) v.findViewById(R.id.tv_city);

			cache.sort_key = (TextView) v.findViewById(R.id.alpha);

			v.setTag(cache);
		}
		ViewCache cache = (ViewCache) v.getTag();
		cache.sort_key.setVisibility(View.GONE);
		String text="";
		String id="";
		
		switch (type) {
		case 0:
			PostcodeCityInfo info = (PostcodeCityInfo) list.get(position);
			text=info.getProvince();
			id=info.getId();
			break;
		case 1:
			PostcodeCityInfo.City city = (PostcodeCityInfo.City) list.get(position);
			text=city.getCity();
			id=city.getId();
			break;
		case 2:
			PostcodeCityInfo.City.District district = (PostcodeCityInfo.City.District) list.get(position);
			text=district.getDistrict();
			id=district.getId();
			break;

		}
		cache.tv_city.setText(text);
		final String textS=text;
		final String idS=id;
		cache.tv_city.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {

				Activity ac = (Activity) context;

				Intent intent = new Intent();
				intent.putExtra("text", textS);
				intent.putExtra("id", idS);
				intent.putExtra("type", type);
				intent.putExtra("position", position);
				ac.setResult(100, intent);
				ac.finish();
				
				
				
			}
		});
		
		
		
		return v;

	}

	class ViewCache {

		TextView tv_city;
		TextView sort_key;
	}

	
}

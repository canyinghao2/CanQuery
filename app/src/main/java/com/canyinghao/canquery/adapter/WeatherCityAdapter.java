package com.canyinghao.canquery.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.weather.WeatherCity2DisQueryActivity;
import com.canyinghao.canquery.activity.weather.WeatherSelectCityQueryActivity;
import com.canyinghao.canquery.model.PostcodeCityInfo;
import com.canyinghao.canquery.model.WeatherCityInfo;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;

public class WeatherCityAdapter extends NewBaseAdapter {

	private int type;

	public WeatherCityAdapter(Context c, final List list, int type) {
		super(c, list);

		this.type = type;
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
		String text = "";
		switch (type) {
		case 0:
			text = (String) list.get(position);

			break;
		case 1:
			PostcodeCityInfo info = (PostcodeCityInfo) list.get(position);
			text = info.getProvince();
			break;
		case 2:
			PostcodeCityInfo.City city = (PostcodeCityInfo.City) list
					.get(position);
			text = city.getCity();
			break;
		case 3:
			PostcodeCityInfo.City.District district = (PostcodeCityInfo.City.District) list
					.get(position);
			text = district.getDistrict();
			break;

		}
		cache.tv_city.setText(text);

		final String city = text;
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch (type) {
				case 0:
					try {
						String rCity = city;
						if (city.contains("市")) {
							rCity = city.replace("市", "");
						}
						if (city.contains("区")) {
							rCity = city.replace("区", "");
						}
						if (city.contains("县")) {
							rCity = rCity.replace("县", "");
						}
						WeatherCityInfo cityInfo = DbUtils.create(
								App.getContext()).findFirst(
								Selector.from(WeatherCityInfo.class).where(
										"city", "=", rCity));
						if (cityInfo == null) {
							cityInfo = new WeatherCityInfo();
							cityInfo.setRid(R.drawable.query_weather_cloudy);
							cityInfo.setWeather("阴");
							
							cityInfo.setCity(rCity);
							DbUtils.create(App.getContext()).save(cityInfo);
						}
						EventBus.getDefault().post(new Intent(
								WeatherSelectCityQueryActivity.TAG));
						Activity ac = (Activity) context;
						ac.finish();

					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
				case 1:


                  

                    PostcodeCityInfo info = (PostcodeCityInfo) list
							.get(position);
					IntentHelper.getInstance().showIntent(
							context,
							WeatherCity2DisQueryActivity.class,
							new String[] { "type", "list" },
							new Serializable[] { 1,
									(Serializable) info.getCity() });

					break;

				}

			}
		});

		return v;

	}

	class ViewCache {

		TextView tv_city;
		TextView sort_key;
	}

}

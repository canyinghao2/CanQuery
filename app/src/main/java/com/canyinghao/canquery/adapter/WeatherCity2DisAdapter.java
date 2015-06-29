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
import com.canyinghao.canquery.model.PostcodeCityInfo.City.District;
import com.canyinghao.canquery.model.WeatherCityInfo;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;

public class WeatherCity2DisAdapter extends NewBaseAdapter {
	private int type;

	public WeatherCity2DisAdapter(Context c, final List list, int type) {
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
		String id = "";

		switch (type) {
		case 0:
			PostcodeCityInfo info = (PostcodeCityInfo) list.get(position);
			text = info.getProvince();
			id = info.getId();
			break;
		case 1:
			PostcodeCityInfo.City city = (PostcodeCityInfo.City) list
					.get(position);
			text = city.getCity();
			id = city.getId();
			break;
		case 2:
			PostcodeCityInfo.City.District district = (PostcodeCityInfo.City.District) list
					.get(position);
			text = district.getDistrict();
			id = district.getId();
			break;

		}
		cache.tv_city.setText(text);

		cache.tv_city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				switch (type) {
				case 1:




                    PostcodeCityInfo.City city = (PostcodeCityInfo.City) list
							.get(position);

					List<District> district2 = city.getDistrict();
					PostcodeCityInfo postcodeCityInfo = new PostcodeCityInfo();
					PostcodeCityInfo.City city2 = postcodeCityInfo.new City();
					District dis = city2.new District();
					dis.setDistrict(city.getCity());
					dis.setId(city.getId());
					district2.add(0, dis);
					IntentHelper.getInstance().showIntent(
							context,
							WeatherCity2DisQueryActivity.class,
							new String[] { "type", "list" },
							new Serializable[] { 2,
									(Serializable) city.getDistrict() });

					break;
				case 2:

					PostcodeCityInfo.City.District district = (PostcodeCityInfo.City.District) list
							.get(position);
					String rCity = district.getDistrict();
					try {
						if (rCity.contains("市")) {
							rCity = rCity.replace("市", "");
						}
						if (rCity.contains("区")) {
							rCity = rCity.replace("区", "");
						}
						if (rCity.contains("县")) {
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
						EventBus.getDefault().post(
								new Intent(WeatherCity2DisQueryActivity.TAG));

						EventBus.getDefault().post(
								new Intent(WeatherSelectCityQueryActivity.TAG));
						Activity ac = (Activity) context;

						ac.finish();
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

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

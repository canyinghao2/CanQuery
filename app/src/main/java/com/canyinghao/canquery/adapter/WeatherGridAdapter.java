package com.canyinghao.canquery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.weather.WeatherDetailQueryActivity;
import com.canyinghao.canquery.configs.QueryConfigs;
import com.canyinghao.canquery.model.WeatherInfo;

import java.io.Serializable;
import java.util.List;

public class WeatherGridAdapter extends NewBaseAdapter {

	private int type;
	private WeatherInfo info;

	public WeatherGridAdapter(Context c, List list, int type, WeatherInfo info) {
		super(c, list);
		this.type = type;
		this.info = info;
	}

	@Override
	public View getView(final int position, View v, ViewGroup arg2) {

		if (v == null) {
			ViewCache cache = new ViewCache();
			v = LayoutInflater.from(context).inflate(
					R.layout.query_weather_grid_item1, null);
			cache.tv1 = (TextView) v.findViewById(R.id.tv_item1);
			cache.tv2 = (TextView) v.findViewById(R.id.tv_item2);
			cache.iv1 = (ImageView) v.findViewById(R.id.iv_item1);
			cache.iv2 = (ImageView) v.findViewById(R.id.iv_item2);

			v.setTag(cache);

		}
		ViewCache cache = (ViewCache) v.getTag();
		final WeatherInfo.Data.Weather weather = (WeatherInfo.Data.Weather) list.get(position);
		switch (type) {
		case 0:
			if (position == 0) {
				cache.tv1.setText("今天");
			} else {
				cache.tv1.setText("周" + weather.getWeek());
			}

			List<String> day = weather.getInfo().getDay();
			if (day != null && day.size() >= 6) {
				cache.tv2.setText(day.get(1));
				int weatherRid = QueryConfigs.getWeatherRid(day.get(0));
				cache.iv2.setImageResource(weatherRid);
				cache.iv1.setVisibility(View.GONE);
			}
			break;
		case 1:

			List<String> day2 = weather.getInfo().getDay();
			if (day2 != null && day2.size() >= 6) {
				cache.tv1.setText(day2.get(1));

				int weatherRid = QueryConfigs.getWeatherRid(day2.get(0));
				cache.iv1.setImageResource(weatherRid);
				cache.iv2.setVisibility(View.GONE);
				List<String> night = weather.getInfo().getNight();
				if (night != null && night.size() >= 6) {
					cache.tv2.setText(day2.get(2) + "/" + night.get(2));
				}
			}

			break;

		case 2:
			if (position == 0) {
				cache.tv1.setText("明天");
			} else {
				cache.tv1.setText("周" + weather.getWeek());
			}
			List<String> day3 = weather.getInfo().getDay();
			if (day3 != null && day3.size() >= 6) {
				cache.tv2.setText(day3.get(1));
				int weatherRid = QueryConfigs.getWeatherRid(day3.get(0));
				cache.iv2.setImageResource(weatherRid);
				cache.iv1.setVisibility(View.GONE);
			}
			break;

		}

		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {



				AnimeHepler.getInstance().startAnimation(context, view, R.anim.small_2_big,
						new OnAnimEnd() {

							

							@Override
							public void end() {



								IntentHelper.getInstance().showIntent(context,
										WeatherDetailQueryActivity.class,
										new String[] { "info", "date" },
										new Serializable[] { info, weather.getDate() });

							}
						});

			}
		});

		return v;
	}

	class ViewCache {
		TextView tv1;
		TextView tv2;
		ImageView iv1;
		ImageView iv2;

	}

}

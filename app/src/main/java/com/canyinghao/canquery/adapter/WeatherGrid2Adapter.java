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
import com.canyinghao.canquery.activity.weather.WeatherTodayQueryActivity;
import com.canyinghao.canquery.model.WeatherExpInfo;
import com.canyinghao.canquery.model.WeatherInfo;

import java.io.Serializable;
import java.util.List;

public class WeatherGrid2Adapter extends NewBaseAdapter {

	private WeatherInfo info;

	public WeatherGrid2Adapter(Context c, List list, WeatherInfo info) {
		super(c, list);
		this.info = info;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {

		if (v == null) {
			ViewCache cache = new ViewCache();
			v = LayoutInflater.from(context).inflate(
					R.layout.query_weather_grid_item2, null);
			cache.tv1 = (TextView) v.findViewById(R.id.tv_item1);
			cache.tv2 = (TextView) v.findViewById(R.id.tv_item2);
			cache.iv1 = (ImageView) v.findViewById(R.id.iv_item1);
			cache.iv2 = (ImageView) v.findViewById(R.id.iv_item2);

			v.setTag(cache);

		}
		ViewCache cache = (ViewCache) v.getTag();
		final WeatherExpInfo winfo = (WeatherExpInfo) list.get(position);
		cache.iv1.setImageResource(winfo.getRid());
		cache.tv1.setText(winfo.getTitle());
		List<String> list2 = winfo.getList();
		if (list2 != null && list2.size() >= 2) {
			cache.tv2.setText(list2.get(0));
		}

		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {



				AnimeHepler.getInstance().startAnimation(context, view, R.anim.small_2_big,
						new OnAnimEnd() {

							
							@Override
							public void end() {



								IntentHelper.getInstance().showIntent(context,
										WeatherTodayQueryActivity.class,
										new String[] { "info", "exp" },
										new Serializable[] { info, winfo });

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

package com.canyinghao.canquery.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.flight.FlightDetailQueryActivity;
import com.canyinghao.canquery.model.FlightInfo;

import java.io.Serializable;
import java.util.List;

public class FlightListAdapter extends NewBaseAdapter {

	public FlightListAdapter(Context c, List list) {
		super(c, list);

	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {

		if (v == null) {

			ViewCache cache = new ViewCache();
			v = inflater.inflate(R.layout.query_flight_list_item, null);

			cache.tv_name = (TextView) v.findViewById(R.id.tv_name);
			cache.tv_complany = (TextView) v.findViewById(R.id.tv_complany);
			cache.tv_AirVoyage = (TextView) v.findViewById(R.id.tv_AirVoyage);
			cache.tv_AirModel = (TextView) v.findViewById(R.id.tv_AirModel);
			cache.tv_AirAge = (TextView) v.findViewById(R.id.tv_AirAge);
			cache.tv_DepAirport = (TextView) v.findViewById(R.id.tv_DepAirport);
			cache.tv_ArrAirport = (TextView) v.findViewById(R.id.tv_ArrAirport);
			cache.tv_status = (TextView) v.findViewById(R.id.tv_status);
			cache.tv_FlyTime = (TextView) v.findViewById(R.id.tv_FlyTime);
			cache.tv_food = (TextView) v.findViewById(R.id.tv_food);
			cache.tv_DepWeather = (TextView) v.findViewById(R.id.tv_DepWeather);
			cache.tv_ArrWeather = (TextView) v.findViewById(R.id.tv_ArrWeather);
			cache.tv_DepTime = (TextView) v.findViewById(R.id.tv_DepTime);
			cache.tv_ArrTime = (TextView) v.findViewById(R.id.tv_ArrTime);
			cache.tv_Dexpected = (TextView) v.findViewById(R.id.tv_Dexpected);
			cache.tv_Aexpected = (TextView) v.findViewById(R.id.tv_Aexpected);
			cache.tv_Dactual = (TextView) v.findViewById(R.id.tv_Dactual);
			cache.tv_Aactual = (TextView) v.findViewById(R.id.tv_Aactual);
			cache.tv_DepDelay = (TextView) v.findViewById(R.id.tv_DepDelay);
			cache.tv_ArrDelay = (TextView) v.findViewById(R.id.tv_ArrDelay);
			cache.tv_ArrDelay = (TextView) v.findViewById(R.id.tv_ArrDelay);
			cache.tv_airplan = (TextView) v.findViewById(R.id.tv_airplan);
			cache.tv_plantime = (TextView) v.findViewById(R.id.tv_plantime);

			cache.tr_AirModel = v.findViewById(R.id.tr_AirModel);
			cache.tr_FlyTime = v.findViewById(R.id.tr_FlyTime);
			cache.tr_Weather = v.findViewById(R.id.tr_Weather);
			cache.tr_DepTime = v.findViewById(R.id.tr_DepTime);
			cache.tr_Dexpected = v.findViewById(R.id.tr_Dexpected);
			cache.tr_Dactual = v.findViewById(R.id.tr_Dactual);
			cache.tr_Delay = v.findViewById(R.id.tr_Delay);
			cache.tr_OnTimeRate = v.findViewById(R.id.tr_OnTimeRate);
			cache.tr_status = v.findViewById(R.id.tr_status);
			cache.tr_food = v.findViewById(R.id.tr_food);

			v.setTag(cache);
		}
		ViewCache cache = (ViewCache) v.getTag();

		final FlightInfo info = (FlightInfo) list.get(position);
		cache.tr_OnTimeRate.setVisibility(View.GONE);
		cache.tr_AirModel.setVisibility(View.GONE);
		cache.tr_Weather.setVisibility(View.GONE);
		cache.tr_Dexpected.setVisibility(View.GONE);
		cache.tr_Dactual.setVisibility(View.GONE);
		cache.tr_Delay.setVisibility(View.GONE);
		cache.tr_FlyTime.setVisibility(View.GONE);
		cache.tr_status.setVisibility(View.GONE);
		cache.tr_food.setVisibility(View.GONE);
		
		if (TextUtils.isEmpty(info.getDepTime())
				&& TextUtils.isEmpty(info.getArrTime())) {
			cache.tr_DepTime.setVisibility(View.GONE);
		} else {
			cache.tr_DepTime.setVisibility(View.VISIBLE);
		}

		cache.tv_name.setText(info.getName());
		cache.tv_complany.setText(info.getComplany());
//		cache.tv_AirVoyage.setText(info.getAirVoyage());
		if (TextUtils.isEmpty(info.getFlyTime())) {
			cache.tv_AirVoyage.setText("");
		}else {
			cache.tv_AirVoyage.setText("耗时"+info.getFlyTime() + "分钟");
		}
		
		cache.tv_AirModel.setText(info.getAirModel());
		cache.tv_AirAge.setText(info.getAirAge());

		cache.tv_DepAirport.setText(info.getDepAirport()
				+ info.getDepTerminal());
		cache.tv_ArrAirport.setText(info.getArrAirport()
				+ info.getArrTerminal());
		cache.tv_status.setText(info.getStatus());
		cache.tv_FlyTime.setText(info.getFlyTime() + "分钟");
		cache.tv_food.setText(info.getFood().equals("1") ? "有" : "无");
		cache.tv_DepWeather.setText(info.getDepWeather());
		cache.tv_ArrWeather.setText(info.getArrWeather());
		cache.tv_DepTime.setText(info.getDepTime());
		cache.tv_ArrTime.setText(info.getArrTime());

		cache.tv_Dexpected.setText(info.getDexpected());
		cache.tv_Aexpected.setText(info.getAexpected());
		cache.tv_Dactual.setText(info.getDactual());
		cache.tv_Aactual.setText(info.getAactual());
		cache.tv_DepDelay.setText(info.getDepDelay());
		cache.tv_ArrDelay.setText(info.getArrDelay());

        int color=context.getResources().getColor(R.color.blue_gray_500);

        cache.tv_AirModel.setTextColor(color);
        cache.tv_AirAge.setTextColor(color);
        cache.tv_ArrAirport.setTextColor(color);
        cache.tv_status.setTextColor(color);
        cache.tv_FlyTime.setTextColor(color);
        cache.tv_food.setTextColor(color);
        cache.tv_DepWeather.setTextColor(color);
        cache.tv_ArrWeather.setTextColor(color);
        cache.tv_DepTime.setTextColor(color);
        cache.tv_ArrTime.setTextColor(color);
        cache.tv_Dexpected.setTextColor(color);
        cache.tv_Aexpected.setTextColor(color);
        cache.tv_Dactual.setTextColor(color);
        cache.tv_Aactual.setTextColor(color);
        cache.tv_DepDelay.setTextColor(color);
        cache.tv_Aactual.setTextColor(color);
        cache.tv_ArrDelay.setTextColor(color);
        cache.tv_name.setTextColor(color);
        cache.tv_complany.setTextColor(color);
        cache.tv_AirVoyage.setTextColor(color);
        cache.tv_ArrDelay.setTextColor(color);
        cache.tv_airplan.setTextColor(color);
        cache.tv_plantime.setTextColor(color);
        cache.tv_DepAirport.setTextColor(color);


		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {


               				IntentHelper.getInstance().showIntent(context, FlightDetailQueryActivity.class,
						new String[] { "info" }, new Serializable[] { info });

			}
		});
		return v;

	}

	class ViewCache {
		private TextView tv_name;
		private TextView tv_complany;
		private TextView tv_AirVoyage;
		private TextView tv_AirModel;
		private TextView tv_AirAge;
		private TextView tv_DepAirport;
		private TextView tv_ArrAirport;
		private TextView tv_status;
		private TextView tv_FlyTime;
		private TextView tv_food;
		private TextView tv_DepWeather;
		private TextView tv_ArrWeather;
		private TextView tv_DepTime;
		private TextView tv_ArrTime;
		private TextView tv_Dexpected;
		private TextView tv_Aexpected;
		private TextView tv_Dactual;
		private TextView tv_Aactual;
		private TextView tv_DepDelay;
		private TextView tv_ArrDelay;
		private TextView tv_airplan;
		private TextView tv_plantime;
		private View tr_OnTimeRate;
		private View tr_AirModel;
		private View tr_FlyTime;
		private View tr_Weather;
		private View tr_DepTime;
		private View tr_Dexpected;
		private View tr_Dactual;
		private View tr_Delay;
		private View tr_status;
		private View tr_food;

	}

}

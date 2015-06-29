package com.canyinghao.canquery.activity.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.WeatherGridAdapter;
import com.canyinghao.canquery.configs.QueryConfigs;
import com.canyinghao.canquery.model.WeatherExpInfo;
import com.canyinghao.canquery.model.WeatherInfo;
import com.canyinghao.canquery.model.WeatherInfo.Data.Weather;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WeatherTodayQueryActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.tv_item1)
	 TextView tv_item1;
	@InjectView(R.id.tv_item2)
	 TextView tv_item2;
	@InjectView(R.id.iv_item1)
	 ImageView iv_item1;
	@InjectView(R.id.tv_exp1)
	 TextView tv_exp1;
	@InjectView(R.id.tv_exp2)
	 TextView tv_exp2;
	@InjectView(R.id.iv_exp)
	 ImageView iv_exp;

	@InjectView(R.id.gridview1)
	 GridView gridview1;

	 final String TEMP = "℃";
	 WeatherInfo info;
	 WeatherExpInfo expInfo;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_weather_today_activity);
		ButterKnife.inject(this);

		getIntentData();
		setView();
	}

	 void getIntentData() {
		info = new WeatherInfo();
		Intent intent = getIntent();
		if (intent.hasExtra("info")) {
			info = (WeatherInfo) intent.getSerializableExtra("info");
		}
		if (intent.hasExtra("exp")) {
			expInfo = (WeatherExpInfo) intent.getSerializableExtra("exp");
		}
		

	}

	 void setView() {


         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", info.getData().getRealtime().getCity_name(), new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();



             }
         },null);
		List<Weather> weather = info.getData().getWeather();
		int weatherRid = QueryConfigs.getWeatherRid(weather.get(0).getInfo()
				.getDay().get(0));
		iv_item1.setImageResource(weatherRid);

		tv_item1.setText(weather.get(0).getInfo().getDay().get(2) + "/"
				+ weather.get(0).getInfo().getNight().get(2) + TEMP);
		tv_item2.setText(weather.get(0).getDate() + " | 周"
				+ weather.get(0).getWeek());

		iv_exp.setImageResource(expInfo.getRid());
		tv_exp1.setText(expInfo.getTitle() + "   " + expInfo.getList().get(0));
		tv_exp2.setText(expInfo.getList().get(1));

		List<Weather> list = new ArrayList<Weather>();
		list.add(weather.get(1));
		list.add(weather.get(2));
		list.add(weather.get(3));
		list.add(weather.get(4));
		WeatherGridAdapter adapter1 = new WeatherGridAdapter(context, list, 2,info);
		gridview1.setAdapter(adapter1);

	}

}

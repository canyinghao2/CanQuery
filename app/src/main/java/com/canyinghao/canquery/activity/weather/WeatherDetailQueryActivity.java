package com.canyinghao.canquery.activity.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.ViewPagerAdapter;
import com.canyinghao.canquery.configs.QueryConfigs;
import com.canyinghao.canquery.model.WeatherInfo;
import com.canyinghao.canquery.model.WeatherInfo.Data.Weather;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WeatherDetailQueryActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
	@InjectView(R.id.rg)
	 RadioGroup rg;
	@InjectView(R.id.pager)
	 ViewPager pager;

	 WeatherInfo info;
	 String date;
	 final String TEMP = "℃";

	@InjectView(R.id.iv_day)
	 ImageView iv_day;
	@InjectView(R.id.tv_day1)
	 TextView tv_day1;
	@InjectView(R.id.tv_day2)
	 TextView tv_day2;
	@InjectView(R.id.tv_day3)
	 TextView tv_day3;
	@InjectView(R.id.iv_night)
	 ImageView iv_night;
	@InjectView(R.id.tv_night1)
	 TextView tv_night1;
	@InjectView(R.id.tv_night2)
	 TextView tv_night2;
	@InjectView(R.id.tv_night3)
	 TextView tv_night3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_weather_detail_activity);
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

		if (intent.hasExtra("date")) {
			date = intent.getStringExtra("date");
		}

	}

	 void setView() {


         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", info.getData().getRealtime().getCity_name(), new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();



             }
         },null);

		initView();
	}

	 void initView() {

		final List<Weather> weather = info.getData().getWeather();

		List<View> vList = new ArrayList<View>();

		for (int i = 0; i < weather.size(); i++) {
			Weather weather2 = weather.get(i);
			RadioButton rb = (RadioButton) rg.getChildAt(i);

			if (i == 0) {
				rb.setText("今天");
				rb.setChecked(true);
			} else {
				rb.setText("周" + weather2.getWeek());
				rb.setChecked(false);
			}

			final int m = i;
			rb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(
						CompoundButton paramCompoundButton, boolean flag) {
					if (flag) {
						pager.setCurrentItem(m);
					}

				}
			});

			LinearLayout ll = new LinearLayout(context);
			ll.setOrientation(LinearLayout.VERTICAL);
			ll.setGravity(Gravity.CENTER);
			ll.setLayoutParams(new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.MATCH_PARENT));
			TextView tv1 = new TextView(context);
			TextView tv2 = new TextView(context);
			TextView tv3 = new TextView(context);
			tv1.setGravity(Gravity.CENTER);
			tv2.setGravity(Gravity.CENTER);
			tv3.setGravity(Gravity.CENTER);
			tv1.setText(weather2.getInfo().getDay().get(2) + "/"
					+ weather2.getInfo().getNight().get(2) + TEMP);
			tv1.setTextColor(getResources().getColor(R.color.white));
			tv1.setTextSize(50);
			tv2.setText(weather2.getInfo().getDay().get(1));
			tv2.setTextColor(getResources().getColor(R.color.white));
			tv2.setTextSize(30);
			tv3.setText(weather2.getDate());
			tv3.setTextColor(getResources().getColor(R.color.white));
			tv3.setTextSize(20);
			ll.addView(tv1);
			
			ll.addView(tv3);
			ll.addView(tv2);
			vList.add(ll);
		}

		ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(vList);
		pager.setAdapter(pagerAdapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int p) {
				RadioButton childAt = (RadioButton) rg.getChildAt(p);
				childAt.setChecked(true);
				Weather weather2 = weather.get(p);
				initDetail(weather2);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		for (int i = 0; i < weather.size(); i++) {
			Weather w = weather.get(i);
			if (w.getDate().equals(date)) {
				pager.setCurrentItem(i);
				break;
			}
		}

	}

	 void initDetail(Weather we) {
		int weatherRid = QueryConfigs.getWeatherRid(we.getInfo().getDay()
				.get(0));
		iv_day.setImageResource(weatherRid);

		tv_day1.setText(we.getInfo().getDay().get(1));
		tv_day2.setText(we.getInfo().getDay().get(3)
				+ we.getInfo().getDay().get(4));
		tv_day3.setText("日出" + we.getInfo().getDay().get(5));
		int nightRid = QueryConfigs.getWeatherRid(we.getInfo().getNight()
				.get(0));
		iv_night.setImageResource(nightRid);

		tv_night1.setText(we.getInfo().getNight().get(1));
		tv_night2.setText(we.getInfo().getNight().get(3)
				+ we.getInfo().getNight().get(4));
		tv_night3.setText("日落" + we.getInfo().getNight().get(5));
	}

}

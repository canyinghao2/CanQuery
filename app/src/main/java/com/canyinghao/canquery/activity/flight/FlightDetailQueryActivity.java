package com.canyinghao.canquery.activity.flight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.model.FlightAirportInfo;
import com.canyinghao.canquery.model.FlightInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.google.gson.reflect.TypeToken;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class FlightDetailQueryActivity extends BaseBarActivity {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;
	
	private WebView wv;
	private WebView wv2;

	private FlightInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_flight_detail_activity);
        ButterKnife.inject(this);
		getIntentData();
		setView();
		
		if (!TextUtils.isEmpty(info.getDepCode())) {
			trainTicketCityQuery(info.getDepCode(),wv);
		}
		if (!TextUtils.isEmpty(info.getArrCode())) {
			trainTicketCityQuery(info.getArrCode(),wv2);
		}
		
		
		
	}

	private void setView() {




        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "",info.getName(), new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);

        wv= (WebView) findViewById(R.id.wv);
        wv2= (WebView) findViewById(R.id.wv2);
		ViewCache cache = new ViewCache();
		cache.tv_name = (TextView) findViewById(R.id.tv_name);
		cache.tv_complany = (TextView) findViewById(R.id.tv_complany);
		cache.tv_AirVoyage = (TextView) findViewById(R.id.tv_AirVoyage);
		cache.tv_AirModel = (TextView) findViewById(R.id.tv_AirModel);
		cache.tv_AirAge = (TextView) findViewById(R.id.tv_AirAge);
		cache.tv_DepAirport = (TextView) findViewById(R.id.tv_DepAirport);
		cache.tv_ArrAirport = (TextView) findViewById(R.id.tv_ArrAirport);
		cache.tv_status = (TextView) findViewById(R.id.tv_status);
		cache.tv_FlyTime = (TextView) findViewById(R.id.tv_FlyTime);
		cache.tv_food = (TextView) findViewById(R.id.tv_food);
		cache.tv_DepWeather = (TextView) findViewById(R.id.tv_DepWeather);
		cache.tv_ArrWeather = (TextView) findViewById(R.id.tv_ArrWeather);
		cache.tv_DepTime = (TextView) findViewById(R.id.tv_DepTime);
		cache.tv_ArrTime = (TextView) findViewById(R.id.tv_ArrTime);
		cache.tv_Dexpected = (TextView) findViewById(R.id.tv_Dexpected);
		cache.tv_Aexpected = (TextView) findViewById(R.id.tv_Aexpected);
		cache.tv_Dactual = (TextView) findViewById(R.id.tv_Dactual);
		cache.tv_Aactual = (TextView) findViewById(R.id.tv_Aactual);
		cache.tv_DepDelay = (TextView) findViewById(R.id.tv_DepDelay);
		cache.tv_ArrDelay = (TextView) findViewById(R.id.tv_ArrDelay);
		cache.tv_OnTimeRate = (TextView) findViewById(R.id.tv_OnTimeRate);

		cache.tr_AirModel = findViewById(R.id.tr_AirModel);
		cache.tr_FlyTime = findViewById(R.id.tr_FlyTime);
		cache.tr_Weather = findViewById(R.id.tr_Weather);
		cache.tr_DepTime = findViewById(R.id.tr_DepTime);
		cache.tr_Dexpected = findViewById(R.id.tr_Dexpected);
		cache.tr_Dactual = findViewById(R.id.tr_Dactual);
		cache.tr_Delay = findViewById(R.id.tr_Delay);
		cache.tr_OnTimeRate = findViewById(R.id.tr_OnTimeRate);

		if (TextUtils.isEmpty(info.getAirModel())
				&& TextUtils.isEmpty(info.getAirAge())) {
			cache.tr_AirModel.setVisibility(View.GONE);
		} else {
			cache.tr_AirModel.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(info.getFlyTime())) {
			cache.tr_FlyTime.setVisibility(View.GONE);
		} else {
			cache.tr_FlyTime.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(info.getDepWeather())
				&& TextUtils.isEmpty(info.getArrWeather())) {
			cache.tr_Weather.setVisibility(View.GONE);
		} else {
			cache.tr_Weather.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(info.getDepTime())
				&& TextUtils.isEmpty(info.getArrTime())) {
			cache.tr_DepTime.setVisibility(View.GONE);
		} else {
			cache.tr_DepTime.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(info.getDexpected())
				&& TextUtils.isEmpty(info.getAexpected())) {
			cache.tr_Dexpected.setVisibility(View.GONE);
		} else {
			cache.tr_Dexpected.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(info.getDactual())
				&& TextUtils.isEmpty(info.getAactual())) {
			cache.tr_Dactual.setVisibility(View.GONE);
		} else {
			cache.tr_Dactual.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(info.getDepTrafficState())
				&& TextUtils.isEmpty(info.getArrTrafficState())) {
			cache.tr_Delay.setVisibility(View.GONE);
		} else {
			cache.tr_Delay.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(info.getOnTimeRate())
				&& TextUtils.isEmpty(info.getOnTimeRateHistory())) {
			cache.tr_OnTimeRate.setVisibility(View.GONE);
		} else {
			cache.tr_OnTimeRate.setVisibility(View.VISIBLE);
		}

		cache.tv_name.setText(info.getName());
		cache.tv_complany.setText(info.getComplany());
		cache.tv_AirVoyage.setText(info.getAirVoyage());
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

		cache.tv_OnTimeRate.setText(info.getOnTimeRate().length() > info
				.getOnTimeRateHistory().length() ? info.getOnTimeRate() : info
				.getOnTimeRateHistory());

	}

	private void getIntentData() {
		Intent intent = getIntent();

		if (intent.hasExtra("info")) {
			info = (FlightInfo) intent.getSerializableExtra("info");
		} else {
			info = new FlightInfo();
		}

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
		private TextView tv_OnTimeRate;
		private View tr_AirModel;
		private View tr_FlyTime;
		private View tr_Weather;
		private View tr_DepTime;
		private View tr_Dexpected;
		private View tr_Dactual;
		private View tr_Delay;
		private View tr_OnTimeRate;

	}

	private void trainTicketCityQuery(String code,final WebView wv) {
		Parameters params = new Parameters();

		params.add("code", code);
		JuheWeb.getJuheData(params, 20, "http://apis.juhe.cn/plan/airport",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");

						String lists = map.get("list");
						if (TextUtils.isEmpty(lists)) {
							return;
						}

						final List<FlightAirportInfo> mlist = JsonHelper.getInstance()
								.getList(
										lists,
										new TypeToken<List<FlightAirportInfo>>() {
										});

						String mimeType = "text/html";
						String encoding = "UTF-8";
						if (mlist != null) {
							if (mlist.size() > 0) {
								FlightAirportInfo info2 = mlist.get(0);
								wv.loadDataWithBaseURL(null,
										 info2.getIntroduce()
												, mimeType,
										encoding, null);
							}

						}

					}

					@Override
					public void requestEnd() {
						// TODO Auto-generated method stub

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

				});

	}
}

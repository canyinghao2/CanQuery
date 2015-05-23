package com.canyinghao.canquery.fragment;

import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.GridView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canhelper.StringHelper;
import com.canyinghao.canhelper.ThreadHelper;
import com.canyinghao.canhelper.ThreadHelper.ThreadAsync;
import com.canyinghao.canhelper.ThreadHelper.ThreadCallBack;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.weather.WeatherMainQueryActivity;
import com.canyinghao.canquery.activity.weather.WeatherSelectCityQueryActivity;
import com.canyinghao.canquery.adapter.WeatherGrid2Adapter;
import com.canyinghao.canquery.adapter.WeatherGridAdapter;
import com.canyinghao.canquery.configs.QueryConfigs;
import com.canyinghao.canquery.model.CityString;
import com.canyinghao.canquery.model.WeatherCityInfo;
import com.canyinghao.canquery.model.WeatherExpInfo;
import com.canyinghao.canquery.model.WeatherInfo;
import com.canyinghao.canquery.model.WeatherInfo.Data.Life.LifeInfo;
import com.canyinghao.canquery.model.WeatherInfo.Data.Weather;
import com.canyinghao.canquery.model.WeatherInfo.Data.Weather.Info;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.canyinghao.canquery.view.dialog.CustomDialog;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshBase;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshScrollView;
import com.db.chart.Tools;
import com.db.chart.listener.OnEntryClickListener;
import com.db.chart.model.LineSet;
import com.db.chart.view.LineChartView;
import com.db.chart.view.XController;
import com.db.chart.view.YController;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BaseEasingMethod;
import com.db.chart.view.animation.easing.bounce.BounceEaseOut;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class WeatherQueryFragment extends BaseFragment {

	@InjectView(R.id.linechart)
	LineChartView mLineChart;
	@InjectView(R.id.gridview1)
	GridView gridview1;
	@InjectView(R.id.gridview2)
	GridView gridview2;
	@InjectView(R.id.gridview3)
	GridView gridview3;

	@InjectView(R.id.tv_weather_main)
	TextView tv_weather_main;
	@InjectView(R.id.tv_temp)
	TextView tv_temp;
	@InjectView(R.id.tv_update)
	TextView tv_update;
	@InjectView(R.id.tv_wind)
	TextView tv_wind;
	@InjectView(R.id.tv_humidity)
	TextView tv_humidity;
	@InjectView(R.id.tv_moon)
	TextView tv_moon;
	@InjectView(R.id.tv_today)
	TextView tv_today;
	@InjectView(R.id.tv_today_weather)
	TextView tv_today_weather;
	@InjectView(R.id.tv_tomorrow)
	TextView tv_tomorrow;
	@InjectView(R.id.tv_tomorrow_weather)
	TextView tv_tomorrow_weather;
	@InjectView(R.id.tv_pm25)
	TextView tv_pm25;

	final String TEMP = "℃";
	public WeatherInfo info;
	final TimeInterpolator enterInterpolator = new DecelerateInterpolator(1.5f);
	final TimeInterpolator exitInterpolator = new AccelerateInterpolator();

	String city = "成都";
	boolean isFirst = true;
	PullToRefreshScrollView refreshScrollView;

	public WeatherMainQueryActivity activity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		info = new WeatherInfo();
		activity = (WeatherMainQueryActivity) getActivity();

	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}




    public static WeatherQueryFragment  getFragment( String city,WeatherInfo   weatherInfo ){

        WeatherQueryFragment wq=   new WeatherQueryFragment();

        Bundle  bundle= new Bundle();
        bundle.putSerializable("weather",weatherInfo);
        bundle.putString("city",city);
        wq.setArguments(bundle);



        return  wq;

    }





	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LogHelper.logd("onCreateView" + city);
		View v = inflater.inflate(R.layout.query_weather_content, container,
				false);
		ButterKnife.inject(this, v);

		refreshScrollView = new PullToRefreshScrollView(context);
		refreshScrollView.setHeadTextColor(getResources().getColor(
				R.color.white));
		ScrollView scrollView = refreshScrollView.getRefreshableView();
		scrollView.addView(v);
		scrollView.setVerticalScrollBarEnabled(false);
		refreshScrollView.setPullLoadEnabled(false);
		refreshScrollView.setPullRefreshEnabled(true);

		setListener();
//        weatherQuery();

        Bundle  bundle= getArguments();
        if (bundle!=null&&!bundle.isEmpty()){
            WeatherInfo   weatherInfo= (WeatherInfo) bundle.getSerializable("weather");
            city=bundle.getString("city");
            if (weatherInfo!=null){

                info=weatherInfo;

                initWeather();


            }else{

                getWeatherString();
            }


        }else{

            getWeatherString();
        }

		isFirst = false;

		return refreshScrollView;
	}

	/**
	 * Line
	 */
	int LINE_MAX;
	int LINE_MIN;
	String[] lineLabels = { "", "ANT", "GNU", "OWL", "APE", "JAY", "" };
	float[][] lineValues = { { -5f, 6f, 2f, 9f, 0f, -2f, 5f },
			{ -9f, -2f, -4f, -3f, -7f, -5f, -3f } };

	Paint mLineGridPaint;
	TextView mLineTooltip;

	final OnEntryClickListener lineEntryListener = new OnEntryClickListener() {
		@Override
		public void onClick(int setIndex, int entryIndex, Rect rect) {
			LogHelper.logd("OnEntryClickListener");
			if (mLineTooltip == null)
				showLineTooltip(setIndex, entryIndex, rect);
			else
				dismissLineTooltip(setIndex, entryIndex, rect);
		}
	};

	final OnClickListener lineClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mLineTooltip != null)
				dismissLineTooltip(-1, -1, null);
		}
	};

	void initLineChart() {

		mLineChart.setOnEntryClickListener(lineEntryListener);

		mLineChart.setOnClickListener(lineClickListener);

		mLineGridPaint = new Paint();
		mLineGridPaint.setColor(this.getResources().getColor(R.color.white));
		mLineGridPaint
				.setPathEffect(new DashPathEffect(new float[] { 5, 5 }, 0));
		mLineGridPaint.setStyle(Paint.Style.STROKE);
		mLineGridPaint.setAntiAlias(true);
		mLineGridPaint.setStrokeWidth(Tools.fromDpToPx(.75f));
	}

	void updateLineChart() {

		mLineChart.reset();

		LineSet dataSet = new LineSet();
		dataSet.addPoints(lineLabels, lineValues[0]);
		dataSet.setDots(true)
				.setDotsColor(this.getResources().getColor(R.color.yellow))
				.setDotsRadius(Tools.fromDpToPx(5))
				.setDotsStrokeThickness(Tools.fromDpToPx(2))
				.setDotsStrokeColor(
						this.getResources().getColor(R.color.yellow))
				.setLineColor(this.getResources().getColor(R.color.yellow))
				.setLineThickness(Tools.fromDpToPx(3));
		mLineChart.addData(dataSet);

		dataSet = new LineSet();
		dataSet.addPoints(lineLabels, lineValues[1]);
		dataSet.setDots(true)
				.setDotsColor(this.getResources().getColor(R.color.white))
				.setDotsRadius(Tools.fromDpToPx(5))
				.setDotsStrokeThickness(Tools.fromDpToPx(2))
				.setDotsStrokeColor(this.getResources().getColor(R.color.white))
				.setLineColor(this.getResources().getColor(R.color.white))
				.setLineThickness(Tools.fromDpToPx(3));
		mLineChart.addData(dataSet);

		mLineChart
				.setBorderSpacing(Tools.fromDpToPx(4))
				// .setHorizontalGrid(mLineGridPaint)
				.setXAxis(false).setXLabels(XController.LabelPosition.NONE)
				.setYAxis(false).setYLabels(YController.LabelPosition.NONE)
				.setAxisBorderValues(LINE_MIN, LINE_MAX, 5)
				// .setLabelsMetric("u")
				.show(getAnimation(true).setEndAction(null));
	}

	Animation getAnimation(boolean newAnim) {

		int mCurrAlpha = -1;

		float mCurrStartX = 0f;
		float mCurrStartY = -1f;
		float mCurrOverlapFactor = .5f;
		int[] mCurrOverlapOrder = { 0, 1, 2, 3, 4, 5, 6 };
		BaseEasingMethod mCurrEasing = new BounceEaseOut();

		return new Animation().setAlpha(mCurrAlpha).setEasing(mCurrEasing)
				.setOverlap(mCurrOverlapFactor, mCurrOverlapOrder)
				.setStartPoint(mCurrStartX, mCurrStartY);

	}

	@SuppressLint("NewApi")
	void showLineTooltip(int setIndex, int entryIndex, Rect rect) {

		TextView tv = new TextView(context);
		tv.setTextSize(12);
		tv.setGravity(Gravity.CENTER);
		tv.setTextColor(getResources().getColor(R.color.blue));
		tv.setBackgroundResource(R.drawable.community_white_point_circle_shape);
		mLineTooltip = tv;
		mLineTooltip.setText(Integer
				.toString((int) lineValues[setIndex][entryIndex]) + "℃");

		LayoutParams layoutParams = new LayoutParams(
				(int) Tools.fromDpToPx(35), (int) Tools.fromDpToPx(35));
		layoutParams.leftMargin = rect.centerX() - layoutParams.width / 2;
		layoutParams.topMargin = rect.centerY() - layoutParams.height / 2;
		mLineTooltip.setLayoutParams(layoutParams);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
			mLineTooltip.setPivotX(layoutParams.width / 2);
			mLineTooltip.setPivotY(layoutParams.height / 2);
			mLineTooltip.setAlpha(0);
			mLineTooltip.setScaleX(0);
			mLineTooltip.setScaleY(0);
			mLineTooltip.animate().setDuration(150).alpha(1).scaleX(1)
					.scaleY(1).rotation(360).setInterpolator(enterInterpolator);
		}

		mLineChart.showTooltip(mLineTooltip);
	}

	@SuppressLint("NewApi")
	void dismissLineTooltip(final int setIndex, final int entryIndex,
			final Rect rect) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			mLineTooltip.animate().setDuration(100).scaleX(0).scaleY(0)
					.alpha(0).setInterpolator(exitInterpolator)
					.withEndAction(new Runnable() {
						@Override
						public void run() {
							mLineChart.removeView(mLineTooltip);
							mLineTooltip = null;
							if (entryIndex != -1)
								showLineTooltip(setIndex, entryIndex, rect);
						}
					});
		} else {
			mLineChart.dismissTooltip(mLineTooltip);
			mLineTooltip = null;
			if (entryIndex != -1)
				showLineTooltip(setIndex, entryIndex, rect);
		}
	}

	void getWeatherString() {

		try {

			LogHelper.logd("getWeatherString()" + city);
			CityString findFirst = DbUtils.create(App.getContext()).findFirst(
					Selector.from(CityString.class).where("key", "=", city));
			if (findFirst != null) {
				getWeatherInfo(findFirst.getCity());

			} else {
				weatherQuery();
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void weatherQuery() {

		Parameters params = new Parameters();
		params.add("cityname", city);

		JuheWeb.getJuheData(params, 73,
				"http://op.juhe.cn/onebox/weather/query", JuheData.GET,
				new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");

						String lists = map.get("list");
                       String code= map.get("code");
						if (TextUtils.isEmpty(lists)||lists.equals("null")) {

                            PhoneHelper.getInstance().show(message);
                            localWeather();
							return;
						}
						try {
							CityString cityString = DbUtils.create(
									App.getContext()).findFirst(
									Selector.from(CityString.class).where(
											"key", "=", city));
							if (cityString == null) {
								cityString = new CityString();
								cityString.setCity(lists);
								cityString.setKey(city);

								final CityString cityS = cityString;
								try {
									DbUtils.create(App.getContext())
											.saveBindingId(cityS);
								} catch (DbException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							} else {
								cityString.setCity(lists);
								cityString.setKey(city);

								final CityString cityS = cityString;
								try {
									DbUtils.create(App.getContext()).update(
											cityS);
								} catch (DbException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

						} catch (DbException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						getWeatherInfo(lists);

					}

					@Override
					public void requestEnd() {
						refreshScrollView.onPullDownRefreshComplete();
						refreshScrollView.onPullUpRefreshComplete();

					}

					@Override
					public void fail(int err, String reason, String result) {
						localWeather();
					}

				});

	}

	void localWeather() {

		ThreadHelper.getInstance().runAsync(new ThreadCallBack() {

			@Override
			public Object run(ThreadAsync arg0) {
				try {
					long count = DbUtils.create(App.getContext()).count(
							Selector.from(CityString.class).where("key", "=",
									city));
					LogHelper.logd("localWeather()" + city);
					if (count <= 0) {
						return "";
					}

					CityString findFirst = DbUtils.create(App.getContext())
							.findFirst(
									Selector.from(CityString.class).where(
											"key", "=", city));

					if (findFirst != null) {

						return findFirst.getCity();

					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public void progress(Integer... arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void result(Object runData) {
				String str = (String) runData;
				if (!TextUtils.isEmpty(str)) {
					getWeatherInfo(str);
				}

			}
		});

	}

	void getWeatherInfo(final String lists) {

		ThreadHelper.getInstance().runAsync(new ThreadCallBack() {

			@Override
			public Object run(ThreadAsync arg0) {
				info = JsonHelper.getInstance().getObject(lists,
						WeatherInfo.class);
				return null;
			}

			@Override
			public void result(Object arg0) {
				if (!context.isFinishing()) {
					initWeather();
				}

			}

			@Override
			public void progress(Integer... arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	void initWeather() {

		if (info != null) {
			List<Weather> weather = info.getData().getWeather();
			WeatherGridAdapter adapter1 = new WeatherGridAdapter(context,
					weather, 0, info);
			gridview1.setAdapter(adapter1);
			WeatherGridAdapter adapter2 = new WeatherGridAdapter(context,
					weather, 1, info);
			gridview2.setAdapter(adapter2);

			float[][] values = new float[2][7];

			for (int i = 0; i < weather.size(); i++) {

				Weather wInfo = weather.get(i);
				Info info2 = wInfo.getInfo();

				List<String> day = info2.getDay();
				List<String> night = info2.getNight();
				if (day != null && day.size() >= 3) {
					int int1 = StringHelper.getInstance().getInt(day.get(2));
					values[0][i] = int1;

					if (i == 0) {
						LINE_MAX = int1;
						LINE_MIN = int1;
					}
					if (int1 > LINE_MAX) {
						LINE_MAX = int1;
					}
					if (int1 < LINE_MIN) {
						LINE_MIN = int1;
					}

				}
				if (night != null && night.size() >= 3) {
					int int1 = StringHelper.getInstance().getInt(night.get(2));
					values[1][i] = int1;
					if (int1 > LINE_MAX) {
						LINE_MAX = int1;
					}
					if (int1 < LINE_MIN) {
						LINE_MIN = int1;
					}
				}
			}

			int max = LINE_MAX / 5;

			if (LINE_MAX < 0) {
				LINE_MAX = max * 5;
			} else {
				LINE_MAX = (max + 1) * 5;
			}

			int min = LINE_MIN / 5;
			if (LINE_MIN < 0) {
				LINE_MIN = (min - 1) * 5;
			} else {
				LINE_MIN = min * 5;
			}

			lineValues = values;
			initLineChart();
			updateLineChart();

			LifeInfo lifeInfo = info.getData().getLife().getInfo();
			ArrayList<WeatherExpInfo> list = new ArrayList<WeatherExpInfo>();

			list.add(new WeatherExpInfo(R.drawable.query_weather_kt, "空调指数",
					lifeInfo.getKongtiao()));
			list.add(new WeatherExpInfo(R.drawable.query_weather_sport, "运动指数",
					lifeInfo.getYundong()));
			list.add(new WeatherExpInfo(R.drawable.query_weather_uvb, "紫外线指数",
					lifeInfo.getZiwaixian()));
			list.add(new WeatherExpInfo(R.drawable.query_weather_gm, "感冒指数",
					lifeInfo.getGanmao()));
			list.add(new WeatherExpInfo(R.drawable.query_weather_wash, "洗车指数",
					lifeInfo.getXiche()));
			list.add(new WeatherExpInfo(R.drawable.query_weather_wr, "污染指数",
					lifeInfo.getWuran()));
			list.add(new WeatherExpInfo(R.drawable.query_weather_dress, "穿衣指数",
					lifeInfo.getChuanyi()));

			WeatherGrid2Adapter adapter3 = new WeatherGrid2Adapter(context,
					list, info);
			gridview3.setAdapter(adapter3);
			int weatherRid = QueryConfigs.getWeatherRid(weather.get(0)
					.getInfo().getDay().get(0));
			tv_weather_main.setCompoundDrawablesWithIntrinsicBounds(0,
					weatherRid, 0, 0);
			tv_weather_main.setText(weather.get(0).getInfo().getDay().get(1));

			tv_temp.setText(info.getData().getRealtime().getWeather()
					.getTemperature()
					+ TEMP);

			tv_update.setText(info.getData().getRealtime().getTime() + "更新");
			tv_wind.setText(info.getData().getRealtime().getWind().getDirect()
					+ info.getData().getRealtime().getWind().getPower());

			tv_humidity.setText("湿度："
					+ info.getData().getRealtime().getWeather().getHumidity()
					+ "%");

			tv_moon.setText("公历：" + info.getData().getRealtime().getDate()
					+ "    农历：" + info.getData().getRealtime().getMoon());

			tv_today.setText(weather.get(0).getInfo().getDay().get(2) + "/"
					+ weather.get(0).getInfo().getNight().get(2) + TEMP);
			tv_today_weather.setText(weather.get(0).getInfo().getDay().get(1));
			tv_tomorrow.setText(weather.get(1).getInfo().getDay().get(2) + "/"
					+ weather.get(1).getInfo().getNight().get(2) + TEMP);
			tv_tomorrow_weather.setText(weather.get(1).getInfo().getDay()
					.get(1));
			tv_pm25.setText(info.getData().getPm25().getPm25().getQuality());

			try {
				WeatherCityInfo cityInfo = DbUtils.create(App.getContext())
						.findFirst(
								Selector.from(WeatherCityInfo.class).where(
										"city",
										"=",
										info.getData().getRealtime()
												.getCity_name()));
				if (cityInfo != null) {
					cityInfo.setCity(info.getData().getRealtime()
							.getCity_name());
					cityInfo.setRid(weatherRid);
					cityInfo.setWeather(weather.get(0).getInfo().getDay()
							.get(1));

					final WeatherCityInfo cityI = cityInfo;
					try {
						DbUtils.create(App.getContext()).update(cityI);
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					cityInfo = new WeatherCityInfo();
					cityInfo.setRid(weatherRid);
					cityInfo.setWeather(weather.get(0).getInfo().getDay()
							.get(1));
					cityInfo.setCity(info.getData().getRealtime()
							.getCity_name());

					final WeatherCityInfo cityI = cityInfo;
					try {
						DbUtils.create(App.getContext()).save(cityI);
					} catch (DbException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@OnClick({ R.id.ll_pm25 })
	public void click(final View v) {

		AnimeHepler.getInstance().startAnimation(context, v,
				R.anim.small_2_big, new OnAnimEnd() {

					@Override
					public void end() {

						switch (v.getId()) {
						case R.id.ll_pm25:
							String des = info.getData().getPm25().getPm25()
									.getDes();
							if (!TextUtils.isEmpty(des)) {
								new CustomDialog("PM2.5", des, context, "", "",
										null, null).show();
							}
							break;
						case R.id.top_center:
							IntentHelper.getInstance().showIntent(context,
									WeatherSelectCityQueryActivity.class);

							break;

						}

					}
				});

	}

	void setListener() {

		OnRefreshListener refreshListener = new OnRefreshListener() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				LogHelper.logd("fragment:" + city);
				weatherQuery();

			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {

				weatherQuery();
			}
		};
		refreshScrollView.setOnRefreshListener(refreshListener);

	}

}

package com.canyinghao.canquery.activity.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.WeatherCityAdapter;
import com.canyinghao.canquery.model.CityString;
import com.canyinghao.canquery.model.PostcodeCityInfo;
import com.canyinghao.canquery.model.PostcodeCityInfo.City;
import com.canyinghao.canquery.model.PostcodeCityInfo.City.District;
import com.canyinghao.canquery.view.alphabeticbar.QuickAlphabeticBar;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class WeatherCityQueryActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.et_city)
	TextView et_city;
	@InjectView(R.id.rb_rg_1)
	RadioButton rb_rg_1;
	@InjectView(R.id.rg)
	RadioGroup rg;
	@InjectView(R.id.rb_rg_2)
	RadioButton rb_rg_2;

	@InjectView(R.id.lv_voip_contact)
	ListView listview;
	@InjectView(R.id.indexScrollerBar)
	QuickAlphabeticBar mQuickAlphabeticBar;

	WeatherCityAdapter adapter;

	List list;
	List listHot;

	List listAll;
	String[] hotCity = new String[] { "北京市", "上海市", "广州市", "深圳市", "武汉市", "天津市",
			"重庆市", "成都市", "厦门市", "昆明市", "杭州市", "西安市", "三亚市" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_trainticket_city_activity);
		ButterKnife.inject(this);
		setView();
		list = new ArrayList();
		listHot = new ArrayList();
		listAll = new ArrayList();
        listview.setDividerHeight(0);
		mQuickAlphabeticBar.init(context);
		mQuickAlphabeticBar.setListView(listview);
		mQuickAlphabeticBar.setHight(mQuickAlphabeticBar.getHeight());
		mQuickAlphabeticBar.setVisibility(View.GONE);
		setListener();

		for (int i = 0; i < hotCity.length; i++) {
			listHot.add(hotCity[i]);

		}
		isSaveCity();
		list.addAll(listHot);
		adapter = new WeatherCityAdapter(context, list, 0);
		listview.setAdapter(adapter);

	}

	void setView() {



        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "选择城市", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);
		rb_rg_1.setText("热门城市");
		rb_rg_2.setText("省份列表");
		et_city.setHint("城市/地区");
	}

	@OnClick({ R.id.iv_search })
	public void click(View v) {

		String city = et_city.getText().toString().trim().toLowerCase();
		if (TextUtils.isEmpty(city)) {
			PhoneHelper.getInstance().show("请输入城市或地区");
		} else {
			rb_rg_1.setChecked(true);
			List<String> searchList = new ArrayList<String>();
			for (int i = 0; i < listAll.size(); i++) {
				PostcodeCityInfo info = (PostcodeCityInfo) listAll.get(i);
				List<City> cityList = info.getCity();
				for (int j = 0; j < cityList.size(); j++) {
					City city2 = cityList.get(j);
					List<District> district = city2.getDistrict();
					String cityName = city2.getCity();
					if (!searchList.contains(cityName)
							&& cityName.contains(city)) {
						searchList.add(cityName);
					}
					for (int k = 0; k < district.size(); k++) {

						District district2 = district.get(k);
						String districtName = district2.getDistrict();
						if (!searchList.contains(districtName)
								&& cityName.contains(districtName)) {
							searchList.add(districtName);
						}
					}

				}
			}

			list.clear();
			list.addAll(searchList);
			adapter = new WeatherCityAdapter(context, list, 0);
			listview.setAdapter(adapter);

		}

	}

	void setListener() {
		ViewTreeObserver vto = mQuickAlphabeticBar.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				mQuickAlphabeticBar.getViewTreeObserver()
						.removeGlobalOnLayoutListener(this);
				mQuickAlphabeticBar.setHight(mQuickAlphabeticBar.getHeight());
			}
		});

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_rg_1:
					list.clear();
					list.addAll(listHot);
					adapter = new WeatherCityAdapter(context, list, 0);
					listview.setAdapter(adapter);

					break;
				case R.id.rb_rg_2:
					list.clear();
					list.addAll(listAll);
					adapter = new WeatherCityAdapter(context, list, 1);
					listview.setAdapter(adapter);
					break;

				}

			}
		});

	}

	void isSaveCity() {

		try {
			final CityString findFirst = DbUtils.create(App.getContext())
					.findFirst(
							Selector.from(CityString.class).where("key", "=",
									"PostCode"));

			if (findFirst != null) {

				List<PostcodeCityInfo> mlist = JsonHelper.getInstance()
						.getList(findFirst.getCity(),
								new TypeToken<List<PostcodeCityInfo>>() {
								});
				listAll.clear();
				listAll.addAll(mlist);

			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	
	
	@Override
	public void onEventMainThread(Intent intent) {
		// TODO Auto-generated method stub
		super.onEventMainThread(intent);
		if ( intent.getAction().equals(WeatherCity2DisQueryActivity.TAG)) {
			finish();
		}
	}
}

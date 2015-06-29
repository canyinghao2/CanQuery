package com.canyinghao.canquery.activity.weather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.WeatherGridCityAdapter;
import com.canyinghao.canquery.model.WeatherCityInfo;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WeatherSelectCityQueryActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
	@InjectView(R.id.gridview)
	 GridView gridview;

	 List list;
	 WeatherGridCityAdapter adapter;
	public static String TAG = "WeatherSelectCityQuery";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_weather_city_activity);
		ButterKnife.inject(this);
		setView();
		list = new ArrayList();

		adapter = new WeatherGridCityAdapter(context, list);
		gridview.setAdapter(adapter);
		refrenshList();
		
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	 void refrenshList() {
		try {
			List<WeatherCityInfo> findAll = DbUtils.create(App.getContext())
					.findAll(WeatherCityInfo.class);

			if (findAll != null && findAll.size() > 0) {
				list.clear();
				list.addAll(findAll);

				adapter.notifyDataSetChanged();
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 void setView() {




         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "城市管理", new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 Intent intent = new Intent();
                 Activity ac = (Activity) context;
                 ac.setResult(100, intent);
                 ac.finish();



             }
         },null);

	}

	

	
	@Override
	public void onEventMainThread(Intent intent) {
		// TODO Auto-generated method stub
		super.onEventMainThread(intent);
		if (intent.getAction().equals(TAG)) {
			refrenshList();

		}
	}

}

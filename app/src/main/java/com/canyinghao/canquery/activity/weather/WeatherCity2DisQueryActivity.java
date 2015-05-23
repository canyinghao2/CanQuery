package com.canyinghao.canquery.activity.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.WeatherCity2DisAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WeatherCity2DisQueryActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	 List list;

	@InjectView(R.id.listview)
	 ListView listview;

	 WeatherCity2DisAdapter adapter;

	 int type;
    public static String TAG="weather_finish";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_find_listview2);
		ButterKnife.inject(this);
		list=new ArrayList();

		listview.setDividerHeight(0);
		getIntentData();
		setView();
		
	}

	 void setView() {

         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "电影票房", new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();



             }
         },null);
		
		switch (type) {
		case 0:
			toolbar.setSubtitle("选择省份");
			break;
		case 1:
			toolbar.setSubtitle("选择城市");
			break;
		case 2:
			toolbar.setSubtitle("选择区县");
			break;

		}



	}

	 void getIntentData() {
		Intent intent = getIntent();
		if (intent.hasExtra("type")) {
			type = intent.getIntExtra("type", 0);

		}
		
		if (intent.hasExtra("list")) {
			list=(List) intent.getSerializableExtra("list");
			
		}
		adapter = new WeatherCity2DisAdapter(context, list, type);
		listview.setAdapter(adapter);
	}


	
	@Override
	public void onEventMainThread(Intent intent) {
		// TODO Auto-generated method stub
		super.onEventMainThread(intent);
		
		if (intent.getAction().equals(TAG)) {
			finish();
			
			
		}
	}

}

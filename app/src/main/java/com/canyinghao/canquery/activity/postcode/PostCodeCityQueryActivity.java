package com.canyinghao.canquery.activity.postcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.PostCodeCityAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PostCodeCityQueryActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;


	 List list;

	@InjectView(R.id.listview)
	 ListView listview;

	 PostCodeCityAdapter adapter;

	 int type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.community_find_listview2);
		ButterKnife.inject(this);
		list=new ArrayList();


		getIntentData();
		setView();

		
	}

	 void setView() {

         String title="";
		switch (type) {
		case 0:
            title="选择省份";
			break;
		case 1:
            title="选择城市";
			break;
		case 2:
            title="选择区县";
			break;

		}

         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "",title, new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();



             }
         },null);

	}

	 void getIntentData() {
		Intent intent = getIntent();
		if (intent.hasExtra("type")) {
			type = intent.getIntExtra("type", 0);

		}
		
		if (intent.hasExtra("list")) {
			list=(List) intent.getSerializableExtra("list");
			
		}
		adapter = new PostCodeCityAdapter(context, list, type);
		listview.setAdapter(adapter);
	}

	
	

}

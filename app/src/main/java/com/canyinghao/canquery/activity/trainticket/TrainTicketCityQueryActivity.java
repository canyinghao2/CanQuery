package com.canyinghao.canquery.activity.trainticket;

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

import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.TrainTicketCityAdapter;
import com.canyinghao.canquery.model.TrainTicketCityInfo;
import com.canyinghao.canquery.view.alphabeticbar.QuickAlphabeticBar;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class TrainTicketCityQueryActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.et_city)
	 TextView et_city;
	@InjectView(R.id.rb_rg_1)
	 RadioButton rb_rg_1;
    @InjectView(R.id.rb_rg_2)
	 RadioButton rb_rg_2;
	@InjectView(R.id.rg)
	RadioGroup rg;

	@InjectView(R.id.lv_voip_contact)
	 ListView listview;
	@InjectView(R.id.indexScrollerBar)
	 QuickAlphabeticBar mQuickAlphabeticBar;

	 TrainTicketCityAdapter adapter;

	 List list;

	 List listAll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_trainticket_city_activity);
		ButterKnife.inject(this);
		setView();
		list = new ArrayList();
		listAll = new ArrayList();
        listview.setDividerHeight(0);
		mQuickAlphabeticBar.init(context);
		mQuickAlphabeticBar.setListView(listview);
		mQuickAlphabeticBar.setHight(mQuickAlphabeticBar.getHeight());
		mQuickAlphabeticBar.setVisibility(View.VISIBLE);
		setListener();

		try {

		

			
			List<TrainTicketCityInfo> mmlist = DbUtils.create(App.getContext())
					.findAll(
							Selector.from(TrainTicketCityInfo.class)
									.where("type", "=", 1)
									.orderBy("sta_ename", false));
			if (mmlist != null) {
				list.clear();
				list.addAll(mmlist);
				adapter = new TrainTicketCityAdapter(context, list,
						mQuickAlphabeticBar);
				listview.setAdapter(adapter);
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 void setView() {

         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "选择车站", new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();


             }
         }, null);
		rb_rg_2.setText("车站列表");
         rb_rg_1.setText("常用");
		et_city.setHint("城市/车站");
	}

	@OnClick({ R.id.iv_search })
	public void click(View v) {

		String city = et_city.getText().toString().trim().toLowerCase();
		if (TextUtils.isEmpty(city)) {
			PhoneHelper.getInstance().show("请输入城市或车站");
		} else {
			rb_rg_2.setChecked(true);
			if (listAll.size() <= 0) {
				try {
					List mlist = DbUtils.create(App.getContext()).findAll(
							Selector.from(TrainTicketCityInfo.class)
									.where("type", "=", 0)
									.orderBy("sta_ename", false));
					if (mlist != null) {
						listAll.clear();
						listAll.addAll(mlist);
					}
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			list.clear();
			for (int i = 0; i < listAll.size(); i++) {
				TrainTicketCityInfo info = (TrainTicketCityInfo) listAll.get(i);

				if (info.getSta_name().contains(city)
						|| info.getSta_ename().contains(city)) {
					list.add(info);

				}

			}
			adapter = new TrainTicketCityAdapter(context, list,
					mQuickAlphabeticBar);
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
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				try {

					List<TrainTicketCityInfo> mlist = new ArrayList<TrainTicketCityInfo>();
					switch (checkedId) {
					case R.id.rb_rg_1:
						mlist = DbUtils.create(App.getContext()).findAll(
								Selector.from(TrainTicketCityInfo.class)
										.where("type", "=", 1)
										.orderBy("sta_ename", false));

						break;
					case R.id.rb_rg_2:
						mlist = DbUtils.create(App.getContext()).findAll(
								Selector.from(TrainTicketCityInfo.class)
										.where("type", "=", 0)
										.orderBy("sta_ename", false));
						break;

					}
					if (mlist != null) {
						list.clear();
						list.addAll(mlist);
						adapter = new TrainTicketCityAdapter(context, list,
								mQuickAlphabeticBar);
						listview.setAdapter(adapter);
					}

				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});

	}

	

	
}

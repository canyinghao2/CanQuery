package com.canyinghao.canquery.activity.flight;

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
import com.canyinghao.canquery.adapter.FlightCityAdapter;
import com.canyinghao.canquery.model.FlightCityInfo;
import com.canyinghao.canquery.view.alphabeticbar.QuickAlphabeticBar;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FlightCityQueryActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.et_city)
	TextView et_city;
	@InjectView(R.id.rg)
	RadioGroup rg;
	@InjectView(R.id.rb_rg_2)
	RadioButton rb_rg_2;

	@InjectView(R.id.lv_voip_contact)
	ListView listview;
	@InjectView(R.id.indexScrollerBar)
	QuickAlphabeticBar mQuickAlphabeticBar;

	FlightCityAdapter adapter;

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

			long count = DbUtils.create(App.getContext()).count(
					Selector.from(FlightCityInfo.class));

			List<FlightCityInfo> mmlist = DbUtils.create(App.getContext())
					.findAll(
							Selector.from(FlightCityInfo.class)
									.where("type", "=", 1)
									.orderBy("pinyin", false));
			if (mmlist != null) {
				list.clear();
				list.addAll(mmlist);
				adapter = new FlightCityAdapter(context, list,
						mQuickAlphabeticBar);
				listview.setAdapter(adapter);
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void setView() {



        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "选择机场", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);
		rb_rg_2.setText("机场列表");
		et_city.setHint("城市/机场");
	}

	@OnClick({ R.id.iv_search })
	public void click(View v) {

		String city = et_city.getText().toString().trim().toLowerCase();
		if (TextUtils.isEmpty(city)) {
			PhoneHelper.getInstance().show("请输入城市或机场");
		} else {
			rb_rg_2.setChecked(true);
			if (listAll.size() <= 0) {
				try {
					List mlist = DbUtils.create(App.getContext()).findAll(
							Selector.from(FlightCityInfo.class)
									.where("type", "=", 0)
									.and("spell", "<>", "")
									.orderBy("pinyin", false));
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
				FlightCityInfo info = (FlightCityInfo) listAll.get(i);

				if (info.getCity().contains(city)
						|| info.getSpell().toLowerCase().contains(city)) {
					list.add(info);

				}

			}
			adapter = new FlightCityAdapter(context, list, mQuickAlphabeticBar);
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

					List<FlightCityInfo> mlist = new ArrayList<FlightCityInfo>();
					switch (checkedId) {
					case R.id.rb_rg_1:
						mlist = DbUtils.create(App.getContext()).findAll(
								Selector.from(FlightCityInfo.class)
										.where("type", "=", 1)
										.and("spell", "<>", "")
										.orderBy("pinyin", false));

						break;
					case R.id.rb_rg_2:
						mlist = DbUtils.create(App.getContext()).findAll(
								Selector.from(FlightCityInfo.class)
										.where("type", "=", 0)
										.and("spell", "<>", "")
										.orderBy("pinyin", false));
						break;

					}
					if (mlist != null) {
						list.clear();
						list.addAll(mlist);
						adapter = new FlightCityAdapter(context, list,
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

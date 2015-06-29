package com.canyinghao.canquery.activity.box_office;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.BoxOfficeAdapter;
import com.canyinghao.canquery.adapter.ViewPagerAdapter;
import com.canyinghao.canquery.model.BoxOfficeInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.google.gson.reflect.TypeToken;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class BoxOfficeActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;


	@InjectView(R.id.rg)
	 RadioGroup rg;
	@InjectView(R.id.pager)
	 ViewPager pager;
	 ViewPagerAdapter pagerAdapter;

	public String[] offices = { "CN", "US", "HK" };
	 List<View> vList;
	 List<View> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        setContentView(R.layout.query_box_office_activity);
		ButterKnife.inject(this);
		vList = new ArrayList<View>();
		list = new ArrayList<View>();
        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "电影票房", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);
		initView();
	}



	 void getListData() {

		for (int i = 0; i < offices.length; i++) {
			FrameLayout fl = new FrameLayout(context);
			ListView lv = new ListView(context);
			lv.setDividerHeight(1);
			lv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			fl.addView(lv);
			AnimeHepler.getInstance().setAnimationEmptyView(context, lv, null, "");
			
			vList.add(lv);
			list.add(fl);
		}

	}

	 void initView() {
		getListData();
		pagerAdapter = new ViewPagerAdapter(list);
		pager.setAdapter(pagerAdapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int p) {
				RadioButton childAt = (RadioButton) rg.getChildAt(p);
				childAt.setChecked(true);

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

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rg, int rid) {
				int num = 0;
				switch (rid) {
				case R.id.rb_1:
					num = 0;
					break;
				case R.id.rb_2:
					num = 1;
					break;
				case R.id.rb_3:
					num = 2;
					break;

				}

				pager.setCurrentItem(num);

			}
		});

		for (int i = 0; i < offices.length; i++) {
			box_officeQuery(offices[i], vList.get(i));
		}

	}

	 void box_officeQuery(String area, final View view) {
		final ListView lv = (ListView) view;
		Parameters params = new Parameters();
		params.add("area", area);
		JuheWeb.getJuheData(params, 44, "http://v.juhe.cn/boxoffice/rank",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {

						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");
						if (map.get("code").equals("200")) {
							String lists = map.get("list");

							List<BoxOfficeInfo> mlist = JsonHelper.getInstance().getList(
									lists,
									new TypeToken<List<BoxOfficeInfo>>() {
									});

							BoxOfficeAdapter adapter = new BoxOfficeAdapter(
									context, mlist);

                            if (mlist!=null&&mlist.size()>0){

                                BoxOfficeInfo  info=   mlist.get(0);
                                setShareContent(info.getName(),"上映时间："+info.getWk()+"周末票房："+info.getWboxoffice()+"累加票房："+info.getTboxoffice());
                            }
							lv.setAdapter(adapter);

						}

					}

					@Override
					public void requestEnd() {
						AnimeHepler.getInstance().setNoDataEmptyView(context, lv,
								R.drawable.empty_cry, "",
								null);

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

				});

	}

}

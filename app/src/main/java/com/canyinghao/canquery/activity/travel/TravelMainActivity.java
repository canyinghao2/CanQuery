package com.canyinghao.canquery.activity.travel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.TravelAdapter;
import com.canyinghao.canquery.model.TravelInfo;
import com.canyinghao.canquery.model.TravelInfo.Hotel;
import com.canyinghao.canquery.model.TravelInfo.Scenery;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshBase;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshGridView;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshListView;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class TravelMainActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
	@InjectView(R.id.ll)
	 View ll;


	@InjectView(R.id.et_search)
	 EditText et_search;

	@InjectView(R.id.iv_search)
	 ImageView iv_search;


	 ListView listview;
	@InjectView(R.id.refreshGridView)
	 PullToRefreshGridView refreshGridView;
	@InjectView(R.id.refreshListView)
	 PullToRefreshListView refreshListView;
	 List list;

	 TravelAdapter adapter;
	 boolean isHotel = false;
	 int skip;
	
	 List tempList;
	
	 String cityId="";

	 String title="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_cookbook_search_activity);
		ButterKnife.inject(this);
		refreshGridView.setVisibility(View.GONE);
		refreshListView.setVisibility(View.VISIBLE);
		listview = refreshListView.getRefreshableView();
//		listview.setNumColumns(2);
        listview.setDividerHeight(0);
        listview.setBackgroundColor(getResources().getColor(R.color.blue_gray_500));

        refreshListView.setPullLoadEnabled(true);
        refreshListView.setPullRefreshEnabled(false);

		ll.setVisibility(View.VISIBLE);
		setListener();
		list = new ArrayList();
		tempList=new ArrayList();
		AnimeHepler.getInstance().setAnimationEmptyView(context, listview, null, "");
		


        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "旅游咨询", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);

        getIntentData();
	}

	 void getIntentData() {
         et_search.setHint("请输入旅游地");
		Intent intent = getIntent();
		if (intent.hasExtra("info")) {
			isHotel = true;
			Scenery info = (Scenery) intent.getSerializableExtra("info");

			cityId=info.getCityId();
			getTravelData(info.getCityId());

            toolbar.setSubtitle("附近酒店");
            et_search.setHint("请输入酒店名");
		} else {
            toolbar.setSubtitle("旅游咨询");

			getTravelData("");
		}

		adapter = new TravelAdapter(context, list, isHotel);
		listview.setAdapter(adapter);

	}

	 void setListener() {

		
		OnRefreshListener refreshListener = new OnRefreshListener() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				  if (listview.getTag()!=null) {
					listview.setTag(null);
					for (int i = tempList.size()/2; i < tempList.size(); i++) {
						
						list.add(tempList.get(i));
						
					}
					
					adapter.notifyDataSetChanged();
                      refreshListView.onPullUpRefreshComplete();
                      refreshListView.setHasMoreData(true);
					LogHelper.logd("tag");
				}else {
					skip+=50;
					getTravelData(cityId);
					LogHelper.logd("null");
				}
				
			}
		};
         refreshListView.setOnRefreshListener(refreshListener);

	}

	@OnClick({ R.id.iv_search })
	public void click(View v) {

		AnimeHepler.getInstance().startAnimation(context, v, R.anim.small_2_big,
				new OnAnimEnd() {

					

					@Override
					public void end() {
						title= et_search.getText().toString().trim();
						if (TextUtils.isEmpty(title)) {
							PhoneHelper.getInstance().show("请输入目的地、旅游景点");
							return;
						}
						list.clear();
						tempList.clear();
						getTravelData("");

					}
				});

	}

	 void getTravelData(String cityId) {
		Parameters params = new Parameters();
		params.add("title", title);
		params.add("v", "1");
		params.add("pname", "com.canyinghao.canquery");
		params.add("skip", skip);

		
		String url = "";
		if (isHotel) {
			params.add("cityId", cityId);
			url = "http://web.juhe.cn:8080/travel/hotel/hotelList.json";
		} else {
			url = "http://web.juhe.cn:8080/travel/scenery/sceneryList.json";
		}
		JuheWeb.getJuheData(params, 57, url, JuheData.GET,
				new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");

						String lists = map.get("list");

						TravelInfo info = JsonHelper.getInstance().getObject(lists,
								TravelInfo.class);

						if (info != null) {
							if (isHotel) {
								List<Hotel> hotelList = info.getHotelList();
								if (hotelList != null) {
									tempList=hotelList;
									

								}
							} else {
								List<Scenery> sceneryList = info
										.getSceneryList();
								if (sceneryList != null) {
									tempList=sceneryList;
								}
							}

						}
						
						for (int i = 0; i < tempList.size()/2; i++) {
							
							list.add(tempList.get(i));
						}
						listview.setTag("half");
						
						adapter.notifyDataSetChanged();
                        refreshListView.setHasMoreData(true);
					}

					@Override
					public void requestEnd() {
                        refreshListView.onPullDownRefreshComplete();
                        refreshListView.onPullUpRefreshComplete();
						
						AnimeHepler.getInstance().setNoDataEmptyView(context, listview,
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

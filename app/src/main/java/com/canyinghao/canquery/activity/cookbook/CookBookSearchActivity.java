package com.canyinghao.canquery.activity.cookbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canhelper.StringHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.CookBookSearchAdapter;
import com.canyinghao.canquery.adapter.NewBaseAdapter;
import com.canyinghao.canquery.model.CookBookIndexInfo;
import com.canyinghao.canquery.model.CookBookIndexInfo.ListInfo;
import com.canyinghao.canquery.model.CookBookInfo;
import com.canyinghao.canquery.model.CookBookInfo.Data;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshListView;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CookBookSearchActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
	@InjectView(R.id.ll)
	 View ll;


	@InjectView(R.id.et_search)
	 EditText et_search;

	@InjectView(R.id.iv_search)
	 ImageView iv_search;

//	@InjectView(R.id.listview)
	 ListView listview;
	
	@InjectView(R.id.refreshListView)
	 PullToRefreshListView refreshListView;

	 List list;
	 CookBookSearchAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_cookbook_search_activity);
		ButterKnife.inject(this);

		listview = refreshListView.getRefreshableView();
        listview.setDividerHeight(0);

		refreshListView.setPullLoadEnabled(false);
		refreshListView.setPullRefreshEnabled(false);
		

        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);

        getIntentData();

	}

	 void getIntentData() {

		
		Intent intent = getIntent();
		if (intent.hasExtra("cook")) {
			String cook = intent.getStringExtra("cook");

			
			ll.setVisibility(View.VISIBLE);

			list = new ArrayList();
			adapter = new CookBookSearchAdapter(context, list);
			listview.setAdapter(adapter);
			AnimeHepler.getInstance().setAnimationEmptyView(context, listview, null, "");
			if (!TextUtils.isEmpty(cook)) {
				et_search.setText(cook);
				getCookDetail(cook, 0 + "", 100 + "", false);

			}

		} else if (intent.hasExtra("indexData")) {
			
			ll.setVisibility(View.GONE);

			CookBookIndexInfo info = (CookBookIndexInfo) intent
					.getSerializableExtra("indexData");
			toolbar.setSubtitle(info.getName());
			IndexAdapter adapter2 = new IndexAdapter(context, info.getList());
			listview.setAdapter(adapter2);

		} else if (intent.hasExtra("index")) {

			ll.setVisibility(View.GONE);
			ListInfo info = (ListInfo) intent.getSerializableExtra("index");
			toolbar.setSubtitle(info.getName());
			list = new ArrayList();
			adapter = new CookBookSearchAdapter(context, list);
			listview.setAdapter(adapter);
			AnimeHepler.getInstance().setAnimationEmptyView(context, listview, null, "");

			getCookDetail(info.getId(), 0 + "", 100 + "", true);

		}

	}



	@OnClick({ R.id.iv_search })
	public void click(View v) {

		AnimeHepler.getInstance().startAnimation(context, v, R.anim.small_2_big,
				new OnAnimEnd() {

					

					@Override
					public void end() {
						String trim = et_search.getText().toString().trim();
						if (TextUtils.isEmpty(trim)) {
							PhoneHelper.getInstance().show(" 请输入菜谱、食材");
							return;
						}

						getCookDetail(trim, 0 + "", 100 + "", false);

					}
				});

	}

	 void getCookDetail(String menu, String pn, String rn,
			boolean isIndex) {

		Parameters params = new Parameters();
		String url = "";
		if (isIndex) {
			params.add("cid", StringHelper.getInstance().getInt(menu));
			url = "http://apis.juhe.cn/cook/index";
		} else {
			params.add("menu", menu);
			url = "http://apis.juhe.cn/cook/query.php";
		}

		params.add("pn", pn);
		params.add("rn", rn);
		// params.add("albums", albums);
		list.clear();
		JuheWeb.getJuheData(params, 46, url, JuheData.GET,
				new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");
						if (map.get("code").equals("200")) {
							String lists = map.get("list");

							CookBookInfo info = JsonHelper.getInstance().getObject(lists,
									CookBookInfo.class);
							List<Data> mlist = info.getData();

							
							list.addAll(mlist);
							adapter.notifyDataSetChanged();

						}

					}

					@Override
					public void requestEnd() {
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

	 class IndexAdapter extends NewBaseAdapter {

		public IndexAdapter(Context c, List list) {
			super(c, list);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int p, View paramView, ViewGroup paramViewGroup) {
			TextView tv = new TextView(context);
			final ListInfo info = (ListInfo) list.get(p);
			tv.setText(info.getName());
			int dp = BitmapHelper.getInstance().dp2Px(20);
			tv.setPadding(dp, dp, dp, dp);
			ListView.LayoutParams params = new ListView.LayoutParams(
					ListView.LayoutParams.MATCH_PARENT,
					ListView.LayoutParams.WRAP_CONTENT);
			tv.setLayoutParams(params);
			tv.setTextSize(15);
            tv.setTextColor(Color.WHITE);
			
			tv.setBackgroundResource(R.drawable.community_bluegray_selector);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View paramView) {

					IntentHelper.getInstance().showIntent(context, CookBookSearchActivity.class,
							new String[] { "index" },
							new Serializable[] { info });

				}
			});

			return tv;
		}

	}

}

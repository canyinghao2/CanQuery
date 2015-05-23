package com.canyinghao.canquery.activity.postcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.PostCodeResultAdapter;
import com.canyinghao.canquery.model.PostcodeInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshBase;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshBase.OnRefreshListener;
import com.canyinghao.canquery.view.pulltorefresh.PullToRefreshListView;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PostCodeResultQueryActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;


	@InjectView(R.id.tv_sum)
	 TextView tv_sum;


	 List list;
	@InjectView(R.id.refreshListView)
	 PullToRefreshListView refreshListView;
	 ListView listview;

	 PostCodeResultAdapter adapter;

	 int page = 1;
	 int size = 50;
	 boolean mIsStart = true;

	 boolean isCity2Post = false;

	String post;
	String pid = "";
	String cid = "";
	String did = "";
	String q = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_postcode_result_activity);
		ButterKnife.inject(this);
		list = new ArrayList();

		getIntentData();

		listview = refreshListView.getRefreshableView();
		refreshListView.setTimeId(203);
		refreshListView.setPullLoadEnabled(true);
		listview.setDividerHeight(1);
		// listview.setBackgroundResource(R.color.main_deep_bg);
		adapter = new PostCodeResultAdapter(context, list);
		listview.setAdapter(adapter);
		AnimeHepler.getInstance().setAnimationEmptyView(context, listview, null, "");
		setListener();

        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "查询结果", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);
	}



	 void getIntentData() {
		Intent intent = getIntent();

		if (intent.hasExtra("postcode")) {
			post = intent.getStringExtra("postcode");
			postCode2cityQuery(post);
			isCity2Post = false;
		} else {
			isCity2Post = true;

			if (intent.hasExtra("pid")) {
				pid = intent.getStringExtra("pid");
			}
			if (intent.hasExtra("cid")) {
				cid = intent.getStringExtra("cid");
			}
			if (intent.hasExtra("did")) {
				did = intent.getStringExtra("did");
			}
			if (intent.hasExtra("q")) {
				q = intent.getStringExtra("q");
			}

			city2postCodeQuery(pid, cid, did, q);

		}

	}

	 void setListener() {

		OnRefreshListener refreshListener = new OnRefreshListener() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase refreshView) {
				mIsStart = true;

				if (isCity2Post) {
					city2postCodeQuery(pid, cid, did, q);
				} else {
					postCode2cityQuery(post);
				}
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase refreshView) {
				mIsStart = false;
				if (isCity2Post) {
					city2postCodeQuery(pid, cid, did, q);
				} else {
					postCode2cityQuery(post);
				}
			}
		};
		refreshListView.setOnRefreshListener(refreshListener);

	}

	 void city2postCodeQuery(String pid, String cid, String did, String q) {
		Parameters params = new Parameters();
		params.add("pid", pid);
		params.add("cid", cid);
		params.add("did", did);
		params.add("q", q);
		LogHelper.logd(pid + "  " + cid + "  " + did + "  " + q);
		setPagesize(params);
		JuheWeb.getJuheData(params, 66, "http://v.juhe.cn/postcode/search",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						thissuccess(result);

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

					@Override
					public void requestEnd() {
						thisrequestEnd();

					}

				});

	}

	 void setPagesize(Parameters params) {
		if (mIsStart) {
			page = 1;
		} else {
			page++;
		}
		params.add("page", page);
		params.add("pagesize", size);

	}

	 void postCode2cityQuery(String post) {

		Parameters params = new Parameters();
		params.add("postcode", post);
		setPagesize(params);
		JuheWeb.getJuheData(params, 66, "http://v.juhe.cn/postcode/query",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						thissuccess(result);

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

					@Override
					public void requestEnd() {
						thisrequestEnd();

					}

				});

	}

	 void thissuccess(String result) {
		Map<String, String> map = JsonUtil
				.getNewApiJsonQuery(result.toString());
		String message = map.get("message");

		String lists = map.get("list");
		if (TextUtils.isEmpty(lists)) {
			if (!TextUtils.isEmpty(message)) {
				PhoneHelper.getInstance().show(message);
			}

			return;
		}
		PostcodeInfo info = JsonHelper.getInstance().getObject(lists, PostcodeInfo.class);
		if (info != null && info.getList()!=null&&info.getList().size() > 0) {
			if (page == 1) {
				list.clear();
			}
			list.addAll(info.getList());

			adapter.notifyDataSetChanged();
			refreshListView.setPullLoadEnabled(true);
			String totalcount = info.getTotalcount();
			String pagesize = info.getPagesize();
			String totalpage = info.getTotalpage();
			String currentpage = info.getCurrentpage();
//			tv_sum.setVisibility(View.VISIBLE);
			tv_sum.setText("总条数：" + totalcount + "        " + currentpage + "/"
					+ totalpage);
			if (currentpage.equals(totalpage)) {
				refreshListView.setHasMoreData(false);
				refreshListView.setPullLoadEnabled(false);
			}
		} else {
			refreshListView.setHasMoreData(false);
			refreshListView.setPullLoadEnabled(false);
		}

	}

	 void thisrequestEnd() {
		refreshListView.onPullDownRefreshComplete();
		refreshListView.onPullUpRefreshComplete();
		refreshListView.setHasMoreData(false);
		AnimeHepler.getInstance().setNoDataEmptyView(context, listview,
				R.drawable.empty_cry, "",
				null);

	}

}

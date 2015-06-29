package com.canyinghao.canquery.activity.expressage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.ExpressageAdapter;
import com.canyinghao.canquery.model.ExpressageCompanyInfo;
import com.canyinghao.canquery.model.ExpressageInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ExpressageQueryResultActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.listview)
	 ListView listview;
	 List<ExpressageInfo> list;
	 ExpressageAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_expressage_result_activity);
		ButterKnife.inject(this);
		setView();

		list = new ArrayList<ExpressageInfo>();

		adapter = new ExpressageAdapter(context, list);
		listview.setAdapter(adapter);
		AnimeHepler.getInstance().setAnimationEmptyView(context, listview, null, "");
		getIntentData();

	}

	 void setView() {


         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "查询结果", new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();



             }
         },null);

	}

	 void getIntentData() {
		Intent intent = getIntent();
		if (intent.hasExtra("list")) {
			List<ExpressageInfo> mlist = (List<ExpressageInfo>) intent
					.getSerializableExtra("list");
			if (mlist != null) {
				list.addAll(mlist);

				adapter.notifyDataSetChanged();
			}
		}

		if (intent.hasExtra("com")) {
			String com = intent.getStringExtra("com");
			String no = intent.getStringExtra("no");
			
			try {
				List<ExpressageCompanyInfo> findAll = DbUtils.create(
						App.getContext()).findAll(ExpressageCompanyInfo.class);
				if (findAll!=null) {
					for (ExpressageCompanyInfo eInfo : findAll) {
						if (com.contains(eInfo.getCom())
								|| eInfo.getCom().contains(com)) {

							expressageSearch(eInfo.getNo(), no);
							break;
						}

					}
				}
				
				
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}

	}

	 void expressageSearch(String com, String no) {


		Parameters params = new Parameters();
		params.add("com", com);
		params.add("no", no);
		JuheWeb.getJuheData(params, 43, "http://v.juhe.cn/exp/index",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");
						if (map.get("code").equals("200")) {
							String results = map.get("list");

							try {
								JSONObject jObject = new JSONObject(results);
								String lists = jObject.optString("list");

								List<ExpressageInfo> mlist = JsonHelper.getInstance().getList(
												lists,
												new TypeToken<List<ExpressageInfo>>() {
												});
								if (mlist != null) {
									list.addAll(mlist);

                                    String str="";

                                    for (ExpressageInfo info:mlist){
                                        str+=info.getRemark();


                                    }

                                    setShareContent("快递",str);

									adapter.notifyDataSetChanged();

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							if (!TextUtils.isEmpty(message)) {
								PhoneHelper.getInstance().show(message);
							}
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

}

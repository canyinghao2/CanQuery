package com.canyinghao.canquery.activity.expressage;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.model.ExpressageCompanyInfo;
import com.canyinghao.canquery.model.ExpressageHistroty;
import com.canyinghao.canquery.model.ExpressageInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.AnimeUtil;
import com.canyinghao.canquery.util.JsonUtil;
import com.canyinghao.canquery.view.dialog.CustomListDialog;
import com.canyinghao.canquery.view.dialog.ExpressageDropPopupWindow;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ExpressageQueryActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.et_query_number)
	 EditText et_query_number;
	@InjectView(R.id.tv_query_company)
	 TextView tv_query_company;
	@InjectView(R.id.tv_query)
	 TextView tv_query;


    @InjectView(R.id.fl_ad)
    FrameLayout fl_ad;

	 String strs[];
	 List<ExpressageCompanyInfo> list;

	 String com;

	public void setCom(String com) {
		this.com = com;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_expressage_activity);
		ButterKnife.inject(this);

		setView();
		list = new ArrayList<ExpressageCompanyInfo>();
		saveData();

        AnimeUtil.addAdFrameLayout(context, fl_ad);
	}

	 void setView() {



         setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "快递物流", new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 finish();



             }
         },null);

	}

	@OnClick({ R.id.tv_query, R.id.tv_query_company, R.id.iv_query_hsitory })
	public void click(View v) {
		switch (v.getId()) {

		case R.id.tv_query:
			String no = et_query_number.getText().toString();
			if (TextUtils.isEmpty(no)) {

				PhoneHelper.getInstance().show("请输入快递单号！");
				return;
			}
			if (TextUtils.isEmpty(com)) {

				PhoneHelper.getInstance().show("请选择快递公司！");
				return;
			}

//			expressageSearch(v, com, no);

            IntentHelper.getInstance().showIntent(
                    context,
                    ExpressageQueryResultActivity.class,new String[]{"com","no"},new String[]{com,no});

			break;
		case R.id.tv_query_company:

			showDialog();

			break;

		case R.id.iv_query_hsitory:

			try {
				List<ExpressageHistroty> findAll = DbUtils.create(
						App.getContext()).findAll(
						Selector.from(ExpressageHistroty.class));
				if (findAll != null && findAll.size() > 0) {

					new ExpressageDropPopupWindow(context, et_query_number,
							tv_query_company, findAll);
				}
			} catch (DbException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			break;
		}
	}

	 void expressageCompany() {
		Parameters params = new Parameters();
		JuheWeb.getJuheData(params, 43, "http://v.juhe.cn/exp/com",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");
						if (map.get("code").equals("200")) {
							String lists = map.get("list");
							if (TextUtils.isEmpty(lists)) {
								return;
							}
							final List<ExpressageCompanyInfo> mlist = JsonHelper
									.getInstance()
									.getList(
											lists,
											new TypeToken<List<ExpressageCompanyInfo>>() {
											});
							if (mlist != null) {
								try {
									DbUtils.create(App.getContext())
											.saveBindingIdAll(mlist);
								} catch (DbException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								list.clear();
								list.addAll(mlist);

							}

						}

					}

					@Override
					public void requestEnd() {
						// TODO Auto-generated method stub

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

				});

	}

	 void saveData() {
		try {
			List<ExpressageCompanyInfo> findAll = DbUtils.create(
					App.getContext()).findAll(ExpressageCompanyInfo.class);

			if (findAll != null && findAll.size() > 0) {
				list.clear();
				list.addAll(findAll);

			} else {
				expressageCompany();
			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 void showDialog() {
		if (list.size() == 0) {
			return;
		}
		strs = new String[list.size()];

		for (int i = 0; i < list.size(); i++) {
			strs[i] = list.get(i).getCom();
		}





//         AlertDialog.Builder builder=  new AlertDialog.Builder(context);
//         builder.setItems(strs,new DialogInterface.OnClickListener() {
//             @Override
//             public void onClick(DialogInterface dialogInterface, int position) {
//                 String text = strs[position];
//
//                 tv_query_company.setText(text);
//                 com = list.get(position).getNo();
//             }
//         });
//
//         builder.setTitle("请选择快递公司");
//         builder.show();





		new CustomListDialog(context, "请选择快递公司", strs,
				new CustomListDialog.OnItemClick() {

					@Override
					public void itemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
                        String text = strs[position];

                 tv_query_company.setText(text);
                 com = list.get(position).getNo();

					}
				}).show();

	}

	 void expressageSearch(final View view, String com, String no) {
		if (!PhoneHelper.getInstance().isNetworkConnected()) {
			PhoneHelper.getInstance().show("请检查网络连接～");
			return;
		}

		view.setEnabled(false);

		Parameters params = new Parameters();
		params.add("com", com);
		params.add("no", no);

		try {
			ExpressageHistroty findFirst = DbUtils.create(App.getContext())
					.findFirst(
							Selector.from(ExpressageHistroty.class).where(
									"num", "=", no));

			if (findFirst == null) {

				final ExpressageHistroty histroty = new ExpressageHistroty(no,
						tv_query_company.getText().toString(), com);
				try {
					DbUtils.create(App.getContext()).saveBindingId(
							histroty);
				} catch (DbException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} catch (DbException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

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

								List<ExpressageInfo> mlist = JsonHelper
										.getInstance()
										.getList(
												lists,
												new TypeToken<List<ExpressageInfo>>() {
												});
								if (mlist != null) {

									if (!isFinishing()) {
										IntentHelper.getInstance().showIntent(
												context,
												ExpressageQueryResultActivity.class,
												new String[] { "list" },
												new Serializable[] { (Serializable) mlist });
									}

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
						view.setEnabled(true);


					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}
				});

		
	}

}

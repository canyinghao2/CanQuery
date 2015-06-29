package com.canyinghao.canquery.activity.trainticket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.DateHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.TrainTicketListAdapter;
import com.canyinghao.canquery.model.TrainTicketInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.google.gson.reflect.TypeToken;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class TrainTicketQueryListActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
	@InjectView(R.id.tv_date)
	TextView tv_date;

	@InjectView(R.id.listview)
	ListView listview;
	@InjectView(R.id.ll_rg)
	View ll_rg;
	@InjectView(R.id.rb_rg_1)
	RadioButton rb_rg_1;
	@InjectView(R.id.rb_rg_2)
	RadioButton rb_rg_2;
	@InjectView(R.id.rg_most)
	RadioGroup rg_most;
	@InjectView(R.id.rg)
	RadioGroup rg;

	List list;
	List list1;
	List list2;
	TrainTicketListAdapter adapter;
	int rbIndex;
	String from = "";
	String to = "";
	String date = "";
	String date2 = "";
	String tt = "";

	int lastRb;
	int nextRb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_trainticket_list_activity);
		ButterKnife.inject(this);
		list = new ArrayList();
		list1 = new ArrayList();
		list2 = new ArrayList();
		getIntentData();
		setView();
		adapter = new TrainTicketListAdapter(context, list);
		listview.setAdapter(adapter);
		AnimeHepler.getInstance().setAnimationEmptyView(context, listview,
				null, "");

        setListener();
	}

	void setView() {
		rb_rg_1.setText("出发");
		rb_rg_2.setText("返回");

		

        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "",from + "-" + to, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);
		setTvData(date);

	}

	void setTvData(String date) {
		String dateS = DateHelper.getInstance().stringDateToStringData(
				"yyyy-MM-dd", "yyyy-MM-dd E", date);
		tv_date.setText(dateS);

	}

	void getIntentData() {

		Intent intent = getIntent();
		if (intent.hasExtra("from")) {
			from = intent.getStringExtra("from");

		}
		if (intent.hasExtra("to")) {
			to = intent.getStringExtra("to");
		}
		if (intent.hasExtra("date")) {
			date = intent.getStringExtra("date");
		}
		if (intent.hasExtra("date2")) {
			date2 = intent.getStringExtra("date2");
		}
		if (intent.hasExtra("tt")) {
			tt = intent.getStringExtra("tt");
		}
		LogHelper.logd(from + to + date + date2 + tt);
		trainTicketQuery(from, to, date, tt, false, false);
		if (TextUtils.isEmpty(date2)) {
			ll_rg.setVisibility(View.GONE);
		} else {

			ll_rg.setVisibility(View.VISIBLE);
		}

	}

	@OnClick({ R.id.tv_down, R.id.tv_up })
	public void click(final View v) {

		AnimeHepler.getInstance().startAnimation(context, v,
				R.anim.small_2_big, new OnAnimEnd() {

					@Override
					public void end() {

                        list.clear();
                        AnimeHepler.getInstance().setAnimationEmptyView(context, listview,
                                null, "");

						switch (v.getId()) {
						case R.id.tv_down:
							getNextQueryList(-1);

							break;
						case R.id.tv_up:
							getNextQueryList(1);
							break;

						}

					}
				});
	}

	void getNextQueryList(int next) {

		String dateS = "";
		boolean isindex = isIndex();
		if (rbIndex == 0) {

			Date date3 = DateHelper.getInstance().getDate(date);
			Calendar c = Calendar.getInstance();
			c.setTime(date3);
			date = DateHelper.getInstance().getDayByDate(c,
					Calendar.DAY_OF_MONTH, next);

			dateS = date;
			trainTicketQuery(from, to, dateS, tt, isindex, true);
		} else {
			Date date3 = DateHelper.getInstance().getDate(date2);
			Calendar c = Calendar.getInstance();
			c.setTime(date3);
			date2 = DateHelper.getInstance().getDayByDate(c,
					Calendar.DAY_OF_MONTH, next);
			dateS = date2;
			trainTicketQuery(to, from, dateS, tt, isindex, true);
		}

		setTvData(dateS);

	}

	boolean isIndex() {
		if (rbIndex == 0) {
			return false;
		} else {
			return true;
		}

	}

	@SuppressWarnings("unchecked")
	void sortList(final int type) {

		Collections.sort(list, new Comparator<TrainTicketInfo>() {

			@Override
			public int compare(TrainTicketInfo info1, TrainTicketInfo info2) {

				int compare = 0;
				switch (type) {
				case 0:
					compare = info1.getStart_time().compareToIgnoreCase(
							info2.getStart_time());
					break;
				case 1:
					compare = info1.getLishi().compareToIgnoreCase(
							info2.getLishi());
					break;
				case 2:
					compare = info1.getArrive_time().compareToIgnoreCase(
							info2.getArrive_time());
					break;

				}
				return compare;
			}

		});

		adapter.notifyDataSetChanged();

	}

	private void setListener() {
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
                list.clear();
                AnimeHepler.getInstance().setAnimationEmptyView(context, listview,
                        null, "");
				switch (checkedId) {
				case R.id.rb_rg_1:
					rbIndex = 0;
					if (list1.size() == 0) {
						trainTicketQuery(from, to, date, tt, false, true);
					} else {
						list.clear();
						list.addAll(list1);
						adapter.notifyDataSetChanged();
					}
					setTvData(date);
					 toolbar.setSubtitle(from + "-" + to);
					break;
				case R.id.rb_rg_2:
					rbIndex = 1;

					if (list2.size() == 0) {
						trainTicketQuery(to, from, date2, tt, true, true);
					} else {
						list.clear();
						list.addAll(list2);
						adapter.notifyDataSetChanged();
					}

					setTvData(date2);
					 toolbar.setSubtitle(to + "-" + from);
					break;

				}
				int at = 0;
				if (rbIndex == 0) {
					at = lastRb;
				} else {
					at = nextRb;
				}
				RadioButton childAt = (RadioButton) rg_most.getChildAt(at);
				childAt.setChecked(true);

			}
		});

		rg_most.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int checkedId) {
				int type = 0;
				switch (checkedId) {
				case R.id.rb_most_from:
					type = 0;
					break;
				case R.id.rb_most_time:
					type = 1;
					break;
				case R.id.rb_most_to:
					type = 2;
					break;

				}

				sortList(type);
				if (rbIndex == 0) {
					lastRb = type;
				} else {
					nextRb = type;
				}

			}
		});

	}

	void trainTicketQuery(String from, String to, String date, String tt,
			final boolean isIndex, boolean isiv) {

		Parameters params = new Parameters();
		params.add("from", from);
		params.add("to", to);
		params.add("date", date);
		params.add("tt", tt);


		JuheWeb.getJuheData(params, 22, "http://apis.juhe.cn/train/yp",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");

						String lists = map.get("list");
						if (isIndex) {
							list2.clear();
						} else {
							list1.clear();

						}

							if (isIndex == isIndex()) {
								list.clear();
								adapter.notifyDataSetChanged();
							}


						List<TrainTicketInfo> mlist = JsonHelper.getInstance()
								.getList(lists,
										new TypeToken<List<TrainTicketInfo>>() {
										});

                           if (mlist!=null){

                               if (isIndex) {
                                   list2.addAll(mlist);
                               } else {
                                   list1.addAll(mlist);
                               }

                               if (isIndex == isIndex()) {
                                   list.clear();
                                   list.addAll(mlist);
                                   adapter.notifyDataSetChanged();
                               }

                           }




					}

					@Override
					public void requestEnd() {



						AnimeHepler.getInstance().setNoDataEmptyView(context,
								listview, R.drawable.empty_cry,
								"", null);

					}

					@Override
					public void fail(int err, String reason, String result) {

						if (isIndex) {
							list2.clear();
						} else {
							list1.clear();

						}

						list.clear();

					}

				});

	}

}

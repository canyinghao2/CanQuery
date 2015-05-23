package com.canyinghao.canquery.activity.cookbook;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.CookBookGridAdapter;
import com.canyinghao.canquery.model.CookBookIndexInfo;
import com.canyinghao.canquery.model.CookBookInfo;
import com.canyinghao.canquery.model.CookBookInfo.Data;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.canyinghao.canquery.view.textview.TextDrawable;
import com.google.gson.reflect.TypeToken;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CookBookMainActivity extends BaseBarActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

	@InjectView(R.id.et_search)
	 EditText et_search;

	@InjectView(R.id.iv_search)
	 ImageView iv_search;
	@InjectView(R.id.ll_hsv)
	 LinearLayout ll_hsv;
	@InjectView(R.id.gridview)
	 GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_cookbook_activity);
		ButterKnife.inject(this);


        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "菜谱大全", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);

		getCookDetail();
		getIndex();
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

						IntentHelper.getInstance().showIntent(context, CookBookSearchActivity.class,
								new String[] { "cook" },
								new Serializable[] { trim });

					}
				});

	}


	 void getCookDetail() {

		Parameters params = new Parameters();
		params.add("cid", 3);
		params.add("pn", 0 + "");
		params.add("rn", 4 + "");
		// params.add("albums", albums);
		// AnimeHepler.getInstance().setAnimationEmptyView(context, gridview, true);
		JuheWeb.getJuheData(params, 46, "http://apis.juhe.cn/cook/index",
				JuheData.GET, new JuheRequestCallBack() {

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

							CookBookGridAdapter adapter = new CookBookGridAdapter(
									context, mlist);
							gridview.setAdapter(adapter);
                            gridview.invalidate();

						}

					}

					@Override
					public void requestEnd() {

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

				});

	}

	 void getIndex() {

		Parameters params = new Parameters();

		JuheWeb.getJuheData(params, 46, "http://apis.juhe.cn/cook/category",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");
						if (map.get("code").equals("200")) {
							String lists = map.get("list");

							List<CookBookIndexInfo> mlist = JsonHelper.getInstance().getList(
											lists,
											new TypeToken<List<CookBookIndexInfo>>() {
											});

							for (int i = 0; i < mlist.size(); i++) {

								final CookBookIndexInfo info = mlist.get(i);
								LinearLayout ll = new LinearLayout(context);
								TextView tv_1 = new TextView(context);
								TextView tv = new TextView(context);
								ll.setOrientation(LinearLayout.HORIZONTAL);
								ll.setGravity(Gravity.CENTER);

								int px = BitmapHelper.getInstance().dp2Px(10);
								ll.setPadding(0, px, 0, px);
								int width = PhoneHelper.getInstance().getScreenDisplayMetrics().widthPixels;
								ll.setLayoutParams(new LayoutParams(width / 4,
										LayoutParams.WRAP_CONTENT));
								Random random = new Random();

								int co = Color.rgb(random.nextInt(200) + 20,
										random.nextInt(200) + 20,
										random.nextInt(200) + 20);
								TextDrawable td = TextDrawable.builder()
										.buildRound("", co);
								LayoutParams layoutParams = new LinearLayout.LayoutParams(
										30, 30);
								layoutParams.setMargins(0, 0, 10, 0);
								tv_1.setLayoutParams(layoutParams);
								tv_1.setBackgroundDrawable(td);

								tv.setText(info.getName());
								tv.setTextSize(12);

								ll.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {

										AnimeHepler.getInstance().startAnimation(context, v,
												R.anim.small_2_big,
												new OnAnimEnd() {

												

													@Override
													public void end() {

														IntentHelper.getInstance().showIntent(
																context,
																CookBookSearchActivity.class,
																new String[] { "indexData" },
																new Serializable[] { info });

													}
												});

									}
								});
								ll.addView(tv_1);
								ll.addView(tv);
								ll_hsv.addView(ll);

							}

						}

					}

					@Override
					public void requestEnd() {

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

				});

	}

}

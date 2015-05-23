package com.canyinghao.canquery.activity.postcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.ViewPagerAdapter;
import com.canyinghao.canquery.model.CityString;
import com.canyinghao.canquery.model.PostcodeCityInfo;
import com.canyinghao.canquery.model.PostcodeCityInfo.City;
import com.canyinghao.canquery.model.PostcodeCityInfo.City.District;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.AnimeUtil;
import com.canyinghao.canquery.util.JsonUtil;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class PostCodeQueryActivity extends BaseBarActivity {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;
	@InjectView(R.id.ll_main)
	 View ll_main;
    @InjectView(R.id.pager)
    public ViewPager pager;
    ViewPagerAdapter pagerAdapter;

	 View tl_city2post;

	 View ll_post2city;


	 TextView tv_province;


	 TextView tv_city;


	 TextView tv_district;
	 TextView tv_search;

    TextView tv_query;


	 EditText et_street;

	 EditText et_post;

	@InjectView(R.id.rg)
	RadioGroup rg;
	@InjectView(R.id.rb_rg_1)
	 RadioButton rb_rg_1;
	@InjectView(R.id.rb_rg_2)
	 RadioButton rb_rg_2;

	 List<PostcodeCityInfo> list1;
	 List<PostcodeCityInfo.City> list2;
	 List<PostcodeCityInfo.City.District> list3;

	 String pid;
	 String cid;
	 String did;

	 String KEY = "PostCode";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_postcode_activity);
		ButterKnife.inject(this);
		list1 = new ArrayList<PostcodeCityInfo>();
		list2 = new ArrayList<PostcodeCityInfo.City>();
		list3 = new ArrayList<PostcodeCityInfo.City.District>();

        initView();

        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "邮编", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);
		isSaveCity();
	}


    List<View> getListData() {
        List<View> vList = new ArrayList<View>();



        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.query_postcode_item, null);

            tl_city2post=  view.findViewById(R.id.tl_city2post);
            ll_post2city=  view.findViewById(R.id.ll_post2city);
            LinearLayout ll_ad= (LinearLayout) view.findViewById(R.id.ll_ad);


            AnimeUtil.addAdFrameLayout(context,ll_ad);


            switch (i){


                case 0:

                    tv_province= (TextView) view.findViewById(R.id.tv_province);
                    tv_city= (TextView) view.findViewById(R.id.tv_city);
                    tv_district= (TextView) view.findViewById(R.id.tv_district);
                    tv_query= (TextView) view.findViewById(R.id.tv_query);
                    et_street= (EditText) view.findViewById(R.id.et_street);


                    tv_province.setOnClickListener(click);
                    tv_city.setOnClickListener(click);
                    tv_district.setOnClickListener(click);

                    tv_city.setOnClickListener(click);

                    tv_query.setOnClickListener(click);


                    ll_post2city.setVisibility(View.GONE);
                    tl_city2post.setVisibility(View.VISIBLE);

                    break;

                case 1:
                    tv_search=(TextView) view.findViewById(R.id.tv_search);
                    et_post= (EditText) view.findViewById(R.id.et_post);

                    tv_search.setOnClickListener(click);


                    ll_post2city.setVisibility(View.VISIBLE);
                    tl_city2post.setVisibility(View.GONE);
                    break;
            }



            vList.add(view);
        }
        return vList;

    }







    private void initView() {
        rb_rg_1.setText("地址-邮编");
        rb_rg_2.setText("邮编-地址");
        List<View> vList = getListData();
        pagerAdapter = new ViewPagerAdapter(vList);
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

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

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup rg, int rid) {
                int count = rg.getChildCount();
                int num = 0;
                for (int i = 0; i < count; i++) {

                    if (rg.getChildAt(i).getId() == rid) {
                        num = i;
                        break;
                    }
                }

                pager.setCurrentItem(num);

            }
        });

    }


    View.OnClickListener click=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_query:
                    String province = tv_province.getText().toString();
                    String city = tv_city.getText().toString();
                    String district = tv_district.getText().toString();
                    if (TextUtils.isEmpty(province)) {
                        PhoneHelper.getInstance().show("请选择省份");
                        return;
                    }
                    if (TextUtils.isEmpty(city)) {
                        PhoneHelper.getInstance().show("请选择城市");
                        return;
                    }
                    if (TextUtils.isEmpty(district)) {
                        PhoneHelper.getInstance().show("请选择区县");
                        return;
                    }


                    IntentHelper.getInstance().showIntent(context, PostCodeResultQueryActivity.class,
                            new String[]{"pid", "cid", "did", "q"},
                            new Serializable[]{pid, cid, did,
                                    et_street.getText().toString().trim()});
                    break;
                case R.id.tv_search:
                    String post = et_post.getText().toString().trim();
                    if (TextUtils.isEmpty(post)) {
                        PhoneHelper.getInstance().show("请输入邮编");
                        return;
                    }


                    IntentHelper.getInstance().showIntent(context, PostCodeResultQueryActivity.class,
                            new String[]{"postcode"}, new Serializable[]{post});

                    break;
                case R.id.tv_province:
                    if (list1.size() > 0) {


                        IntentHelper.getInstance().showIntentForResult(context,
                                PostCodeCityQueryActivity.class, new String[]{"type",
                                        "list"}, new Serializable[]{0,
                                        (Serializable) list1}, 100);
                    }

                    break;
                case R.id.tv_city:
                    if (list2.size() > 0) {


                        IntentHelper.getInstance().showIntentForResult(context,
                                PostCodeCityQueryActivity.class, new String[]{"type",
                                        "list"}, new Serializable[]{1,
                                        (Serializable) list2}, 100);
                    } else {
                        PhoneHelper.getInstance().show("请先选择省份");
                    }
                    break;
                case R.id.tv_district:
                    if (list3.size() > 0) {


                        IntentHelper.getInstance().showIntentForResult(context,
                                PostCodeCityQueryActivity.class, new String[]{"type",
                                        "list"}, new Serializable[]{2,
                                        (Serializable) list3}, 100);
                    } else {
                        PhoneHelper.getInstance().show("请先选择城市");
                    }
                    break;

            }


        }};




	 void isSaveCity() {

		try {
			final CityString findFirst = DbUtils.create(App.getContext())
					.findFirst(
							Selector.from(CityString.class).where("key", "=",
									KEY));

			if (findFirst != null) {

				List<PostcodeCityInfo> mlist = JsonHelper.getInstance().getList(
						findFirst.getCity(),
						new TypeToken<List<PostcodeCityInfo>>() {
						});
				list1.clear();
				list1.addAll(mlist);

			} else {

				postCodeCityQuery();

			}

		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	 void postCodeCityQuery() {
		Parameters params = new Parameters();

		JuheWeb.getJuheData(params, 66, "http://v.juhe.cn/postcode/pcd",
				JuheData.GET, new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						Map<String, String> map = JsonUtil
								.getNewApiJsonQuery(result.toString());
						String message = map.get("message");

						String lists = map.get("list");
						if (TextUtils.isEmpty(lists)) {
							return;
						}
						List<PostcodeCityInfo> mlist = JsonHelper.getInstance().getList(
								lists, new TypeToken<List<PostcodeCityInfo>>() {
								});
						if (mlist != null) {

							list1 = mlist;

							try {
								long count = DbUtils.create(App.getContext())
										.count(Selector.from(CityString.class)
												.where("key", "=", "PostCode"));
								if (count < 1) {
									final CityString cityString = new CityString();
									cityString.setCity(lists);
									cityString.setKey(KEY);
									
									
									try {
										DbUtils.create(App.getContext())
										.saveBindingId(cityString);
									} catch (DbException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
								}

							} catch (DbException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						} else {
							PhoneHelper.getInstance().show(message);
						}

					}

					@Override
					public void fail(int err, String reason, String result) {
						// TODO Auto-generated method stub

					}

					@Override
					public void requestEnd() {
						// TODO Auto-generated method stub

					}

				});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		LogHelper.logd("onActivityResult");
		if (data != null && resultCode == 100) {

			int type = 0;
			String text = "";
			String id = "";

			int position = 0;
			if (data.hasExtra("type")) {
				type = data.getIntExtra("type", 0);

			}
			if (data.hasExtra("position")) {
				position = data.getIntExtra("position", 0);
			}
			if (data.hasExtra("text")) {
				text = data.getStringExtra("text");
			}
			if (data.hasExtra("id")) {
				id = data.getStringExtra("id");
			}

			switch (type) {
			case 0:
				tv_province.setText(text);
				pid = id;
				LogHelper.logd("position" + position);
				List<City> city2 = list1.get(position).getCity();
				list2.clear();
				list2.addAll(city2);
				if (list2.size() > 0) {
					City city = list2.get(0);
					tv_city.setText(city.getCity());
					cid = city.getId();
					list3.clear();
					list3.addAll(city.getDistrict());
					if (city.getDistrict().size() > 0) {
						District district = city.getDistrict().get(0);
						tv_district.setText(district.getDistrict());
						did = district.getId();

					}
				}

				break;
			case 1:
				tv_city.setText(text);
				cid = id;
				list3.clear();
				list3.addAll(list2.get(position).getDistrict());
				if (list3.size() > 0) {
					District district = list3.get(0);
					tv_district.setText(district.getDistrict());
					did = district.getId();
				}
				break;
			case 2:
				tv_district.setText(text);
				did = id;

				break;

			}

		}

	}



}

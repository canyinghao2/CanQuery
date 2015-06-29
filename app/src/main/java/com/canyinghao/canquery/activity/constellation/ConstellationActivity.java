package com.canyinghao.canquery.activity.constellation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.SPHepler;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.ViewPagerAdapter;
import com.canyinghao.canquery.model.ConstellationInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.AnimeUtil;
import com.canyinghao.canquery.view.dialog.ConstellationPopupWindow;
import com.canyinghao.canquery.view.textview.TextDrawable;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ConstellationActivity extends BaseBarActivity {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.ll_main)
    LinearLayout ll_main;
	@InjectView(R.id.rg)
	 RadioGroup rg;
	@InjectView(R.id.pager)
	public ViewPager pager;
	@InjectView(R.id.tv_con)
	public TextView tv_con;
	public static String TAG="com.canyinghao.canquery.activity.constellation.ConstellationActivity";

	 String[] types = { "today", "tomorrow", "week" };
	 ViewPagerAdapter pagerAdapter;
	 int num;
	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.query_constellation_activity);

		ButterKnife.inject(this);

		num = SPHepler.getInstance().getInt(TAG);
		setCons();
		initView();

        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "星座运势", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();



            }
        },null);
	
	}
	
	
	
	 void setCons() {
         Random random = new Random();

         int co = Color.rgb(random.nextInt(200) + 20,
                 random.nextInt(200) + 20,
                 random.nextInt(200) + 20);
         TextDrawable td = TextDrawable.builder()
                 .buildRound("", co);
		tv_con.setBackgroundDrawable(td);
		tv_con.setText(ConstellationPopupWindow.cons[num]+"座");
		RadioButton childAt = (RadioButton) rg.getChildAt(0);
		childAt.setChecked(true);

	}
	
	
	 List<View> getListData() {
		List<View> vList = new ArrayList<View>();

		for (int i = 0; i < 3; i++) {
			View view = LayoutInflater.from(context).inflate(
					R.layout.query_constellation_item1, null);

            LinearLayout ll_view= (LinearLayout) view.findViewById(R.id.ll_view);
            AnimeUtil.addAdFrameLayout(context, ll_view);
			String str = tv_con.getText().toString();
			constellationQuery(str, i, view);
			vList.add(view);
		}
		return vList;

	}

	 void initView() {
		List<View> vList = getListData();
		pagerAdapter = new ViewPagerAdapter(vList);
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

	}

	 void constellationQuery(final  String consName, final int type,
			final View view) {
		Parameters params = new Parameters();
		params.add("consName", consName);
		params.add("type", types[type]);
		JuheWeb.getJuheData(params, 58,
				"http://web.juhe.cn:8080/constellation/getAll", JuheData.GET,
				new JuheRequestCallBack() {

					@Override
					public void success(int err, String reason, String result) {
						ConstellationInfo info = JsonHelper.getInstance().getObject(result,
								ConstellationInfo.class);

                        if (info==null){

                            return;
                        }

						LinearLayout lv = (LinearLayout) view.findViewById(R.id.ll_view);
						View tl = view.findViewById(R.id.tl);
						View ll = view.findViewById(R.id.ll);
						TextView tv_date = (TextView) view
								.findViewById(R.id.tv_date);
						LinearLayout ll_1 = (LinearLayout) view
								.findViewById(R.id.ll_1);
						LinearLayout ll_2 = (LinearLayout) view
								.findViewById(R.id.ll_2);
						LinearLayout ll_3 = (LinearLayout) view
								.findViewById(R.id.ll_3);
						LinearLayout ll_4 = (LinearLayout) view
								.findViewById(R.id.ll_4);
						LinearLayout ll_5 = (LinearLayout) view
								.findViewById(R.id.ll_5);
						LinearLayout ll_6 = (LinearLayout) view
								.findViewById(R.id.ll_6);
						LinearLayout ll_7 = (LinearLayout) view
								.findViewById(R.id.ll_7);
						LinearLayout ll_8 = (LinearLayout) view
								.findViewById(R.id.ll_8);




						if (type == 2) {
							tl.setVisibility(View.GONE);
							ll.setVisibility(View.GONE);

							addTextview(lv, info.getHealth(), 10);
							addTextview(lv, info.getJob(), 10);
							addTextview(lv, info.getLove(), 10);
							addTextview(lv, info.getMoney(), 10);
							addTextview(lv, info.getWork(), 10);

                            setShareContent(consName,info.getHealth()+info.getJob()+info.getLove()+info.getMoney()+info.getWork());

						} else {
							tv_date.setText(info.getDatetime());
							float all = getFloat(info.getAll());
							float love = getFloat(info.getLove());
							float work = getFloat(info.getWork());
							float money = getFloat(info.getMoney());
							float health = getFloat(info.getHealth());
							addStar(ll_1, all);
							addStar(ll_2, love);
							addStar(ll_3, work);
							addStar(ll_4, money);
							addStar(ll_5, health);
							addTextview(ll_6, info.getQFriend(), 5);
							addTextview(ll_7, info.getColor(), 5);
							addTextview(ll_8, info.getNumber(), 5);
							addTextview(lv, info.getSummary(), 10);

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

	 float getFloat(String a) {
		float result = 0;
		if (a.contains("%")) {
			String split = a.replace("%", "");

			result = Integer.parseInt(split) / (float) 20;
		}

		return result;

	}

	 void addStar(LinearLayout ll, float fl) {
		for (int i = 0; i < fl; i++) {
			ImageView iv = new ImageView(context);
			iv.setImageResource(R.drawable.query_star);
			ll.addView(iv);
		}

	}

	 void addTextview(LinearLayout ll, String str, int num) {
		TextView tv = new TextView(context);
		tv.setTextColor(context.getResources().getColor(R.color.blue_gray_500));
		int _5dp = BitmapHelper.getInstance().dp2Px(num);
		tv.setPadding(_5dp, _5dp, _5dp, _5dp);
		tv.setText(str);
		tv.setTextSize(14);
		ll.addView(tv);

	}

	@OnClick(R.id.tv_con)
	public void click(View v) {
		
		AnimeHepler.getInstance().startAnimation(context, v, R.anim.small_2_big, new OnAnimEnd() {
			
			
			
			@Override
			public void end() {
                ConstellationPopupWindow cp=new ConstellationPopupWindow(context);

                ll_main.startAnimation(AnimeUtil.getScaleAnime(true));
                cp.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {


                        ll_main.startAnimation(AnimeUtil.getScaleAnime(false));



                    }
                });
				
			}
		});

		
	}
	
	
	
	@Override
	public void onEventMainThread(Intent intent) {
		// TODO Auto-generated method stub
		super.onEventMainThread(intent);
		if (intent.getAction().equals(TAG)) {
			if (intent.hasExtra("num")) {
				
				num = intent.getIntExtra("num", 0);
				
				SPHepler.getInstance().setInt(TAG, num);
				setCons();
				List<View> vList = getListData();
				pagerAdapter = new ViewPagerAdapter(vList);
				pager.setAdapter(pagerAdapter);
			}
			
		}
	}

}

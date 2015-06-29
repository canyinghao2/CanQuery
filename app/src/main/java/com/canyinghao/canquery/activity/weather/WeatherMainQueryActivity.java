package com.canyinghao.canquery.activity.weather;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.SupportFragmentAdapter;
import com.canyinghao.canquery.fragment.BaseFragment;
import com.canyinghao.canquery.fragment.WeatherQueryFragment;
import com.canyinghao.canquery.model.CityString;
import com.canyinghao.canquery.model.WeatherCityInfo;
import com.canyinghao.canquery.model.WeatherInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.JsonUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class WeatherMainQueryActivity extends BaseBarActivity {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.pager)
    public ViewPager pager;
    @InjectView(R.id.rg_point)
    RadioGroup rg_point;
    FragmentManager supportManager;

    SupportFragmentAdapter adapter;
    List<WeatherCityInfo> findAll;

    WeatherMainQueryActivity context;
    String cityName = "成都";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_weather_activity);
        ButterKnife.inject(this);
        context = this;

        setViewPager();
        isSave();


        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", cityName, new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_share:


                        showShare(toolbar);

                        break;
                    case R.id.action_city:


                        IntentHelper.getInstance().showIntent(context, WeatherSelectCityQueryActivity.class);
                        break;
                }


                return true;
            }
        });

    }

    void setViewPager() {
        final List<WeatherQueryFragment> list = buidData();

        supportManager = getSupportFragmentManager();

        adapter = new SupportFragmentAdapter(supportManager, list);
        pager.setAdapter(adapter);
        if (pager.getTag() == null) {
            pager.setCurrentItem(0);
            pager.setTag("");
        }
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                RadioButton rb = (RadioButton) rg_point.getChildAt(position);
                rb.setChecked(true);
                WeatherQueryFragment cityInfo = (WeatherQueryFragment) list
                        .get(position);
                toolbar.setSubtitle(cityInfo.getCity());

                WeatherInfo info = cityInfo.info;
                List<WeatherInfo.Data.Weather> weather = info.getData().getWeather();
                setShareContent(cityInfo.getCity() + "的天气", weather.get(0).getInfo().getDay().get(1) + "湿度："
                        + info.getData().getRealtime().getWeather().getHumidity()
                        + "%");

            }

            @Override
            public void onPageScrolled(int paramInt1, float paramFloat,
                                       int paramInt2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int paramInt) {
                // TODO Auto-generated method stub

            }
        });

    }

    List<WeatherQueryFragment> buidData() {
        List<WeatherQueryFragment> list = new ArrayList<WeatherQueryFragment>();

        WeatherInfo weatherInfo = null;
        try {
            findAll = DbUtils.create(App.getContext()).findAll(
                    WeatherCityInfo.class);

            Intent intent = getIntent();
            if (intent.hasExtra("weather")) {
                weatherInfo = (WeatherInfo) intent.getSerializableExtra("weather");


            }

            if (findAll != null && findAll.size() > 0) {

                for (int i = 0; i < findAll.size(); i++) {
                    WeatherCityInfo cityInfo = findAll.get(i);

                    WeatherInfo tempW = null;



                    if (weatherInfo!=null){

                        WeatherInfo.Data data = weatherInfo.getData();
                    if (data != null && data.getRealtime().getCity_name().contains(cityInfo.getCity())) {

                        tempW = weatherInfo;

                    }}

                    WeatherQueryFragment fragment = WeatherQueryFragment.getFragment(cityInfo.getCity(), tempW);


                    list.add(fragment);
                }

            } else {


                if (weatherInfo!=null) {
                    WeatherInfo.Data data = weatherInfo.getData();


                    if (data != null) {

                        cityName = data.getRealtime().getCity_name();

                    }
                }
                WeatherQueryFragment fragment = WeatherQueryFragment.getFragment(cityName, weatherInfo);

                list.add(fragment);
            }

            for (BaseFragment fragment : list) {
                WeatherQueryFragment fra = (WeatherQueryFragment) fragment;
                LogHelper.logd(fra.getCity());
            }
            if (list.size() == 1) {
                WeatherQueryFragment cityInfo = (WeatherQueryFragment) list
                        .get(0);
                if (cityInfo != null && !TextUtils.isEmpty(cityInfo.getCity())) {
                    toolbar.setSubtitle(cityInfo.getCity());
                }

            }


        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        rg_point.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            RadioButton view = (RadioButton) LayoutInflater.from(this).inflate(
                    R.layout.banner_point, null);
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                    BitmapHelper.getInstance().dp2Px(7), BitmapHelper.getInstance().dp2Px(7));
            params.setMargins(3, 0, 3, 0);
            view.setLayoutParams(params);

            rg_point.addView(view);

        }
        return list;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null && resultCode == 100) {

            List<WeatherQueryFragment> list = buidData();
            adapter.setFragments(list);

            if (data.hasExtra("num")) {
                int intExtra = data.getIntExtra("num", 0);
                pager.setCurrentItem(intExtra);
            } else {
                pager.setCurrentItem(0);
            }
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

                        try {
                            long count = DbUtils.create(App.getContext())
                                    .count(Selector.from(CityString.class)
                                            .where("key", "=", "PostCode"));
                            if (count < 1) {
                                final CityString cityString = new CityString();
                                cityString.setCity(lists);
                                cityString.setKey("PostCode");


                                try {
                                    DbUtils.create(App.getContext()).saveBindingId(
                                            cityString);
                                } catch (DbException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }

                        } catch (DbException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
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

    void isSave() {
        try {
            long count = DbUtils.create(App.getContext()).count(
                    Selector.from(CityString.class).where("key", "=",
                            "PostCode"));
            if (count < 1) {
                postCodeCityQuery();
            }
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather, menu);

        return true;
    }
}

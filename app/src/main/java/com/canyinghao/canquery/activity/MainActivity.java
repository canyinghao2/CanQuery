package com.canyinghao.canquery.activity;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.LogHelper;
import com.canyinghao.canhelper.SPHepler;
import com.canyinghao.canhelper.TouchHelper;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.constellation.ConstellationActivity;
import com.canyinghao.canquery.activity.cookbook.CookBookDetailActivity;
import com.canyinghao.canquery.activity.travel.TravelDetailActivity;
import com.canyinghao.canquery.activity.weather.WeatherMainQueryActivity;
import com.canyinghao.canquery.configs.QueryConfigs;
import com.canyinghao.canquery.model.ConstellationInfo;
import com.canyinghao.canquery.model.CookBookInfo;
import com.canyinghao.canquery.model.TodayHistroy;
import com.canyinghao.canquery.model.TravelInfo;
import com.canyinghao.canquery.model.WeatherInfo;
import com.canyinghao.canquery.util.AnimeUtil;
import com.canyinghao.canquery.util.QueryUtil;
import com.canyinghao.canquery.view.dialog.ConstellationPopupWindow;
import com.canyinghao.canquery.view.dialog.CustomDialog;
import com.canyinghao.canquery.view.dialog.MenuPop;
import com.canyinghao.canquery.view.scan.CaptureActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnTouch;
import cn.domob.android.ads.Updater;


public class MainActivity extends BaseBarActivity {


    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.tv_histroy)
    TextView tv_histroy;
    @InjectView(R.id.tv_time)
    TextView tv_time;
    @InjectView(R.id.tv_title)
    TextView tv_title;

    @InjectView(R.id.tl)
    View tl;
    @InjectView(R.id.ll_cookitem)
    View ll_cookitem;
    @InjectView(R.id.ll_travel)
    View ll_travel;

    @InjectView(R.id.ll_weather)
    LinearLayout ll_weather;
    @InjectView(R.id.ll_main)
    LinearLayout ll_main;
    List<TodayHistroy.Result> result;


    @InjectView(R.id.tv_weather_main)
    TextView tv_weather_main;
    @InjectView(R.id.tv_temp)
    TextView tv_temp;
    @InjectView(R.id.tv_update)
    TextView tv_update;
    @InjectView(R.id.tv_wind)
    TextView tv_wind;
    @InjectView(R.id.tv_humidity)
    TextView tv_humidity;
    @InjectView(R.id.tv_city)
    TextView tv_city;


    @InjectView(R.id.tv_pm25)
    TextView tv_pm25;
    final String TEMP = "℃";


    private WeatherInfo weatherInfo;
    private ConstellationInfo constellationInfo;

    private List<CookBookInfo.Data> mlist;
    private List<TravelInfo.Scenery> travelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSwipe = false;

        getWindow().setBackgroundDrawableResource(R.color.blue_gray_500);
        getWindow().getDecorView().setBackgroundResource(R.color.blue_gray_500);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);



        ButterKnife.inject(this);

        Updater.checkUpdate(this, QueryConfigs.PUBLISHER_ID);

        setView();


        QueryUtil.toh_Query(new QueryUtil.QueryCallBack() {
            @Override
            public void success(Object obj) {

                TodayHistroy info = (TodayHistroy) obj;
                result = info.getResult();

                if (result != null && result.size() > 0) {
                    postResult();

                }


            }
        });


        QueryUtil.weatherQuery(new QueryUtil.QueryCallBack() {
            @Override
            public void success(Object obj) {


                weatherInfo = (WeatherInfo) obj;

                if (weatherInfo != null) {


                    getDataWeather();
                }


            }
        });


        int num = SPHepler.getInstance().getInt(ConstellationActivity.TAG);

        QueryUtil.constellationQuery(ConstellationPopupWindow.cons[num] + "座", new QueryUtil.QueryCallBack() {
            @Override
            public void success(Object obj) {


                constellationInfo = (ConstellationInfo) obj;


                if (constellationInfo != null) {


                    getDataConstellation();

                }


            }
        });


        QueryUtil.getCookDetail(new QueryUtil.QueryCallBack() {
            @Override
            public void success(Object obj) {


                CookBookInfo info = (CookBookInfo) obj;

                mlist = info.getData();


                if (mlist != null && mlist.size() > 0) {


                    getDataCookDetail(false);
                }


            }
        });


        QueryUtil.getTravelData(new QueryUtil.QueryCallBack() {
            @Override
            public void success(Object obj) {
                TravelInfo info = (TravelInfo) obj;

                if (info != null) {

                    travelList = info.getSceneryList();
                }


                if (travelList != null) {


                    getDataTravel();
                }

            }
        });


    }


    private void setView() {
        setToolbar(toolbar, R.drawable.ic_view_list_white, "", "", new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MenuPop mp = new MenuPop(context, toolbar);


                ll_main.startAnimation(AnimeUtil.getScaleAnime(true));
                mp.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {


                        ll_main.startAnimation(AnimeUtil.getScaleAnime(false));


                    }
                });
            }
        }, new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {

                    case R.id.action_sao:


                        IntentHelper.getInstance().showIntent(context, CaptureActivity.class);

                        break;
                    case R.id.action_set:

                        IntentHelper.getInstance().showIntent(context, AboutMeActivity.class);
                        break;

                }
                return false;
            }
        });

    }


    private void getDataWeather() {

        WeatherInfo.Data data = weatherInfo.getData();
        if (data == null) {
            return;
        }
        List<WeatherInfo.Data.Weather> weather = data.getWeather();
        if (weather == null || weather.size() <= 0) {
            return;
        }
        int weatherRid = QueryConfigs.getWeatherRid(weather.get(0)
                .getInfo().getDay().get(0));
        tv_weather_main.setCompoundDrawablesWithIntrinsicBounds(0,
                weatherRid, 0, 0);
        tv_weather_main.setText(weather.get(0).getInfo().getDay().get(1));

        tv_temp.setText(weatherInfo.getData().getRealtime().getWeather()
                .getTemperature()
                + TEMP);

        tv_update.setText(weatherInfo.getData().getRealtime().getTime() + "更新");
        tv_wind.setText(weatherInfo.getData().getRealtime().getWind().getDirect()
                + weatherInfo.getData().getRealtime().getWind().getPower());

        tv_humidity.setText("湿度："
                + weatherInfo.getData().getRealtime().getWeather().getHumidity()
                + "%");


        tv_pm25.setText(weatherInfo.getData().getPm25().getPm25().getQuality());


        tv_city.setText(data.getRealtime().getCity_name());

    }


    private void getDataConstellation() {


        View ll = tl.findViewById(R.id.ll);

        LinearLayout ll_1 = (LinearLayout) tl
                .findViewById(R.id.ll_1);
        LinearLayout ll_2 = (LinearLayout) tl
                .findViewById(R.id.ll_2);
        LinearLayout ll_3 = (LinearLayout) tl
                .findViewById(R.id.ll_3);
        LinearLayout ll_4 = (LinearLayout) tl
                .findViewById(R.id.ll_4);
        LinearLayout ll_5 = (LinearLayout) tl
                .findViewById(R.id.ll_5);
        LinearLayout ll_6 = (LinearLayout) tl
                .findViewById(R.id.ll_6);
        LinearLayout ll_7 = (LinearLayout) tl
                .findViewById(R.id.ll_7);
        LinearLayout ll_8 = (LinearLayout) tl
                .findViewById(R.id.ll_8);


        float all = getFloat(constellationInfo.getAll());
        float love = getFloat(constellationInfo.getLove());
        float work = getFloat(constellationInfo.getWork());
        float money = getFloat(constellationInfo.getMoney());
        float health = getFloat(constellationInfo.getHealth());
        addStar(ll_1, all);
        addStar(ll_2, love);
        addStar(ll_3, work);
        addStar(ll_4, money);
        addStar(ll_5, health);
        addTextview(ll_6, constellationInfo.getQFriend(), 5);
        addTextview(ll_7, constellationInfo.getColor(), 5);
        addTextview(ll_8, constellationInfo.getNumber(), 5);


    }

    private void getDataCookDetail(boolean isL) {


        int m = (int) (Math.random() * (mlist.size() - 1));

        final CookBookInfo.Data info = mlist.get(m);


        TextView tv_1 = (TextView) ll_cookitem.findViewById(R.id.tv_1);
        TextView tv_2 = (TextView) ll_cookitem.findViewById(R.id.tv_2);
        ImageView iv_2 = (ImageView) ll_cookitem.findViewById(R.id.iv_2);
        ImageView iv_1 = (ImageView) ll_cookitem.findViewById(R.id.iv_1);

        if (isL) {

            tv_1.setVisibility(View.GONE);
            tv_2.setVisibility(View.GONE);
        }

        String img = "";

        List<String> albums = info.getAlbums();
        if (albums.size() > 0) {
            img = albums.get(0);
        }
        AnimeUtil.getImageLoad().displayImage(img, iv_2,
                AnimeUtil.getImageOption());

        tv_1.setText(info.getTitle());

        String str = "用料：" + info.getIngredients() + ";" + info.getBurden();

        tv_2.setText(str);
        List<CookBookInfo.Data.Steps> stepList = info.getSteps();

        if (stepList.size() > 0) {
            CookBookInfo.Data.Steps steps = stepList.get(0);
            String img2 = steps.getImg();
            AnimeUtil.getImageLoad().displayImage(img2, iv_1,
                    AnimeUtil.getImageOption());
        }

        ll_cookitem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View paramView) {

                IntentHelper.getInstance().showIntent(context, CookBookDetailActivity.class,
                        new String[]{"data"}, new Serializable[]{info});

            }
        });


    }

    private void getDataTravel() {

        ImageView iv_1 = (ImageView) ll_travel.findViewById(R.id.iv_1);
        TextView tv_1 = (TextView) ll_travel.findViewById(R.id.tv_1);
        TextView tv_2 = (TextView) ll_travel.findViewById(R.id.tv_2);



        int m = (int) (Math.random() * (travelList.size() - 1));


        final TravelInfo.Scenery sinfo = travelList.get(m);


        String title = sinfo.getTitle();

        String price = sinfo.getPrice_min();

        String imgurl = sinfo.getImgurl();


        tv_1.setText(title);
        tv_2.setText("￥" + price);


        AnimeUtil.getImageLoad().displayImage(imgurl, iv_1,
                AnimeUtil.getImageOption());

        ll_travel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                IntentHelper.getInstance().showIntent(context, TravelDetailActivity.class,
                        new String[]{"info", "isHotel"}, new Serializable[]{
                                sinfo, false});
            }
        });

    }


    public float getFloat(String a) {
        float result = 0;
        LogHelper.logi(a);
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

            LogHelper.logi("ddd");
        }

        ll.invalidate();

    }

    void addTextview(LinearLayout ll, String str, int num) {
        TextView tv = new TextView(context);
        tv.setTextColor(context.getResources().getColor(R.color.black));
        int _5dp = BitmapHelper.getInstance().dp2Px(num);
        tv.setPadding(_5dp, _5dp, _5dp, _5dp);
        tv.setText(str);
        tv.setTextSize(14);
        tv.setTextColor(Color.WHITE);
        ll.addView(tv);

    }


    private int i_handler;

    private void postResult() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                i_handler++;
                int m = (result.size() - 1) % i_handler;


                TodayHistroy.Result res = result.get(m);
                if (res != null) {
                    tv_histroy.setText(res.getDes());
                    tv_time.setText(res.getYear());
                    tv_title.setText(res.getTitle());

                }

                postResult();

            }
        }, 4000);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @OnTouch({R.id.ll_cookitem, R.id.ll_travel, R.id.ll_weather, R.id.tl})
    public boolean onTouch(View view, MotionEvent motionEvent) {


        return TouchHelper.getInstance().setTouch(view, motionEvent);
    }

    @OnClick({R.id.ll_weather, R.id.tl})
    public void click(View v) {


        switch (v.getId()) {


            case R.id.ll_weather:
                IntentHelper.getInstance().showIntent(context, WeatherMainQueryActivity.class, new String[]{"weather"}, new Serializable[]{weatherInfo});
                break;
            case R.id.tl:
                IntentHelper.getInstance().showIntent(context, ConstellationActivity.class);
                break;
        }


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏

//            setContentView(R.layout.main_activity);
//            ButterKnife.inject(this);
//            setView();
//            if (result != null && result.size() > 0) {
//                postResult();
//
//            }
//
//            if (constellationInfo != null) {
//
//                getDataConstellation();
//
//            }
//
//            if (mlist != null) {
//                getDataCookDetail(true);
//            }
//
//            if (travelList != null) {
//
//                getDataTravel();
//            }

        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

//            setContentView(R.layout.main_activity);
//            ButterKnife.inject(this);
//            setView();
//            if (result != null && result.size() > 0) {
//                postResult();
//
//            }
//
//            if (constellationInfo != null) {
//
//                getDataConstellation();
//
//            }
//
//            if (mlist != null) {
//                getDataCookDetail(false);
//            }
//
//            if (travelList != null) {
//
//                getDataTravel();
//            }

        }


    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        new CustomDialog("您要走了", "慢走，不送，有空常来！", context, "离开", "留下", new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                finish();


            }
        }, null).show();


    }
}

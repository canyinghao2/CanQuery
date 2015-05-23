package com.canyinghao.canquery.activity.trainticket;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.DateHelper;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.JsonHelper;
import com.canyinghao.canhelper.PhoneHelper;
import com.canyinghao.canhelper.SPHepler;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.BaseBarActivity;
import com.canyinghao.canquery.adapter.ViewPagerAdapter;
import com.canyinghao.canquery.model.TrainTicketCityInfo;
import com.canyinghao.canquery.net.JuheWeb;
import com.canyinghao.canquery.net.JuheWeb.JuheRequestCallBack;
import com.canyinghao.canquery.util.AnimeUtil;
import com.canyinghao.canquery.util.JsonUtil;
import com.canyinghao.canquery.view.dialog.CustomListDialog;
import com.canyinghao.canquery.view.dialog.CustomListDialog.OnItemClick;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TrainTicketQueryActivity extends BaseBarActivity {
    @InjectView(R.id.toolbar)
    Toolbar toolbar;


    @InjectView(R.id.pager)
    public ViewPager pager;
    ViewPagerAdapter pagerAdapter;


    TextView tv_city_from;

    TextView tv_city_to;

    TextView tv_time_from;

    TextView tv_time_to;

    TextView tv_type;
    TextView tv_query;
    @InjectView(R.id.rg)
    RadioGroup rg;
    @InjectView(R.id.rb_rg_1)
    RadioButton rb_rg_1;
    @InjectView(R.id.rb_rg_2)
    RadioButton rb_rg_2;

    View tr_time_to;

    String[] tt = new String[]{"全部", "高铁", "动车", "特快", "直达", "快速", "其他"};
    String[] ttCodes = new String[]{"", "G", "D", "T", "Z", "K", "Q"};
    String ttcode = "";

    int rbIndex;

    Calendar c1 = Calendar.getInstance();
    Calendar c2 = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_postcode_activity);
        ButterKnife.inject(this);


        setToolbar(toolbar, R.drawable.ic_arrow_back_white, "", "火车票", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();


            }
        }, null);

        initView();

    }


    private void startData() {

        String city_from = SPHepler.getInstance().getString("city_from");
        String city_to = SPHepler.getInstance().getString("city_to");

        tv_city_from.setText(city_from);
        tv_city_to.setText(city_to);
        tv_type.setText(tt[0]);

        String dayByDate = DateHelper.getInstance().getDayByDate(
                Calendar.getInstance(), Calendar.DAY_OF_MONTH, 0);
        String dayByDate1 = DateHelper.getInstance().getDayByDate(
                Calendar.getInstance(), Calendar.DAY_OF_MONTH, 1);
        c2.add(Calendar.DATE, 1);
        tv_time_from.setText(DateHelper.getInstance().stringDateToStringData(
                "yyyy-MM-dd", "yyyy-MM-dd E", dayByDate));
        tv_time_to.setText(DateHelper.getInstance().stringDateToStringData(
                "yyyy-MM-dd", "yyyy-MM-dd E", dayByDate1));

        try {
            long count = DbUtils.create(App.getContext()).count(
                    Selector.from(TrainTicketCityInfo.class));
            if (count <= 0) {
                trainTicketCityQuery();
            }
        } catch (DbException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tv_city_from:


                    Intent intent = new Intent(context, TrainTicketCityQueryActivity.class);

                    startActivityForResult(intent, 101);

                    break;
                case R.id.tv_city_to:


                    Intent intent2 = new Intent(context,
                            TrainTicketCityQueryActivity.class);

                    startActivityForResult(intent2, 102);
                    break;
                case R.id.tv_time_from:
                    buildDatePicker(tv_time_from, true);
                    break;
                case R.id.tv_time_to:
                    buildDatePicker(tv_time_to, false);
                    break;
                case R.id.tv_type:

                    buildTT();

                    break;
                case R.id.tv_query:

                    String from = tv_city_from.getText().toString().trim();
                    String to = tv_city_to.getText().toString().trim();
                    String date = tv_time_from.getText().toString().trim();
                    String date2 = tv_time_to.getText().toString().trim();
                    if (TextUtils.isEmpty(from)) {
                        PhoneHelper.getInstance().show("请选择出发城市");
                        return;
                    }
                    if (TextUtils.isEmpty(to)) {
                        PhoneHelper.getInstance().show("请选择到达城市");
                        return;
                    }
                    if (TextUtils.isEmpty(date)) {
                        PhoneHelper.getInstance().show("请选择出发时间");
                        return;
                    }

                    if (rbIndex == 1) {
                        if (TextUtils.isEmpty(date2)) {
                            PhoneHelper.getInstance().show("请选择返回时间");
                            return;
                        } else {
                            date2 = DateHelper.getInstance().stringDateToStringData(
                                    "yyyy-MM-dd E", "yyyy-MM-dd", date2);
                        }
                    } else {
                        date2 = "";
                    }

                    date = DateHelper.getInstance().stringDateToStringData(
                            "yyyy-MM-dd E", "yyyy-MM-dd", date);


                    IntentHelper.getInstance().showIntent(context,
                            TrainTicketQueryListActivity.class,
                            new String[]{"from", "to", "date", "date2", "tt",},
                            new Serializable[]{from, to, date, date2, ttcode});

                    break;

            }
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
            String city = "";
            if (data != null && data.hasExtra("city")) {
                city = data.getStringExtra("city");
            }

            if (requestCode == 101) {
                tv_city_from.setText(city);

                SPHepler.getInstance().setString("city_from", city);
            } else if (requestCode == 102) {
                tv_city_to.setText(city);
                SPHepler.getInstance().setString("city_to", city);
            }
        }

    }

    void buildDatePicker(final TextView tv, final boolean isFirst) {

        Calendar c = Calendar.getInstance();
        if (isFirst) {
            c = c1;
        } else {
            c = c2;
        }


        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                Calendar calendar = Calendar.getInstance();

                calendar.set(i, i2, i3);
                if (isFirst) {
                    c1 = calendar;
                } else {
                    c2 = calendar;
                }
                Date time = calendar.getTime();

                SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-MM-dd E");
                String string = sdfFrom.format(time);

                tv.setText(string);
                if (isFirst && rbIndex == 1) {
                    if (c2.compareTo(c1) < 0) {
                        c2 = (Calendar) c1.clone();

                        c2.add(Calendar.DATE, 2);

                        String dayByDate =
                                DateHelper.getInstance().getDataString_2(c2.getTime());
                        tv_time_to.setText(DateHelper.getInstance().stringDateToStringData("yyyy-MM-dd", "yyyy-MM-dd E",
                                dayByDate));


                    }

                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();


        // CustomDatePickerDialog dialog = new CustomDatePickerDialog(context,
        // c);
        //
        //
        // dialog.addDateListener(new onDateListener() {
        //
        // @Override
        // public void dateFinish(Calendar ca) {
        //
        // if (isFirst) {
        // c1=ca;
        // }else {
        // c2=ca;
        // }
        // Date time = ca.getTime();
        //
        // SimpleDateFormat sdfFrom = new SimpleDateFormat("yyyy-MM-dd E");
        // String string = sdfFrom.format(time);
        //
        // tv.setText(string);
        // if (isFirst&&rbIndex==1) {
        // if (c2.compareTo(c1)<0) {
        // c2=(Calendar) c1.clone();
        //
        // c2.add(Calendar.DATE, 2);
        // String dayByDate =
        // GetLastDateTime.getInstance().getDayByDate(c2.getTime(), 0);
        // tv_time_to.setText(Util.formatDateTime("yyyy-MM-dd", "yyyy-MM-dd E",
        // dayByDate));
        // }
        //
        // }
        // }
        // });
        // dialog.show();

        // if (isFirst) {
        // dialog.setNowData(Calendar.getInstance());
        // }else {
        // dialog.setNowData(c1);
        // }

    }

    void buildTT() {

        new CustomListDialog(context, "列车类型", tt, new OnItemClick() {

            @Override
            public void itemClick(AdapterView<?> arg0, View arg1, int p,
                                  long arg3) {
                tv_type.setText(tt[p]);
                ttcode = ttCodes[p];

            }
        }).show();

    }


    List<View> getListData() {
        List<View> vList = new ArrayList<View>();


        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(context).inflate(
                    R.layout.query_trainticket_pager_item, null);


            vList.add(view);
        }
        return vList;

    }

    boolean isF;

    private void initItem(int p, View view) {

        tv_city_from = (TextView) view.findViewById(R.id.tv_city_from);
        tv_city_to = (TextView) view.findViewById(R.id.tv_city_to);
        tv_time_from = (TextView) view.findViewById(R.id.tv_time_from);
        tv_time_to = (TextView) view.findViewById(R.id.tv_time_to);
        tv_type = (TextView) view.findViewById(R.id.tv_type);

        tr_time_to = view.findViewById(R.id.tr_time_to);
        tv_query = (TextView) view.findViewById(R.id.tv_query);
        View iv_refresh = view.findViewById(R.id.iv_refresh);
        LinearLayout ll_ad= (LinearLayout) view.findViewById(R.id.ll_ad);
        if (!isF){
            AnimeUtil.addAdFrameLayout(context,  ll_ad);
            isF=true;
        }

        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimeHepler.getInstance().startAnimation(context, view,
                        R.anim.small_2_big, new AnimeHepler.OnAnimEnd() {

                            @Override
                            public void end() {
                                String city_from = tv_city_from.getText().toString();
                                String city_to = tv_city_to.getText().toString();
                                String temp = city_from;
                                tv_city_from.setText(city_to);
                                tv_city_to.setText(temp);

                            }
                        });
            }
        });
        if (p == 0) {
            tr_time_to.setVisibility(View.GONE);

        } else {


            tr_time_to.setVisibility(View.VISIBLE);
        }

        tv_city_from.setOnClickListener(clickListener);
        tv_city_to.setOnClickListener(clickListener);
        tv_time_from.setOnClickListener(clickListener);
        tv_time_to.setOnClickListener(clickListener);
        tv_type.setOnClickListener(clickListener);
        tv_type.setOnClickListener(clickListener);
        tv_query.setOnClickListener(clickListener);

    }

    private void initView() {
        rb_rg_1.setText("单程");
        rb_rg_2.setText("往返");
        final List<View> vList = getListData();
        initItem(1, vList.get(1));
        startData();
        initItem(0, vList.get(0));
        startData();

        pagerAdapter = new ViewPagerAdapter(vList);
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int p) {
                RadioButton childAt = (RadioButton) rg.getChildAt(p);
                childAt.setChecked(true);
                rbIndex = p;
                initItem(p, vList.get(p));


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

    void trainTicketCityQuery() {
        Parameters params = new Parameters();

        JuheWeb.getJuheData(params, 22,
                "http://apis.juhe.cn/train/station.list.php", JuheData.GET,
                new JuheRequestCallBack() {

                    @Override
                    public void success(int err, String reason, String result) {
                        Map<String, String> map = JsonUtil
                                .getNewApiJsonQuery(result.toString());
                        String message = map.get("message");

                        String lists = map.get("list");
                        if (TextUtils.isEmpty(lists)) {
                            return;
                        }
                        final List<TrainTicketCityInfo> mlist = JsonHelper
                                .getInstance()
                                .getList(
                                        lists,
                                        new TypeToken<List<TrainTicketCityInfo>>() {
                                        });
                        if (mlist != null) {

                            new Thread() {

                                public void run() {
                                    try {
                                        DbUtils.create(App.getContext())
                                                .dropTable(
                                                        TrainTicketCityInfo.class);
                                        DbUtils.create(App.getContext())
                                                .saveAll(mlist);

                                    } catch (DbException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                }

                                ;
                            }.start();

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

}

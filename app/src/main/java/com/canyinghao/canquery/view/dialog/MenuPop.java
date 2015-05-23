package com.canyinghao.canquery.view.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.BitmapHelper;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canhelper.NoScrollGridView;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.box_office.BoxOfficeActivity;
import com.canyinghao.canquery.activity.constellation.ConstellationActivity;
import com.canyinghao.canquery.activity.cookbook.CookBookMainActivity;
import com.canyinghao.canquery.activity.expressage.ExpressageQueryActivity;
import com.canyinghao.canquery.activity.flight.FlightQueryActivity;
import com.canyinghao.canquery.activity.idcard.IDCardQueryActivity;
import com.canyinghao.canquery.activity.postcode.PostCodeQueryActivity;
import com.canyinghao.canquery.activity.trainticket.TrainTicketQueryActivity;
import com.canyinghao.canquery.activity.travel.TravelMainActivity;
import com.canyinghao.canquery.activity.weather.WeatherMainQueryActivity;
import com.canyinghao.canquery.adapter.NewBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjian on 15/4/11.
 */
public class MenuPop extends PopupWindow {

    private List list;
    private int[] imgGrid = {


            R.drawable.query_menu, R.drawable.query_travel, R.drawable.query_constellation, R.drawable.query_movie, R.drawable.query_weather, R.drawable.query_idcard,
            R.drawable.query_postcode, R.drawable.query_express, R.drawable.query_train, R.drawable.query_flight};



    private String[] strGrid = {
            "菜谱大全", "旅游资讯", "星座运势", "电影票房", "天气预报", "身份证查询", "邮编查询", "常用快递", "火车票查询", "航班动态"};




    private Class[] cls = {


            CookBookMainActivity.class, TravelMainActivity.class, ConstellationActivity.class, BoxOfficeActivity.class, WeatherMainQueryActivity.class, IDCardQueryActivity.class, PostCodeQueryActivity.class, ExpressageQueryActivity.class, TrainTicketQueryActivity.class, FlightQueryActivity.class
    };



    public MenuPop(final Context context, View v) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.menu_pop, null);

        NoScrollGridView gv = (NoScrollGridView) view.findViewById(R.id.ngv);


        setAnimationStyle(R.style.down_animation);

        list = new ArrayList();
        for (int i = 0; i < strGrid.length; i++) {
            GridInfo info = new GridInfo();
            info.setTitle(strGrid[i]);
            info.setRid(imgGrid[i]);
            list.add(info);
        }
        QueryAdater adapter = new QueryAdater(context, list);

        gv.setAdapter(adapter);
        setContentView(view);

        setOutsideTouchable(true);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());

        showAtLocation(v, Gravity.BOTTOM, 0, 0);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {


                AnimeHepler.getInstance().startAnimation(context, view, AnimeHepler.getInstance().animSmall2Big(), new AnimeHepler.OnAnimEnd() {
                    @Override
                    public void end() {

                        dismiss();

                        IntentHelper.getInstance().showIntent(context,
                                cls[i]);


                    }
                });


            }
        });


    }


    class QueryAdater extends NewBaseAdapter {

        public QueryAdater(Context c, List list) {
            super(c, list);
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(int p, View arg1, ViewGroup arg2) {
            TextView tv = new TextView(context);


            GridInfo info = (GridInfo) list.get(p);
            tv.setText(info.getTitle());
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(0, BitmapHelper.getInstance().dp2Px(5), 0,
                    BitmapHelper.getInstance().dp2Px(10));
            tv.setTextSize(12);
            tv.setEllipsize(TextUtils.TruncateAt.END);
            tv.setSingleLine();
            tv.setTextColor(Color.WHITE);
            tv.setPadding(10, 10, 10, 10);

            tv.setCompoundDrawablePadding(BitmapHelper.getInstance().dp2Px(5));


            tv.setCompoundDrawablesWithIntrinsicBounds(0, info.getRid(), 0, 0);

            return tv;
        }

    }




    class GridInfo {
        private String title;
        private int rid;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getRid() {
            return rid;
        }

        public void setRid(int rid) {
            this.rid = rid;
        }

    }

}

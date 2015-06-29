package com.canyinghao.canquery.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canyinghao.canhelper.AnimeHepler;
import com.canyinghao.canhelper.AnimeHepler.OnAnimEnd;
import com.canyinghao.canhelper.IntentHelper;
import com.canyinghao.canquery.App;
import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.weather.WeatherCityQueryActivity;
import com.canyinghao.canquery.activity.weather.WeatherSelectCityQueryActivity;
import com.canyinghao.canquery.model.WeatherCityInfo;
import com.canyinghao.canquery.view.dialog.CustomDialog;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

import java.util.List;

import de.greenrobot.event.EventBus;

public class WeatherGridCityAdapter extends NewBaseAdapter {

	public WeatherGridCityAdapter(Context c, List list) {
		super(c, list);

	}

	@Override
	public int getCount() {
		if (list == null) {
			return 1;
		}
		return list.size() + 1;
	}

	@Override
	public View getView(final int position, View v, ViewGroup arg2) {

		if (v == null) {
			ViewCache cache = new ViewCache();
			v = LayoutInflater.from(context).inflate(
					R.layout.query_weather_grid_item3, null);
			cache.tv1 = (TextView) v.findViewById(R.id.tv_item1);
			cache.tv2 = (TextView) v.findViewById(R.id.tv_item2);
			cache.tv3 = (TextView) v.findViewById(R.id.tv_item3);
			cache.iv1 = (ImageView) v.findViewById(R.id.iv_item1);

			v.setTag(cache);

		}
		ViewCache cache = (ViewCache) v.getTag();


		if (position == getCount() - 1) {

			cache.tv1.setVisibility(View.GONE);
			cache.tv2.setVisibility(View.GONE);
			cache.tv3.setVisibility(View.VISIBLE);
			cache.iv1.setVisibility(View.GONE);

            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {

                    AnimeHepler.getInstance().startAnimation(context, view, R.anim.small_2_big,
                            new OnAnimEnd() {



                                @Override
                                public void end() {

                                    IntentHelper.getInstance().showIntent(context,
                                            WeatherCityQueryActivity.class);

                                }
                            });

                }
            });

		} else {

			cache.tv1.setVisibility(View.VISIBLE);
			cache.tv2.setVisibility(View.VISIBLE);
			cache.tv3.setVisibility(View.GONE);
			cache.iv1.setVisibility(View.VISIBLE);



            final WeatherCityInfo winfo = (WeatherCityInfo) list.get(position);
            cache.iv1.setImageResource(winfo.getRid());
            cache.tv1.setText(winfo.getCity());
            cache.tv2.setText(winfo.getWeather());



            v.setOnLongClickListener(new OnLongClickListener() {

                @Override
                public boolean onLongClick(View arg0) {
                    if (list.size()>1) {
                        new CustomDialog(winfo.getCity(), "是否删除？", context, "否",
                                "是", null, new OnClickListener() {

                            @Override
                            public void onClick(View arg0) {

                                try {
                                    DbUtils.create(App.getContext())
                                            .delete(winfo);

                                    EventBus.getDefault().post(new Intent(
                                            WeatherSelectCityQueryActivity.TAG));
                                } catch (DbException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                            }
                        }).show();
                    }

                    return false;
                }
            });

            v.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View view) {

                    AnimeHepler.getInstance().startAnimation(context, view, R.anim.small_2_big,
                            new OnAnimEnd() {



                                @Override
                                public void end() {



                                        Intent intent = new Intent();
                                        intent.putExtra("num", position);
                                        Activity ac = (Activity) context;
                                        ac.setResult(100, intent);
                                        ac.finish();

                                    }


                            });

                }
            });





		}

		return v;
	}

	class ViewCache {
		TextView tv1;
		TextView tv2;
		TextView tv3;
		ImageView iv1;

	}

}

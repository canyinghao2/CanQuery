/*
 * Copyright (c) 2010-2011, The MiCode Open Source Community (www.micode.net)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.canyinghao.canquery.view.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.weather.WeatherMainQueryActivity;
import com.canyinghao.canquery.configs.QueryConfigs;
import com.canyinghao.canquery.model.WeatherInfo;
import com.canyinghao.canquery.util.QueryUtil;

import java.util.List;

public class QueryWidgetProvider extends AppWidgetProvider {


    final String TEMP = "℃";
	// 没接收一次广播消息就调用一次，使用频繁
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("recrive");
		super.onReceive(context, intent);
	}

	// 每次更新都调用一次该方法，使用频繁
	public void onUpdate(final Context context, final AppWidgetManager appWidgetManager,
			final int[] appWidgetIds) {
		// TODO Auto-generated method stub
		System.out.println("update--->");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
        for (final int widgetId : appWidgetIds) {
            final RemoteViews rv = new RemoteViews(context.getPackageName(),
                    getLayoutId());

            QueryUtil.weatherQuery(new QueryUtil.QueryCallBack() {
                @Override
                public void success(Object obj) {
                    WeatherInfo   weatherInfo = (WeatherInfo)obj;
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


                    rv.setImageViewResource(R.id.iv,weatherRid);

                    rv.setTextViewText(R.id.tv_weather_main,weather.get(0).getInfo().getDay().get(1));


                    rv.setTextViewText(R.id.tv_weather_date,data.getRealtime().getDate());
                    rv.setTextViewText(R.id.tv_update,weatherInfo.getData().getRealtime().getTime() + "更新");



                    rv.setTextViewText(R.id.tv_wind,weatherInfo.getData().getRealtime().getWind().getDirect()
                            + weatherInfo.getData().getRealtime().getWind().getPower());



                    rv.setTextViewText(R.id.tv_temp,weatherInfo.getData().getRealtime().getWeather()
                            .getTemperature()
                            + TEMP);

                    rv.setTextViewText(R.id.tv_humidity,"湿度："
                            + weatherInfo.getData().getRealtime().getWeather().getHumidity()
                            + "%");



                    rv.setTextViewText(R.id.tv_pm25,weatherInfo.getData().getPm25().getPm25().getQuality());
                    rv.setTextViewText(R.id.tv_city,data.getRealtime().getCity_name());
                    rv.setTextViewText(R.id.tv_temps,weather.get(0).getInfo().getDay().get(2) + "/"
                            + weather.get(0).getInfo().getNight().get(2) + TEMP);

                    Intent intent=  new Intent(context, WeatherMainQueryActivity.class);
                    intent.putExtra("weather",weatherInfo);

                    PendingIntent pi=  PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    rv.setOnClickPendingIntent(R.id.ll_weather, pi);


                    appWidgetManager.updateAppWidget(widgetId, rv);

                }
            });








            appWidgetManager.updateAppWidget(widgetId, rv);
        }

		
	
		
		

	}

	// 没删除一个就调用一次
	public void onDeleted(Context context, int[] appWidgetIds) {
		// TODO Auto-generated method stub
		System.out.println("Deleted");
		super.onDeleted(context, appWidgetIds);
	}

	// 当该Widget第一次添加到桌面是调用该方法，可添加多次但只第一次调用
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		System.out.println("OnEnable");
		AlarmReceiver.scheduleAlarm(context);
		super.onEnabled(context);
	}

	// 当最后一个该Widget删除是调用该方法，注意是最后一个
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		System.out.println("onDisable");
		super.onDisabled(context);
       AlarmReceiver.cancelALarm();


	}

	protected int getLayoutId() {
		return 0;
	};

	public static void updateAllWidgets(Context context, Class cla) {
		AppWidgetManager appWidgetManager = AppWidgetManager
				.getInstance(context);
		ComponentName compName = new ComponentName(context, cla);
		Intent intent = new Intent(context, cla);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
				appWidgetManager.getAppWidgetIds(compName));
		context.sendBroadcast(intent);
	}

	public static void updateWidget(Context context, int appWidgetId, Class cla) {
		Intent intent = new Intent(context, cla);
		intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		context.sendBroadcast(intent);
	}
}

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
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.canyinghao.canquery.R;
import com.canyinghao.canquery.activity.MainActivity;
import com.canyinghao.canquery.model.TodayHistroy;
import com.canyinghao.canquery.util.QueryUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class QueryWidgetProvider_4x extends QueryWidgetProvider {


    protected int getLayoutId() {
        return R.layout.widget_4x;
    }


    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);


        for (final int widgetId : appWidgetIds) {
            final RemoteViews rv = new RemoteViews(context.getPackageName(),
                    getLayoutId());

            QueryUtil.toh_Query(new QueryUtil.QueryCallBack() {
                @Override
                public void success(Object obj) {


                    TodayHistroy info = (TodayHistroy) obj;

                    final List<TodayHistroy.Result> result = info.getResult();

                    if (result != null && result.size() > 0) {

                        int m = (int) (Math.random() * (result.size() - 1));


                        TodayHistroy.Result res = result.get(m);
                        if (res != null) {


                            rv.setTextViewText(R.id.tv_histroy, res.getDes());
                            rv.setTextViewText(R.id.tv_time, res.getYear());
                            rv.setTextViewText(R.id.tv_title, res.getTitle());

                            appWidgetManager.updateAppWidget(widgetId, rv);
                            PendingIntent pi=  PendingIntent.getActivity(context,1,new Intent(context, MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);

                            rv.setOnClickPendingIntent(R.id.ll_today,pi);


                        }


                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                int m = (int) (Math.random() * (result.size() - 1));


                                TodayHistroy.Result res = result.get(m);
                                if (res != null) {


                                    rv.setTextViewText(R.id.tv_histroy, res.getDes());
                                    rv.setTextViewText(R.id.tv_time, res.getYear());
                                    rv.setTextViewText(R.id.tv_title, res.getTitle());
                                    PendingIntent pi=  PendingIntent.getActivity(context,1,new Intent(context, MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);

                                    rv.setOnClickPendingIntent(R.id.ll_today,pi);
                                    appWidgetManager.updateAppWidget(widgetId, rv);


                                }


                            }
                        }, 6 * 60 * 1000);


                    }


                }
            });


        }


    }
}

// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.fragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class WeatherQueryFragment$$ViewInjector<T extends com.canyinghao.canquery.fragment.WeatherQueryFragment> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296502, "field 'mLineChart'");
    target.mLineChart = finder.castView(view, 2131296502, "field 'mLineChart'");
    view = finder.findRequiredView(source, 2131296501, "field 'gridview1'");
    target.gridview1 = finder.castView(view, 2131296501, "field 'gridview1'");
    view = finder.findRequiredView(source, 2131296503, "field 'gridview2'");
    target.gridview2 = finder.castView(view, 2131296503, "field 'gridview2'");
    view = finder.findRequiredView(source, 2131296504, "field 'gridview3'");
    target.gridview3 = finder.castView(view, 2131296504, "field 'gridview3'");
    view = finder.findRequiredView(source, 2131296489, "field 'tv_weather_main'");
    target.tv_weather_main = finder.castView(view, 2131296489, "field 'tv_weather_main'");
    view = finder.findRequiredView(source, 2131296490, "field 'tv_temp'");
    target.tv_temp = finder.castView(view, 2131296490, "field 'tv_temp'");
    view = finder.findRequiredView(source, 2131296491, "field 'tv_update'");
    target.tv_update = finder.castView(view, 2131296491, "field 'tv_update'");
    view = finder.findRequiredView(source, 2131296492, "field 'tv_wind'");
    target.tv_wind = finder.castView(view, 2131296492, "field 'tv_wind'");
    view = finder.findRequiredView(source, 2131296493, "field 'tv_humidity'");
    target.tv_humidity = finder.castView(view, 2131296493, "field 'tv_humidity'");
    view = finder.findRequiredView(source, 2131296496, "field 'tv_moon'");
    target.tv_moon = finder.castView(view, 2131296496, "field 'tv_moon'");
    view = finder.findRequiredView(source, 2131296497, "field 'tv_today'");
    target.tv_today = finder.castView(view, 2131296497, "field 'tv_today'");
    view = finder.findRequiredView(source, 2131296498, "field 'tv_today_weather'");
    target.tv_today_weather = finder.castView(view, 2131296498, "field 'tv_today_weather'");
    view = finder.findRequiredView(source, 2131296499, "field 'tv_tomorrow'");
    target.tv_tomorrow = finder.castView(view, 2131296499, "field 'tv_tomorrow'");
    view = finder.findRequiredView(source, 2131296500, "field 'tv_tomorrow_weather'");
    target.tv_tomorrow_weather = finder.castView(view, 2131296500, "field 'tv_tomorrow_weather'");
    view = finder.findRequiredView(source, 2131296495, "field 'tv_pm25'");
    target.tv_pm25 = finder.castView(view, 2131296495, "field 'tv_pm25'");
    view = finder.findRequiredView(source, 2131296494, "method 'click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
  }

  @Override public void reset(T target) {
    target.mLineChart = null;
    target.gridview1 = null;
    target.gridview2 = null;
    target.gridview3 = null;
    target.tv_weather_main = null;
    target.tv_temp = null;
    target.tv_update = null;
    target.tv_wind = null;
    target.tv_humidity = null;
    target.tv_moon = null;
    target.tv_today = null;
    target.tv_today_weather = null;
    target.tv_tomorrow = null;
    target.tv_tomorrow_weather = null;
    target.tv_pm25 = null;
  }
}

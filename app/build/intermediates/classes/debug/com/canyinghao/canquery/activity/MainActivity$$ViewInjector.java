// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class MainActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.MainActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296381, "field 'tv_histroy'");
    target.tv_histroy = finder.castView(view, 2131296381, "field 'tv_histroy'");
    view = finder.findRequiredView(source, 2131296380, "field 'tv_time'");
    target.tv_time = finder.castView(view, 2131296380, "field 'tv_time'");
    view = finder.findRequiredView(source, 2131296379, "field 'tv_title'");
    target.tv_title = finder.castView(view, 2131296379, "field 'tv_title'");
    view = finder.findRequiredView(source, 2131296369, "field 'tl', method 'click', and method 'onTouch'");
    target.tl = view;
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view.setOnTouchListener(
      new android.view.View.OnTouchListener() {
        @Override public boolean onTouch(
          android.view.View p0,
          android.view.MotionEvent p1
        ) {
          return target.onTouch(p0, p1);
        }
      });
    view = finder.findRequiredView(source, 2131296424, "field 'll_cookitem' and method 'onTouch'");
    target.ll_cookitem = view;
    view.setOnTouchListener(
      new android.view.View.OnTouchListener() {
        @Override public boolean onTouch(
          android.view.View p0,
          android.view.MotionEvent p1
        ) {
          return target.onTouch(p0, p1);
        }
      });
    view = finder.findRequiredView(source, 2131296382, "field 'll_travel' and method 'onTouch'");
    target.ll_travel = view;
    view.setOnTouchListener(
      new android.view.View.OnTouchListener() {
        @Override public boolean onTouch(
          android.view.View p0,
          android.view.MotionEvent p1
        ) {
          return target.onTouch(p0, p1);
        }
      });
    view = finder.findRequiredView(source, 2131296570, "field 'll_weather', method 'click', and method 'onTouch'");
    target.ll_weather = finder.castView(view, 2131296570, "field 'll_weather'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view.setOnTouchListener(
      new android.view.View.OnTouchListener() {
        @Override public boolean onTouch(
          android.view.View p0,
          android.view.MotionEvent p1
        ) {
          return target.onTouch(p0, p1);
        }
      });
    view = finder.findRequiredView(source, 2131296378, "field 'll_main'");
    target.ll_main = finder.castView(view, 2131296378, "field 'll_main'");
    view = finder.findRequiredView(source, 2131296518, "field 'tv_weather_main'");
    target.tv_weather_main = finder.castView(view, 2131296518, "field 'tv_weather_main'");
    view = finder.findRequiredView(source, 2131296519, "field 'tv_temp'");
    target.tv_temp = finder.castView(view, 2131296519, "field 'tv_temp'");
    view = finder.findRequiredView(source, 2131296520, "field 'tv_update'");
    target.tv_update = finder.castView(view, 2131296520, "field 'tv_update'");
    view = finder.findRequiredView(source, 2131296521, "field 'tv_wind'");
    target.tv_wind = finder.castView(view, 2131296521, "field 'tv_wind'");
    view = finder.findRequiredView(source, 2131296522, "field 'tv_humidity'");
    target.tv_humidity = finder.castView(view, 2131296522, "field 'tv_humidity'");
    view = finder.findRequiredView(source, 2131296344, "field 'tv_city'");
    target.tv_city = finder.castView(view, 2131296344, "field 'tv_city'");
    view = finder.findRequiredView(source, 2131296524, "field 'tv_pm25'");
    target.tv_pm25 = finder.castView(view, 2131296524, "field 'tv_pm25'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.tv_histroy = null;
    target.tv_time = null;
    target.tv_title = null;
    target.tl = null;
    target.ll_cookitem = null;
    target.ll_travel = null;
    target.ll_weather = null;
    target.ll_main = null;
    target.tv_weather_main = null;
    target.tv_temp = null;
    target.tv_update = null;
    target.tv_wind = null;
    target.tv_humidity = null;
    target.tv_city = null;
    target.tv_pm25 = null;
  }
}

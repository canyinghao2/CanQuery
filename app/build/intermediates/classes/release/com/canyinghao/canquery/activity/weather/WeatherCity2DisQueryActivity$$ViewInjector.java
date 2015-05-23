// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.weather;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class WeatherCity2DisQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.weather.WeatherCity2DisQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296326, "field 'listview'");
    target.listview = finder.castView(view, 2131296326, "field 'listview'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.listview = null;
  }
}

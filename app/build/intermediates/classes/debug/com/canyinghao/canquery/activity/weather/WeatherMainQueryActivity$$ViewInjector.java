// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.weather;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class WeatherMainQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.weather.WeatherMainQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296409, "field 'pager'");
    target.pager = finder.castView(view, 2131296409, "field 'pager'");
    view = finder.findRequiredView(source, 2131296517, "field 'rg_point'");
    target.rg_point = finder.castView(view, 2131296517, "field 'rg_point'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.pager = null;
    target.rg_point = null;
  }
}

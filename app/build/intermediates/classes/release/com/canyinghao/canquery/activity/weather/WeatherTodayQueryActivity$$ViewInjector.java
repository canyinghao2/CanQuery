// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.weather;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class WeatherTodayQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.weather.WeatherTodayQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296547, "field 'tv_item1'");
    target.tv_item1 = finder.castView(view, 2131296547, "field 'tv_item1'");
    view = finder.findRequiredView(source, 2131296548, "field 'tv_item2'");
    target.tv_item2 = finder.castView(view, 2131296548, "field 'tv_item2'");
    view = finder.findRequiredView(source, 2131296546, "field 'iv_item1'");
    target.iv_item1 = finder.castView(view, 2131296546, "field 'iv_item1'");
    view = finder.findRequiredView(source, 2131296552, "field 'tv_exp1'");
    target.tv_exp1 = finder.castView(view, 2131296552, "field 'tv_exp1'");
    view = finder.findRequiredView(source, 2131296553, "field 'tv_exp2'");
    target.tv_exp2 = finder.castView(view, 2131296553, "field 'tv_exp2'");
    view = finder.findRequiredView(source, 2131296551, "field 'iv_exp'");
    target.iv_exp = finder.castView(view, 2131296551, "field 'iv_exp'");
    view = finder.findRequiredView(source, 2131296530, "field 'gridview1'");
    target.gridview1 = finder.castView(view, 2131296530, "field 'gridview1'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.tv_item1 = null;
    target.tv_item2 = null;
    target.iv_item1 = null;
    target.tv_exp1 = null;
    target.tv_exp2 = null;
    target.iv_exp = null;
    target.gridview1 = null;
  }
}

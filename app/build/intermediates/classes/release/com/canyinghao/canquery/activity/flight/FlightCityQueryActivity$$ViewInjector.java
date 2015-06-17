// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.flight;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class FlightCityQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.flight.FlightCityQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296490, "field 'et_city'");
    target.et_city = finder.castView(view, 2131296490, "field 'et_city'");
    view = finder.findRequiredView(source, 2131296360, "field 'rg'");
    target.rg = finder.castView(view, 2131296360, "field 'rg'");
    view = finder.findRequiredView(source, 2131296362, "field 'rb_rg_2'");
    target.rb_rg_2 = finder.castView(view, 2131296362, "field 'rb_rg_2'");
    view = finder.findRequiredView(source, 2131296567, "field 'listview'");
    target.listview = finder.castView(view, 2131296567, "field 'listview'");
    view = finder.findRequiredView(source, 2131296568, "field 'mQuickAlphabeticBar'");
    target.mQuickAlphabeticBar = finder.castView(view, 2131296568, "field 'mQuickAlphabeticBar'");
    view = finder.findRequiredView(source, 2131296417, "method 'click'");
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
    target.toolbar = null;
    target.et_city = null;
    target.rg = null;
    target.rb_rg_2 = null;
    target.listview = null;
    target.mQuickAlphabeticBar = null;
  }
}

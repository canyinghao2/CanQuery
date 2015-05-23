// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.flight;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class FlightQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.flight.FlightQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296380, "field 'pager'");
    target.pager = finder.castView(view, 2131296380, "field 'pager'");
    view = finder.findRequiredView(source, 2131296344, "field 'rg'");
    target.rg = finder.castView(view, 2131296344, "field 'rg'");
    view = finder.findRequiredView(source, 2131296345, "field 'rb_rg_1'");
    target.rb_rg_1 = finder.castView(view, 2131296345, "field 'rb_rg_1'");
    view = finder.findRequiredView(source, 2131296346, "field 'rb_rg_2'");
    target.rb_rg_2 = finder.castView(view, 2131296346, "field 'rb_rg_2'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.pager = null;
    target.rg = null;
    target.rb_rg_1 = null;
    target.rb_rg_2 = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.trainticket;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TrainTicketQueryListActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.trainticket.TrainTicketQueryListActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296415, "field 'tv_date'");
    target.tv_date = finder.castView(view, 2131296415, "field 'tv_date'");
    view = finder.findRequiredView(source, 2131296342, "field 'listview'");
    target.listview = finder.castView(view, 2131296342, "field 'listview'");
    view = finder.findRequiredView(source, 2131296491, "field 'll_rg'");
    target.ll_rg = view;
    view = finder.findRequiredView(source, 2131296361, "field 'rb_rg_1'");
    target.rb_rg_1 = finder.castView(view, 2131296361, "field 'rb_rg_1'");
    view = finder.findRequiredView(source, 2131296362, "field 'rb_rg_2'");
    target.rb_rg_2 = finder.castView(view, 2131296362, "field 'rb_rg_2'");
    view = finder.findRequiredView(source, 2131296494, "field 'rg_most'");
    target.rg_most = finder.castView(view, 2131296494, "field 'rg_most'");
    view = finder.findRequiredView(source, 2131296360, "field 'rg'");
    target.rg = finder.castView(view, 2131296360, "field 'rg'");
    view = finder.findRequiredView(source, 2131296492, "method 'click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296493, "method 'click'");
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
    target.tv_date = null;
    target.listview = null;
    target.ll_rg = null;
    target.rb_rg_1 = null;
    target.rb_rg_2 = null;
    target.rg_most = null;
    target.rg = null;
  }
}

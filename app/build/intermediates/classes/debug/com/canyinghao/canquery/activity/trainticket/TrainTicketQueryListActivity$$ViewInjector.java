// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.trainticket;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TrainTicketQueryListActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.trainticket.TrainTicketQueryListActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296386, "field 'tv_date'");
    target.tv_date = finder.castView(view, 2131296386, "field 'tv_date'");
    view = finder.findRequiredView(source, 2131296326, "field 'listview'");
    target.listview = finder.castView(view, 2131296326, "field 'listview'");
    view = finder.findRequiredView(source, 2131296462, "field 'll_rg'");
    target.ll_rg = view;
    view = finder.findRequiredView(source, 2131296345, "field 'rb_rg_1'");
    target.rb_rg_1 = finder.castView(view, 2131296345, "field 'rb_rg_1'");
    view = finder.findRequiredView(source, 2131296346, "field 'rb_rg_2'");
    target.rb_rg_2 = finder.castView(view, 2131296346, "field 'rb_rg_2'");
    view = finder.findRequiredView(source, 2131296465, "field 'rg_most'");
    target.rg_most = finder.castView(view, 2131296465, "field 'rg_most'");
    view = finder.findRequiredView(source, 2131296344, "field 'rg'");
    target.rg = finder.castView(view, 2131296344, "field 'rg'");
    view = finder.findRequiredView(source, 2131296463, "method 'click'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296464, "method 'click'");
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

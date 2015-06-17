// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.travel;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TravelMainActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.travel.TravelMainActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296414, "field 'll'");
    target.ll = view;
    view = finder.findRequiredView(source, 2131296416, "field 'et_search'");
    target.et_search = finder.castView(view, 2131296416, "field 'et_search'");
    view = finder.findRequiredView(source, 2131296417, "field 'iv_search' and method 'click'");
    target.iv_search = finder.castView(view, 2131296417, "field 'iv_search'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296427, "field 'refreshGridView'");
    target.refreshGridView = finder.castView(view, 2131296427, "field 'refreshGridView'");
    view = finder.findRequiredView(source, 2131296426, "field 'refreshListView'");
    target.refreshListView = finder.castView(view, 2131296426, "field 'refreshListView'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.ll = null;
    target.et_search = null;
    target.iv_search = null;
    target.refreshGridView = null;
    target.refreshListView = null;
  }
}

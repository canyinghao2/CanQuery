// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.postcode;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class PostCodeResultQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.postcode.PostCodeResultQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296484, "field 'tv_sum'");
    target.tv_sum = finder.castView(view, 2131296484, "field 'tv_sum'");
    view = finder.findRequiredView(source, 2131296426, "field 'refreshListView'");
    target.refreshListView = finder.castView(view, 2131296426, "field 'refreshListView'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.tv_sum = null;
    target.refreshListView = null;
  }
}

// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.box_office;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class BoxOfficeActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.box_office.BoxOfficeActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296344, "field 'rg'");
    target.rg = finder.castView(view, 2131296344, "field 'rg'");
    view = finder.findRequiredView(source, 2131296380, "field 'pager'");
    target.pager = finder.castView(view, 2131296380, "field 'pager'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.rg = null;
    target.pager = null;
  }
}

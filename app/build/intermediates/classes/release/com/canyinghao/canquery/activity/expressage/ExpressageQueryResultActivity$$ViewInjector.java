// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.expressage;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ExpressageQueryResultActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.expressage.ExpressageQueryResultActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296342, "field 'listview'");
    target.listview = finder.castView(view, 2131296342, "field 'listview'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.listview = null;
  }
}

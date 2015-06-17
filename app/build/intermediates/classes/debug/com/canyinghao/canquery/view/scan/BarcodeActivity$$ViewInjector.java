// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.view.scan;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class BarcodeActivity$$ViewInjector<T extends com.canyinghao.canquery.view.scan.BarcodeActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296402, "field 'iv'");
    target.iv = finder.castView(view, 2131296402, "field 'iv'");
    view = finder.findRequiredView(source, 2131296403, "field 'tv_name'");
    target.tv_name = finder.castView(view, 2131296403, "field 'tv_name'");
    view = finder.findRequiredView(source, 2131296404, "field 'tv_code'");
    target.tv_code = finder.castView(view, 2131296404, "field 'tv_code'");
    view = finder.findRequiredView(source, 2131296405, "field 'tv_interval'");
    target.tv_interval = finder.castView(view, 2131296405, "field 'tv_interval'");
    view = finder.findRequiredView(source, 2131296342, "field 'listview'");
    target.listview = finder.castView(view, 2131296342, "field 'listview'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.iv = null;
    target.tv_name = null;
    target.tv_code = null;
    target.tv_interval = null;
    target.listview = null;
  }
}

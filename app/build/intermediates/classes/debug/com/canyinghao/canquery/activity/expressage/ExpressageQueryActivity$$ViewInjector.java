// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.expressage;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ExpressageQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.expressage.ExpressageQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296399, "field 'et_query_number'");
    target.et_query_number = finder.castView(view, 2131296399, "field 'et_query_number'");
    view = finder.findRequiredView(source, 2131296401, "field 'tv_query_company' and method 'click'");
    target.tv_query_company = finder.castView(view, 2131296401, "field 'tv_query_company'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296402, "field 'tv_query' and method 'click'");
    target.tv_query = finder.castView(view, 2131296402, "field 'tv_query'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296403, "field 'fl_ad'");
    target.fl_ad = finder.castView(view, 2131296403, "field 'fl_ad'");
    view = finder.findRequiredView(source, 2131296400, "method 'click'");
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
    target.et_query_number = null;
    target.tv_query_company = null;
    target.tv_query = null;
    target.fl_ad = null;
  }
}

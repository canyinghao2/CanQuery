// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.constellation;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ConstellationActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.constellation.ConstellationActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296362, "field 'll_main'");
    target.ll_main = finder.castView(view, 2131296362, "field 'll_main'");
    view = finder.findRequiredView(source, 2131296344, "field 'rg'");
    target.rg = finder.castView(view, 2131296344, "field 'rg'");
    view = finder.findRequiredView(source, 2131296380, "field 'pager'");
    target.pager = finder.castView(view, 2131296380, "field 'pager'");
    view = finder.findRequiredView(source, 2131296383, "field 'tv_con' and method 'click'");
    target.tv_con = finder.castView(view, 2131296383, "field 'tv_con'");
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
    target.ll_main = null;
    target.rg = null;
    target.pager = null;
    target.tv_con = null;
  }
}

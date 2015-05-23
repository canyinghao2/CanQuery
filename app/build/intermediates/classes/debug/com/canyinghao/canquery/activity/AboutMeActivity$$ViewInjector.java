// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AboutMeActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.AboutMeActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296321, "field 'tv_version' and method 'click'");
    target.tv_version = finder.castView(view, 2131296321, "field 'tv_version'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296320, "field 'tv_have'");
    target.tv_have = finder.castView(view, 2131296320, "field 'tv_have'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.tv_version = null;
    target.tv_have = null;
  }
}

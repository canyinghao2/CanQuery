// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.idcard;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class IDCardQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.idcard.IDCardQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296473, "field 'tv_number'");
    target.tv_number = finder.castView(view, 2131296473, "field 'tv_number'");
    view = finder.findRequiredView(source, 2131296474, "field 'tv_gender'");
    target.tv_gender = finder.castView(view, 2131296474, "field 'tv_gender'");
    view = finder.findRequiredView(source, 2131296475, "field 'tv_birthday'");
    target.tv_birthday = finder.castView(view, 2131296475, "field 'tv_birthday'");
    view = finder.findRequiredView(source, 2131296344, "field 'tv_city'");
    target.tv_city = finder.castView(view, 2131296344, "field 'tv_city'");
    view = finder.findRequiredView(source, 2131296432, "field 'fl_ad'");
    target.fl_ad = finder.castView(view, 2131296432, "field 'fl_ad'");
    view = finder.findRequiredView(source, 2131296416, "field 'et_search'");
    target.et_search = finder.castView(view, 2131296416, "field 'et_search'");
    view = finder.findRequiredView(source, 2131296417, "method 'click'");
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
    target.tv_number = null;
    target.tv_gender = null;
    target.tv_birthday = null;
    target.tv_city = null;
    target.fl_ad = null;
    target.et_search = null;
  }
}

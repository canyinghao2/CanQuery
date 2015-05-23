// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.travel;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class TravelDetailActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.travel.TravelDetailActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296367, "field 'iv_1'");
    target.iv_1 = finder.castView(view, 2131296367, "field 'iv_1'");
    view = finder.findRequiredView(source, 2131296368, "field 'tv_1'");
    target.tv_1 = finder.castView(view, 2131296368, "field 'tv_1'");
    view = finder.findRequiredView(source, 2131296369, "field 'tv_2'");
    target.tv_2 = finder.castView(view, 2131296369, "field 'tv_2'");
    view = finder.findRequiredView(source, 2131296370, "field 'tv_3'");
    target.tv_3 = finder.castView(view, 2131296370, "field 'tv_3'");
    view = finder.findRequiredView(source, 2131296381, "field 'tv_4' and method 'click'");
    target.tv_4 = finder.castView(view, 2131296381, "field 'tv_4'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296382, "field 'tv_5'");
    target.tv_5 = finder.castView(view, 2131296382, "field 'tv_5'");
    view = finder.findRequiredView(source, 2131296485, "field 'tv_6'");
    target.tv_6 = finder.castView(view, 2131296485, "field 'tv_6'");
    view = finder.findRequiredView(source, 2131296487, "field 'tv_7'");
    target.tv_7 = finder.castView(view, 2131296487, "field 'tv_7'");
    view = finder.findRequiredView(source, 2131296484, "field 'tv_8' and method 'click'");
    target.tv_8 = finder.castView(view, 2131296484, "field 'tv_8'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.click(p0);
        }
      });
    view = finder.findRequiredView(source, 2131296486, "field 'tv_9'");
    target.tv_9 = finder.castView(view, 2131296486, "field 'tv_9'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.iv_1 = null;
    target.tv_1 = null;
    target.tv_2 = null;
    target.tv_3 = null;
    target.tv_4 = null;
    target.tv_5 = null;
    target.tv_6 = null;
    target.tv_7 = null;
    target.tv_8 = null;
    target.tv_9 = null;
  }
}

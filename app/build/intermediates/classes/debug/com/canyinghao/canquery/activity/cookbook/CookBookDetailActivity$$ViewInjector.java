// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.cookbook;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class CookBookDetailActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.cookbook.CookBookDetailActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296319, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296319, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296391, "field 'tv_tag'");
    target.tv_tag = finder.castView(view, 2131296391, "field 'tv_tag'");
    view = finder.findRequiredView(source, 2131296392, "field 'tv_imtro'");
    target.tv_imtro = finder.castView(view, 2131296392, "field 'tv_imtro'");
    view = finder.findRequiredView(source, 2131296393, "field 'll_ingredients'");
    target.ll_ingredients = finder.castView(view, 2131296393, "field 'll_ingredients'");
    view = finder.findRequiredView(source, 2131296394, "field 'll_steps'");
    target.ll_steps = finder.castView(view, 2131296394, "field 'll_steps'");
    view = finder.findRequiredView(source, 2131296373, "field 'iv'");
    target.iv = finder.castView(view, 2131296373, "field 'iv'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.tv_tag = null;
    target.tv_imtro = null;
    target.ll_ingredients = null;
    target.ll_steps = null;
    target.iv = null;
  }
}

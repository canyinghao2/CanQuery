// Generated code from Butter Knife. Do not modify!
package com.canyinghao.canquery.activity.postcode;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class PostCodeQueryActivity$$ViewInjector<T extends com.canyinghao.canquery.activity.postcode.PostCodeQueryActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131296335, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131296335, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131296378, "field 'll_main'");
    target.ll_main = view;
    view = finder.findRequiredView(source, 2131296409, "field 'pager'");
    target.pager = finder.castView(view, 2131296409, "field 'pager'");
    view = finder.findRequiredView(source, 2131296360, "field 'rg'");
    target.rg = finder.castView(view, 2131296360, "field 'rg'");
    view = finder.findRequiredView(source, 2131296361, "field 'rb_rg_1'");
    target.rb_rg_1 = finder.castView(view, 2131296361, "field 'rb_rg_1'");
    view = finder.findRequiredView(source, 2131296362, "field 'rb_rg_2'");
    target.rb_rg_2 = finder.castView(view, 2131296362, "field 'rb_rg_2'");
  }

  @Override public void reset(T target) {
    target.toolbar = null;
    target.ll_main = null;
    target.pager = null;
    target.rg = null;
    target.rb_rg_1 = null;
    target.rb_rg_2 = null;
  }
}

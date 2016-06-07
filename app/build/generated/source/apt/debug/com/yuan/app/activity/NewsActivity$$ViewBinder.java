// Generated code from Butter Knife. Do not modify!
package com.yuan.app.activity;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class NewsActivity$$ViewBinder<T extends NewsActivity> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492972, "field 'toolbar'");
    target.toolbar = finder.castView(view, 2131492972, "field 'toolbar'");
    view = finder.findRequiredView(source, 2131492977, "field 'newsContentPager'");
    target.newsContentPager = finder.castView(view, 2131492977, "field 'newsContentPager'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends NewsActivity> implements Unbinder {
    private T target;

    protected InnerUnbinder(T target) {
      this.target = target;
    }

    @Override
    public final void unbind() {
      if (target == null) throw new IllegalStateException("Bindings already cleared.");
      unbind(target);
      target = null;
    }

    protected void unbind(T target) {
      target.toolbar = null;
      target.newsContentPager = null;
    }
  }
}

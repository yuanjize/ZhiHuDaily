// Generated code from Butter Knife. Do not modify!
package com.yuan.app.fragment;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class MainFragment$$ViewBinder<T extends MainFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492989, "field 'mainList'");
    target.mainList = finder.castView(view, 2131492989, "field 'mainList'");
    view = finder.findRequiredView(source, 2131492988, "field 'swipe'");
    target.swipe = finder.castView(view, 2131492988, "field 'swipe'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends MainFragment> implements Unbinder {
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
      target.mainList = null;
      target.swipe = null;
    }
  }
}

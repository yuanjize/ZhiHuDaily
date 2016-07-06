// Generated code from Butter Knife. Do not modify!
package com.yuan.app.fragment;

import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Finder;
import butterknife.internal.ViewBinder;
import java.lang.IllegalStateException;
import java.lang.Object;
import java.lang.Override;

public class OtherNewsFragment$$ViewBinder<T extends OtherNewsFragment> implements ViewBinder<T> {
  @Override
  public Unbinder bind(final Finder finder, final T target, Object source) {
    InnerUnbinder unbinder = createUnbinder(target);
    View view;
    view = finder.findRequiredView(source, 2131492991, "field 'themeIamge'");
    target.themeIamge = finder.castView(view, 2131492991, "field 'themeIamge'");
    view = finder.findRequiredView(source, 2131492994, "field 'themeNews'");
    target.themeNews = finder.castView(view, 2131492994, "field 'themeNews'");
    view = finder.findRequiredView(source, 2131492990, "field 'themeRefresh'");
    target.themeRefresh = finder.castView(view, 2131492990, "field 'themeRefresh'");
    view = finder.findRequiredView(source, 2131492992, "field 'themeDescription'");
    target.themeDescription = finder.castView(view, 2131492992, "field 'themeDescription'");
    view = finder.findRequiredView(source, 2131492993, "field 'editors'");
    target.editors = finder.castView(view, 2131492993, "field 'editors'");
    return unbinder;
  }

  protected InnerUnbinder<T> createUnbinder(T target) {
    return new InnerUnbinder(target);
  }

  protected static class InnerUnbinder<T extends OtherNewsFragment> implements Unbinder {
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
      target.themeIamge = null;
      target.themeNews = null;
      target.themeRefresh = null;
      target.themeDescription = null;
      target.editors = null;
    }
  }
}

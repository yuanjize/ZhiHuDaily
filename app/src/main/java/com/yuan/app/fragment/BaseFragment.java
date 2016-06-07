package com.yuan.app.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuan.app.utils.LogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {
    public static final int MAIN_FRAGMENT = 0;
    public static final int OTHER_FRAGMENT = 1;

    private static MainFragment mainfragment;
    private static OtherNewsFragment otherNewsFragment;
    public static final int SIZE = 2;

    public static BaseFragment newInstance(int fragmentType) {
        BaseFragment fragment = null;
        switch (fragmentType) {
            case MAIN_FRAGMENT:
                if (mainfragment == null) {
                    mainfragment = new MainFragment();
                }
                fragment = mainfragment;
                break;
            case OTHER_FRAGMENT:
                if (otherNewsFragment == null) {
                    otherNewsFragment = new OtherNewsFragment();
                }
                fragment = otherNewsFragment;
                break;
        }
        return fragment;
    }

    private Unbinder bind;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LogUtils.e("onCreateView" + getActivity());
        return inflater.inflate(getLayoutId(), container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        LogUtils.e("onViewCreated" + getActivity());
        bind = ButterKnife.bind(this, view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e("onActivityCreated" + getActivity());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e("onDestroyView" + getActivity());
        if (bind != null) {
            bind.unbind();
        }
    }

    protected abstract int getLayoutId();

    public abstract void onToolBarChange(String date);

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("onDestroy" + getActivity());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e("onDetach:" + getActivity());
    }
}

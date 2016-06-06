package com.yuan.app.other;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yuan.app.fragment.BaseFragment;

/**
 * Created by yjz on 2016/5/30.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter {
    public MyFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return BaseFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        return BaseFragment.SIZE;
    }
}

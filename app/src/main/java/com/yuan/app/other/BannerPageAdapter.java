package com.yuan.app.other;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuan.app.application.MyApplication;
import com.yuan.app.entities.MainData;
import com.yuan.app.utils.LogUtils;

import java.util.List;

/**
 * Created by yjz on 2016/5/31.
 */
public class BannerPageAdapter extends PagerAdapter {
    private List<ImageView> images;
    private Fragment fragment;
    private List<MainData.TopStoriesBean> datas;

    public BannerPageAdapter() {
    }

    public BannerPageAdapter(List<MainData.TopStoriesBean> datas, Fragment fragment, List<ImageView> images) {
        this.datas = datas;
        this.fragment = fragment;
        this.images = images;
    }

    public void setContext(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setDatas(List<MainData.TopStoriesBean> datas) {
        this.datas = datas;
    }

    public void setImages(List<ImageView> images) {
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(images.get(position));
        Glide.with(MyApplication.getContext()).load(datas.get(position).getImage()).into(images.get(position));
        return images.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(images.get(position));
    }

    public void clear() {
        if (datas != null) {
            datas.clear();
        }
    }
}
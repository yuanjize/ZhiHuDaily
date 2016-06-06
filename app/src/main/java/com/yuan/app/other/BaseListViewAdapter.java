package com.yuan.app.other;

import android.widget.BaseAdapter;

import com.yuan.app.entities.MainData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjz on 2016/5/30.
 * 这里需要2个ITEM 一个是时间，一个是新闻。
 * 通过getType区分两个Item，0就是时间，1就是新闻
 * getview方法里面，先判断是哪种类型的Item，如果需要的是日期Item，直接创建新的添加进去，否则复用view。
 */
public abstract class BaseListViewAdapter extends BaseAdapter {
    protected List<MainData.StoriesBean> datas=new ArrayList<>();
    public BaseListViewAdapter(List<MainData.StoriesBean> datas) {
        this.datas.addAll(datas);
    }

    public BaseListViewAdapter() {
    }

    public void clear(){
        datas.clear();
    }

    public void addData(List<MainData.StoriesBean> datas){
        this.datas.addAll(datas);
    }
    public List<MainData.StoriesBean> getData(){
        return datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}

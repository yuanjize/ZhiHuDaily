package com.yuan.app.other;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.beans.MainData;

import java.util.List;

/**
 * Created by yjz on 2016/5/30.
 */
public class MainAdapter extends BaseListViewAdapter {
    private int listHeight=0;

    public int getListHeight() {
        return listHeight;
    }

    private Fragment fragment;
    public MainAdapter(List<MainData.StoriesBean> datas) {
        super(datas);
    }
    public MainAdapter() {
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            view = View.inflate(MyApplication.getContext(), R.layout.main_recycle_item3, null);
            viewHolder = new ViewHolder();
            viewHolder.image= (ImageView) view.findViewById(R.id.main_pic);
            viewHolder.tittle= (TextView) view.findViewById(R.id.main_tittle);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tittle.setText(datas.get(position).getTitle());
        Glide.with(fragment).load(datas.get(position).getImages().get(0)).into(viewHolder.image);
        return view;
    }
}
class ViewHolder{
    public TextView tittle;
    public ImageView image;
}
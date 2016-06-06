package com.yuan.app.other;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.constants.ItemType;
import com.yuan.app.fragment.BaseFragment;

import java.util.List;

/**
 * Created by yjz on 2016/6/4.
 */
public class ThemeNewsAdapter extends BaseListViewAdapter {

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        ViewHolder holder = null;
        if (convertView == null) {
            view = View.inflate(MyApplication.getContext(), R.layout.main_recycle_item3, null);
            holder = new ViewHolder();
            holder.text = (TextView) view.findViewById(R.id.main_tittle);
            holder.image = (ImageView) view.findViewById(R.id.main_pic);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        switch (getItemViewType(position)) {
            case ItemType.TYPE_HASIMAGE: {
                holder.image.setVisibility(View.VISIBLE);
                Glide.with(BaseFragment.newInstance(BaseFragment.OTHER_FRAGMENT)).
                        load(datas.get(position).getImages().get(0)).
                        into(holder.image);
                break;
            }
            case ItemType.TYPE_NOIMAGE: {
                holder.image.setVisibility(View.GONE);
                break;
            }
        }
        holder.text.setText(datas.get(position).getTitle());
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        List<String> images = datas.get(position).getImages();
        if (images != null && images.size() != 0) {
            return ItemType.TYPE_HASIMAGE;
        } else {
            return ItemType.TYPE_NOIMAGE;
        }
    }

    static class ViewHolder {
        public TextView text;
        public ImageView image;
    }
}

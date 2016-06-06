package com.yuan.app.other;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.entities.ThemeNews;
import com.yuan.app.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjz on 2016/6/5.
 */
public class EditorAdapter extends RecyclerView.Adapter<EditorViewHolder> {
    private List<ThemeNews.EditorsBean> editorsBeen = new ArrayList<>();

    public void addData(List<ThemeNews.EditorsBean> editorsBeen) {
        this.editorsBeen.addAll(editorsBeen);
    }

    public void clean() {
        editorsBeen.clear();
    }

    @Override
    public EditorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditorViewHolder(View.inflate(MyApplication.getContext(), R.layout.editor_layout, null));
    }

    @Override
    public void onBindViewHolder(EditorViewHolder holder, int position) {
        Glide.with(BaseFragment.newInstance(BaseFragment.OTHER_FRAGMENT)).load(editorsBeen.get(position).getAvatar()).into(holder.head);
    }

    @Override
    public int getItemCount() {
        return editorsBeen.size();
    }
}

class EditorViewHolder extends RecyclerView.ViewHolder {
    public ImageView head;

    public EditorViewHolder(View itemView) {
        super(itemView);
        head = (ImageView) itemView.findViewById(R.id.editor_head);
    }

}
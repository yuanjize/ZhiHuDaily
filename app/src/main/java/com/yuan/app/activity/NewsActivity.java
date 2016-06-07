package com.yuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yuan.app.R;
import com.yuan.app.entities.MainData;
import com.yuan.app.other.NewsContentPageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.news_content_pager)
    ViewPager newsContentPager;
    private NewsContentPageAdapter adapter = new NewsContentPageAdapter();
    private List<MainData.StoriesBean> news;
    private int currentPosition;

    @Override
    protected void init() {
        newsContentPager.setAdapter(adapter);
        Intent intent = getIntent();
        if (intent != null) {
            List<MainData.StoriesBean> news = intent.getParcelableArrayListExtra("datas");
            int currentPosition = intent.getIntExtra("position", 0);
            this.currentPosition = currentPosition;
            this.news = news;
            getDatas();
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void getDatas() {
        adapter.clear();
        adapter.addData(news);
        adapter.notifyDataSetChanged();
        newsContentPager.setCurrentItem(currentPosition);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("news", new ArrayList<Parcelable>(news));
        outState.putInt("currentPosition", currentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        news = savedInstanceState.getParcelableArrayList("news");
        currentPosition = savedInstanceState.getInt("currentPosition");
        getDatas();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }
}
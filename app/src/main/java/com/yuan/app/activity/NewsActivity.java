package com.yuan.app.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.yuan.app.R;
import com.yuan.app.entities.MainData;
import com.yuan.app.other.NewsContentPageAdapter;

import java.util.List;

import butterknife.BindView;

public class NewsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.news_content_pager)
    ViewPager newsContentPager;
    private NewsContentPageAdapter adapter = new NewsContentPageAdapter();

    @Override
    protected void init() {
        newsContentPager.setAdapter(adapter);
        Intent intent = getIntent();
        if (intent != null) {
            List<MainData.StoriesBean> news = intent.getParcelableArrayListExtra("datas");
            int currentPosition = intent.getIntExtra("position", 0);
            adapter.clear();
            adapter.addData(news);
            adapter.notifyDataSetChanged();
            newsContentPager.setCurrentItem(currentPosition);
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
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
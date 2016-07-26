package com.yuan.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.reflect.TypeToken;
import com.yuan.app.R;
import com.yuan.app.activity.NewsActivity;
import com.yuan.app.beans.MainData;
import com.yuan.app.constants.URLs;
import com.yuan.app.other.BannerPageAdapter;
import com.yuan.app.other.BaseSubscribe;
import com.yuan.app.other.MainAdapter;
import com.yuan.app.utils.CommonUtils;
import com.yuan.app.utils.LogUtils;
import com.yuan.app.utils.RxUtils;
import com.yuan.app.views.LoopViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * 新闻主页面
 */
public class MainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    LoopViewPager banner;
    @BindView(R.id.main_list)
    ListView mainList;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private String date;
    private MainAdapter adapter = new MainAdapter();
    private BannerPageAdapter pagerAdapter = new BannerPageAdapter();
    public List<MainData.StoriesBean> storys;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onToolBarChange(String date) {
        this.date = date;
        if ((!TextUtils.isEmpty(date)) && date.equals(CommonUtils.getCurrentDate())) {
            onRefresh();
        } else if (!TextUtils.isEmpty(date)) {
            getOldNews(date);
        }
    }

    private void getOldNews(String date) {
        RxUtils.getOldyNews(date)
                .subscribe(new BaseSubscribe<List<MainData.StoriesBean>>(URLs.OLD_NEWS, new TypeToken<List<MainData.StoriesBean>>() {
                           }.getType()) {
                               @Override
                               protected void handleMessage(List<MainData.StoriesBean> storiesBeen) {
                                   MainFragment.this.storys = storiesBeen;
                                   adapter.addData(storiesBeen);
                                   banner.start();
                                   swipe.setRefreshing(false);
                               }

                               @Override
                               public void onError(Throwable e) {
                                   super.onError(e);
                                   swipe.setRefreshing(false);

                               }

                               @Override
                               public void onStart() {
                                   banner.stop();
                               }
                           }
                );
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View v = View.inflate(getContext(), R.layout.main_banner, null);
        banner = (LoopViewPager) v.findViewById(R.id.banner);
        mainList.addHeaderView(v);
        adapter.setFragment(this);
        mainList.setAdapter(adapter);
        swipe.setOnRefreshListener(this);
        mainList.setOnScrollListener(new SwipeOnScrollListener(swipe, null));
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                LogUtils.e("storys :" + storys);
                intent.putParcelableArrayListExtra("datas", new ArrayList<Parcelable>(storys));
                intent.putExtra("position", position);
                getActivity().startActivity(intent);
            }
        });
        onRefresh();
    }

    @Override
    public void onRefresh() {
        //不是当前日期，刷新旧的数据
        if (date != null && (!date.equals(CommonUtils.getCurrentDate()))) {
            getOldNews(date);
        } else {
            getLatestNews();
        }
    }

    private void getLatestNews() {
        RxUtils.getTodayNews().subscribe(new BaseSubscribe<MainData>(URLs.LASTEST_NEWS, new TypeToken<MainData>() {
        }.getType()) {
            @Override
            public void onStart() {
                super.onStart();
                banner.stop();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                swipe.setRefreshing(false);

            }

            @Override
            protected void handleMessage(MainData mainData) {
                MainFragment.this.storys = mainData.getStories();
                adapter.addData(mainData.getStories());
                LogUtils.e("init get data:" + mainData.getStories());
                pagerAdapter.clear();
                banner.setPageAdapter(pagerAdapter, mainData.getTop_stories());
                banner.start();
                swipe.setRefreshing(false);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}


//解决ListView和Swip滑动冲突。
class SwipeOnScrollListener implements AbsListView.OnScrollListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private AbsListView.OnScrollListener listener;

    public SwipeOnScrollListener(SwipeRefreshLayout swipeRefreshLayout, AbsListView.OnScrollListener listener) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0) {
            View firstVisibleItemview = view.getChildAt(firstVisibleItem);
            //第0个Item显示出来斌且滑动到最顶部
            if (firstVisibleItemview != null && (firstVisibleItemview.getTop() == 0)) {
                swipeRefreshLayout.setEnabled(true);
            } else {
                swipeRefreshLayout.setEnabled(false);
            }
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
        if (listener != null) {
            listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
}

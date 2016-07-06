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

import com.yuan.app.R;
import com.yuan.app.activity.NewsActivity;
import com.yuan.app.constants.URLs;
import com.yuan.app.entities.MainData;
import com.yuan.app.entities.OldNews;
import com.yuan.app.net.MainService;
import com.yuan.app.other.BannerPageAdapter;
import com.yuan.app.other.BaseRetrofitCallBack;
import com.yuan.app.other.MainAdapter;
import com.yuan.app.utils.CommonUtils;
import com.yuan.app.utils.RetrofitUtils;
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
        banner.stop();
        RetrofitUtils.request(MainService.class, "getOldyNews", new BaseRetrofitCallBack<OldNews>(URLs.OLD_NEWS, OldNews.class) {
                    @Override
                    protected void handleMessage(OldNews body) {
                        adapter.clear();
                        adapter.addData(body.getStories());
                        adapter.notifyDataSetChanged();
                        banner.start();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        super.onFailure(t);
                        banner.start();
                    }
                }
                , date);

    }

    @Override
    public void onStart() {
        super.onStart();
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
        banner.stop();
        RetrofitUtils.request(MainService.class, "getTodayNews", new BaseRetrofitCallBack<MainData>(URLs.LASTEST_NEWS, MainData.class) {

            @Override
            protected void handleMessage(MainData body) {
                storys = body.getStories();
                adapter.clear();
                adapter.addData(body.getStories());
                adapter.notifyDataSetChanged();
                pagerAdapter.clear();
                banner.setPageAdapter(pagerAdapter, body.getTop_stories());
                banner.start();
                swipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                super.onFailure(t);
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

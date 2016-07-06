package com.yuan.app.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuan.app.R;
import com.yuan.app.activity.NewsActivity;
import com.yuan.app.application.MyApplication;
import com.yuan.app.constants.URLs;
import com.yuan.app.entities.ThemeNews;
import com.yuan.app.entities.Themes;
import com.yuan.app.net.ThemesService;
import com.yuan.app.other.BaseRetrofitCallBack;
import com.yuan.app.other.EditorAdapter;
import com.yuan.app.other.MyItemDecoration;
import com.yuan.app.other.ThemeNewsAdapter;
import com.yuan.app.utils.DensityUtil;
import com.yuan.app.utils.RetrofitUtils;
import com.yuan.app.views.MyListView;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherNewsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.theme_iamge)
    ImageView themeIamge;
    @BindView(R.id.theme_news)
    MyListView themeNews;
    @BindView(R.id.theme_refresh)
    SwipeRefreshLayout themeRefresh;
    @BindView(R.id.theme_description)
    TextView themeDescription;
    @BindView(R.id.editors)
    RecyclerView editors;
    private ThemeNewsAdapter adapter = new ThemeNewsAdapter();
    private int themeId;
    private Themes.OthersBean bean;
    private ThemeNews news;
    private EditorAdapter editorAdapter = new EditorAdapter();
    private static final String THEME_ID = "themeid";
    private static final String OTHER_BEAN = "otherbean";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other_news;
    }

    @Override
    public void onToolBarChange(String date) {

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            themeId = savedInstanceState.getInt(THEME_ID);
            bean = (Themes.OthersBean) savedInstanceState.getSerializable(OTHER_BEAN);
            onRefresh();
        }

    }

    @Override
    public void onRefresh() {
        if (bean != null) {
            onThemeChange(themeId, bean);
        } else {
            themeRefresh.setRefreshing(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(THEME_ID, themeId);
        outState.putSerializable(OTHER_BEAN, bean);
    }


    //主题切换，重新刷新数据
    public void onThemeChange(int themeId, Themes.OthersBean bean) {
        this.themeId = themeId;
        this.bean = bean;
        Glide.with(this).load(bean.getThumbnail()).centerCrop().animate(R.anim.image_zoom_in).into(themeIamge);
        themeDescription.setText(bean.getDescription());
        RetrofitUtils.request(ThemesService.class, "getThemeNews", new BaseRetrofitCallBack<ThemeNews>(URLs.THEME_CONTENT + themeId, ThemeNews.class) {
            @Override
            protected void handleMessage(ThemeNews body) {
                OtherNewsFragment.this.news = body;
                adapter.clear();
                adapter.addData(body.getStories());
                adapter.notifyDataSetChanged();
                editorAdapter.clean();
                editorAdapter.addData(body.getEditors());
                editorAdapter.notifyDataSetChanged();
                themeRefresh.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                super.onFailure(t);
                themeRefresh.setRefreshing(false);
            }
        }, String.valueOf(themeId));

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        themeNews.setAdapter(adapter);
        themeRefresh.setOnRefreshListener(this);
        editors.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        editors.setAdapter(editorAdapter);
        editors.addItemDecoration(new MyItemDecoration(DensityUtil.dip2px(MyApplication.getContext(), 10)));
        themeNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NewsActivity.class);
                intent.putParcelableArrayListExtra("datas", new ArrayList<Parcelable>(news.getStories()));
                intent.putExtra("position", position);
                getActivity().startActivity(intent);
            }
        });
    }
}
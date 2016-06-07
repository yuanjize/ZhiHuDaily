package com.yuan.app.other;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.constants.URLs;
import com.yuan.app.entities.MainData;
import com.yuan.app.entities.NewsContent;
import com.yuan.app.net.NewsService;
import com.yuan.app.utils.LogUtils;
import com.yuan.app.utils.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjz on 2016/6/5.
 */
public class NewsContentPageAdapter extends PagerAdapter {
    private List<WebView> views = new ArrayList<>(3);
    private List<MainData.StoriesBean> datas = new ArrayList<>();

    public void addData(List<MainData.StoriesBean> datas) {
        this.datas.addAll(datas);
    }

    public void clear() {
        datas.clear();
    }

    public NewsContentPageAdapter() {
        while (views.size() < 4) {
            WebView webView = (WebView) View.inflate(MyApplication.getContext(), R.layout.news_content_view, null);
            views.add(webView);
        }
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LogUtils.e("position--------------->" + position);
        WebView webView = views.get(position % 4);
        getNewsContent(datas.get(position).getId(), webView);
        container.addView(webView);
        return webView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position % 4));
    }

    private void getNewsContent(int storyId, final WebView webView) {
        RetrofitUtils.request(NewsService.class, "getNewsContent", new BaseRetrofitCallBack<NewsContent>(URLs.NEWS_CONTENT, NewsContent.class) {
            @Override
            protected void handleMessage(NewsContent body) {
                WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                StringBuffer buffer=new StringBuffer();
                String page = "<html><head>" +
                        "<link rel='stylesheet' type='text/css' href='" +
                        body.getCss() + "'/>" + "<style>img{display: inline;height: auto;max-width: 100%;}</style>" +
                        "</head>" + "<body>" + body.getBody() + "</body></html>";
                webView.loadData(page, "text/html;charset=utf-8", "utf-8");
            }
        }, String.valueOf(storyId));
    }
}

package com.yuan.app.net;

import com.yuan.app.beans.NewsContent;
import com.yuan.app.constants.URLs;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yjz on 2016/6/5.
 */
public interface NewsService {
    @GET(URLs.NEWS_CONTENT + "{storyId}")
    public Observable<NewsContent> getNewsContent(@Path("storyId") String storyId);
}

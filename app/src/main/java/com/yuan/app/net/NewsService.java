package com.yuan.app.net;

import com.yuan.app.constants.URLs;
import com.yuan.app.entities.NewsContent;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by yjz on 2016/6/5.
 */
public interface NewsService {
    @GET(URLs.NEWS_CONTENT + "{storyId}")
    public Call<NewsContent> getNewsContent(@Path("storyId") String storyId);
}

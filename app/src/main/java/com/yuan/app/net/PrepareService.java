package com.yuan.app.net;

import com.yuan.app.constants.URLs;
import com.yuan.app.entities.LunchPicture;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by yjz on 2016/5/29.
 * 还没有正式进入app主页面之前的一些网络请求
 */
public interface PrepareService {
    @GET(URLs.LUNCH_PICTURE)
    public Call<LunchPicture> getLunchPicture();
}

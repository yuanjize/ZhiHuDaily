package com.yuan.app.net;

import com.yuan.app.beans.LunchPicture;
import com.yuan.app.constants.URLs;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by yjz on 2016/5/29.
 * 还没有正式进入app主页面之前的一些网络请求
 */
public interface PrepareService {
    @GET(URLs.LUNCH_PICTURE)
    public Observable<LunchPicture> getLunchPicture();
}

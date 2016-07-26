package com.yuan.app.net;


import com.yuan.app.beans.MainData;
import com.yuan.app.beans.OldNews;
import com.yuan.app.constants.URLs;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yjz on 2016/5/29.
 */
public interface MainService {
    @GET(URLs.LASTEST_NEWS)
    public Observable<MainData> getTodayNews();

    @GET(URLs.OLD_NEWS + "{date}")
    public Observable<OldNews> getOldyNews(@Path("date") String date);
}

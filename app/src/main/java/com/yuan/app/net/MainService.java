package com.yuan.app.net;


import com.yuan.app.constants.URLs;
import com.yuan.app.entities.MainData;
import com.yuan.app.entities.OldNews;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by yjz on 2016/5/29.
 */
public interface MainService {
        @GET(URLs.LASTEST_NEWS)
        public Call<MainData> getTodayNews();
        @GET(URLs.OLD_NEWS+"{date}")
        public  Call<OldNews> getOldyNews(@Path("date") String date);
}

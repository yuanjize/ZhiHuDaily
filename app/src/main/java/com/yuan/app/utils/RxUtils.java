package com.yuan.app.utils;

import com.yuan.app.application.MyApplication;
import com.yuan.app.beans.LunchPicture;
import com.yuan.app.beans.MainData;
import com.yuan.app.beans.NewsContent;
import com.yuan.app.beans.OldNews;
import com.yuan.app.beans.ThemeNews;
import com.yuan.app.beans.Themes;
import com.yuan.app.net.MainService;
import com.yuan.app.net.NewsService;
import com.yuan.app.net.PrepareService;
import com.yuan.app.net.ThemesService;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by yjz on 2016/7/25.
 */
public class RxUtils {

    private RxUtils(){

    }

    private static MainService sMainService = MyApplication.getRetrofit().create(MainService.class);
    private static NewsService sNewsService = MyApplication.getRetrofit().create(NewsService.class);
    private static PrepareService sPrepareService = MyApplication.getRetrofit().create(PrepareService.class);
    private static ThemesService sThemesService = MyApplication.getRetrofit().create(ThemesService.class);

    public static Observable<List<MainData.StoriesBean>> getOldyNews(String date) {

        return assignThread(sMainService.getOldyNews(date))
                .map(new Func1<OldNews, List<MainData.StoriesBean>>() {

                    @Override
                    public List<MainData.StoriesBean> call(OldNews oldNews) {
                        return oldNews.getStories();
                    }
                });
    }

    public static Observable<MainData> getTodayNews() {
        return assignThread(sMainService.getTodayNews());
    }

    public static Observable<ThemeNews> getThemeNews(String themeId) {
        return assignThread(sThemesService.getThemeNews(themeId));
    }

    public static Observable<LunchPicture> getLunchPicture() {
        return assignThread(sPrepareService.getLunchPicture());
    }

    public static Observable<NewsContent> getNewsContent(String storyId) {
        return assignThread(sNewsService.getNewsContent(storyId));

    }

    public static Observable<Themes> getAllThemes(){
        return  assignThread(sThemesService.getAllThemes());
    }

    //制定执行的线程
    private static Observable assignThread(Observable observable) {
        return observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
    }
}

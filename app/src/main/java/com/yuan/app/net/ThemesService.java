package com.yuan.app.net;

import com.yuan.app.beans.ThemeNews;
import com.yuan.app.beans.Themes;
import com.yuan.app.constants.URLs;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yjz on 2016/6/4.
 */
public interface ThemesService {
    @GET(URLs.THEME_LIST)
    public Observable<Themes> getAllThemes();

    @GET(URLs.THEME_CONTENT + "{themeId}")
    public Observable<ThemeNews> getThemeNews(@Path("themeId") String themeId);
}

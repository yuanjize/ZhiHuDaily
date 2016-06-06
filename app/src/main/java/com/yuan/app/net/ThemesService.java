package com.yuan.app.net;

import com.yuan.app.constants.URLs;
import com.yuan.app.entities.ThemeNews;
import com.yuan.app.entities.Themes;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by yjz on 2016/6/4.
 */
public interface ThemesService {
    @GET(URLs.THEME_LIST)
    public Call<Themes> getAllThemes();

    @GET(URLs.THEME_CONTENT+"{themeId}")
    public Call<ThemeNews> getTheweNews(@Path("themeId") String themeId);
}

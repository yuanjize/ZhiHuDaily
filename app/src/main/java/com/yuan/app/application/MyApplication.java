package com.yuan.app.application;

import android.app.Application;
import android.content.Context;

import com.yuan.app.constants.URLs;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yjz on 2016/5/27.
 */
public class MyApplication extends Application {
    private static Context context;
    private static Retrofit retrofit;

    public MyApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URLs.BASE_URL)
                .build();
    }

    public static Context getContext() {
        return context;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}

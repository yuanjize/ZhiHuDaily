package com.yuan.app.other;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.utils.CommonUtils;
import com.yuan.app.utils.SPUtils;

import java.lang.reflect.Type;

import rx.Subscriber;

/**
 * Created by yjz on 2016/7/25.
 */
public abstract class BaseSubscribe<T> extends Subscriber<T> {
    private String cacheKey;
    //private Class clazz;
    private Type type;

    public BaseSubscribe(String url, Type type) {
        this.cacheKey = url;
        //  this.clazz=clazz;
        this.type = type;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        Context context = MyApplication.getContext();
        useCache();
        Toast.makeText(context, context.getResources().getString(R.string.serverException), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNext(T t) {
        Gson gson = CommonUtils.getGson();
        String cache = gson.toJson(t);
        SPUtils.getEditor().putString(cacheKey, cache).apply();
        handleMessage(t);
    }

    protected void useCache() {
        String cache = SPUtils.getSharedPreferences().getString(cacheKey, null);
        if (cache != null) {
            Gson gson = CommonUtils.getGson();
            T body = (T) gson.fromJson(cache, type);
            handleMessage(body);
        }
    }

    protected abstract void handleMessage(T t);
}

package com.yuan.app.other;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.utils.CommonUtils;
import com.yuan.app.utils.LogUtils;
import com.yuan.app.utils.SPUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yjz on 2016/5/29.
 */
public abstract class BaseRetrofitCallBack<T> implements Callback<T> {
    private String cacheKey;
    private Class clazz;

    public BaseRetrofitCallBack(String cacheKey, Class clazz) {
        this.cacheKey = cacheKey;
        this.clazz = clazz;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {

        char initial = String.valueOf(response.code()).charAt(0);
        LogUtils.e(response.code() + "");
        if (initial != '4' && initial != '5') {
            LogUtils.i(response.code() + "");
            //此处进行缓存
            Gson gson = CommonUtils.getGson();
            String cache = gson.toJson(response.body());
            SPUtils.getEditor().putString(cacheKey, cache).commit();
            //在外部处理信息
            LogUtils.e("NEWSTYPE:------------------>" + cache);
            handleMessage(response.body());
        } else {
            if (initial == '5') {
                Context context = MyApplication.getContext();
                Toast.makeText(context, context.getResources().getString(R.string.serverException), Toast.LENGTH_LONG).show();
            }
            useCache();
        }
    }


    //缓存
    protected void useCache() {
        String cache = SPUtils.getSharedPreferences().getString(cacheKey, null);
        if (cache != null) {
            Gson gson = CommonUtils.getGson();
            T body = (T) gson.fromJson(cache, clazz);
            handleMessage(body);
        }
    }

    protected abstract void handleMessage(T body);

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        LogUtils.i(t.getMessage() + t.toString());
        Context context = MyApplication.getContext();
        useCache();
        Toast.makeText(context, context.getResources().getString(R.string.requestfailure), Toast.LENGTH_LONG).show();
        //此处进行缓存的回复
    }
}

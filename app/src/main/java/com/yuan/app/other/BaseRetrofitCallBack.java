package com.yuan.app.other;

import android.content.Context;
import android.widget.Toast;

import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.utils.LogUtils;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by yjz on 2016/5/29.
 */
public abstract class BaseRetrofitCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        char initial = String.valueOf(response.code()).charAt(0);
        LogUtils.e(response.code()+"");
        if (initial != '4' && initial != '5') {
            LogUtils.i(response.code()+"");
            //在外部处理信息
            LogUtils.e("NEWSTYPE:------------------>"+response.body());
            handleMessage(response.body());
        } else {
            if (initial == '5') {
                Context context = MyApplication.getContext();
                Toast.makeText(context, context.getResources().getString(R.string.serverException), Toast.LENGTH_LONG).show();
            }
        }
    }

    protected abstract void handleMessage(T body);

    @Override
    public void onFailure(Throwable t) {
        LogUtils.i(t.getMessage()+t.toString());
        Context context = MyApplication.getContext();
        Toast.makeText(context, context.getResources().getString(R.string.requestfailure), Toast.LENGTH_LONG).show();
    }
}

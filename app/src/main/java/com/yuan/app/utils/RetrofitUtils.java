package com.yuan.app.utils;

import android.widget.Toast;

import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.other.BaseRetrofitCallBack;

import java.lang.reflect.Method;

import retrofit.Call;
import retrofit.Retrofit;

/**
 * Created by yjz on 2016/6/3.
 */
public class RetrofitUtils {
    public static <S,T> void request(Class<T> serviceClazz, String methodName, BaseRetrofitCallBack<S> callback, Object... params){
        Retrofit retrofit = MyApplication.getRetrofit();
        T service=retrofit.create(serviceClazz);
        try {
            Method[] methods = serviceClazz.getMethods();
            for (Method method:methods) {
                if(method.getName().equals(methodName)){
                    Call<S>call= (Call<S>) method.invoke(service,params);
                    call.enqueue(callback);
                }
            }
        } catch (Exception e) {
            Toast.makeText(MyApplication.getContext(), R.string.requestfailure, Toast.LENGTH_SHORT).show();
        }
    }
}
package com.yuan.app.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.yuan.app.application.MyApplication;

/**
 * Created by yjz on 2016/5/27.
 * SharedPreferences工具类
 */
public class SPUtils {
    private static String SPNAME="zhuHu";
    public static SharedPreferences getSharedPreferences(){
        Log.e("YUANJIZE",""+MyApplication.getContext());
        return  MyApplication.getContext().getSharedPreferences(SPNAME,Context.MODE_PRIVATE);
    }
    public static SharedPreferences.Editor getEditor(){
        return getSharedPreferences().edit();
    }
}

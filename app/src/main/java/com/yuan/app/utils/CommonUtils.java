package com.yuan.app.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.yuan.app.application.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yjz on 2016/6/3.
 */
public class CommonUtils {
    public static String getCurrentDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyyMMdd");
        return format.format(date);
    }

    public static float getScreenWidth() {
        WindowManager windowManager = (WindowManager) MyApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }
}

package com.yuan.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yuan.app.application.MyApplication;

/**
 * Created by yjz on 2016/5/29.
 * 有关网络连接的工具类
 */
public class ConnectUtils {
    //网络是否连接
    public static boolean isConnected(){
        ConnectivityManager manager=getConnectivityManager();
        NetworkInfo info=getConnectivityManager().getActiveNetworkInfo();
        if(info!=null&&info.isConnected()){
            return true;
        }else{
            return false;
        }
    }

    //是否连接WIFI
    public static boolean isWIFIConnected(){
        ConnectivityManager manager=getConnectivityManager();
        NetworkInfo networkInfo = manager.getNetworkInfo(manager.TYPE_WIFI);
        if(networkInfo.isConnected()){
            return true;
        }
        else{
            return false;
        }
    }
    //得到系统的网络链接管理器
    public  static ConnectivityManager getConnectivityManager(){
      return (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}

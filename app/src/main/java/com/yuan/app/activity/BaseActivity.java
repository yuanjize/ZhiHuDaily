package com.yuan.app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.yuan.app.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by yjz on 2016/5/29.
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        activities.add(this); //对Activity进行管理
        init();
    }

    //初始化一些View监听器什么的
    protected abstract void init();

    //退出程序
    public static void exitApplication() {
        for (Activity activity : activities) {
            activity.finish();
            activities.remove(activity);
        }

    }

    //得到布局ID
    protected abstract int getLayoutId();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (activities.contains(this)) {
            activities.remove(this);
        }
    }

}

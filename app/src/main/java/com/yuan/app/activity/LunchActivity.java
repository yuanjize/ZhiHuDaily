package com.yuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yuan.app.R;
import com.yuan.app.application.MyApplication;
import com.yuan.app.constants.URLs;
import com.yuan.app.entities.LunchPicture;
import com.yuan.app.net.PrepareService;
import com.yuan.app.other.BaseRetrofitCallBack;
import com.yuan.app.utils.ConnectUtils;
import com.yuan.app.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit.Call;


/**
 * @author yjz
 *         启动界面，如果联网，就加载 启动页边并检测更新，否则跳转到主页面
 */
public class LunchActivity extends AppCompatActivity {

    @BindView(R.id.lunch_image)
    ImageView lunchImage;

    //使用handler定时显示1.5s
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(LunchActivity.this, MainActivity.class));
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ConnectUtils.isConnected()) {
            LogUtils.i("ISOK");
            setContentView(R.layout.activity_lunch);
            ButterKnife.bind(this);
            showLunchPicture();
            checkUpdate();
        } else {
            LogUtils.i("FALSE");
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

    }

    //检查更新
    private void checkUpdate() {

    }

    //展示启动图片
    private void showLunchPicture() {
        PrepareService prepareService = MyApplication.getRetrofit().create(PrepareService.class);
        Call<LunchPicture> call = prepareService.getLunchPicture();
        call.enqueue(new BaseRetrofitCallBack<LunchPicture>(URLs.LUNCH_PICTURE, LunchPicture.class) {
            @Override
            protected void handleMessage(LunchPicture body) {
                if (body != null) {
                    LogUtils.i(body.getImg());
                    Glide.with(LunchActivity.this).load(body.getImg()).into(lunchImage);
                    handler.sendEmptyMessageDelayed(0, 3000);
                }
            }
        });
    }
}

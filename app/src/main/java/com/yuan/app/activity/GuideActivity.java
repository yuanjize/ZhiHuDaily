package com.yuan.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.yuan.app.R;
import com.yuan.app.other.GuidePageTransformer;
import com.yuan.app.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author yjz
 *         安装之后的引导界面
 *         hasInstall，如果为false那么是第一次安装，进入引导界面，并修改标记为true
 *         否则不是第一次安装进入，直接进入启动页面
 */
public class GuideActivity extends AppCompatActivity {
    @BindView(R.id.install_page)
    ViewPager installPage;
    private List<View> images = new ArrayList<>(3);
    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean hasInstall = SPUtils.getSharedPreferences().getBoolean("hasInstall", false);
        if (hasInstall) {
            startActivity(new Intent(this, LunchActivity.class));
            finish();
        } else {

            setContentView(R.layout.activity_guide);
            ButterKnife.bind(this);
            inflater = LayoutInflater.from(this);
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            int width = windowManager.getDefaultDisplay().getWidth();
            installPage.setPageTransformer(true, new GuidePageTransformer(width));
            View view1 = inflater.inflate(R.layout.guide1_layout, null);
            View view2 = inflater.inflate(R.layout.guide2_layout, null);
            View view3 = inflater.inflate(R.layout.guide3_layout, null);
            images.add(view1);
            images.add(view2);
            images.add(view3);
            view3.findViewById(R.id.begin).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SPUtils.getEditor().putBoolean("hasInstall", true).commit();
                    startActivity(new Intent(GuideActivity.this, LunchActivity.class));
                    finish();
                }
            });


            installPage.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return images.size();
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(images.get(position));
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(images.get(position));
                    return images.get(position);
                }
            });
        }
    }
}

package com.yuan.app.views;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuan.app.R;
import com.yuan.app.entities.MainData;
import com.yuan.app.other.BannerPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjz on 2016/5/29.
 * 循环ViewPager
 */
public class LoopViewPager extends LinearLayout {
    private View view;
    private Context context;
    private List<ImageView> points;
    private LinearLayout pointParent;
    private TextView tittle;
    private ViewPager pager;
    private List<MainData.TopStoriesBean> datas;
    private List<ImageView> pages = new ArrayList<>();
    private BannerPageAdapter adapter;
    private int lastPointCount = 0;
    private int lastImageSize = 0;
    private int currentIndex = 0;//当前页序号
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            currentIndex++;
            if (currentIndex >= datas.size()) {
                currentIndex = 0;
            }
            pager.setCurrentItem(currentIndex);
            handler.sendEmptyMessageDelayed(8, 3000);
        }
    };


    public LoopViewPager(Context context) {
        super(context);
        init(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoopViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //加载到当前group
    private void init(Context context) {
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.loopviewpager_layout, this, true);
        pointParent = (LinearLayout) view.findViewById(R.id.banner_points);
        tittle = (TextView) view.findViewById(R.id.banner_tittle);
        pager = (ViewPager) view.findViewById(R.id.banner_pager);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                select(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setPageAdapter(BannerPageAdapter adapter, List<MainData.TopStoriesBean> datas) {
        this.adapter = adapter;
        this.datas = datas;
        adapter.setDatas(datas);
        int size = datas.size();
        if (lastImageSize != size) {
            pages.clear();
            for (int i = 0; i < size; i++) {
                ImageView image = new ImageView(context);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                image.setLayoutParams(layoutParams);
                image.setScaleType(ImageView.ScaleType.CENTER_CROP);
                pages.add(image);
            }
        }

        adapter.setImages(pages);
        pager.setAdapter(adapter);
        setPointCount(datas.size());
    }

    //点的数量
    public void setPointCount(int count) {
        if (count == lastPointCount) {
            setCurrentPage(0);
            return;
        }
        lastPointCount = count;
        pointParent.removeAllViews();
        if (points == null) {
            points = new ArrayList<>(count);
        }
        points.clear();
        for (int i = 0; i < count; i++) {
            ImageView image = new ImageView(context);
            image.setBackgroundResource(R.drawable.banner_point_selector);
            points.add(image);
            pointParent.addView(image);
        }
        setCurrentPage(0);
    }

    private void setCurrentPage(int currentPage) {
        this.currentIndex = currentPage;
        select(currentPage);
        pager.setCurrentItem(currentPage);
    }

    public void select(int currentPage) {
        if (points == null) {
            return;
        }
        for (ImageView point : points) {
            point.setSelected(false);
        }
        points.get(currentPage).setSelected(true);
        tittle.setText(datas.get(currentPage).getTitle());
    }

    public void start() {
        handler.sendEmptyMessageDelayed(8, 3000);
    }

    public void stop() {
        handler.removeMessages(8);
    }

}

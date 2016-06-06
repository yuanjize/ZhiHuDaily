package com.yuan.app.other;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by yjz on 2016/5/27.
 * Guide页面ViewPage的滑动效果
 *
 * 如果想要一个渐变的效果，那么需要当position
 * 由0到1和0到-1的时候颜色变浅，即离开屏幕的页面变浅
 * 由-1到0的时候颜色变深色，进入屏幕的页面变身色
 * 出去时scal变大
 * 进来时scal变小
 * 同时不断设置page偏移量为0
 *
 */
public class GuidePageTransformer implements ViewPager.PageTransformer{

    private float last=0;
    private float begin=0;
    private float width;

    public GuidePageTransformer(int width) {
        this.width=width;
    }

    @Override
    public void transformPage(View page, float position) {
        //待在固定位置不变
        page.setTranslationX(0f);
        page.setTranslationY(0f);
        if(position<=-1){
            page.setAlpha(0);
        }else if(position<0){
            page.setTranslationX(width*(-position));
            page.setAlpha(1-Math.abs(position));
            page.setScaleX(Math.abs(1+Math.abs(position)));
            page.setScaleY(Math.abs(1+Math.abs((position))));
        }else if(position<=1){
            page.setTranslationX(width*(-position));
            page.setAlpha(1-Math.abs(position));
            page.setScaleX(Math.abs(1+position));
            page.setScaleY(Math.abs(1+position));
        }
    }
}

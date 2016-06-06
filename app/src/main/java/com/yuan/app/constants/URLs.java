package com.yuan.app.constants;

/**
 * Created by yjz on 2016/5/27.
 * 主要用来存储URL常量
 *
 */
public interface URLs {
    //主机地址
    String BASE_URL="http://news-at.zhihu.com/api/4/";
    //启动图片
    String LUNCH_PICTURE="start-image/720*1184";
    //最新消息
    String LASTEST_NEWS="news/latest";
    //消息内容获取与离线下载
    String NEWS_CONTENT="news/";
    //过往消息
    String OLD_NEWS="news/before/";
    //新闻额外信息
    String NEWS_EXTRA_INFO="story-extra/#{id}";
    //新闻对应长评论查看
    String NEWS_LONG_CONTENT="/story/4232852/long-comments";
    //新闻对应短评论查看
    String NEWS_SHORT_CONTENT="/story/4232852/short-comments";

    //主题日报列表查看
    String THEME_LIST="themes";
    //主题日报内容查看
    String THEME_CONTENT="theme/";
    //获取某个专栏之前的新闻
    String GET_OLD_THEMENEWS="/theme/#{theme id}/before/#{id}";

}

package com.yuan.app.beans;

import java.util.List;

/**
 * Created by yjz on 2016/6/3.
 */
public class OldNews {


    private String date;

    private List<MainData.StoriesBean> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MainData.StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<MainData.StoriesBean> stories) {
        this.stories = stories;
    }

}

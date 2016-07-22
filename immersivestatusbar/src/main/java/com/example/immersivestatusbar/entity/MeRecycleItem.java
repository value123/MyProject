package com.example.immersivestatusbar.entity;

/**
 * Created by shenwenjie on 6/7/2016.
 */
public class MeRecycleItem {
    int iconId;
    public String title;
    public String url;
    public String description;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public MeRecycleItem(int iconId, String title, String description, String url) {
        this.iconId = iconId;
        this.title = title;
        this.description = description;
        this.url = url;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

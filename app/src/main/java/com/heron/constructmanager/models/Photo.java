package com.heron.constructmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Photo {
    String constructionUid;
    String title;
    String desc;
    @Exclude
    String photoUid;

    @Exclude
    public String getPhotoUid() {
        return photoUid;
    }
    @Exclude
    public void setPhotoUid(String photoUid) {
        this.photoUid = photoUid;
    }

    public String getConstructionUid() {
        return constructionUid;
    }

    public void setConstructionUid(String constructionUid) {
        this.constructionUid = constructionUid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Photo() {
    }

    public Photo(String constructionUid, String title, String desc) {
        this.constructionUid = constructionUid;
        this.title = title;
        this.desc = desc;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("constructionUid", constructionUid);
        result.put("title", title);
        result.put("desc", desc);
        return result;
    }

}

package com.heron.constructmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Construction {
    private String title;
    private String address;

    @Exclude
    String uid;

    @Exclude
    public String getUid() {
        return uid;
    }

    @Exclude
    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType_construction() {
        return constructionType;
    }

    public void setType_construction(String constructionType) {
        this.constructionType = constructionType;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getResponsibles() {
        return responsibles;
    }

    public void setResponsibles(String responsibles) {
        this.responsibles = responsibles;
    }

    private String constructionType;
    private String stage;
    private String responsibles;

    public Construction() {
        // Default constructor required for calls to DataSnapshot.getValue(Construction.class)
    }

    public Construction(String title, String address, String constructionType, String stage, String responsibles) {
        this.title = title;
        this.address = address;
        this.constructionType = constructionType;
        this.stage = stage;
        this.responsibles = responsibles;
    }

    // [START construction_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("address", address);
        result.put("constructionType", constructionType);
        result.put("stage", stage);
        result.put("responsibles", responsibles);

        return result;
    }
    // [END construction_to_map]
}

package com.heron.constructmanager.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Information {
    private String title;
    private String address;
    private String type;
    private String stage;
    private String responsibles;

    public Information() {
        // Default constructor required for calls to DataSnapshot.getValue(Construction.class)
    }

    public Information(String title, String address, String type, String stage, String responsibles) {
        this.title = title;
        this.address = address;
        this.type = type;
        this.stage = stage;
        this.responsibles = responsibles;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    // [START info_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("address", address);
        result.put("type", type);
        result.put("stage", stage);
        result.put("responsibles", responsibles);

        return result;
    }
    // [END info_to_map]


}

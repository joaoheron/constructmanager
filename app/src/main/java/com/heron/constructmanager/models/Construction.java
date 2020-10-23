package com.heron.constructmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Construction {
//    private String title;
//    private String address;
//    private String type;
//    private String stage;
//    private String responsibles;
    private Information information;

    public Information getInformation(){ return information; }

    public void setInformation(Information information){ this.information = information; }

    @Exclude
    String constructionUid;

    @Exclude
    public String getUid() {
        return constructionUid;
    }

    @Exclude
    public void setUid(String uid) {
        this.constructionUid = uid;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public String getStage() {
//        return stage;
//    }
//
//    public void setStage(String stage) {
//        this.stage = stage;
//    }
//
//    public String getResponsibles() {
//        return responsibles;
//    }
//
//    public void setResponsibles(String responsibles) {
//        this.responsibles = responsibles;
//    }

    public Construction() {
        // Default constructor required for calls to DataSnapshot.getValue(Construction.class)
    }

    public Construction(Information information) {
        this.information = information;
    }

//    public Construction(String title, String address, String type, String stage, String responsibles) {
//        this.title = title;
//        this.address = address;
//        this.type = type;
//        this.stage = stage;
//        this.responsibles = responsibles;
//    }

//    // [START construction_to_map]
//    @Exclude
//    public Map<String, Object> toMap() {
//        HashMap<String, Object> result = new HashMap<>();
//        result.put("title", title);
//        result.put("address", address);
//        result.put("type", type);
//        result.put("stage", stage);
//        result.put("responsibles", responsibles);
//
//        return result;
//    }
//    // [END construction_to_map]
}

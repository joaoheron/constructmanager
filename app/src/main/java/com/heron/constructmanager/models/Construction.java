package com.heron.constructmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Construction {

    Information information;
    @Exclude
    String constructionUid;

    public Information getInformation(){ return information; }

    public void setInformation(Information information){ this.information = information; }

    @Exclude
    public String getUid() {
        return constructionUid;
    }

    @Exclude
    public void setUid(String uid) {
        this.constructionUid = uid;
    }


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

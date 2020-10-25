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

}

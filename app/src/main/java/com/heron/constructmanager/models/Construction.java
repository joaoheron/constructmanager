package com.heron.constructmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.List;

@IgnoreExtraProperties
public class Construction {

    Information information;
    @Exclude
    List<Responsability> responsabilities;

    @Exclude
    String constructionUid;

    public Information getInformation(){ return information; }

    public void setInformation(Information information){ this.information = information; }

    @Exclude
    public List<Responsability> getResponsabilities(){ return responsabilities; }

    @Exclude
    public void setResponsabilities(List<Responsability> responsabilities){ this.responsabilities = responsabilities; }

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

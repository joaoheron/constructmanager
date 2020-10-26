package com.heron.constructmanager.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Responsability {
    public String constructionUid;
    public String responsibleUid;
    public String responsabilityTitle;
    public String desc;
    public String responsibleName;
    public String deadline;
    public String state;

    public String getResponsabilityTitle() {
        return responsabilityTitle;
    }

    public void setResponsabilityTitle(String responsabilityTitle) {
        this.responsabilityTitle = responsabilityTitle;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Exclude
    public String responsabilityUid;

    @Exclude
    public String getResponsabilityUid() {
        return responsabilityUid;
    }

    @Exclude
    public void setResponsabilityUid(String responsabilityUid) {
        this.responsabilityUid = responsabilityUid;
    }

    public String getConstructionUid() {
        return constructionUid;
    }

    public void setConstructionUid(String constructionUid) {
        this.constructionUid = constructionUid;
    }

    public String getResponsibleUid() {
        return responsibleUid;
    }

    public void setResponsibleUid(String responsibleUid) {
        this.responsibleUid = responsibleUid;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Responsability() {
        // Default constructor required for calls to DataSnapshot.getValue(Responsability.class)
    }

    public Responsability(String constructionUid, String userUid, String responsible, String deadline, String state) {
        this.constructionUid = constructionUid;
        this.responsibleUid = userUid;
        this.responsibleName = responsible;
        this.deadline = deadline;
        this.state = state;
    }

    // [START info_to_map]
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("constructionUid", constructionUid);
        result.put("responsibleUid", responsibleUid);
        result.put("responsibleName", responsibleName);
        result.put("responsabilityTitle", responsabilityTitle);
        result.put("desc", responsabilityTitle);
        result.put("deadline", deadline);
        result.put("state", state);

        return result;
    }

}

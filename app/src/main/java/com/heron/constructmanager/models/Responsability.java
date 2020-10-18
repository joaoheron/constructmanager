package com.heron.constructmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Responsability {
    public String uid;
    public String obra;
    public String author;
    public String responsible;
    public String deadline;
    public String state;

    public Responsability() {
        // Default constructor required for calls to DataSnapshot.getValue(Responsability.class)
    }

    public Responsability(String uid, String obra, String author, String responsible, String deadline, String state) {
        this.uid = uid;
        this.obra = obra;
        this.author = author;
        this.responsible = responsible;
        this.deadline = deadline;
        this.state = state;
    }
}

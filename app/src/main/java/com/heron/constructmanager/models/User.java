package com.heron.constructmanager.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START blog_user_class]
@IgnoreExtraProperties
public class User {

    public String name;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
// [END blog_user_class]

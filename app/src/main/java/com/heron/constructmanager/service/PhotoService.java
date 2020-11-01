package com.heron.constructmanager.service;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.Constants;
import com.heron.constructmanager.Utils;
import com.heron.constructmanager.models.Photo;

import java.util.HashMap;
import java.util.Map;

public class PhotoService {
    Context context;
    DatabaseReference rootReference;
    FirebaseDatabase db;
    FirebaseAuth auth;

    public PhotoService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootReference = db.getReference();
    }

    public void writePhoto(String constructionUid, String title, String desc, String photoUid) {
        DatabaseReference constructionReference = rootReference.child("constructions").child(constructionUid);
        if (photoUid == null) {
            photoUid = constructionReference.child("photos").push().getKey();
        }
        Photo photo = new Photo(constructionUid, title, desc);
        Map<String, Object> postValues = photo.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/photos/" + photoUid, postValues);

        constructionReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            Utils.showToastMsg(context, task, Constants.WRITE);
        });
    }

    public DatabaseReference getPhotosReference(String constructionUid) {
        return db.getReference().child("constructions").child(constructionUid).child("photos");
    }
}

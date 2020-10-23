package com.heron.constructmanager.service;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.models.Construction;

import java.util.HashMap;
import java.util.Map;

public class ConstructionService {

    Context context;
    DatabaseReference rootReference;
    FirebaseAuth auth;

    public ConstructionService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();
    }

    public void deleteConstruction(String userId, String constructionUid) {

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid, null);
        childUpdates.put("/users/" + userId + "/constructions/" + constructionUid, null);

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(context, "Obra deletada com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Erro ao deletar obra! Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void writeConstruction(String userId, String title, String address, String stage, String type, String responsibles, String constructionUid) {
        // Create new post at /users/$user_id/$construction_id and at
        // /constructions/$construction_id simultaneously
        if (constructionUid == null) {
            constructionUid = rootReference.child("constructions").push().getKey();
        }

        Construction construction = new Construction(title, address, type, stage, responsibles);
        Map<String, Object> postValues = construction.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid, postValues);
        childUpdates.put("/users/" + userId + "/constructions/" + constructionUid, postValues);

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(context, "Obra cadastrada com sucesso.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Erro ao cadastrar obra! Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });
    }

}

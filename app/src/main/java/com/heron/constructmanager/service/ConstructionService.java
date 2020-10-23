package com.heron.constructmanager.service;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
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

    public final String NEW_STAGE = "Avanço de Etapa";
    public final String WRITE = "Cadastro";
    public final String DELETE = "Remoção";
    public final String CANCEL = "Cancelamento";


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
            showToastMsg(task, DELETE);
        });

    }

    public void cancelConstruction(String userId, String title, String address, String type, String responsibles, String constructionUid) {
        String stage = "Cancelada";

        Construction construction = new Construction(title, address, type, stage, responsibles);
        Map<String, Object> postValues = construction.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid, postValues);
        childUpdates.put("/users/" + userId + "/constructions/" + constructionUid, postValues);

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, CANCEL);
        });
    }

    public void advanceStageToExec(String userId, String title, String address, String type, String responsibles, String constructionUid) {
        String stage = "Execução";

        Construction construction = new Construction(title, address, type, stage, responsibles);
        Map<String, Object> postValues = construction.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid, postValues);
        childUpdates.put("/users/" + userId + "/constructions/" + constructionUid, postValues);

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, NEW_STAGE);
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
            showToastMsg(task, WRITE);
        });
    }

    public void showToastMsg(Task task, String action) {
        if(task.isSuccessful()){
            Toast.makeText(context, action + " efetuado com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Erro tentar realizar " + action + " !", Toast.LENGTH_LONG).show();
        }

    }

}

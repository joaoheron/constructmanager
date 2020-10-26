package com.heron.constructmanager.service;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.models.Construction;
import com.heron.constructmanager.models.Information;
import com.heron.constructmanager.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConstructionService {

    Context context;
    DatabaseReference rootReference;
    DatabaseReference constructionsReference;
    FirebaseDatabase db;
    FirebaseAuth auth;

    List constructionsList;

    public final String NEW_STAGE = "Avanço de Etapa";
    public final String WRITE = "Cadastro";
    public final String DELETE = "Remoção";
    public final String CANCEL = "Cancelamento";


    public ConstructionService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootReference = db.getReference();
        constructionsList = new ArrayList<>();
    }

    //  @@@@@@@@@@ WRITE, EDIT AND DELETE @@@@@@@@@@

    public void writeConstructionInfo(String userId, String title, String address, String stage, String type, List<User> responsibles, String constructionUid) {
        // Create new post at /users/$user_id/$construction_id and at /constructions/$construction_id simultaneously
        if (constructionUid == null) {
            constructionUid = rootReference.child("constructions").push().getKey();
        }
        Information information = new Information(title, address, type, stage, responsibles);
        Construction construction = new Construction(information);
        Map<String, Object> postValues = construction.getInformation().toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid + "/information", postValues);
        childUpdates.put("/users/" + userId + "/constructions/" + constructionUid + "/information", postValues);

        for (User responsible: responsibles) {
            if (!responsible.getUid().equals(userId)) {
                childUpdates.put("/users/" + responsible.getUid() + "/constructions/" + constructionUid + "/information", postValues);
            }
        }

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, WRITE);
        });
    }

    public void deleteConstruction(String userId, String constructionUid, List<User> responsibles) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid, null);
        childUpdates.put("/users/" + userId + "/constructions/" + constructionUid, null);

        for (User responsible: responsibles) {
            if (!responsible.getUid().equals(userId)) {
                childUpdates.put("/users/" + responsible.getUid() + "/constructions/" + constructionUid + "/information", null);
            }
        }

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, DELETE);
        });

    }

    //  @@@@@@@@@@ STAGE ACTIONS @@@@@@@@@@

    public void cancelConstruction(String userId, String title, String address, String type, List<User> responsibles, String constructionUid) {
        String stage = "Cancelada";

        Information information = new Information(title, address, type, stage, responsibles);
        Construction construction = new Construction(information);
        Map<String, Object> postValues = construction.getInformation().toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid + "/information", postValues);
        childUpdates.put("/users/" + userId + "/constructions/" + constructionUid + "/information", postValues);

        for (User responsible: responsibles) {
            if (!responsible.getUid().equals(userId)) {
                childUpdates.put("/users/" + responsible.getUid() + "/constructions/" + constructionUid + "/information", postValues);
            }
        }

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, CANCEL);
        });
    }

    public void advanceStageToExec(String userId, String title, String address, String type, List<User> responsibles, String constructionUid) {
        String stage = "Execução";

        Information information = new Information(title, address, type, stage, responsibles);
        Construction construction = new Construction(information);
        Map<String, Object> postValues = construction.getInformation().toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid + "/information", postValues);
        childUpdates.put("/users/" + userId + "/constructions/" + constructionUid + "/information", postValues);

        for (User responsible: responsibles) {
            if (!responsible.getUid().equals(userId)) {
                childUpdates.put("/users/" + responsible.getUid() + "/constructions/" + constructionUid + "/information", postValues);
            }
        }

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, NEW_STAGE);
        });
    }

    //  @@@@@@@@@@ GET REFERENCES @@@@@@@@@@

    public DatabaseReference getConstructionsReference(String userUid) {
        return db.getReference().child("users").child(userUid).child("constructions");
    }

    //  @@@@@@@@@@ TOAST MESSAGES @@@@@@@@@@

    public void showToastMsg(Task task, String action) {
        if(task.isSuccessful()){
            Toast.makeText(context, action + " efetuado com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Erro tentar realizar " + action + " !", Toast.LENGTH_LONG).show();
        }
    }
}

package com.heron.constructmanager.service;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.heron.constructmanager.models.Construction;
import com.heron.constructmanager.models.Information;
import com.heron.constructmanager.models.Responsability;
import com.heron.constructmanager.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponsabilityService {

    Context context;
    DatabaseReference rootReference;
    DatabaseReference usersReference;
    FirebaseDatabase db;
    FirebaseAuth auth;


    public final String OPEN = "Aberto";
    public final String SOLVED = "Resolvido";
    public final String WRITE = "Cadastro";


    public ResponsabilityService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootReference = db.getReference();
    }

    public void writeResponsability(String constructionUid, String responsabilityUid, String title, String desc, String deadline, String state, String responsibleEmail) {
        DatabaseReference constructionReference = rootReference.child("constructions").child(constructionUid);
        if (responsabilityUid == null) {
            responsabilityUid = constructionReference.child("responsabilities").push().getKey();
        }

        Responsability responsability = new Responsability(constructionUid, title, desc, deadline, state, responsibleEmail);
        Map<String, Object> postValues = responsability.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/responsabilities/" + responsabilityUid, postValues);

        constructionReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, WRITE);
        });
    }

    public void solveResponsability(String constructionUid, String responsabilityUid) {
        DatabaseReference responsabilityReference = rootReference.child("constructions").child(constructionUid).child("responsabilities").child(responsabilityUid);
        responsabilityReference.child("state").setValue(SOLVED).addOnCompleteListener(task -> {
            showToastMsg(task, SOLVED);
        });
    }

    //  @@@@@@@@@@ GET REFERENCES @@@@@@@@@@

    public DatabaseReference getResponsabilitiesReference(String constructionUid) {
        return db.getReference().child("constructions").child(constructionUid).child("responsabilities");
    }

    public void showToastMsg(Task task, String action) {
        if(task.isSuccessful()){
            Toast.makeText(context, action + " efetuado com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Erro tentar realizar " + action + " !", Toast.LENGTH_LONG).show();
        }

    }

}

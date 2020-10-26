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

    List<Responsability> allResponsabilitiesList;
    List<String> allResponsiblesEmailsList;

    public final String OPEN = "Aberta";
    public final String SOLVED = "Resolvida";
    public final String WRITE = "Cadastro";


    public ResponsabilityService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootReference = db.getReference();
        allResponsabilitiesList = new ArrayList<>();
        allResponsiblesEmailsList = new ArrayList<>();
    }

    // TODO remove construction/ node e keep only user/userid/construction

    public void writeResponsability(String constructionUid, String userUid, String responsible, String deadline, String state, String responsabilityUid, List<User> responsibles) {
        // Create new post at /users/$user_id/$construction_id and at /constructions/$construction_id simultaneously
        if (responsabilityUid == null) {
            responsabilityUid = rootReference.child("constructions").child(constructionUid).child("responsabilities").push().getKey();
        }
        Responsability responsability = new Responsability(constructionUid, userUid, responsible, deadline, state);
        Map<String, Object> postValues = responsability.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + constructionUid + "/responsabilities/" + responsabilityUid, postValues);
        childUpdates.put("/users/" + userUid + "/constructions/" + constructionUid + "/responsabilities/" + responsabilityUid, postValues);

        for (User user: responsibles) {
            if (!user.getUid().equals(userUid)) {
                childUpdates.put("/users/" + user.getUid() + "/constructions/" + constructionUid + "/responsabilities/" + responsabilityUid, postValues);
            }
        }

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, WRITE);
        });
    }

    //  @@@@@@@@@@ GET REFERENCES @@@@@@@@@@

    public DatabaseReference getResponsabilitiesReference(String userUid, String constructionUid) {
        return db.getReference().child("users").child(userUid).child("constructions").child(constructionUid).child("responsabilities");
    }

    public void showToastMsg(Task task, String action) {
        if(task.isSuccessful()){
            Toast.makeText(context, action + " efetuado com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Erro tentar realizar " + action + " !", Toast.LENGTH_LONG).show();
        }

    }



}

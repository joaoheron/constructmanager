package com.heron.constructmanager.service;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.models.Budget;

import java.util.HashMap;
import java.util.Map;

public class BudgetService {

    Context context;
    DatabaseReference rootReference;
    FirebaseDatabase db;
    FirebaseAuth auth;
    public final String WRITE = "Cadastro";

    public BudgetService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootReference = db.getReference();
    }

    public void writeBudget(String constructionUid, String title, String desc, float value, String budgetUid) {
        DatabaseReference constructionReference = rootReference.child("constructions").child(constructionUid);
        if (budgetUid == null) {
            budgetUid = constructionReference.child("budgets").push().getKey();
        }
        Budget budget = new Budget(constructionUid, title, desc, value);
        Map<String, Object> postValues = budget.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/budgets/" + budgetUid, postValues);

        constructionReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, WRITE);
        });
    }

    public DatabaseReference getBudgetsReference(String constructionUid) {
        return db.getReference().child("constructions").child(constructionUid).child("budgets");
    }

    public void showToastMsg(Task task, String action) {
        if(task.isSuccessful()){
            Toast.makeText(context, action + " efetuado com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Erro tentar realizar " + action + " !", Toast.LENGTH_LONG).show();
        }

    }

}

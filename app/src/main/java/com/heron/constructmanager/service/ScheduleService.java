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
import com.heron.constructmanager.models.Delay;
import com.heron.constructmanager.models.Information;
import com.heron.constructmanager.models.Responsability;
import com.heron.constructmanager.models.Schedule;
import com.heron.constructmanager.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleService {

    Context context;
    DatabaseReference rootReference;
    FirebaseDatabase db;
    FirebaseAuth auth;
    String WRITE = "Cadastro";

    public final String OPEN = "Aberto";
    public final String SOLVED = "Resolvido";
    public final String LATE = "Atrasado";


    public ScheduleService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootReference = db.getReference();
    }

    public void writeSchedule(String constructionUid, String title, String deadline, String state, String scheduleUid) {
        DatabaseReference schedulesReference = rootReference.child("constructions").child(constructionUid).child("schedules");
        if (scheduleUid == null) {
            scheduleUid = schedulesReference.push().getKey();
        }

        Schedule schedule = new Schedule(constructionUid, title, deadline, state);
        Map<String, Object> postValues = schedule.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(scheduleUid, postValues);

        schedulesReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, WRITE);
        });
    }

    public void writeDelay(String constructionUid, String scheduleUid, String title, String reason, boolean isExcusable, boolean isCompensable, boolean isConcurrent, boolean isCritical, String aditionalInfo, String delayUid) {
        DatabaseReference delaysReference = rootReference.child("constructions").child(constructionUid).child("schedules").child(scheduleUid).child("delays");
        if (delayUid == null) {
            delayUid = delaysReference.push().getKey();
        }

        Delay delay = new Delay(constructionUid, scheduleUid, title, reason, isExcusable, isCompensable, isConcurrent, isCritical, aditionalInfo);
        Map<String, Object> postValues = delay.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(delayUid, postValues);

        delaysReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, WRITE);
        });
    }

    public DatabaseReference getSchedulesReference(String constructionUid) {
        return db.getReference().child("constructions").child(constructionUid).child("schedules");
    }

    public DatabaseReference getDelaysReference(String constructionUid, String scheduleUid) {
        return db.getReference().child("constructions").child(constructionUid).child("schedules").child(scheduleUid).child("delays");
    }

    public void showToastMsg(Task task, String action) {
        if(task.isSuccessful()){
            Toast.makeText(context, action + " efetuado com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Erro tentar realizar " + action + " !", Toast.LENGTH_LONG).show();
        }

    }

}

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
import com.heron.constructmanager.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {

    Context context;
    DatabaseReference rootReference;
    DatabaseReference usersReference;
    FirebaseDatabase db;
    FirebaseAuth auth;

    List<User> allUsersList;
    List<String> allEmailsList;

    public final String WRITE = "Cadastro";
    public final String DELETE = "Remoção";

    public UserService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootReference = db.getReference();
        allUsersList = new ArrayList<>();
        allEmailsList = new ArrayList<>();
    }

    public void deleteUser(String userId) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId, null);

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, DELETE);
        });
    }

    public void readUsers() {
        usersReference = db.getReference("/users/");
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                allUsersList = new ArrayList<>();
                User user;
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    user = userSnapshot.getValue(User.class);
                    user.setUid(userSnapshot.getKey());
                    allUsersList.add(user);
                    allEmailsList.add(user.getName());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public List<User> getUsersByEmails(List<String> emails) {
        List <User> allUsersMatchedEmail = new ArrayList();
        if (allUsersList.size() > 0 && emails.size() > 0) {
            for (int i = 0; i < allUsersList.size(); i++) {
                if (emails.contains(allUsersList.get(i).getEmail())) {
                    allUsersMatchedEmail.add(allUsersList.get(i));
                }
            }
        }
        return allUsersMatchedEmail;
    }

    public void showToastMsg(Task task, String action) {
        if(task.isSuccessful()){
            Toast.makeText(context, action + " efetuado com sucesso.", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Erro tentar realizar " + action + " !", Toast.LENGTH_LONG).show();
        }

    }

}



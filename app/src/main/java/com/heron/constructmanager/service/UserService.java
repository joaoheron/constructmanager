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

    public final String WRITE = "Cadastro";
    public final String DELETE = "Remoção";

    public UserService(Context c) {
        context = c;
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        rootReference = db.getReference();
    }

    public void writeNewUser(String userUid, String name, String email, boolean admin) {
        User user = new User(name, email, admin);
        rootReference.child("users").child(userUid).setValue(user).addOnCompleteListener(task -> {
            showToastMsg(task, WRITE);
        });;
    }

    public DatabaseReference getUsersReference() {
        return rootReference.child("users");
    }

    public void deleteUser(String userId) {
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userId, null);

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            showToastMsg(task, DELETE);
        });
    }

    public List<User> getUsersByEmails(List<String> selectedEmails, List<User> allUsersList) {
        List <User> allUsersMatchedEmail = new ArrayList();
        if (allUsersList.size() > 0 && selectedEmails.size() > 0) {
            for (int i = 0; i < allUsersList.size(); i++) {
                if (selectedEmails.contains(allUsersList.get(i).getEmail())) {
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



package com.heron.constructmanager.activities.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;
import com.heron.constructmanager.animations.LoadingAnimation;
import com.heron.constructmanager.models.User;
import com.heron.constructmanager.service.ConstructionService;
import com.heron.constructmanager.service.UserService;
import com.hootsuite.nachos.NachoTextView;

import java.util.ArrayList;
import java.util.List;

public class ConstructionExecReponsabilityFormActivity extends AppCompatActivity {

    Spinner spinner;
    ImageView backArrowImg, deleteImg, checkImg;
    EditText titleEditText, descEditText, scheduleEditText;
    Button addButton;

    List<User> usersList;
    List<String> emailsList;

    FirebaseAuth auth;
    FirebaseUser user;
    UserService userService;

    String userIdStr, constructionUidStr, titleStr, descStr, scheduleStr;

    ValidateInput validateInput;
    LoadingAnimation loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_exec_reponsability_form);

        userService = new UserService(this);
        spinner = findViewById(R.id.responsability_form_responsible_spinner);
        backArrowImg = findViewById(R.id.responsability_form_back_arrow);
        emailsList = new ArrayList<>();

        // Listeners
        DatabaseReference usersReference = userService.getUsersReference();
        usersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    String email = childSnapshot.child("email").getValue(String.class);
                    emailsList.add(email);
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ConstructionExecReponsabilityFormActivity.this, android.R.layout.simple_spinner_item, emailsList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
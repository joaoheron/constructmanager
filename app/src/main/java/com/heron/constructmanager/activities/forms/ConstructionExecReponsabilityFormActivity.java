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
    EditText titleEditText, descEditText, deadlineEditText;
    Button addButton;

    List<User> usersList;
    List<String> emailsList;

    FirebaseAuth auth;
    FirebaseUser user;
    UserService userService;

    String userIdStr, constructionUidStr, titleStr, descStr, responsibleEmailStr, deadlineStr, stateStr;

    ValidateInput validateInput;
    LoadingAnimation loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_exec_reponsability_form);
        deleteImg = findViewById(R.id.responsability_form_delete);
        checkImg = findViewById(R.id.responsability_form_solve_img);
        spinner = findViewById(R.id.responsability_form_responsible_spinner);
        backArrowImg = findViewById(R.id.responsability_form_back_arrow);
        titleEditText = findViewById(R.id.responsability_form_title);
        descEditText = findViewById(R.id.responsability_form_title);
        deadlineEditText = findViewById(R.id.responsability_form_deadline);
        addButton = findViewById(R.id.responsability_form_add_button);
        emailsList = new ArrayList<>();
        userService = new UserService(this);
//
//        intent.putExtra("constructionUid", responsability.getConstructionUid());
//        intent.putExtra("responsibleEmail", responsability.getResponsibleEmail());
//        intent.putExtra("title", responsability.getTitle());
//        intent.putExtra("desc", responsability.getDesc());
//        intent.putExtra("deadline", responsability.getDeadline());
//        intent.putExtra("state", responsability.getState());

        if(getIntent().getExtras() != null) {
            // SHOULD ALWAYS GET THESE EXTRAS

            // WILL GET THESE EXTRAS ONLY IF EDIT
            titleStr = getIntent().getStringExtra("title");
            responsibleEmailStr = getIntent().getStringExtra("email");
            descStr = getIntent().getStringExtra("desc");
            descStr = getIntent().getStringExtra("desc");
            constructionUidStr = getIntent().getStringExtra("constructionUid");

        }


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
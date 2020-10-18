package com.heron.constructmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.R;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    String email_str, id_str;

    Button update_email_button, update_password_button, logout_button, obras_button;
    TextView email_text, id_text, verified_account_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        update_email_button = findViewById(R.id.update_email_button_home);
        update_password_button = findViewById(R.id.update_password_button_home);
        logout_button = findViewById(R.id.logout_button_home);
        email_text = findViewById(R.id.email_address_text_home);
        id_text = findViewById(R.id.id_text_home);
        verified_account_txt = findViewById(R.id.verified_account_home);
        obras_button = findViewById(R.id.obras_button_home);

        fillUserInfo();

        // Db operations
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
////        db.setPersistenceEnabled(true);
//        // Write a message to the database
//        DatabaseReference usersRef = db.getReference("users");
//        DatabaseReference myRef = db.getReference("message");
//        myRef.setValue("Donald Duck!");
////
//
//        // Read from the database
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                System.out.println("Value is: " + value);
//            }
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                System.out.println("Cancelled!" + error.toException());
//            }
//        });

        obras_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListaObrasActivity.class);
                startActivity(intent);
            }
        });

        update_email_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UpdateEmailActivity.class);
                startActivity(intent);
            }
        });


        update_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UpdatePasswordActivity.class);
                startActivity(intent);
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        fillUserInfo();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        builder.setTitle("Logout");
        builder.setMessage("Tem certeza que deseja sair?");
        AlertDialog dialog = builder.create();
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                finish();
            }
        });
        dialog.setButton(Dialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void checkAcccountVerification() {
        boolean verified = user.isEmailVerified();
        if (verified) {
            verified_account_txt.setText("Conta verificada");
            verified_account_txt.setTextColor(getResources().getColor(R.color.green));
        }
    }

    public void fillUserInfo() {
        if (user != null) {
            email_str = user.getEmail();
            id_str = user.getUid();
            // Set email and id
            email_text.setText(email_str);
            id_text.setText(id_str);
            // Set account verification
            checkAcccountVerification();
        }
    }

    public void saveNameInDb(FirebaseUser user) {
//        FirebaseDatabase db;
//        DatabaseReference root_ref;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        root_ref = db.getReference();

        // Write a message to the database
        DatabaseReference myRef = db.getReference("message");
        myRef.setValue("Hello, World!");

//        DatabaseReference email_reference = root_ref.child("users").child(user.getUid()).child("email");
//        email_reference.setValue(email_str);

//        String new_id = "1111";
//        Obra obra = new Obra(new_id, email_str, email_str, "lograudors");
//        ref.child(new_id).setValue(obra);

    }
}



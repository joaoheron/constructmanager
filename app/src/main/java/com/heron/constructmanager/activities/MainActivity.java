package com.heron.constructmanager.activities;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.LoadingAnimation;
import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;
import com.heron.constructmanager.models.User;

public class MainActivity extends Activity {

    private FirebaseAuth auth;
    private DatabaseReference db;
    private FirebaseUser user;

    String email_str, pw_str;

    ValidateInput validate_input;
    LoadingAnimation loading;

    EditText sign_in_email, sign_in_password;
    TextView create_account;
    Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_account = findViewById(R.id.create_account_txt);
        sign_in = findViewById(R.id.sign_in_button);
        sign_in_email = findViewById(R.id.sign_in_email);
        sign_in_password = findViewById(R.id.sign_in_password);
        validate_input = new ValidateInput(MainActivity.this, sign_in_email, sign_in_password);

        // Init firebase Auth
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        // Loading animation
        loading = new LoadingAnimation(this);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInAcc();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = auth.getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            Toast.makeText(this, user.getEmail(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Por favor realize o login para o usar o app.", Toast.LENGTH_LONG).show();
        }

    }

    public void signInAcc() {
        loading.loadingAnimationDialog();

        boolean email_verified = validate_input.validateEmail();
        boolean pw_verified = validate_input.validatePassword();

        if (email_verified && pw_verified) {

            email_str = sign_in_email.getText().toString().trim();
            pw_str = sign_in_password.getText().toString().trim();

            auth.signInWithEmailAndPassword(email_str, pw_str)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                onAuthSuccess(task.getResult().getUser());
                                loading.dismissLoading();
                            } else {
                                Toast.makeText(MainActivity.this, "Erro inesperado. Tente novamente.", Toast.LENGTH_SHORT).show();
                                loading.dismissLoading();
                            }
                        }
                    });
        } else {
            loading.dismissLoading();
        }

    }

    private void onAuthSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private String usernameFromEmail(String email) {
        if (email.contains("@")) {
            return email.split("@")[0];
        } else {
            return email;
        }
    }

    // [START basic_write]
    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);

        db.child("users").child(userId).setValue(user);
    }
    // [END basic_write]


}
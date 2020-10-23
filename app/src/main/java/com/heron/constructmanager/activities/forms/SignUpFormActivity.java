package com.heron.constructmanager.activities.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.activities.MainActivity;
import com.heron.constructmanager.animations.LoadingAnimation;
import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;
import com.heron.constructmanager.activities.HomeActivity;
import com.heron.constructmanager.models.User;

public class SignUpFormActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private DatabaseReference db;
    private FirebaseUser user;

    String emailStr, pwStr;

    EditText signUpEmailEditText, signUpPwEditText, signUpRepeatPwEditText;
    ImageView backArrowImg;
    Button signUpButton;

    ValidateInput valiteInput;
    LoadingAnimation loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Components
        signUpEmailEditText = findViewById(R.id.sign_up_email);
        signUpPwEditText = findViewById(R.id.sign_up_password);
        signUpRepeatPwEditText = findViewById(R.id.sign_up_repeat_password);
        backArrowImg = findViewById(R.id.sign_up_back_arrow);
        signUpButton = findViewById(R.id.sign_up_button);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference();

        // Animation
        loading = new LoadingAnimation(this);

        // Listeners
        valiteInput = new ValidateInput(SignUpFormActivity.this, signUpEmailEditText, signUpPwEditText, signUpRepeatPwEditText);

        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpNewAcc();
            }
        });

    }

    public void signUpNewAcc() {
        loading.loadingAnimationDialog();

        boolean emailVerified = valiteInput.validateEmail();
        boolean pwVerified = valiteInput.validatePassword();
        boolean repeatPwVerified = valiteInput.validateRepeatPassword();

        if (emailVerified && pwVerified && repeatPwVerified) {

            emailStr = signUpEmailEditText.getText().toString().trim();
            pwStr = signUpPwEditText.getText().toString().trim();

            auth.createUserWithEmailAndPassword(emailStr, pwStr)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = auth.getCurrentUser();
                                onCompleteSuccess(user);
                                loading.dismissLoading();
                            } else {
                                Toast.makeText(SignUpFormActivity.this, "Erro inesperado. Tente novamente.", Toast.LENGTH_SHORT).show();
                                loading.dismissLoading();
                            }
                        }
                    });
        } else {
            loading.dismissLoading();
        }
    }

    private void onCompleteSuccess(FirebaseUser user) {
        String username = usernameFromEmail(user.getEmail());

        // Write new user
        writeNewUser(user.getUid(), username, user.getEmail());

        Intent intent = new Intent(SignUpFormActivity.this, HomeActivity.class);
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
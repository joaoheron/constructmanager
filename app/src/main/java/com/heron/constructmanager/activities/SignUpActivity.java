package com.heron.constructmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.heron.constructmanager.LoadingAnimation;
import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;

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

        // Animation
        loading = new LoadingAnimation(this);

        // Listeners
        valiteInput = new ValidateInput(SignUpActivity.this, signUpEmailEditText, signUpPwEditText, signUpRepeatPwEditText);

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
                                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                startActivity(intent);
                                loading.dismissLoading();
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, "Erro inesperado. Tente novamente.", Toast.LENGTH_SHORT).show();
                                loading.dismissLoading();
                            }
                        }
                    });
        } else {
            loading.dismissLoading();
        }
    }

}
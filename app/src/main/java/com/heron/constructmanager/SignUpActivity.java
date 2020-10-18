package com.heron.constructmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.heron.constructmanager.models.User;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    String email_str, pw_str;

    EditText sign_up_email, sign_up_password, sign_up_repeat_password;
    ImageView back_arrow;
    Button sign_up;

    ValidateInput validate_input;
    LoadingAnimation loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Components
        sign_up_email = findViewById(R.id.sign_up_email);
        sign_up_password = findViewById(R.id.sign_up_password);
        sign_up_repeat_password = findViewById(R.id.sign_up_repeat_password);
        back_arrow = findViewById(R.id.sign_up_back_arrow);
        sign_up = findViewById(R.id.sign_up_button);

        // Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Animation
        loading = new LoadingAnimation(this);

        // Listeners
        validate_input = new ValidateInput(SignUpActivity.this, sign_up_email, sign_up_password, sign_up_repeat_password);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpNewAcc();
            }
        });

    }

    public void signUpNewAcc() {
        loading.loadingAnimationDialog();

        boolean email_verified = validate_input.validateEmail();
        boolean pw_verified = validate_input.validatePassword();
        boolean repeat_pw_verified = validate_input.validateRepeatPassword();

        if (email_verified && pw_verified && repeat_pw_verified) {


            email_str = sign_up_email.getText().toString().trim();
            pw_str = sign_up_password.getText().toString().trim();

            auth.createUserWithEmailAndPassword(email_str, pw_str)
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
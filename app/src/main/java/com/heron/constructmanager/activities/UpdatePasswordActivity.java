package com.heron.constructmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.heron.constructmanager.R;

public class UpdatePasswordActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    String new_password_str, repeat_password_str;

    ImageView back_arrow;
    Button update_password_button;
    EditText new_password, repeat_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        back_arrow = findViewById(R.id.update_password_back_arrow);
        update_password_button = findViewById(R.id.update_password_button);
        new_password = findViewById(R.id.update_password_new_password);
        repeat_password = findViewById(R.id.update_password_repeat_password);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update_password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_password_str = new_password.getText().toString().trim();
                repeat_password_str = repeat_password.getText().toString().trim();
                if (user != null) {
                    if (new_password_str.equals(repeat_password_str)) {
                        user.updatePassword(new_password_str);
                        Toast.makeText(UpdatePasswordActivity.this, "Senha atualizada com sucesso.", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(UpdatePasswordActivity.this, "Senhas inseridas não são iguais.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
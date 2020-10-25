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
import com.heron.constructmanager.R;
import com.heron.constructmanager.activities.forms.UpdateEmailFormActivity;
import com.heron.constructmanager.activities.forms.UpdatePasswordFormActivity;
import com.heron.constructmanager.activities.lists.ListConstructionsActivity;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;

    String emailStr, idStr;

    Button updateEmaiilButton, updatePasswordButton, logoutButton, constructionsButton;
    TextView emailText, idText, verifiedAccountText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        updateEmaiilButton = findViewById(R.id.update_email_button_home);
        updatePasswordButton = findViewById(R.id.update_password_button_home);
        logoutButton = findViewById(R.id.logout_button_home);
        emailText = findViewById(R.id.email_address_text_home);
        idText = findViewById(R.id.id_text_home);
        verifiedAccountText = findViewById(R.id.verified_account_home);
        constructionsButton = findViewById(R.id.constructions_button_home);

        fillUserInfo();

        // Db operations
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        constructionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ListConstructionsActivity.class);
                startActivity(intent);
            }
        });

        updateEmaiilButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UpdateEmailFormActivity.class);
                startActivity(intent);
            }
        });


        updatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, UpdatePasswordFormActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
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
            verifiedAccountText.setText("Conta verificada");
            verifiedAccountText.setTextColor(getResources().getColor(R.color.green));
        }
    }

    public void fillUserInfo() {
        if (user != null) {
            emailStr = user.getEmail();
            idStr = user.getUid();
            // Set email and id
            emailText.setText(emailStr);
            idText.setText(idStr);
            // Set account verification
            checkAcccountVerification();
        }
    }

}



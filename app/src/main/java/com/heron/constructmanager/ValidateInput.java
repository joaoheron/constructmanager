package com.heron.constructmanager;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class ValidateInput {

    private Context context;
    private EditText email, password, repeat_password;

    String email_str, pw_str, repeat_pw_str;

    ValidateInput(Context c, EditText e) {
        context = c;
        email = e;
    }

    ValidateInput(Context c, EditText e, EditText p) {
        context = c;
        email = e;
        password = p;
    }

    ValidateInput(Context c, EditText e, EditText p, EditText rp){
        context = c;
        email = e;
        password = p;
        repeat_password = rp;
    }

    boolean validateEmail() {
        email_str = email.getText().toString().trim();
//        Log.wtf("WTF", "email input: " + email_str);
        if (email_str.isEmpty()) {
            Toast.makeText(context, "Preencha seu e-mail.", Toast.LENGTH_LONG).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email_str).matches()) {
            Toast.makeText(context, "E-mail inválido.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    boolean validatePassword() {
        pw_str = password.getText().toString();

        if (pw_str.isEmpty()) {
            Toast.makeText(context, "Preencha sua senha", Toast.LENGTH_LONG).show();
            return false;
        } else if (pw_str.length() < 8) {
            Toast.makeText(context, "Preencha uma senha com mais de 8 caracteres.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    boolean validateRepeatPassword() {
        repeat_pw_str = repeat_password.getText().toString().trim();
        if (repeat_pw_str.isEmpty()) {
            Toast.makeText(context, "Preencha sua senha novamente", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!repeat_pw_str.equals(pw_str)) {
            Toast.makeText(context, "As senhas prenchidas são diferentes", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}


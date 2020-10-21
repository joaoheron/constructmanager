package com.heron.constructmanager;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class ValidateInput {

    private Context context;
    private EditText email, password, repeat_password, title, address, type, responsibles;
    private String email_str, pw_str, repeat_pw_str, title_str, address_str, type_str, responsibles_str;

    public ValidateInput(Context c, EditText e) {
        context = c;
        email = e;
    }

    public ValidateInput(Context c, EditText e, EditText p) {
        context = c;
        email = e;
        password = p;
    }

    public ValidateInput(Context c, EditText e, EditText p, EditText rp){
        context = c;
        email = e;
        password = p;
        repeat_password = rp;
    }

    public ValidateInput(Context c, EditText t, EditText a, EditText ty, EditText r){
        context = c;
        title = t;
        address = a;
        type = ty;
        responsibles = r;
    }

    public boolean validateTitle() {
        title_str = title.getText().toString().trim();
        if (title_str.isEmpty()) {
            Toast.makeText(context, "Preencha o título.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateAddress() {
        address_str = address.getText().toString().trim();
        if (address_str.isEmpty()) {
            Toast.makeText(context, "Preencha o endereço.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
    public boolean validateType() {
        type_str = title.getText().toString().trim();
        if (type_str.isEmpty()) {
            Toast.makeText(context, "Preencha o tipo de obra.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
    public boolean validateResponsibles() {
        responsibles_str = responsibles.getText().toString().trim();
        if (responsibles_str.isEmpty()) {
            Toast.makeText(context, "Selecione algum responsável pela obra.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateEmail() {
        email_str = email.getText().toString().trim();
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

    public boolean validatePassword() {
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

    public boolean validateRepeatPassword() {
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


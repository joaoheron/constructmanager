package com.heron.constructmanager;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

public class ValidateInput {

    private Context context;
    private EditText email, password, repeat_password, title, address, type, responsibles;
    private String emailStr, pwStr, repeatPwStr, titleStr, addressStr, typeStr, responsiblesStr;

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
        titleStr = title.getText().toString().trim();
        if (titleStr.isEmpty()) {
            Toast.makeText(context, "Preencha o título.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateAddress() {
        addressStr = address.getText().toString().trim();
        if (addressStr.isEmpty()) {
            Toast.makeText(context, "Preencha o endereço.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
    public boolean validateType() {
        typeStr = title.getText().toString().trim();
        if (typeStr.isEmpty()) {
            Toast.makeText(context, "Preencha o tipo de obra.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
    public boolean validateResponsibles() {
        responsiblesStr = responsibles.getText().toString().trim();
        if (responsiblesStr.isEmpty()) {
            Toast.makeText(context, "Selecione algum responsável pela obra.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateEmail() {
        emailStr = email.getText().toString().trim();
        if (emailStr.isEmpty()) {
            Toast.makeText(context, "Preencha seu e-mail.", Toast.LENGTH_LONG).show();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailStr).matches()) {
            Toast.makeText(context, "E-mail inválido.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validatePassword() {
        pwStr = password.getText().toString();

        if (pwStr.isEmpty()) {
            Toast.makeText(context, "Preencha sua senha", Toast.LENGTH_LONG).show();
            return false;
        } else if (pwStr.length() < 8) {
            Toast.makeText(context, "Preencha uma senha com mais de 8 caracteres.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateRepeatPassword() {
        repeatPwStr = repeat_password.getText().toString().trim();
        if (repeatPwStr.isEmpty()) {
            Toast.makeText(context, "Preencha sua senha novamente", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!repeatPwStr.equals(pwStr)) {
            Toast.makeText(context, "As senhas prenchidas são diferentes", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

}


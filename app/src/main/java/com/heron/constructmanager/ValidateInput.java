package com.heron.constructmanager;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.heron.constructmanager.activities.forms.ScheduleFormActivity;
import com.hootsuite.nachos.NachoTextView;

import java.util.ArrayList;
import java.util.List;

public class ValidateInput {

    Context context;
    EditText emailEditText, passwordEditText, repeatPasswordEditText, titleEditText, addressEditText, typeEditText, responsiblesEditText, descEditText, deadlineEditText;
    NachoTextView nachoTextView ;
    Spinner spinner;
    String emailStr, pwStr, repeatPwStr, titleStr, addressStr, typeStr, responsiblesStr, spinnerStr, descStr, stateStr, deadlinelineStr;

    public ValidateInput(Context c, EditText e) {
        this.context = c;
        this.emailEditText = e;
    }

    public ValidateInput(Context c, EditText e, EditText p) {
        this.context = c;
        this.emailEditText = e;
        this.passwordEditText = p;
    }

    public ValidateInput(Context c, EditText e, EditText p, EditText rp){
        this.context = c;
        this.emailEditText = e;
        this.passwordEditText = p;
        this.repeatPasswordEditText = rp;
    }

    public ValidateInput(ScheduleFormActivity c, EditText title, EditText deadline){
        this.context = c;
        this.titleEditText = title;
        this.deadlineEditText = deadline;
    }

    public ValidateInput(Context c, EditText t, EditText a, EditText ty, EditText r){
        context = c;
        titleEditText = t;
        addressEditText = a;
        typeEditText = ty;
    }

    public ValidateInput(Context c, EditText t, EditText d, EditText dl, Spinner spinner){
        this.context = c;
        this.titleEditText = t;
        this.descEditText = d;
        this.deadlineEditText = dl;
        this.spinner = spinner;
    }

    public ValidateInput(Context c, EditText titleEditText, EditText addressEditText, EditText typeEditText, NachoTextView nachoTextView) {
        this.context = c;
        this.titleEditText = titleEditText;
        this.addressEditText = addressEditText;
        this.typeEditText = typeEditText;
        this.nachoTextView = nachoTextView;
    }

    public boolean validateNachoTextView(){
        List<String> values = new ArrayList();
        values = nachoTextView.getChipValues();
        if (values == null || values.size() == 0) {
            Toast.makeText(context, "Preencha o TextView.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateSpinner(){
        Object obj = spinner.getSelectedItem();
        if (obj == null) {
            Toast.makeText(context, "Preencha o dropdown.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateDeadline() {
        deadlinelineStr = deadlineEditText.getText().toString().trim();
        if (deadlinelineStr.isEmpty()) {
            Toast.makeText(context, "Preencha o Prazo.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateDesc() {
        descStr = descEditText.getText().toString().trim();
        if (descStr.isEmpty()) {
            Toast.makeText(context, "Preencha a descrição.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }


    public boolean validateTitle() {
        titleStr = titleEditText.getText().toString().trim();
        if (titleStr.isEmpty()) {
            Toast.makeText(context, "Preencha o Título.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateAddress() {
        addressStr = addressEditText.getText().toString().trim();
        if (addressStr.isEmpty()) {
            Toast.makeText(context, "Preencha o endereço.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
    public boolean validateType() {
        typeStr = titleEditText.getText().toString().trim();
        if (typeStr.isEmpty()) {
            Toast.makeText(context, "Preencha o tipo de construção.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }

    }
    public boolean validateResponsibles() {
        responsiblesStr = responsiblesEditText.getText().toString().trim();
        if (responsiblesStr.isEmpty()) {
            Toast.makeText(context, "Selecione algum responsável pela construction.", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean validateEmail() {
        emailStr = emailEditText.getText().toString().trim();
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
        pwStr = passwordEditText.getText().toString();

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
        repeatPwStr = repeatPasswordEditText.getText().toString().trim();
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


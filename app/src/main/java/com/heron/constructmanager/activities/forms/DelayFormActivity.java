package com.heron.constructmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;
import com.heron.constructmanager.service.ResponsabilityService;
import com.heron.constructmanager.service.UserService;

import java.util.List;

public class DelayFormActivity extends AppCompatActivity {

    Spinner spinner;
    ImageView backArrowImg, deleteImg, solveImg;
    EditText titleEditText, descEditText, deadlineEditText;
    Button addButton;

    List<String> emailsList;

    UserService userService;
    ResponsabilityService responsabilityService;

    String constructionUidStr, titleStr, descStr, responsibleEmailStr, deadlineStr, stateStr, responsabilityUidStr;

    ValidateInput validateInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delay_form);
    }
}
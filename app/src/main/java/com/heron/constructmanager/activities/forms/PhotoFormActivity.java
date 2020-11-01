package com.heron.constructmanager.activities.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;
import com.heron.constructmanager.service.PhotoService;

public class PhotoFormActivity extends AppCompatActivity {

    ImageView backArrowImg, uploadImg;
    EditText titleEditText, descEditText;
    Button addButton;

    PhotoService photoService;
    ValidateInput validateInput;

    String constructionUidStr, titleStr, descStr, photoUidStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_form);
        backArrowImg = findViewById(R.id.photo_form_back_arrow);
        titleEditText = findViewById(R.id.photo_form_title);
        descEditText = findViewById(R.id.photo_form_desc);
        addButton = findViewById(R.id.photo_form_add_button);
        uploadImg = findViewById(R.id.photo_form_upload_button);
        photoService = new PhotoService(this);

        if(getIntent().getExtras() != null) {
            constructionUidStr = getIntent().getStringExtra("constructionUid");
            photoUidStr = getIntent().getStringExtra("photoUid");
            descStr = getIntent().getStringExtra("desc");
            titleStr = getIntent().getStringExtra("title");
        }
        titleEditText.setText(titleStr);
        descEditText.setText(descStr);

        validateInput = new ValidateInput(PhotoFormActivity.this, titleEditText, descEditText);

        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infosVerified()) {
                    getEditTextsContent();
                    photoService.writePhoto(constructionUidStr, titleStr, descStr, photoUidStr);
                    finish();
                }
            }
        });

    }

    public boolean infosVerified() {
        boolean titleVerified = validateInput.validateTitle();
        boolean deadlineVerified = validateInput.validateDeadline();
        return titleVerified && deadlineVerified;
    }

    public void getEditTextsContent() {
        titleStr = titleEditText.getText().toString().trim();
        descStr = descEditText.getText().toString().trim();
    }
}
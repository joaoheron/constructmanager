package com.heron.constructmanager.activities.views;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.heron.constructmanager.R;
import com.heron.constructmanager.activities.HomeActivity;
import com.heron.constructmanager.activities.forms.ConstructionPrepFormActivity;
import com.heron.constructmanager.models.User;
import com.heron.constructmanager.service.ConstructionService;
import com.heron.constructmanager.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class ConstructionPrepViewActivity extends AppCompatActivity {

    Context context;
    ImageView backArrowImg, editImg;
    TextView titleTextView, stageTextView, addressTextView, responsiblesTextView, typeTextView;
    String titleStr, stageStr, addressStr, responsiblesStr, typeStr, constructionUidStr;

    FirebaseAuth auth;
    FirebaseDatabase db;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_prep_view);
        context = this;

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();

        titleTextView = findViewById(R.id.construction_prep_view_title_text);
        stageTextView = findViewById(R.id.construction_prep_view_stage_text);
        addressTextView = findViewById(R.id.construction_prep_view_address_text);
        typeTextView = findViewById(R.id.construction_prep_view_type_text);
        responsiblesTextView = findViewById(R.id.construction_prep_view_responsibles_text);
        backArrowImg = findViewById(R.id.construction_prep_view_back_arrow);
        editImg = findViewById(R.id.construction_prep_view_edit);

        if (getIntent().getExtras() != null) {
            titleStr = getIntent().getStringExtra("title");
            stageStr = getIntent().getStringExtra("stage");
            addressStr = getIntent().getStringExtra("address");
            typeStr = getIntent().getStringExtra("type");
            responsiblesStr = getIntent().getStringExtra("responsibles");
            constructionUidStr = getIntent().getStringExtra("constructionUid");
        }

        titleTextView.setText(titleStr);
        stageTextView.setText(stageStr);
        addressTextView.setText(addressStr);
        typeTextView.setText(typeStr);
        responsiblesTextView.setText(responsiblesStr);

        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ConstructionPrepFormActivity.class);
                intent.putExtra("title", titleStr);
                intent.putExtra("address", addressStr);
                intent.putExtra("stage", stageStr);
                intent.putExtra("type", typeStr);
                intent.putExtra("responsibles", responsiblesStr);
                intent.putExtra("constructionUid", constructionUidStr);
                context.startActivity(intent);
            }
        });
    }


}
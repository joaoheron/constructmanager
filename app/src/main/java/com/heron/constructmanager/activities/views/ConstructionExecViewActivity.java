package com.heron.constructmanager.activities.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heron.constructmanager.R;
import com.heron.constructmanager.activities.lists.ListBudgetsActivity;
import com.heron.constructmanager.activities.lists.ListPhotosActivity;
import com.heron.constructmanager.activities.lists.ListResponsabilitiesActivity;
import com.heron.constructmanager.activities.lists.ListSchedulesActivity;

import java.util.ArrayList;

public class ConstructionExecViewActivity extends AppCompatActivity {

    Context context;
    ImageView backArrowImg;
    CardView infoCard, budgetCard, scheduleCard, photoCard, mapCard, responsabilitiesCard;
    TextView titleTextView, stageTextView, infoTextView, budgetTextView, scheduleTextView, photoTextView, mapTextView, responsiblesTextView;
    String titleStr, stageStr, addressStr, typeStr, constructionUidStr;
    ArrayList<String> responsiblesEmailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_exec_view);
        context = this;
        responsiblesEmailList = new ArrayList<>();

        backArrowImg = findViewById(R.id.construction_exec_view_back_arrow);
        titleTextView = findViewById(R.id.construction_exec_view_title_text);
        stageTextView = findViewById(R.id.construction_exec_view_stage_text);
        infoCard  = findViewById(R.id.construction_exec_view_info_card);
        budgetCard  = findViewById(R.id.construction_exec_budget_card);
        scheduleCard  = findViewById(R.id.construction_exec_schedule_card);
        photoCard  = findViewById(R.id.construction_exec_photo_card);
        mapCard  = findViewById(R.id.construction_exec_map_card);
        responsabilitiesCard  = findViewById(R.id.construction_exec_responsabilities_card);

        if(getIntent().getExtras() != null) {
            titleStr = getIntent().getStringExtra("title");
            stageStr = getIntent().getStringExtra("stage");
            addressStr = getIntent().getStringExtra("address");
            typeStr = getIntent().getStringExtra("type");
            responsiblesEmailList = getIntent().getStringArrayListExtra("responsibles");
            constructionUidStr = getIntent().getStringExtra("constructionUid");

            titleTextView.setText(titleStr);
            stageTextView.setText(stageStr);
        }

        infoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConstructionExecViewActivity.this, InfoViewActivity.class);
                intent = putExtrasConstruction(intent, titleStr, addressStr, stageStr, typeStr, constructionUidStr);
                startActivity(intent);
            }
        });

        budgetCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConstructionExecViewActivity.this, ListBudgetsActivity.class);
                intent = putExtrasConstruction(intent, titleStr, addressStr, stageStr, typeStr, constructionUidStr);
                startActivity(intent);
            }
        });

        scheduleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConstructionExecViewActivity.this, ListSchedulesActivity.class);
                intent = putExtrasConstruction(intent, titleStr, addressStr, stageStr, typeStr, constructionUidStr);
                startActivity(intent);
            }
        });

        photoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConstructionExecViewActivity.this, ListPhotosActivity.class);
                intent = putExtrasConstruction(intent, titleStr, addressStr, stageStr, typeStr, constructionUidStr);
                startActivity(intent);
            }
        });

        mapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConstructionExecViewActivity.this, MapViewActivity.class);
                intent = putExtrasConstruction(intent, titleStr, addressStr, stageStr, typeStr, constructionUidStr);
                startActivity(intent);
            }
        });

        responsabilitiesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConstructionExecViewActivity.this, ListResponsabilitiesActivity.class);
                intent = putExtrasConstruction(intent, titleStr, addressStr, stageStr, typeStr, constructionUidStr);
                startActivity(intent);
            }
        });


        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private Intent putExtrasConstruction(Intent intent, String title, String address, String stage, String type, String constructionUid) {
        intent.putExtra("title", title);
        intent.putExtra("address", address);
        intent.putExtra("stage", stage);
        intent.putExtra("type", type);
        intent.putExtra("constructionUid", constructionUid);
        intent.putStringArrayListExtra("responsibles", responsiblesEmailList);
        return intent;
    }
}
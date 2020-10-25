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
import com.heron.constructmanager.activities.HomeActivity;
import com.heron.constructmanager.activities.forms.UpdateEmailFormActivity;
import com.heron.constructmanager.models.Construction;

public class ConstructionExecViewActivity extends AppCompatActivity {

    Context context;
    ImageView backArrowImg;
    CardView infoCard;
    TextView titleTextView, stageTextView, infoTextView, budgetTextView, scheduleTextView, photoTextView, mapTextView, responsiblesTextView;
    String titleStr, stageStr, addressStr, typeStr, responsiblesStr, constructionUidStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_exec_view);
        context = this;

        backArrowImg = findViewById(R.id.construction_exec_view_back_arrow);
        infoCard  = findViewById(R.id.construction_exec_view_info_card);
        titleTextView = findViewById(R.id.construction_exec_view_title_text);
        stageTextView = findViewById(R.id.construction_exec_view_stage_text);
        infoTextView = findViewById(R.id.construction_exec_view_info_text);
        budgetTextView = findViewById(R.id.construction_exec_view_budget_text);
        scheduleTextView = findViewById(R.id.construction_exec_view_schedule_text);
        photoTextView = findViewById(R.id.construction_exec_view_photo_text);
        mapTextView = findViewById(R.id.construction_exec_view_map_text);
        responsiblesTextView = findViewById(R.id.construction_exec_view_responsbiles_text);

        if(getIntent().getExtras() != null) {
            titleStr = getIntent().getStringExtra("title");
            stageStr = getIntent().getStringExtra("stage");
            addressStr = getIntent().getStringExtra("address");
            typeStr = getIntent().getStringExtra("type");
            responsiblesStr = getIntent().getStringExtra("responsibles");
            constructionUidStr = getIntent().getStringExtra("constructionUid");

            titleTextView.setText(titleStr);
            stageTextView.setText(stageStr);
        }

        infoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConstructionExecViewActivity.this, ConstructionExecInfoViewActivity.class);
                intent = putExtrasConstruction(intent, titleStr, addressStr, stageStr, typeStr, responsiblesStr, constructionUidStr);
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

    private Intent putExtrasConstruction(Intent intent, String title, String address, String stage, String type, String responsibles, String constructionUid) {
        intent.putExtra("title", title);
        intent.putExtra("address", address);
        intent.putExtra("stage", stage);
        intent.putExtra("type", type);
        intent.putExtra("responsibles", responsibles);
        intent.putExtra("constructionUid", constructionUid);
        return intent;
    }
}
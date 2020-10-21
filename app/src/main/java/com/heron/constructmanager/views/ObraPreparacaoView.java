package com.heron.constructmanager.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.heron.constructmanager.R;

public class ObraPreparacaoView extends AppCompatActivity {

    ImageView backArrowImg;
    TextView titleTextView, stageTextView, addressTextView, responsiblesTextView, typeTextView;
    String titleStr, stageStr, addressStr, responsiblesStr, typeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_preparacao_view);

        titleTextView = findViewById(R.id.obra_prep_view_title_text);
        stageTextView = findViewById(R.id.obra_prep_view_stage_text);
        addressTextView = findViewById(R.id.obra_prep_view_address_text);
        typeTextView = findViewById(R.id.obra_prep_view_type_text);
        responsiblesTextView = findViewById(R.id.obra_prep_view_responsibles_text);
        backArrowImg = findViewById(R.id.obra_prep_view_back_arrow);

        if(getIntent().getExtras() != null) {
            titleStr = getIntent().getStringExtra("title");
            stageStr = getIntent().getStringExtra("stage");
            addressStr = getIntent().getStringExtra("address");
            typeStr = getIntent().getStringExtra("type");
            responsiblesStr = getIntent().getStringExtra("responsibles");
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
    }
}
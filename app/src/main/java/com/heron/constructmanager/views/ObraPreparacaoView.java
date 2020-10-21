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

    ImageView back_arrow_button;

    TextView title_text, stage_text, address_text, responsibles_text, type_text;
    String title_str, stage_str, address_str, responsibles_str, type_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obra_preparacao_view);

        title_text = findViewById(R.id.obra_prep_view_title_text);
        stage_text = findViewById(R.id.obra_prep_view_stage_text);
        address_text = findViewById(R.id.obra_prep_view_address_text);
        type_text = findViewById(R.id.obra_prep_view_type_text);
        responsibles_text = findViewById(R.id.obra_prep_view_responsibles_text);
        back_arrow_button = findViewById(R.id.obra_prep_view_back_arrow);

        if(getIntent().getExtras() != null) {
            title_str = getIntent().getStringExtra("title");
            stage_str = getIntent().getStringExtra("stage");
            address_str = getIntent().getStringExtra("address");
            type_str = getIntent().getStringExtra("type");
            responsibles_str = getIntent().getStringExtra("responsibles");
        }

        title_text.setText(title_str);
        stage_text.setText(stage_str);
        address_text.setText(address_str);
        type_text.setText(type_str);
        responsibles_text.setText(responsibles_str);

        back_arrow_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
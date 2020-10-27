package com.heron.constructmanager.activities.views;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.heron.constructmanager.R;
import com.heron.constructmanager.activities.forms.ConstructionExecReponsabilityFormActivity;

public class ResponsabilityOpenViewActivity extends AppCompatActivity {

    Context context;
    ImageView backArrowImg, editImg;
    TextView titleTextView, stateTextView, descTextView, deadlineTextView, responsibleEmailTextView;
    String constructionUidStr, responsabilityUidStr, titleStr, stateStr, descStr, deadlineStr, responsibleEmailStr;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsability_open_view);

        context = this;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        titleTextView = findViewById(R.id.responsability_open_view_title_text);
        stateTextView = findViewById(R.id.responsability_open_view_state_text);
        descTextView = findViewById(R.id.responsability_open_view_desc_text);
        deadlineTextView = findViewById(R.id.responsability_open_view_deadline_text);
        responsibleEmailTextView = findViewById(R.id.responsability_open_view_responsible_email_text);
        backArrowImg = findViewById(R.id.responsability_open_view_back_arrow);
        editImg = findViewById(R.id.responsability_open_view_edit);

        if (getIntent().getExtras() != null) {
            constructionUidStr = getIntent().getStringExtra("constructionUid");
            responsabilityUidStr = getIntent().getStringExtra("responsabilityUid");
            titleStr = getIntent().getStringExtra("title");
            stateStr = getIntent().getStringExtra("state");
            descStr = getIntent().getStringExtra("desc");
            deadlineStr = getIntent().getStringExtra("deadline");
            responsibleEmailStr = getIntent().getStringExtra("responsibleEmail");
        }

        titleTextView.setText(titleStr);
        stateTextView.setText(stateStr);
        descTextView.setText(descStr);
        deadlineTextView.setText(deadlineStr);
        responsibleEmailTextView.setText(responsibleEmailStr);

        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        editImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ConstructionExecReponsabilityFormActivity.class);
                intent.putExtra("constructionUid", constructionUidStr);
                intent.putExtra("responsabilityUid", responsabilityUidStr);
                intent.putExtra("title", titleStr);
                intent.putExtra("state", stateStr);
                intent.putExtra("desc", descStr);
                intent.putExtra("deadline", deadlineStr);
                intent.putExtra("responsibleEmail", responsibleEmailStr);
                context.startActivity(intent);
            }
        });
    }
}
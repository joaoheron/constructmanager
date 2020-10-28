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
import com.heron.constructmanager.activities.DelayFormActivity;
import com.heron.constructmanager.activities.forms.ReponsabilityFormActivity;
import com.heron.constructmanager.activities.forms.ScheduleFormActivity;
import com.heron.constructmanager.activities.lists.ListSchedulesActivity;

public class ScheduleViewAcitivity extends AppCompatActivity {

    Context context;
    ImageView backArrowImg, addDelayImg;
    TextView titleTextView, stateTextView, deadlineTextView;
    String constructionUidStr, scheduleUidStr, titleStr, stateStr, deadlineStr;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_view);

        context = this;
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        titleTextView = findViewById(R.id.schedule_view_title_text);
        stateTextView = findViewById(R.id.schedule_view_state_text);
        deadlineTextView = findViewById(R.id.schedule_view_deadline_text);

        backArrowImg = findViewById(R.id.schedule_view_back_arrow);
        addDelayImg = findViewById(R.id.list_schedule_add_delay_button);

        if (getIntent().getExtras() != null) {
            constructionUidStr = getIntent().getStringExtra("constructionUid");
            scheduleUidStr = getIntent().getStringExtra("responsabilityUid");
            titleStr = getIntent().getStringExtra("title");
            stateStr = getIntent().getStringExtra("state");
            deadlineStr = getIntent().getStringExtra("deadline");
        }

        titleTextView.setText(titleStr);
        stateTextView.setText(stateStr);
        deadlineTextView.setText(deadlineStr);

        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addDelayImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScheduleViewAcitivity.this, DelayFormActivity.class);
                intent.putExtra("constructionUid", constructionUidStr);
                intent.putExtra("scheduleUidStr", scheduleUidStr);
                startActivity(intent);
            }
        });


    }
}
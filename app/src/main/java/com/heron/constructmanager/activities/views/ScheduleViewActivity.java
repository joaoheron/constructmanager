package com.heron.constructmanager.activities.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleViewAcitivity extends AppCompatActivity {

    Context context;
    ImageView backArrowImg, addDelayImg;
    TextView titleTextView, stateTextView, deadlineTextView;
    CardView stateCard;
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
        stateCard = findViewById(R.id.schedule_view_state_card);

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

        if (delayExists(deadlineStr, null)) {
            stateCard.setBackgroundColor(ContextCompat.getColor(this, R.color.lightred));
            stateTextView.setText(ContextCompat.getString(this, R.string.late_schedule));
        }

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

    public boolean delayExists(String deadline, SimpleDateFormat dateFormatter) {
        Date dateDeadline;
        Date dateToday;
        if (dateFormatter == null) {
            dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        }
        try {
            dateDeadline = dateFormatter.parse(deadline);
            dateToday = new Date();

            if(dateToday.compareTo(dateDeadline) > 0) {
                System.out.println("O cronograma da obra está atrasado.");
                return true;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void

}
package com.heron.constructmanager.activities.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.heron.constructmanager.R;
import com.heron.constructmanager.activities.forms.DelayFormActivity;
import com.heron.constructmanager.activities.lists.ListSchedulesActivity;
import com.heron.constructmanager.adapters.DelayListAdapter;
import com.heron.constructmanager.adapters.ScheduleListAdapter;
import com.heron.constructmanager.models.Delay;
import com.heron.constructmanager.models.Schedule;
import com.heron.constructmanager.service.ScheduleService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ScheduleViewActivity extends AppCompatActivity {

    Context context;
    ImageView backArrowImg, addDelayImg;
    TextView titleTextView, stateTextView, deadlineTextView, delayLabelTextView;
    CardView stateCard;
    RecyclerView recyclerView;

    LinearLayoutManager linearLayoutManager;
    ScheduleService scheduleService;
    ArrayList<Delay> delays;
    DelayListAdapter adapter;

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

        linearLayoutManager = new LinearLayoutManager(this);
        scheduleService = new ScheduleService(this);

        titleTextView = findViewById(R.id.schedule_view_title_text);
        stateTextView = findViewById(R.id.schedule_view_state_text);
        deadlineTextView = findViewById(R.id.schedule_view_deadline_text);
        stateCard = findViewById(R.id.schedule_view_state_card);
        delayLabelTextView = findViewById(R.id.schedule_view_delay_label);

        backArrowImg = findViewById(R.id.schedule_view_back_arrow);
        addDelayImg = findViewById(R.id.schedule_view_add_delay_button);
        recyclerView = findViewById(R.id.schedule_view_delays_recycler_view);

        if (getIntent().getExtras() != null) {
            constructionUidStr = getIntent().getStringExtra("constructionUid");
            scheduleUidStr = getIntent().getStringExtra("scheduleUid");
            titleStr = getIntent().getStringExtra("title");
            stateStr = getIntent().getStringExtra("state");
            deadlineStr = getIntent().getStringExtra("deadline");
        }

        titleTextView.setText(titleStr);
        stateTextView.setText(stateStr);
        deadlineTextView.setText(deadlineStr);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        adaptDelaysToView(constructionUidStr, scheduleUidStr);

        if (delayExists(deadlineStr, null)) {
            showInfoDelayDialog();
            recyclerView.setVisibility(View.VISIBLE);
            addDelayImg.setVisibility(View.VISIBLE);
            delayLabelTextView.setVisibility(View.VISIBLE);
            stateCard.setBackgroundColor(ContextCompat.getColor(this, R.color.lightred));
            stateTextView.setText("Atrasado");
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
                Intent intent = new Intent(ScheduleViewActivity.this, DelayFormActivity.class);
                intent.putExtra("constructionUid", constructionUidStr);
                intent.putExtra("scheduleUid", scheduleUidStr);
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

    public void showInfoDelayDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ScheduleViewActivity.this);
        builder.setTitle("Atenção");
        builder.setMessage("O cronograma atual está atrasado.\nGerencie os motivos e atribua as resoluções clicando no botão de adicionar.");
        AlertDialog dialog = builder.create();
        dialog.setButton(Dialog.BUTTON_POSITIVE, "Entendi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void adaptDelaysToView(String constructionUid, String scheduleUid) {
        DatabaseReference delaysReference = scheduleService.getDelaysReference(constructionUid, scheduleUid);

        delaysReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                delays = new ArrayList<>();
                Delay delay;
                for(DataSnapshot delaySnap : snapshot.getChildren()) {
                    delay = delaySnap.getValue(Delay.class);
                    delay.setDelayUid(delaySnap.getKey());
                    delays.add(delay);
                }
                adapter = new DelayListAdapter(delays, context, constructionUid, scheduleUid);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ScheduleViewActivity.this, "Erro inesperado.", Toast.LENGTH_LONG).show();
            }
        });
    }



}
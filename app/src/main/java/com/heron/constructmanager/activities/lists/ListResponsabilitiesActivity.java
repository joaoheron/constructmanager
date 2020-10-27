package com.heron.constructmanager.activities.lists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.heron.constructmanager.R;
import com.heron.constructmanager.activities.forms.ConstructionExecReponsabilityFormActivity;
import com.heron.constructmanager.adapters.ConstructionListAdapter;
import com.heron.constructmanager.adapters.ResponsabilityListAdapter;
import com.heron.constructmanager.models.Responsability;
import com.heron.constructmanager.service.ResponsabilityService;

import java.util.ArrayList;

public class ListResponsabilitiesActivity extends AppCompatActivity {

    Context context;

    ArrayList<Responsability> responsabilities;
    ArrayList<String> responsiblesEmailList;
    ResponsabilityListAdapter adapter;

    RecyclerView recyclerView;
    Button addConstructionButton;
    ImageView backArrowButton;

    FirebaseUser user;
    FirebaseAuth auth;
    ResponsabilityService responsabilityService;

    String userUidStr, constructionUidStr, titleStr, stageStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_responsabilities);
        responsabilities = new ArrayList<>();
        responsiblesEmailList = new ArrayList<>();
        context = this;

        if(getIntent().getExtras() != null) {
            titleStr = getIntent().getStringExtra("title");
            stageStr = getIntent().getStringExtra("stage");
            responsiblesEmailList = getIntent().getStringArrayListExtra("responsibles");
            constructionUidStr = getIntent().getStringExtra("constructionUid");
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userUidStr = auth.getCurrentUser().getUid();

        responsabilityService = new ResponsabilityService(this);
        backArrowButton = findViewById(R.id.list_responsabilities_back_arrow);
        addConstructionButton = findViewById(R.id.list_responsabilities_add_button);

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addConstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListResponsabilitiesActivity.this, ConstructionExecReponsabilityFormActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.list_responsabilities_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        adaptResponsabilitiesToView();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void adaptResponsabilitiesToView() {
        DatabaseReference responsabilitiesReference = responsabilityService.getResponsabilitiesReference(constructionUidStr);

        responsabilitiesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                responsabilities = new ArrayList<>();
                Responsability responsability;
                for(DataSnapshot resp_snap : snapshot.getChildren()) {
                    responsability = resp_snap.getValue(Responsability.class);
                    responsability.setResponsabilityUid(resp_snap.getKey()); // !!!
                    responsabilities.add(responsability);
                }
                adapter = new ResponsabilityListAdapter(responsabilities, context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListResponsabilitiesActivity.this, "Erro inesperado.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
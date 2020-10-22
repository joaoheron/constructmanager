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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.heron.constructmanager.adapters.ConstructionAdapter;
import com.heron.constructmanager.R;
import com.heron.constructmanager.activities.forms.ConstructionNewFormActivity;
import com.heron.constructmanager.models.Construction;

import java.util.ArrayList;

public class ListConstructionsActivity extends AppCompatActivity {

    Context context;

    ArrayList<Construction> constructions;
    ConstructionAdapter adapter;

    RecyclerView recyclerView;

    Button addConstructionButton;
    ImageView backArrowButton;

    DatabaseReference constructionsReference;
    FirebaseDatabase db;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_constructions);
        constructions = new ArrayList<>();
        context = this;

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();

        backArrowButton = findViewById(R.id.list_constructions_back_arrow);
        addConstructionButton = findViewById(R.id.list_constructions_add_button);

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addConstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListConstructionsActivity.this, ConstructionNewFormActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.list_constructions_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        readConstructions();

    }

    private void readConstructions() {

        constructionsReference = db.getReference().child("users").child(user.getUid()).child("constructions");
        constructionsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                constructions = new ArrayList<>();
                Construction construction;
                for(DataSnapshot construction_snap : snapshot.getChildren()) {
                    construction = construction_snap.getValue(Construction.class);
                    construction.setUid(construction_snap.getKey()); // !!!
                    constructions.add(construction);
                }
                adapter = new ConstructionAdapter(constructions, context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListConstructionsActivity.this, "Erro inesperado.", Toast.LENGTH_LONG).show();
            }
        });

    }
}
package com.heron.constructmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
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
import com.heron.constructmanager.ObrasAdapter;
import com.heron.constructmanager.R;
import com.heron.constructmanager.forms.NewObraForm;
import com.heron.constructmanager.models.Obra;

import java.util.ArrayList;

public class ListaObrasActivity extends AppCompatActivity {

    Context context;

    ArrayList<Obra> obras;
    ObrasAdapter adapter;

    RecyclerView recyclerView;

    Button addObraButton;
    ImageView backArrowButton;

    DatabaseReference obrasReference;
    FirebaseDatabase db;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_obras);
        obras = new ArrayList<>();
        context = this;

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();

        backArrowButton = findViewById(R.id.list_obras_back_arrow);
        addObraButton = findViewById(R.id.list_obras_add_button);

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addObraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaObrasActivity.this, NewObraForm.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.list_obras_recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        readObras();

    }

    private void readObras() {

        obrasReference = db.getReference().child("users").child(user.getUid()).child("obras");
        obrasReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                obras = new ArrayList<>();
                Obra obra;
                for(DataSnapshot obra_snap : snapshot.getChildren()) {
                    obra = obra_snap.getValue(Obra.class);
                    obra.setUid(obra_snap.getKey()); // !!!
                    obras.add(obra);
                }
                adapter = new ObrasAdapter(obras, context);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListaObrasActivity.this, "Erro inesperado.", Toast.LENGTH_LONG).show();
            }
        });

    }
}
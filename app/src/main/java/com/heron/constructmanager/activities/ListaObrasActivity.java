package com.heron.constructmanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.heron.constructmanager.R;
import com.heron.constructmanager.forms.NewObraForm;
import com.heron.constructmanager.models.Obra;

public class ListaObrasActivity extends AppCompatActivity {

    Button add_obra;

    DatabaseReference obras_ref;
    FirebaseDatabase db;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_obras);

        auth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        user = auth.getCurrentUser();

        add_obra = findViewById(R.id.list_obras_add_button);
        add_obra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaObrasActivity.this, NewObraForm.class);
                startActivity(intent);
            }
        });

    }

    private void readObras() {

        obras_ref = db.getReference().child("users").child(user.getUid()).child("obras");
        obras_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Obra obra;
                for(DataSnapshot obra_snap : snapshot.getChildren()) {
                    obra = obra_snap.getValue(Obra.class);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
package com.heron.constructmanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.heron.constructmanager.R;
import com.heron.constructmanager.forms.NewObraForm;

public class ListaObrasActivity extends AppCompatActivity {

    Button add_obra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_obras);

        add_obra = findViewById(R.id.list_obras_add_button);
        add_obra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaObrasActivity.this, NewObraForm.class);
                startActivity(intent);
            }
        });

    }
}
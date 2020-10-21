package com.heron.constructmanager.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.LoadingAnimation;
import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;
import com.heron.constructmanager.activities.HomeActivity;
import com.heron.constructmanager.activities.MainActivity;
import com.heron.constructmanager.models.Obra;
import com.heron.constructmanager.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewObraForm extends AppCompatActivity {

    ImageView back_arrow;

    DatabaseReference root_ref;
    FirebaseAuth auth;

    EditText title, address, type, responsibles;
    Button add_button;

    String user_id_str, title_str, address_str, type_str, responsibles_str;

    ValidateInput validate_input;
    LoadingAnimation loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_obra_form);
        // Components
        title = findViewById(R.id.new_obra_title);
        address = findViewById(R.id.new_obra_address);
        type = findViewById(R.id.new_obra_type);
        responsibles = findViewById(R.id.new_obra_responsibles);
        add_button = findViewById(R.id.new_obra_add_button);
        back_arrow = findViewById(R.id.new_obra_back_arrow);
        // Firebase
        auth = FirebaseAuth.getInstance();
        root_ref = FirebaseDatabase.getInstance().getReference();
        // Validate
        validate_input = new ValidateInput(NewObraForm.this, title, address, type, responsibles);
        // Loading animation
        loading = new LoadingAnimation(this);
        // Listeners
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.loadingAnimationDialog();

                boolean title_verified = validate_input.validateTitle();
                boolean address_verified = validate_input.validateAddress();
                boolean type_verified = validate_input.validateType();
                boolean responsibles_verified = validate_input.validateResponsibles();

                if (title_verified && address_verified && type_verified && responsibles_verified) {

                    title_str = title.getText().toString().trim();
                    address_str = address.getText().toString().trim();
                    type_str = type.getText().toString().trim();
                    responsibles_str = responsibles.getText().toString().trim();
                    user_id_str = auth.getCurrentUser().getUid();

                    writeObra(user_id_str, title_str, address_str, type_str, responsibles_str);

                    loading.dismissLoading();
                    finish();
                }
                else {
                    loading.dismissLoading();
                }

            }
        });

    }

    private void writeObra(String user_id, String title, String address, String type,  String responsibles) {
        // Create new post at /users/$user_id/$obra_id and at
        // /obras/$obra_id simultaneously
        String key = root_ref.child("obras").push().getKey();
        Obra obra = new Obra(title, address, type, "Preparacao", responsibles);
        Map<String, Object> postValues = obra.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/obras/" + key, postValues);
        childUpdates.put("/users/" + user_id + "/obras/" + key, postValues);

        root_ref.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(NewObraForm.this, "Obra cadastrada com sucesso.", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(NewObraForm.this, "Erro ao cadastrar obra! Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
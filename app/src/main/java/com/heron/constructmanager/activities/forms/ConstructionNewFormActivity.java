package com.heron.constructmanager.activities.forms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.animations.LoadingAnimation;
import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;
import com.heron.constructmanager.models.Construction;

import java.util.HashMap;
import java.util.Map;

public class ConstructionNewFormActivity extends AppCompatActivity {

    ImageView backArrowImg;

    DatabaseReference rootReference;
    FirebaseAuth auth;

    EditText titleEditText, addressEditText, typeEditText, responsiblesEditText;
    Button addButton;

    String userIdStr, titleStr, addressStr, typeStr, responsiblesStr;

    ValidateInput validateInput;
    LoadingAnimation loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_new_form);
        // Components
        titleEditText = findViewById(R.id.new_construction_title);
        addressEditText = findViewById(R.id.new_construction_address);
        typeEditText = findViewById(R.id.new_construction_type);
        responsiblesEditText = findViewById(R.id.new_construction_responsibles);
        addButton = findViewById(R.id.new_construction_add_button);
        backArrowImg = findViewById(R.id.new_construction_back_arrow);
        // Firebase
        auth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();
        // Validate
        validateInput = new ValidateInput(ConstructionNewFormActivity.this, titleEditText, addressEditText, typeEditText, responsiblesEditText);
        // Loading animation
        loading = new LoadingAnimation(this);
        // Listeners
        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loading.loadingAnimationDialog();

                boolean title_verified = validateInput.validateTitle();
                boolean address_verified = validateInput.validateAddress();
                boolean type_verified = validateInput.validateType();
                boolean responsibles_verified = validateInput.validateResponsibles();

                if (title_verified && address_verified && type_verified && responsibles_verified) {

                    titleStr = titleEditText.getText().toString().trim();
                    addressStr = addressEditText.getText().toString().trim();
                    typeStr = typeEditText.getText().toString().trim();
                    responsiblesStr = responsiblesEditText.getText().toString().trim();
                    userIdStr = auth.getCurrentUser().getUid();

                    writeConstruction(userIdStr, titleStr, addressStr, typeStr, responsiblesStr);

                    loading.dismissLoading();
                    finish();
                }
                else {
                    loading.dismissLoading();
                }

            }
        });

    }

    private void writeConstruction(String user_id, String title, String address, String type,  String responsibles) {
        // Create new post at /users/$user_id/$construction_id and at
        // /constructions/$construction_id simultaneously
        String key = rootReference.child("constructions").push().getKey();
        Construction construction = new Construction(title, address, type, "Preparacao", responsibles);
        Map<String, Object> postValues = construction.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/constructions/" + key, postValues);
        childUpdates.put("/users/" + user_id + "/constructions/" + key, postValues);

        rootReference.updateChildren(childUpdates).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(ConstructionNewFormActivity.this, "Construction cadastrada com sucesso.", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(ConstructionNewFormActivity.this, "Erro ao cadastrar construction! Tente novamente.", Toast.LENGTH_LONG).show();
            }
        });
    }

}
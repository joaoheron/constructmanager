package com.heron.constructmanager.activities.forms;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.heron.constructmanager.animations.LoadingAnimation;
import com.heron.constructmanager.R;
import com.heron.constructmanager.ValidateInput;
import com.heron.constructmanager.service.ConstructionService;

public class ConstructionPrepFormActivity extends AppCompatActivity {

    ImageView backArrowImg, deleteImg, newStageImg, cancelImg;

    DatabaseReference rootReference;
    FirebaseAuth auth;
    ConstructionService service;

    EditText titleEditText, addressEditText, typeEditText, responsiblesEditText;
    Button addButton;

    String userIdStr, titleStr, addressStr, stageStr, typeStr, responsiblesStr, constructionUidStr;

    ValidateInput validateInput;
    LoadingAnimation loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_construction_prep_form);
        constructionUidStr = null;
        stageStr = "Preparação";

        service = new ConstructionService(this);
        // Components
        titleEditText = findViewById(R.id.construction_prep_title);
        addressEditText = findViewById(R.id.construction_prep_address);
        typeEditText = findViewById(R.id.construction_prep_type);
        responsiblesEditText = findViewById(R.id.construction_prep_responsibles);
        addButton = findViewById(R.id.construction_prep_add_button);
        backArrowImg = findViewById(R.id.construction_prep_back_arrow);
        deleteImg = findViewById(R.id.construction_prep_delete);
        newStageImg = findViewById(R.id.construction_prep_new_stage);
        cancelImg = findViewById(R.id.construction_prep_cancel);
        // Firebase
        auth = FirebaseAuth.getInstance();
        rootReference = FirebaseDatabase.getInstance().getReference();
        userIdStr = auth.getCurrentUser().getUid();
        // Validate
        validateInput = new ValidateInput(ConstructionPrepFormActivity.this, titleEditText, addressEditText, typeEditText, responsiblesEditText);
        // Loading animation
        loading = new LoadingAnimation(this);

        if(getIntent().getExtras() != null) {
            titleStr = getIntent().getStringExtra("title");
            addressStr = getIntent().getStringExtra("address");
            typeStr = getIntent().getStringExtra("type");
            responsiblesStr = getIntent().getStringExtra("responsibles");
            constructionUidStr = getIntent().getStringExtra("constructionUid");

            titleEditText.setText(titleStr);
            addressEditText.setText(addressStr);
            typeEditText.setText(typeStr);
            responsiblesEditText.setText(responsiblesStr);
        }

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

                if (infosVerified()) {
                    getEditTextsContent();
                    service.writeConstruction(userIdStr, titleStr, addressStr, stageStr, typeStr, responsiblesStr, constructionUidStr);
                    loading.dismissLoading();
                    finish();
                }
                else {
                    loading.dismissLoading();
                }
            }
        });

        deleteImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConstructionPrepFormActivity.this);
                builder.setTitle("Deletar obra");
                builder.setMessage("Tem certeza que deseja deletar a obra?");
                AlertDialog dialog = builder.create();
                dialog.setButton(Dialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        service.deleteConstruction(userIdStr, constructionUidStr);
                        finish();
                    }
                });
                dialog.setButton(Dialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        cancelImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infosVerified()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConstructionPrepFormActivity.this);
                    builder.setTitle("Cancelar obra");
                    builder.setMessage("Tem certeza que deseja cancelar a obra?");
                    AlertDialog dialog = builder.create();
                    dialog.setButton(Dialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getEditTextsContent();
                            service.cancelConstruction(userIdStr, titleStr, addressStr, typeStr, responsiblesStr, constructionUidStr);
                            finish();
                        }
                    });
                    dialog.setButton(Dialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });

        newStageImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (infosVerified()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConstructionPrepFormActivity.this);
                    builder.setTitle("Avançar etapa");
                    builder.setMessage("Tem certeza que deseja avançar a etapa da obra para \"Em execução\"?");
                    AlertDialog dialog = builder.create();
                    dialog.setButton(Dialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getEditTextsContent();
                            service.advanceStageToExec(userIdStr, titleStr, addressStr, typeStr, responsiblesStr, constructionUidStr);
                            finish();
                        }
                    });
                    dialog.setButton(Dialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();

                }
            }
        });

    }

    public boolean infosVerified() {
        boolean title_verified = validateInput.validateTitle();
        boolean address_verified = validateInput.validateAddress();
        boolean type_verified = validateInput.validateType();
        boolean responsibles_verified = validateInput.validateResponsibles();

        return title_verified && address_verified && type_verified && responsibles_verified;
    }

    public void getEditTextsContent() {
        titleStr = titleEditText.getText().toString().trim();
        addressStr = addressEditText.getText().toString().trim();
        typeStr = typeEditText.getText().toString().trim();
        responsiblesStr = responsiblesEditText.getText().toString().trim();
    }

}
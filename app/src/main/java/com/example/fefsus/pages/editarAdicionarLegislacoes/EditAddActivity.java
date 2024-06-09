package com.example.fefsus.pages.editarAdicionarLegislacoes;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fefsus.R;
import com.example.fefsus.domain.legislacao.LegislacaoCallback;
import com.example.fefsus.domain.legislacao.LegislacaoEditAdd;
import com.example.fefsus.domain.legislacao.LegislacaoModel;
import com.example.fefsus.domain.legislacao.LegislacaoService;

import java.util.Objects;

public class EditAddActivity extends AppCompatActivity {
    private final LegislacaoService licitacoesService = new LegislacaoService();
    private Dialog dialog;
    private TextView textViewMsg;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_add_activity);
        TextView titlePage = findViewById(R.id.titlePage);
        ImageButton imageButtonSave = findViewById(R.id.imageButtonSave);
        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextNum = findViewById(R.id.editTextNum);
        EditText editTextDetails = findViewById(R.id.editTextDetails);
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);

        dialog = new Dialog(EditAddActivity.this);
        dialog.setContentView(R.layout.dialog_activity);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.custom_shape_dialog));
        dialog.setCancelable(false);

        TextView btnDialog = dialog.findViewById(R.id.textViewOk);
        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        textViewMsg = dialog.findViewById(R.id.textViewMsg);

        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();


        if (intent != null && intent.hasExtra("id") && intent.getLongExtra("id", 0) != 0) {
            titlePage.setText(R.string.editar_legisla_o);
            long legislacaoId = intent.getLongExtra("id", 0);

            licitacoesService.getId(legislacaoId, new LegislacaoCallback() {
                @Override
                public void onLicitacaoReceived(LegislacaoModel licitacoes) {
                    runOnUiThread(() -> {
                        editTextTitle.setText(licitacoes.getDescricao());
                        editTextNum.setText(licitacoes.getNumero());
                        editTextDetails.setText(licitacoes.getDetalhe());
                    });
                }

                @Override
                public void onError(Throwable throwable) {
                    runOnUiThread(() -> Toast.makeText(getApplicationContext(), "Error loading legislation", Toast.LENGTH_SHORT).show());
                }
            });

            imageButtonSave.setOnClickListener(v -> {
                LegislacaoModel licitacoesModel = new LegislacaoModel();
                if (validateFields(editTextNum, editTextDetails, editTextTitle)) {
                    licitacoesModel.setNumero(editTextNum.getText().toString());
                    licitacoesModel.setDetalhe(editTextDetails.getText().toString());
                    licitacoesModel.setDescricao(editTextTitle.getText().toString());
                    licitacoesModel.setId(legislacaoId);

                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    String token = sharedPref.getString("token", "");

                    licitacoesService.update(licitacoesModel, token, new LegislacaoEditAdd() {
                        @Override
                        public void onLicitacaoEditAddReceived(String returnMsg) {
                            runOnUiThread(() -> {
                                setResult(RESULT_OK);
                                textViewMsg.setText("Editado com sucesso");
                                dialog.show();
                            });
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show());
                            Log.e("EditAddActivity", "Error updating legislation", throwable);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Todos os campos são obrigatorios", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            titlePage.setText(R.string.nova_legisla_o);
            imageButtonSave.setOnClickListener(v -> {
                LegislacaoModel licitacoesModel = new LegislacaoModel();
                if (validateFields(editTextNum, editTextDetails, editTextTitle)) {
                    licitacoesModel.setNumero(editTextNum.getText().toString());
                    licitacoesModel.setDetalhe(editTextDetails.getText().toString());
                    licitacoesModel.setDescricao(editTextTitle.getText().toString());

                    SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                    String token = sharedPref.getString("token", "");

                    licitacoesService.create(licitacoesModel, token, new LegislacaoEditAdd() {
                        @Override
                        public void onLicitacaoEditAddReceived(String returnMsg) {
                            runOnUiThread(() -> {
                                setResult(RESULT_OK);
                                textViewMsg.setText("Criado com sucesso");
                                dialog.show();
                            });
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            runOnUiThread(() -> Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show());
                            Log.e("EditAddActivity", "Error creating legislation", throwable);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Todos os campos são obrigatorios", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validateFields(EditText editTextNum, EditText editTextDetails, EditText editTextTitle) {
        return editTextNum.getText().length() > 0 &&
                editTextDetails.getText().length() > 0 &&
                editTextTitle.getText().length() > 0;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("EditAddActivity", "onSaveInstanceState called");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("EditAddActivity", "onRestoreInstanceState called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("EditAddActivity", "onDestroy called");
    }
}

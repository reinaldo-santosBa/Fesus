package com.example.fefsus.pages.editarAdicionarLegislacoes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fefsus.R;
import com.example.fefsus.domain.licitacoes.LicitacaoCallback;
import com.example.fefsus.domain.licitacoes.LicitacoesEditAdd;
import com.example.fefsus.domain.licitacoes.LicitacoesModel;
import com.example.fefsus.domain.licitacoes.LicitacoesService;

public class EditAddActivity  extends AppCompatActivity {
    private LicitacoesService licitacoesService = new LicitacoesService();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_add_activity);
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        TextView titlePage = findViewById(R.id.titlePage);
        ImageButton imageButtonSave = findViewById(R.id.imageButtonSave);
        EditText editTextTitle = findViewById(R.id.editTextTitle);
        EditText editTextNum = findViewById(R.id.editTextNum);
        EditText editTextDetails = findViewById(R.id.editTextDetails);

        if (intent != null && intent.hasExtra("id") && intent.getLongExtra("id", 0) != 0) {
            titlePage.setText(R.string.nova_legisla_o);
            titlePage.setText(R.string.editar_legisla_o);
            Log.d("243484id", String.valueOf(intent.getLongExtra("id",0)));

            licitacoesService.getId(intent.getLongExtra("id",0),
                    new LicitacaoCallback(){
                        @Override
                        public void onLicitacaoReceived(LicitacoesModel licitacoes) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    editTextTitle.setText(licitacoes.getDescricao());
                                    editTextNum.setText(licitacoes.getNumero());
                                    editTextDetails.setText(licitacoes.getDetalhe());
                                }
                            });
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
            imageButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LicitacoesModel licitacoesModel  = new LicitacoesModel();
                    if(
                            editTextNum.getText().length() > 0 ||
                            editTextDetails.getText().length() > 0 ||
                            editTextTitle.getText().length() > 0
                    ) {
                        licitacoesModel.setNumero(String.valueOf(editTextNum.getText()));
                        licitacoesModel.setDetalhe(String.valueOf(editTextDetails.getText()));
                        licitacoesModel.setDescricao(String.valueOf(editTextTitle.getText()));
                        licitacoesModel.setId(intent.getLongExtra("id",0));

                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                                getString(R.string.preference_file_key),
                                Context.MODE_PRIVATE
                        );
                        String token = sharedPref.getString("token", "");
                        licitacoesService.update(licitacoesModel, token, new LicitacoesEditAdd() {
                            @Override
                            public void onLicitacaoEditAddReceived(String returnMsg) {
                                Toast.makeText(getApplicationContext(), returnMsg, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }else {

            imageButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LicitacoesModel licitacoesModel = new LicitacoesModel();
                    if (
                            editTextNum.getText().length() > 0 ||
                                    editTextDetails.getText().length() > 0 ||
                                    editTextTitle.getText().length() > 0
                    ) {
                        licitacoesModel.setNumero(String.valueOf(editTextNum.getText()));
                        licitacoesModel.setDetalhe(String.valueOf(editTextDetails.getText()));
                        licitacoesModel.setDescricao(String.valueOf(editTextTitle.getText()));
                        licitacoesModel.setId(1L);

                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences(
                                getString(R.string.preference_file_key),
                                Context.MODE_PRIVATE
                        );
                        String token = sharedPref.getString("token", "");
                        licitacoesService.create(licitacoesModel, token, new LicitacoesEditAdd() {
                            @Override
                            public void onLicitacaoEditAddReceived(String returnMsg) {
                                Toast.makeText(getApplicationContext(), returnMsg, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }


    }
}

package com.example.fefsus.pages.detailsLegislacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fefsus.R;
import com.example.fefsus.domain.licitacoes.LicitacaoCallback;
import com.example.fefsus.domain.licitacoes.LicitacoesModel;
import com.example.fefsus.domain.licitacoes.LicitacoesService;

public class DetailsLegislacaoActivity  extends AppCompatActivity {
    private LicitacoesService licitacoesService = new LicitacoesService();
    private LicitacoesModel licitacoesModel;
    private TextView textDescricao, textNumero, textDetalhe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_legislacao_activity);
        Intent intent = getIntent();
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        boolean logged = sharedPref.getBoolean("logged",false);
        ImageButton imageButtonEdit = findViewById(R.id.imageButtonEdit);

        ViewGroup parent = (ViewGroup) imageButtonEdit.getParent();
        if(!logged){
            parent.removeView(imageButtonEdit);
        }
        if(intent != null){
            licitacoesService.getId(intent.getLongExtra("id",0),
                    new LicitacaoCallback(){
                        @Override
                        public void onLicitacaoReceived(LicitacoesModel licitacoes) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    textDetalhe = findViewById(R.id.textDetalhe);
                                    textDetalhe.setText(licitacoes.getDetalhe());
                                    textNumero = findViewById(R.id.textNumero);
                                    textNumero.setText(licitacoes.getNumero());
                                    textDescricao = findViewById(R.id.textDescricao);
                                    textDescricao.setText(licitacoes.getDescricao());

                                }
                            });
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
    }
}

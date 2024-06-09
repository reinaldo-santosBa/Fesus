package com.example.fefsus.pages.detailsLegislacao;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fefsus.R;
import com.example.fefsus.domain.legislacao.LegislacaoCallback;
import com.example.fefsus.domain.legislacao.LegislacaoModel;
import com.example.fefsus.domain.legislacao.LegislacaoService;
import com.example.fefsus.pages.editarAdicionarLegislacoes.EditAddActivity;

public class DetailsLegislacaoActivity  extends AppCompatActivity {
    private LegislacaoService licitacoesService = new LegislacaoService();
    private LegislacaoModel licitacoesModel;
    private TextView textDescricao, textNumero, textDetalhe;
    private ActivityResultLauncher<Intent> addEditLauncher;

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

        if(intent != null){
            Long id =   intent.getLongExtra("id",0);
            if(!logged){
                parent.removeView(imageButtonEdit);
            }else {
                addEditLauncher = registerForActivityResult(
                        new ActivityResultContracts.StartActivityForResult(),
                        result ->{
                            getId(id);
                        });
                getId(id);
                imageButtonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailsLegislacaoActivity.this, EditAddActivity.class);
                        intent.putExtra("id",id);
                        addEditLauncher.launch(intent);
                    }
                });
            }

        }
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void getId(Long id){
        licitacoesService.getId(id,
                new LegislacaoCallback(){
                    @Override
                    public void onLicitacaoReceived(LegislacaoModel licitacoes) {
                    Log.d("4t645g","yterfwsrfg");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textDetalhe = findViewById(R.id.textDetalhe);
                                String detalheCompleto = "<b>Detalhe: </b>" + licitacoes.getDetalhe();
                                textDetalhe.setText(Html.fromHtml(detalheCompleto, Html.FROM_HTML_MODE_LEGACY));
                                textNumero = findViewById(R.id.textNumero);
                                String numeroCompleto = "<b>Número da legislação: </b>" + licitacoes.getNumero();
                                textNumero.setText(Html.fromHtml(numeroCompleto, Html.FROM_HTML_MODE_LEGACY));
                                textDescricao = findViewById(R.id.textDescricao);
                                String descricaoCompleto = "<b>Descrição: </b>" + licitacoes.getDescricao();
                                textDescricao.setText(Html.fromHtml(descricaoCompleto, Html.FROM_HTML_MODE_LEGACY));
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

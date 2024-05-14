package com.example.fefsus.pages.detailsLegislacao;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fefsus.R;
import com.example.fefsus.domain.licitacoes.LicitacaoCallback;
import com.example.fefsus.domain.licitacoes.LicitacoesCallback;
import com.example.fefsus.domain.licitacoes.LicitacoesModel;
import com.example.fefsus.domain.licitacoes.LicitacoesService;

import java.util.ArrayList;

public class DetailsLegislacaoActivity  extends AppCompatActivity {
    private LicitacoesService licitacoesService = new LicitacoesService();
    private TextView textViewDescricao, textViewNumero, textViewDetalhe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_legislacao_activity);
        Intent intent = getIntent();
        if(intent != null){
            licitacoesService.getId(intent.getLongExtra("id",0),
                    new LicitacaoCallback(){
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onLicitacaoReceived(LicitacoesModel licitacoes) {
                            Log.d("detalhesDetails",licitacoes.getDetalhe());

                            textViewDetalhe = findViewById(R.id.textDel);
                            textViewDescricao = findViewById(R.id.textViewDetailsDescricao);
                            textViewNumero = findViewById(R.id.textViewDetalhesNumero);

                            textViewDetalhe.setText("Soluta laboriosam reiciendis sunt ratione. Voluptates veniam autem dolore modi commodi ad ut iste. Voluptate vitae iste magni libero atque. Eos repudiandae ullam nam vitae eligendi. Fugiat dolore alias veritatis temporibus maiores nemo facere et omnis. Expedita commodi laudantium natus.\nArchitecto illum voluptate excepturi nisi asperiores dolor soluta voluptate quas. Quod asperiores neque aspernatur enim molestias laborum natus occaecati. Iure nemo consequuntur sint sed. Fuga aliquid iste nemo ratione. Quibusdam ullam quaerat hic. Ducimus occaecati mollitia assumenda.\nPossimus illum magni a aperiam officiis perferendis tenetur nesciunt. Saepe perferendis quas voluptatem repellat nisi necessitatibus porro sequi est. Distinctio quia esse dolores excepturi doloremque ad.");
                            textViewDescricao.setText(licitacoes.getDescricao());
                            textViewNumero.setText(licitacoes.getNumero());

                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}

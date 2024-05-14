package com.example.fefsus.pages.listLicitacoes;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fefsus.R;
import com.example.fefsus.domain.licitacoes.LicitacoesAdapter;
import com.example.fefsus.domain.licitacoes.LicitacoesCallback;
import com.example.fefsus.domain.licitacoes.LicitacoesModel;
import com.example.fefsus.domain.licitacoes.LicitacoesService;

import java.util.ArrayList;

public class ListLicitacoesActivity extends AppCompatActivity {
    private LicitacoesService licitacoesService = new LicitacoesService();
    private EditText textSearch;
    private LicitacoesAdapter licitacoesAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_licitacao_activity);
        Handler handler = new Handler(Looper.getMainLooper());

        RecyclerView recyclerViewLicitacoes = findViewById(R.id.recyclerViewLicitacoes);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewLicitacoes.setLayoutManager(layoutManager);
        recyclerViewLicitacoes.setHasFixedSize(true);

        licitacoesService.get(new LicitacoesCallback() {
            @Override
            public void onLicitacoesReceived(ArrayList<LicitacoesModel> licitacoes) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!licitacoes.isEmpty()) {
                            licitacoesAdapter = new LicitacoesAdapter(licitacoes,ListLicitacoesActivity.this);
                            recyclerViewLicitacoes.setAdapter(licitacoesAdapter);
                        } else {
                            Log.e("List", "A lista de licitações está vazia.");
                        }
                        textSearch = findViewById(R.id.inputSearch);
                        textSearch.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                if (licitacoesAdapter != null) {
                                    licitacoesAdapter.filtrar(s.toString());
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable s) {}
                        });
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
            }

        });
    }

}
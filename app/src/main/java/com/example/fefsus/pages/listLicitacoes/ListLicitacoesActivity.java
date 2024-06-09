package com.example.fefsus.pages.listLicitacoes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fefsus.R;
import com.example.fefsus.domain.legislacao.LegislacaoAdapter;
import com.example.fefsus.domain.legislacao.LegislacoesCallback;
import com.example.fefsus.domain.legislacao.LegislacaoModel;
import com.example.fefsus.domain.legislacao.LegislacaoService;
import com.example.fefsus.pages.editarAdicionarLegislacoes.EditAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListLicitacoesActivity extends AppCompatActivity {
    private LegislacaoService licitacoesService = new LegislacaoService();
    private EditText textSearch;
    private LegislacaoAdapter licitacoesAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());
    private ActivityResultLauncher<Intent> addEditLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_licitacao_activity);
        addEditLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    loadItens();
                }
        );
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        boolean logged = sharedPref.getBoolean("logged", false);
        FloatingActionButton floatingActionButton = findViewById(R.id.btnAdd);

        ViewGroup parent = (ViewGroup) floatingActionButton.getParent();

        if (!logged) {
            parent.removeView(floatingActionButton);
        } else {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ListLicitacoesActivity.this, EditAddActivity.class);
                    addEditLauncher.launch(intent);
                }
            });
        }
        loadItens();

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
    public void loadItens(){
        licitacoesService.get(new LegislacoesCallback() {
            @Override
            public void onLicitacoesReceived(ArrayList<LegislacaoModel> licitacoes) {
                handler.post(() -> {
                    RecyclerView recyclerViewLicitacoes = findViewById(R.id.recyclerViewLicitacoes);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListLicitacoesActivity.this);
                    recyclerViewLicitacoes.setLayoutManager(layoutManager);
                    recyclerViewLicitacoes.setHasFixedSize(true);
                    if (!licitacoes.isEmpty()) {
                        licitacoesAdapter = new LegislacaoAdapter(licitacoes, ListLicitacoesActivity.this,addEditLauncher);
                        recyclerViewLicitacoes.setAdapter(licitacoesAdapter);
                    } else {
                        Toast.makeText(getApplicationContext(), "Sem dados para exibir", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onError(Throwable throwable) {
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

}

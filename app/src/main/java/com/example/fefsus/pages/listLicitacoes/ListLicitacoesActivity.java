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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fefsus.R;
import com.example.fefsus.domain.licitacoes.LicitacoesAdapter;
import com.example.fefsus.domain.licitacoes.LicitacoesCallback;
import com.example.fefsus.domain.licitacoes.LicitacoesModel;
import com.example.fefsus.domain.licitacoes.LicitacoesService;
import com.example.fefsus.pages.editarAdicionarLegislacoes.EditAddActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ListLicitacoesActivity extends AppCompatActivity {
    private LicitacoesService licitacoesService = new LicitacoesService();
    private EditText textSearch;
    private LicitacoesAdapter licitacoesAdapter;
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_licitacao_activity);

        RecyclerView recyclerViewLicitacoes = findViewById(R.id.recyclerViewLicitacoes);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewLicitacoes.setLayoutManager(layoutManager);
        recyclerViewLicitacoes.setHasFixedSize(true);

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
                    startActivity(intent);
                }
            });
        }

        licitacoesService.get(new LicitacoesCallback() {
            @Override
            public void onLicitacoesReceived(ArrayList<LicitacoesModel> licitacoes) {
                handler.post(() -> {
                    if (!licitacoes.isEmpty()) {
                        licitacoesAdapter = new LicitacoesAdapter(licitacoes, ListLicitacoesActivity.this);
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

package com.example.fefsus;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fefsus.pages.listLicitacoes.ListLicitacoesActivity;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    public TextView btnConvidado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConvidado = findViewById(R.id.btnConvidado);
        btnConvidado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ListLicitacoesIntent = new Intent(MainActivity.this, ListLicitacoesActivity.class);
                startActivity(ListLicitacoesIntent);
            }
        });
    }
}
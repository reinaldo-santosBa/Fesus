package com.example.fefsus.pages.editarAdicionarLegislacoes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fefsus.R;

public class EditAddActivity  extends AppCompatActivity {
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

        if(intent == null){
            titlePage.setText(R.string.nova_legisla_o);
            imageButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }else {
            titlePage.setText(R.string.editar_legisla_o);
            imageButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }


    }
}

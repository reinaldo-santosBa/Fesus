package com.example.fefsus;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fefsus.domain.usuario.UsuarioLoginCallback;
import com.example.fefsus.domain.usuario.UsuarioModel;
import com.example.fefsus.domain.usuario.UsuarioService;
import com.example.fefsus.pages.listLicitacoes.ListLicitacoesActivity;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextView btnConvidado;
    private UsuarioService usuarioService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConvidado = findViewById(R.id.btnConvidado);
        Context context = getApplicationContext();
        SharedPreferences sharedPref = context.getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
        );
        btnConvidado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ListLicitacoesIntent = new Intent(MainActivity.this, ListLicitacoesActivity.class);
                startActivity(ListLicitacoesIntent);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putBoolean("logged",false);
                editor.putString("token","");
                editor.apply();
            }
        });

        Button buttonLogin = findViewById(R.id.buttonLogin);
        ProgressBar progressBarLogin = findViewById(R.id.progressBarLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarLogin.setVisibility(View.VISIBLE);
                buttonLogin.setVisibility(View.GONE);
                TextInputEditText editTextEmail = findViewById(R.id.textInputEditTextEmail);
                TextInputEditText editTextSenha = findViewById(R.id.textInputEditTextSenha);
                String editTextEmailValue = String.valueOf(editTextEmail.getText());
                String editTextSenhaValue = String.valueOf(editTextSenha.getText());
                usuarioService = new UsuarioService();

                if (editTextEmailValue.isEmpty() || editTextSenhaValue.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Preencha os campos acima", Toast.LENGTH_SHORT).show();
                    return;
                }
                usuarioService.login(editTextEmailValue, editTextSenhaValue, new UsuarioLoginCallback() {
                    @Override
                    public void onUsuarioReceived(UsuarioModel usuario) {
                        runOnUiThread(() -> {
                            progressBarLogin.setVisibility(View.GONE);
                            buttonLogin.setVisibility(View.VISIBLE);
                            Intent ListLicitacoesIntent = new Intent(MainActivity.this, ListLicitacoesActivity.class);
                            startActivity(ListLicitacoesIntent);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putBoolean("logged", true);
                            editor.putString("token", usuario.getToken());
                            editor.apply();
                        });
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        runOnUiThread(() -> {
                            progressBarLogin.setVisibility(View.GONE);
                            buttonLogin.setVisibility(View.VISIBLE);
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }
        });

    }
}
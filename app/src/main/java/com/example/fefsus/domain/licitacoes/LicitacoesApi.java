package com.example.fefsus.domain.licitacoes;

import android.util.Log;

import com.example.fefsus.utils.ApiResponseListener;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LicitacoesApi {

    public LicitacoesApi() {
    }

    private final OkHttpClient client = new OkHttpClient();
    private final Executor executor = Executors.newSingleThreadExecutor();

    public void getAsync(ApiResponseListener apiResponseListener) {
        executor.execute(() -> {
            String url = "http://192.168.100.50:8080/legislacao/listar";
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Log.d("Errorafew",response.toString());
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Log.d("LicitacoesApi", "Sucesso ao obter dados da API");
                    apiResponseListener.onResponse(responseBody);
                } else {
                    Log.e("LicitacoesApi", "Erro ao obter dados da API: " + response);
                    apiResponseListener.onError(new IOException("Unsuccessful response: " + response));
                }
            } catch (IOException e) {
                Log.e("LicitacoesApi", "Erro ao obter dados da API", e);
                apiResponseListener.onError(e);
            }
        });
    }

    public void getIdAsync(Long id, ApiResponseListener apiResponseListener) {
        executor.execute(() -> {
            String url = "http://192.168.100.50:8080/legislacao/" + id;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Log.d("LicitacoesApi", "Sucesso ao obter dados da API para o ID: " + id);
                    apiResponseListener.onResponse(responseBody);
                } else {
                    Log.e("LicitacoesApi", "Erro ao obter dados da API para o ID: " + id + " - " + response);
                    apiResponseListener.onError(new IOException("Unsuccessful response: " + response));
                }
            } catch (IOException e) {
                Log.e("LicitacoesApi", "Erro ao obter dados da API para o ID: " + id, e);
                apiResponseListener.onError(e);
            }
        });
    }
}

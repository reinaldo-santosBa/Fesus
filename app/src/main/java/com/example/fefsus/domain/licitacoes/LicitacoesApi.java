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
            String url = "http://10.0.2.2:8080/legislacao";
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    apiResponseListener.onResponse(responseBody);
                } else {
                    apiResponseListener.onError(new IOException("Unsuccessful response: " + response));
                }
            } catch (IOException e) {
                apiResponseListener.onError(e);
            }
        });
    }
    public void getIdAsync(Long id,ApiResponseListener apiResponseListener) {
        executor.execute(() -> {
            String url = "http://10.0.2.2:8080/legislacao/" + id;
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    apiResponseListener.onResponse(responseBody);
                } else {
                    apiResponseListener.onError(new IOException("Unsuccessful response: " + response));
                }
            } catch (IOException e) {
                apiResponseListener.onError(e);
            }
        });
    }
}

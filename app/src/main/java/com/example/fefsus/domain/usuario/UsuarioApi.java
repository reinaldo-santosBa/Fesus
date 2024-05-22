package com.example.fefsus.domain.usuario;

import android.util.Log;

import com.example.fefsus.utils.ApiResponseListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UsuarioApi {
    public UsuarioApi(){
    }
    private final OkHttpClient client = new OkHttpClient();

    private final Executor executor = Executors.newSingleThreadExecutor();
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final Gson gson = new Gson();

    public void loginApi(String email, String senha, ApiResponseListener apiResponseListener){
        String url = "http://192.168.100.50:8080/usuario/login";
        UsuarioDto credentials = new UsuarioDto(email, senha);
        String json = gson.toJson(credentials);
        RequestBody body = RequestBody.Companion.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        executor.execute(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    apiResponseListener.onResponse(responseBody);
                } else {
                    apiResponseListener.onError(new IOException(response.body().string()));
                }
            } catch (IOException e) {
                apiResponseListener.onError(e);
            }
            // Important to avoid leaking resources
        });
    }
}

package com.example.fefsus.domain.usuario;

import android.util.Log;

import com.example.fefsus.utils.ApiResponseListener;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UsuarioApi {
    public UsuarioApi(){
    }
    private final OkHttpClient client = new OkHttpClient();

    private final Executor executor = Executors.newSingleThreadExecutor();

    public void loginApi(String email, String senha, ApiResponseListener apiResponseListener){
        String url = "http://localhost:8080/usuario/login";
        Log.d("emailLogin",email);
        RequestBody formBody = new FormBody.Builder()
                .add("email", email)
                .add("senha", senha)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
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
        }catch ( IOException e){
            apiResponseListener.onError(e);
        }
    }

}

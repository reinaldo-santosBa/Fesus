package com.example.fefsus.domain.legislacao;

import android.util.Log;

import com.example.fefsus.config.ApiLink;
import com.example.fefsus.utils.ApiResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LegislacaoApi {

    public LegislacaoApi() {
    }

    private final OkHttpClient client = new OkHttpClient();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final ApiLink apiLink = new ApiLink();

    public void getAsync(ApiResponseListener apiResponseListener) {
        executor.execute(() -> {
            String url = apiLink.getLink() + "legislacao/listar";
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                Log.d("Errorafew",response.toString());
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

    public void getIdAsync(Long id, ApiResponseListener apiResponseListener) {
        executor.execute(() -> {
            String url = apiLink.getLink() + "legislacao/" + id;
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

    public void create(LegislacaoModel licitacao, String authToken, ApiResponseListener apiResponseListener){
        executor.execute(() -> {
            String url = apiLink.getLink() + "legislacao/salvar";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("titulo", licitacao.getDescricao());
                jsonObject.put("numeroLegislacao", licitacao.getNumero());
                jsonObject.put("ativo", true);
            } catch (JSONException e) {
                apiResponseListener.onError(e);
            }

            RequestBody jsonBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json; charset=utf-8"));
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            builder.addFormDataPart("data", null, jsonBody);
            builder.addFormDataPart("detalhe", licitacao.getDetalhe());
            MultipartBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .post(requestBody)
                    .addHeader("Authorization", "Bearer " + authToken)
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
    public void update(LegislacaoModel licitacao, String authToken, ApiResponseListener apiResponseListener){
        executor.execute(() -> {
            String url = apiLink.getLink() + "legislacao/" + licitacao.getId();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("titulo", licitacao.getDescricao());
                jsonObject.put("numeroLegislacao", licitacao.getNumero());
                jsonObject.put("ativo", true);
            } catch (JSONException e) {
                apiResponseListener.onError(e);
            }

            RequestBody jsonBody = RequestBody.create(jsonObject.toString(), MediaType.parse("application/json; charset=utf-8"));
            MultipartBody.Builder builder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            builder.addFormDataPart("data", null, jsonBody);
            builder.addFormDataPart("detalhe", licitacao.getDetalhe());

            MultipartBody requestBody = builder.build();

            Request request = new Request.Builder()
                    .url(url)
                    .put(requestBody)
                    .addHeader("Authorization", "Bearer " + authToken)
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

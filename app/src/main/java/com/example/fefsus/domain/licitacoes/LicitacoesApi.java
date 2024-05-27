package com.example.fefsus.domain.licitacoes;

import android.util.Log;

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

    public void create(LicitacoesModel licitacao,String authToken,ApiResponseListener apiResponseListener){
        executor.execute(() -> {
            String url = "http://192.168.100.50:8080/legislacao/salvar";
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("titulo", licitacao.getDescricao());
                jsonObject.put("numeroLegislacao", licitacao.getNumero());
                jsonObject.put("ativo", true);
            } catch (JSONException e) {
                Log.d("Teste 3","Parou aqui 3");
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
                Log.d("Teste 4","Parou aqui 4");
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Log.d("Teste 5","Parou aqui 5");
                    apiResponseListener.onResponse(responseBody);
                } else {
                    Log.d("Teste 6","Parou aqui 6");
                    apiResponseListener.onError(new IOException("Unsuccessful response: " + response));
                }
            } catch (IOException e) {
                Log.d("Teste 7","Parou aqui 7");
                apiResponseListener.onError(e);
            }
        });
    }
    public void update(LicitacoesModel licitacao,String authToken,ApiResponseListener apiResponseListener){
        executor.execute(() -> {
            String url = "http://192.168.100.50:8080/legislacao/" + licitacao.getId();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("titulo", licitacao.getDescricao());
                jsonObject.put("numeroLegislacao", licitacao.getNumero());
                jsonObject.put("ativo", true);
            } catch (JSONException e) {
                Log.d("Teste 3","Parou aqui 3");
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
                Log.d("Teste 4","Parou aqui 4");
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Log.d("Teste 5","Parou aqui 5");
                    apiResponseListener.onResponse(responseBody);
                } else {
                    Log.d("Teste 6","Parou aqui 6");
                    apiResponseListener.onError(new IOException("Unsuccessful response: " + response));
                }
            } catch (IOException e) {
                Log.d("Teste 7","Parou aqui 7");
                apiResponseListener.onError(e);
            }
        });
    }
}

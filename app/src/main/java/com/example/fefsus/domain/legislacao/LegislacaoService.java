package com.example.fefsus.domain.legislacao;


import android.util.Log;

import com.example.fefsus.utils.ApiResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LegislacaoService {
    LegislacaoApi licitacoesApi = new LegislacaoApi();

    public LegislacaoService(){

    }

    public void get(LegislacoesCallback callback) {
        licitacoesApi.getAsync(new ApiResponseListener() {
            @Override
            public void onResponse(String responseBody) {
                try {
                    JSONArray jsonArray = new JSONArray(responseBody);
                    ArrayList<LegislacaoModel> arrayLicitacoes = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LegislacaoModel licitacoesModel = new LegislacaoModel();
                        licitacoesModel.setDescricao(jsonObject.getString("titulo"));
                        licitacoesModel.setDetalhe(jsonObject.getString("detalhe"));
                        licitacoesModel.setId(Long.valueOf(jsonObject.getString("id")));
                        licitacoesModel.setNumero(jsonObject.getString("numeroLegislacao"));
                        arrayLicitacoes.add(licitacoesModel);
                    }

                    callback.onLicitacoesReceived(arrayLicitacoes);
                } catch (JSONException e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
    public void getId(Long id, LegislacaoCallback callback) {
        licitacoesApi.getIdAsync(id,new ApiResponseListener() {
            @Override
            public void onResponse(String responseBody) {
                try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        LegislacaoModel licitacoesModel = new LegislacaoModel();
                        licitacoesModel.setDescricao(jsonObject.getString("titulo"));
                        licitacoesModel.setDetalhe(jsonObject.getString("detalhe"));
                        licitacoesModel.setId(Long.valueOf(jsonObject.getString("id")));
                        licitacoesModel.setNumero(jsonObject.getString("numeroLegislacao"));

                        callback.onLicitacaoReceived(licitacoesModel);
                } catch (JSONException e) {
                    callback.onError(e);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                callback.onError(throwable);
            }
        });
    }
    public void create(LegislacaoModel licitacoesModel, String token, LegislacaoEditAdd licitacoesEditAdd){

        licitacoesApi.create(licitacoesModel,token, new ApiResponseListener() {
            @Override
            public void onResponse(String responseBody) {
                Log.d("Teste 1","Parou aqui 1");
                licitacoesEditAdd.onLicitacaoEditAddReceived("Criado com sucesso");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("Teste 2","Parou aqui 2");
                licitacoesEditAdd.onError(throwable);
            }
        });
    }
    public void update(LegislacaoModel licitacoesModel, String token, LegislacaoEditAdd licitacoesEditAdd){

        licitacoesApi.update(licitacoesModel,token, new ApiResponseListener() {
            @Override
            public void onResponse(String responseBody) {
                Log.d("Teste 1","Parou aqui 1");
                licitacoesEditAdd.onLicitacaoEditAddReceived("Editado com sucesso");
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("Teste 2","Parou aqui 2");
                licitacoesEditAdd.onError(throwable);
            }
        });
    }
}

package com.example.fefsus.domain.licitacoes;


import android.util.Log;

import com.example.fefsus.utils.ApiResponseListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LicitacoesService {
    LicitacoesApi licitacoesApi = new LicitacoesApi();

    public LicitacoesService(){

    }

    public void get(LicitacoesCallback callback) {
        licitacoesApi.getAsync(new ApiResponseListener() {
            @Override
            public void onResponse(String responseBody) {
                try {
                    JSONArray jsonArray = new JSONArray(responseBody);
                    ArrayList<LicitacoesModel> arrayLicitacoes = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        LicitacoesModel licitacoesModel = new LicitacoesModel();
                        licitacoesModel.setDescricao(jsonObject.getString("descricao"));
                        licitacoesModel.setDetalhe(jsonObject.getString("detalhes"));
                        licitacoesModel.setId(Long.valueOf(jsonObject.getString("id")));
                        licitacoesModel.setNumero(jsonObject.getString("numero"));
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
    public void getId(Long id, LicitacaoCallback callback) {
        licitacoesApi.getIdAsync(id,new ApiResponseListener() {
            @Override
            public void onResponse(String responseBody) {
                try {
                        JSONObject jsonObject = new JSONObject(responseBody);
                        LicitacoesModel licitacoesModel = new LicitacoesModel();

                        licitacoesModel.setDetalhe(jsonObject.getString("detalhes"));
                        licitacoesModel.setDescricao(jsonObject.getString("descricao"));
                        licitacoesModel.setId(Long.parseLong(jsonObject.getString("id")));
                        licitacoesModel.setNumero(jsonObject.getString("numero"));

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
}

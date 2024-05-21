package com.example.fefsus.domain.usuario;

import android.util.Log;

import com.example.fefsus.utils.ApiResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioService {
    UsuarioApi usuarioApi = new UsuarioApi();
    public UsuarioService(){}
    public void login(String email, String senha,UsuarioLoginCallback usuarioLoginCallback){
        usuarioApi.loginApi(email, senha, new ApiResponseListener() {

            @Override
            public void onResponse(String responseBody) {
                try{
                    Log.d("responseBody",responseBody);
                    JSONObject jsonObject = new JSONObject(responseBody);
                    UsuarioModel usuarioModel = new UsuarioModel();
                    usuarioModel.setNome(jsonObject.getString("nome"));
                    usuarioModel.setEmail(jsonObject.getString("email"));
                    usuarioModel.setSenha("");
                    usuarioModel.setToken(jsonObject.getString("token"));
                    usuarioModel.setId(1L);
                    usuarioLoginCallback.onUsuarioReceived(usuarioModel);
                }catch (JSONException e){
                    usuarioLoginCallback.onError(e);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("onErrorrtwetwe",throwable.toString());
                usuarioLoginCallback.onError(throwable);
            }
        });
    }
}

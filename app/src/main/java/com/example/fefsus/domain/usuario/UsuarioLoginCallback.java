package com.example.fefsus.domain.usuario;


public interface UsuarioLoginCallback {
    void onUsuarioReceived(UsuarioModel usuario);
    void onError(Throwable throwable);
}

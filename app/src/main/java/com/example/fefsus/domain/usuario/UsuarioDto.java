package com.example.fefsus.domain.usuario;

public class UsuarioDto {
    String email;
    String senha;
    public UsuarioDto(String email, String senha){
        this.senha = senha;
        this.email = email;
    }
}

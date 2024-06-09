package com.example.fefsus.domain.legislacao;

public class LegislacaoModel {
    private Long id;
    private String detalhe;
    private String numero;
    private String descricao;

    public LegislacaoModel() {
    }
    public LegislacaoModel(LegislacaoModel licitacoesModel) {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}

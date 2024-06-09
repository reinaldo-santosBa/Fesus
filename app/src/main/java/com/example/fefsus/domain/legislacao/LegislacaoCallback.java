package com.example.fefsus.domain.legislacao;

public interface LegislacaoCallback {
    void onLicitacaoReceived(LegislacaoModel licitacoes);
    void onError(Throwable throwable);
}

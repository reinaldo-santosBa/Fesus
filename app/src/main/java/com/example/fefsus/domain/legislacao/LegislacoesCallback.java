package com.example.fefsus.domain.legislacao;

import java.util.ArrayList;

public interface LegislacoesCallback {
    void onLicitacoesReceived(ArrayList<LegislacaoModel> licitacoes);
    void onError(Throwable throwable);
}

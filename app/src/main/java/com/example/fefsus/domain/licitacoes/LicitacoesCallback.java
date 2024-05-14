package com.example.fefsus.domain.licitacoes;

import java.util.ArrayList;

public interface LicitacoesCallback {
    void onLicitacoesReceived(ArrayList<LicitacoesModel> licitacoes);
    void onError(Throwable throwable);
}

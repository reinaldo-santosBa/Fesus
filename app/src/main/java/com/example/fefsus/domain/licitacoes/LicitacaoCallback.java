package com.example.fefsus.domain.licitacoes;

import java.util.ArrayList;

public interface LicitacaoCallback {
    void onLicitacaoReceived(LicitacoesModel licitacoes);
    void onError(Throwable throwable);
}

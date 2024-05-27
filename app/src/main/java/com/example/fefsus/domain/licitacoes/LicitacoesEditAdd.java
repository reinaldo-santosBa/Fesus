package com.example.fefsus.domain.licitacoes;

public interface LicitacoesEditAdd {
    void onLicitacaoEditAddReceived(String returnMsg);
    void onError(Throwable throwable);
}

package com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio;

import android.support.annotation.NonNull;

public enum TipoDeStatusTransacao {
    CONSOLIDADO("Consolidado"),
    NAO_CONSOLIDADO("Não consolidado");

    private final String descricao;

    TipoDeStatusTransacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @NonNull
    @Override
    public String toString() {
        return this.descricao;
    }
}

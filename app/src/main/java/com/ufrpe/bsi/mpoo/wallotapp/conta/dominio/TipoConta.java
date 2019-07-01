package com.ufrpe.bsi.mpoo.wallotapp.conta.dominio;

import android.support.annotation.NonNull;

public enum TipoConta {
    DINHEIRO("Dinheiro"),
    CREDITO("Cartão de crédito");

    private final String descricao;

    TipoConta(String descricao) {
        this.descricao = descricao;
    }

    @NonNull
    @Override
    public String toString() {
        return this.descricao;
    }


}

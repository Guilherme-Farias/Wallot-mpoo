package com.ufrpe.bsi.mpoo.wallotapp.conta.dominio;

import android.support.annotation.NonNull;

public enum TipoEstadoConta {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String descricao;

    TipoEstadoConta(String descricao) {
        this.descricao = descricao;
    }

    @NonNull
    @Override
    public String toString() {
        return this.descricao;
    }

    }

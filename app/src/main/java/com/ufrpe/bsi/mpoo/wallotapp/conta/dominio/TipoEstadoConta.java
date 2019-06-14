package com.ufrpe.bsi.mpoo.wallotapp.conta.dominio;

public enum TipoEstadoConta {
    ATIVO("Ativo"),
    INATIVO("Inativo");

    private final String descricao;

    TipoEstadoConta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    }

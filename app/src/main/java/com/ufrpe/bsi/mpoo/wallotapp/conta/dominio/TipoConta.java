package com.ufrpe.bsi.mpoo.wallotapp.conta.dominio;

public enum TipoConta {
    DINHEIRO("Dinheiro");

    private final String descricao;

    TipoConta(String descricao) {
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

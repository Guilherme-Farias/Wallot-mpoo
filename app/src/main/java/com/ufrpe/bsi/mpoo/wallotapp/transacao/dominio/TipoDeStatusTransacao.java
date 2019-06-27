package com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio;

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

    @Override
    public String toString() {
        return this.descricao;
    }
}

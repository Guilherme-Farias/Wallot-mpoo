package com.ufrpe.wallot_app.pagamento.dominio;

public enum TipoTransacao {
    RECEITA("Receita"),
    DESPESA("Despesa"),
    TRANSFERENCIA("Transferência");

    private final String descricao;

    TipoTransacao(String descricao) {
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

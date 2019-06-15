package com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio;

import java.math.BigDecimal;

public enum TipoTransacao {
    RECEITA("Receita", 1),
    DESPESA("Despesa", -1),
    TRANSFERENCIA("Transferencia", -1);

    private final String descricao;
    private final int multiplicador;

    TipoTransacao(String descricao, int multiplicador){
        this.descricao = descricao;
        this.multiplicador = multiplicador;
    }



    @Override
    public String toString() {
        return this.descricao;
    }

    public int getMultiplicador() {
        return multiplicador;
    }

}

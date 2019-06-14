package com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio;

import java.math.BigDecimal;

public enum TipoTransacao {
    RECEITA("Receita", new BigDecimal("1")),
    DESPESA("Despesa", new BigDecimal("-1")),
    TRANSFERENCIA("Transferencia", new BigDecimal("-1"));

    private final String descricao;
    private final BigDecimal multiplicador;

    TipoTransacao(String descricao, BigDecimal multiplicador){
        this.descricao = descricao;
        this.multiplicador = multiplicador;
    }

    @Override
    public String toString() {
        return this.descricao;
    }

    public BigDecimal getMultiplicador(){
        return this.multiplicador;
    }
}

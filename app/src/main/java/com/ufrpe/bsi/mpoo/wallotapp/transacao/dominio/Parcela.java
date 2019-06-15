package com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio;

import java.math.BigDecimal;
import java.util.Date;

public class Parcela {
    private long id;
    private BigDecimal valorParcela;
    private long numeroParcela;
    private Date dataTransacao;
    private long fkTransacao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getValorParcela() {
        return valorParcela;
    }

    public void setValorParcela(BigDecimal valorParcela) {
        this.valorParcela = valorParcela;
    }

    public long getNumeroParcela() {
        return numeroParcela;
    }

    public void setNumeroParcela(long numeroParcela) {
        this.numeroParcela = numeroParcela;
    }

    public Date getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(Date dataTransacao) {
        this.dataTransacao = dataTransacao;
    }

    public long getFkTransacao() {
        return fkTransacao;
    }

    public void setFkTransacao(long fkTransacao) {
        this.fkTransacao = fkTransacao;
    }
}

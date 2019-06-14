package com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio;

import java.math.BigDecimal;

public class Transacao {
    private long id;
    private String titulo;
    private BigDecimal valor;
    private TipoTransacao tipoTransacao;
    private long qntParcelas;
    private long fkCategoria;
    private long fkSubCategoria;
    private long fkUsuario;
    private long fkConta;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public TipoTransacao getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(TipoTransacao tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public long getQntParcelas() {
        return qntParcelas;
    }

    public void setQntParcelas(long qntParcelas) {
        this.qntParcelas = qntParcelas;
    }

    public long getFkCategoria() {
        return fkCategoria;
    }

    public void setFkCategoria(long fkCategoria) {
        this.fkCategoria = fkCategoria;
    }

    public long getFkSubCategoria() {
        return fkSubCategoria;
    }

    public void setFkSubCategoria(long fkSubCategoria) {
        this.fkSubCategoria = fkSubCategoria;
    }

    public long getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(long fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public long getFkConta() {
        return fkConta;
    }

    public void setFkConta(long fkConta) {
        this.fkConta = fkConta;
    }
}

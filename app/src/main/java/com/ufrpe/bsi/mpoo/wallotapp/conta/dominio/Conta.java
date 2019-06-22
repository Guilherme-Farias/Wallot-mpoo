package com.ufrpe.bsi.mpoo.wallotapp.conta.dominio;

import java.math.BigDecimal;

public class Conta {
    private long id;
    private String nome;
    private BigDecimal saldo;
    private TipoConta tipoConta;
    private TipoEstadoConta tipoEstadoConta;
    private long fkUsuario;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public TipoEstadoConta getTipoEstadoConta() {
        return tipoEstadoConta;
    }

    public void setTipoEstadoConta(TipoEstadoConta tipoEstadoConta) {
        this.tipoEstadoConta = tipoEstadoConta;
    }

    public long getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(long fkUsuario) {
        this.fkUsuario = fkUsuario;
    }


    public void addSaldo(BigDecimal valor){
        this.saldo = this.saldo.add(valor);
    }

    @Override
    public String toString() {
        return this.nome;
    }
}

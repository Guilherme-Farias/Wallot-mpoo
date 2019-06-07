package com.ufrpe.wallot_app.conta.dominio;

import com.ufrpe.wallot_app.pagamento.dominio.Pagamento;

import java.math.BigDecimal;
import java.util.List;

public class Conta {
    private long id;
    private String nome;
    private BigDecimal saldo;
    private List<Pagamento> pagamentos;
    private long tbUsuario;

    public Conta(String nome, String saldo, long id){
        this.tbUsuario = id;
        this.nome = nome;
        this.saldo = new BigDecimal(saldo);
    }
    public Conta(){}

    public Conta(String nome, String saldo){
        this.nome = nome;
        this.saldo = new BigDecimal(saldo);
    }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public BigDecimal getSaldo() {return saldo;}
    public void setSaldo(BigDecimal saldo) {this.saldo = saldo;}

    public void addReceita(BigDecimal receita){this.saldo.add(receita);}
    public void addDespesa(BigDecimal despesa){this.saldo.subtract(despesa);}

    public String getNome() {return nome;}
    public void setNome(String descricao) {this.nome = descricao;}

    public List<Pagamento> getPagamentos() {return pagamentos;}
    public void setPagamentos(List<Pagamento> pagamentos) {this.pagamentos = pagamentos;}

    public long getTbUsuario() {return tbUsuario;}
    public void setTbUsuario(long tbUsuario) {this.tbUsuario = tbUsuario;}

    @Override
    public String toString() {
        return this.nome;
    }
}

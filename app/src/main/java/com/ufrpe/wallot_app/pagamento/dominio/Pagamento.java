package com.ufrpe.wallot_app.pagamento.dominio;

import java.math.BigDecimal;
import java.util.Date;

public class Pagamento {
    private long id;
    private String descricao;
    private BigDecimal valor;
    private Date dataPagamento;
    private long tbConta;
    private long tbUsuario;
    private long tbTipoPagamento;
    private String categoria;
    private String subCategoria;

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getDescricao() {return descricao;}
    public void setDescricao(String descrcição) {
        descricao = descrcição;}

    public BigDecimal getValor() {return valor;}
    public void setValor(BigDecimal valor) {this.valor = valor;}

    public Date getDataPagamento() {return dataPagamento;}
    public void setDataPagamento(Date dataPagamento) {this.dataPagamento = dataPagamento;}

    public long getTbConta() {return tbConta;}
    public void setTbConta(long tbConta) {this.tbConta = tbConta;}

    public long getTbUsuario() {return tbUsuario;}
    public void setTbUsuario(long tbUsuario) {this.tbUsuario = tbUsuario;}

    public String getCategoria() {return categoria;}
    public void setCategoria(String categoria) {this.categoria = categoria;}

    public String getSubCategoria() {return subCategoria;}
    public void setSubCategoria(String subCategoria) {this.subCategoria = subCategoria;}

    public long getTbTipoPagamento() {return tbTipoPagamento;}
    public void setTbTipoPagamento(long tbTipoPagamento) {this.tbTipoPagamento = tbTipoPagamento;}
}

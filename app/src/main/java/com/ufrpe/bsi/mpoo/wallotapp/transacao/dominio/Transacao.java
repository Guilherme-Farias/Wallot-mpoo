package com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.conta.dominio.Conta;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;

public class Transacao {
    private long id;
    private String titulo;
    private BigDecimal valor;
    private TipoTransacao tipoTransacao;
    private long qntParcelas;
    private Categoria categoria;
    private SubCategoria subCategoria;
    private Usuario usuario;
    private Conta conta;

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

    public void setTipoTransacao(TipoTransacao tipoTransacao) {this.tipoTransacao = tipoTransacao;}

    public long getQntParcelas() {
        return qntParcelas;
    }

    public void setQntParcelas(long qntParcelas) {
        this.qntParcelas = qntParcelas;
    }

    public Categoria getCategoria(){
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public SubCategoria getSubCategoria(){
        return subCategoria;
    }

    public void setSubCategoria(SubCategoria subCategoria) {
        this.subCategoria = subCategoria;
    }

    public Conta getConta(){
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Usuario getUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}

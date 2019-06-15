package com.ufrpe.bsi.mpoo.wallotapp.transacao.dominio;

import com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio.Categoria;
import com.ufrpe.bsi.mpoo.wallotapp.categoria.persistencia.CategoriaDAO;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio.SubCategoria;
import com.ufrpe.bsi.mpoo.wallotapp.subcategoria.persistencia.SubCategoriaDAO;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Date data;

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


    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    CategoriaDAO categoriaDAO = new CategoriaDAO();

    public String getCategoria(long idCategoria){
        String categoria = categoriaDAO.getCategoria(idCategoria);
        return categoria;
    }


    SubCategoriaDAO subCategoriaDAO = new SubCategoriaDAO();
    public String getSubCategoria(long idSubCategoria){
        String subCategoria = subCategoriaDAO.getSubCategoria(idSubCategoria);
        return subCategoria;
    }

    public String dataFormatada(Date data){
        String strData = new SimpleDateFormat("dd/MM/yyyy").format(this.data);
        return strData;
    }



    @Override
    public String toString() {
        return this.titulo + "\n" +
                this.valor + "\n" +
                getCategoria(this.fkCategoria) + "\n" +
                getSubCategoria(this.fkSubCategoria) + "\n" +
                dataFormatada(this.data) + "\n" +
                this.tipoTransacao;
    }


}

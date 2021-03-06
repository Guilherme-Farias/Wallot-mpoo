package com.ufrpe.bsi.mpoo.wallotapp.orcamento.dominio;

import com.ufrpe.bsi.mpoo.wallotapp.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Orcamento {
    private long id;
    private String titulo = "Orçamento";
    private BigDecimal gastoEstimado;
    private Date dataInicial;
    private Date dataFinal;
    private Usuario usuario;

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

    public BigDecimal getGastoEstimado() {
        return gastoEstimado;
    }

    public void setGastoEstimado(BigDecimal gastoEstimado) {
        this.gastoEstimado = gastoEstimado;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public String getDataInicialFormatada() {
        return new SimpleDateFormat("yyyyMMdd").format(this.dataInicial);
    }

    public String getDataFinalFormatada() {
        return new SimpleDateFormat("yyyyMMdd").format(this.dataFinal);
    }

    public String[] getDatesFormatada() {
        return new String[]{new SimpleDateFormat("dd/MM/yyyy").format(this.dataInicial), new SimpleDateFormat("dd/MM/yyyy").format(this.dataFinal)};
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}


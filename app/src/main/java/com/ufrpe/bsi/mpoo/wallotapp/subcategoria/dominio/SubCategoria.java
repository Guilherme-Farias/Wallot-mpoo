package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio;

public class SubCategoria {
    private long id;
    private String nome;
    /*private byte[] icone;*/
    private long fkCategoria;
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

    /*public byte[] getIcone() {
        return icone;
    }

    public void setIcone(byte[] icone) {
        this.icone = icone;
    }*/

    public long getFkCategoria() {return fkCategoria;}

    public void setFkCategoria(long fkCategoria) {this.fkCategoria = fkCategoria;}

    public long getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(long fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    @Override
    public String toString() {
        return this.nome;
    }
}

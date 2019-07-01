package com.ufrpe.bsi.mpoo.wallotapp.subcategoria.dominio;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class SubCategoria {
    private long id;
    private String nome;
    private byte[] icone;
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

    public byte[] getIcone() {
        return icone;
    }

    public void setIcone(byte[] icone) {
        this.icone = icone;
    }

    public long getFkCategoria() {return fkCategoria;}

    public void setFkCategoria(long fkCategoria) {this.fkCategoria = fkCategoria;}

    public long getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(long fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    //pega o byte e transforma em drawable
    public Drawable byteArrayToDrawable(byte[] byteArray) {return new BitmapDrawable(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));}

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }
}

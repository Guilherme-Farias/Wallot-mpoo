package com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

public class Categoria {
    private long id;
    private String nome;
    private byte[] icone;
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

    public long getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(long fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    @NonNull
    @Override
    public String toString() {
        return this.nome;
    }


    //retorna o icone como drawable
    public Drawable byteArrayToDrawable(byte[] byteArray) {
        return new BitmapDrawable(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
    }
}

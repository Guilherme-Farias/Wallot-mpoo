package com.ufrpe.bsi.mpoo.wallotapp.categoria.dominio;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

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

    public Drawable byteArrayToDrawable(byte[] byteArray) {
        Drawable drawable = new BitmapDrawable(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length));
        return drawable;
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

    @Override
    public String toString() {
        return this.nome;
    }
}
